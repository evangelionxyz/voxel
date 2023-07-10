package core;
import renderer.*;
import math.*;

public class Application {
	private Window window;
	private Scene mainScene;

	public Application(){
		window = new Window("Voxel", true);
		window.init();
		mainScene = new Scene();
	}

	public void Run(){
		while(window.isRunning()) {
			Timestep.update();

			Renderer.clearColor(new Vector4(0.1f, 0.3f, 0.3f, 1.0f));
			Renderer.clear();

			// Render Here
			mainScene.update(Timestep.deltaTime);
			window.update();
			mainScene.resize(window.getWidth(), window.getHeight());
		}
		shutdown();
	}

	private void shutdown(){
		mainScene.shutdown();
		window.destroy();
	}

}
