package renderer;
import math.*;
import static org.lwjgl.opengl.GL11.*;

public class Renderer {

	public static void enableDepthTest(){
		glEnable(GL_DEPTH_TEST);
	}

	public static void enableBlend(){
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	}

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
