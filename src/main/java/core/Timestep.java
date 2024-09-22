package core;

import org.lwjgl.glfw.GLFW;
import java.text.DecimalFormat;

public class Timestep {
	private static float currentTime = 0.0f;
    public static float deltaTime = 0.0f;
    public static float second = 0.0f;
    public static float millisecond = 0.0f;

    public static void update() {
    	float time = second = (float)GLFW.glfwGetTime();
    	millisecond = second * 1000.0f;

        deltaTime  = time - currentTime;
        currentTime = time;
    }
    
    public static String secondToString() {
    	DecimalFormat decimalFormat = new DecimalFormat("#.#######");
        return decimalFormat.format(second);
    }
    
    public static String millisecondToString() {
    	DecimalFormat decimalFormat = new DecimalFormat("#.#######");
        return decimalFormat.format(millisecond);
    }
    
    public static String deltaTimeToString() {
        DecimalFormat decimalFormat = new DecimalFormat("#.#######");
        return decimalFormat.format(deltaTime);
    }
}
