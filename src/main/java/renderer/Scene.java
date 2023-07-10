package renderer;

import core.Input;
import org.joml.Vector3f;

import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.*;

public class Scene {
	private Camera camera;
	private Cube cube;
	private ArrayList<Cube> cubes = new ArrayList<>();

	public Scene(){
		Renderer.enableDepthTest();
		Renderer.enableBlend();

		camera = new Camera(30.0f, 16.0f / 9.0f, 0.1f, 1000.0f);
		cube = new Cube(new Vector3f(-1.0f, 2.f, 0.0f), "assets/textures/checkerboard.jpg");

		float mapSizeX = 10;
		for(float x = -mapSizeX; x < mapSizeX; x++)
			for(float z = -mapSizeX; z < mapSizeX; z++)
				cubes.add(new Cube(new Vector3f(x, 0.0f, z / 2.0f), "assets/textures/block.png"));
	}
	
	public void update(float deltaTime) {
		camera.onUpdate(deltaTime);
		cube.render(camera.getViewProjection());

		for(Cube c : cubes)
			c.render(camera.getViewProjection());

		Input.endFrame();
	}
	
	public void resize(float width, float height) {
		camera.resize(width, height);
	}
	
	public void shutdown() {
		
	}
}
