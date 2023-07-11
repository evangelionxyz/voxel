package core;
import renderer.*;
import math.*;

public class Application {
	private Window window;
	private Scene scene;

	public Application(){
		window = new Window("Voxel", true);
		window.init();
		scene = new Scene();
	}

	public void Run(){
		while(window.isRunning()) {
			Timestep.update();

			Renderer.clearColor(new Vector4(0.9f, 0.9f, 0.9f, 1.0f));
			Renderer.clear();

			// Render Here
			scene.update(Timestep.deltaTime);
			window.update();

			scene.resize(window.getWidth(), window.getHeight());
		}

		shutdown();
	}

	private void shutdown(){
		scene.shutdown();
		window.destroy();
	}

}
