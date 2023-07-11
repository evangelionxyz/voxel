package core;

import org.joml.Vector2f;

import static org.lwjgl.glfw.GLFW.*;

public class Input {
    private static Input instance;
    private boolean[] keyPressed = new boolean[350];

    private double scrollX, scrollY;
    private double xPos, yPos, lastY, lastX;
    private Vector2f cursorPos = new Vector2f();
    private boolean[] buttonPressed = new boolean[4];

    private Input() {
        scrollX = scrollY = 0.0;
        xPos = yPos = 0.0f;
        lastX = lastY = 0.0;
    }

    public static void scrollCallback(long window, double xOffset, double yOffset){
        get().scrollX = xOffset;
        get().scrollY = yOffset;
    }

    public static void mouseButtonCallback(long window, int button, int action, int mods){
        get().buttonPressed[button] = (action == GLFW_PRESS);
    }

    public static void cursorPosCallback(long window, double xPos, double yPos){
        get().lastX = get().xPos;
        get().lastY = get().yPos;
        get().xPos = xPos;
        get().yPos = yPos;

        get().cursorPos = new Vector2f((float)xPos, (float)yPos);
    }

    public static void keyCallback(long window, int key, int scancode, int action, int mods){
        get().keyPressed[key] = (action == GLFW_PRESS || action == GLFW_REPEAT);
    }
    public static Input get(){
        if(instance == null)
            instance = new Input();

        return instance;
    }
    public static boolean isKeyPressed(int key){
        return get().keyPressed[key];
    }

    public static boolean isMouseButtonPressed(int button){
        return get().buttonPressed[button];
    }

    public static double getMouseSrollX(){
        return get().scrollX;
    }

    public static double getMouseSrollY(){
        return get().scrollY;
    }

    public static float getMouseX(){
        return get().cursorPos.x;
    }

    public static float getMouseY(){
        return get().cursorPos.y;
    }

    public static void endFrame(){
        get().scrollX = 0;
        get().scrollY = 0;
        get().lastX = get().xPos;
        get().lastY = get().yPos;
    }
}
