package core;

import java.nio.*;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.glfw.GLFW.*;

public class Window {
	
	private long mWindow;
	private String mTitle;
	private int mWidth = 860, mHeight = 480;
	private boolean mVSync;
	
	public Window(String title, boolean vSync) {
		mTitle = title;
		mVSync = vSync;
	}
	
	public void init() {
		glfwInit();
		
		mWindow = glfwCreateWindow(mWidth, mHeight, mTitle, 0, 0);
		
		try(MemoryStack stack = stackPush()){
			IntBuffer pWidth = stack.mallocInt(1);
			IntBuffer pHeight = stack.mallocInt(1);
			
			glfwGetWindowSize(mWindow, pWidth, pHeight);
			GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
			
			glfwSetWindowPos(mWindow,
					(vidMode.width() - pWidth.get(0))/2, 
					(vidMode.height() - pHeight.get(0))/2);
			
		}
		
		// Window Size call back
		glfwSetWindowSizeCallback(mWindow, new GLFWWindowSizeCallback(){
			@Override
			public void invoke(long window, int width, int height) {
				Window.this.mWidth = width;
				Window.this.mHeight = height;
			}
		});

		glfwSetKeyCallback(mWindow, Input::keyCallback);
		
		glfwMakeContextCurrent(mWindow);
		ApplyVSync();
		
		GL.createCapabilities();
	}
	
	public void update() {
		ApplyVSync();
		glfwPollEvents();
		glfwSwapBuffers(mWindow);		
	}
	
	private void ApplyVSync() {
		if(mVSync)
			glfwSwapInterval(1);
		else
			glfwSwapInterval(0);
	}
	
	public float getWidth() {
		return mWidth;
	}
	
	public float getHeight() {
		return mHeight;
	}
	
	public void SetVSync(boolean enable) {
		mVSync = enable;
	}
	
	public boolean isRunning() {
		return glfwWindowShouldClose(mWindow) == false;
	}
	
	public void destroy() {
		glfwDestroyWindow(mWindow);
		glfwTerminate();
	}
	
	public String windowSizeToString() {
		return new String("width "+mWidth+" Height "+mHeight);
	}
}
