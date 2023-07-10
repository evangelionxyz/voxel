package core;

import static org.lwjgl.glfw.GLFW.*;

public class Input {
    private static Input instance;
    private boolean[] keyPressed = new boolean[350];

    private Input() {}

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
}
