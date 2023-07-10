package renderer;
import math.*;
import static org.lwjgl.opengl.GL11.*;

public class Renderer {
	
	public static void clear() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}
	
	public static void clearColor(Vector4 color) {
		glClearColor(color.x, color.y, color.z, color.w);		
	}
	
	public static void setViewport(int width, int height) {
		glViewport(0, 0, width, height);
	}

	public static void drawElements(VertexArray vertexArray, int count){
		glDrawElements(GL_TRIANGLES, count, GL_UNSIGNED_INT, 0);
	}
}
