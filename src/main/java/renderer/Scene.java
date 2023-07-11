package renderer;

import core.Input;
import org.joml.*;

import java.util.ArrayList;

public class Scene {
	private Camera camera = new Camera(30.0f, 16.0f / 9.0f, 0.1f, 1000.0f);
	private Texture2D blockTexture;
	private ArrayList<Cube>  cubes;

	public Scene(){
		Renderer.enableDepthTest();
		Renderer.enableBlend();

		camera.setPosition(new Vector3f(0.0f, 2.0f, 10.0f));

		cubes = new ArrayList<>();
		blockTexture = new Texture2D("assets/textures/block.png");

		float mapSize = 10.0f;
		float mapHeight = 2.0f;
		for(float x = -mapSize; x < mapSize; x++){
			for(float z = -mapSize; z < mapSize; z++) {
				for(float y = 0; y < mapHeight; y++){
					if(y % 3 == 0)
					{
						cubes.add(new Cube(new Vector3f(x, y, z), blockTexture));
					}
					else
					{
						Vector4f color = new Vector4f((x / mapSize),0.0f, (z / mapSize), 1.0f);
						//color.sub(1.0f, 0.0f, 1.0f, 1.0f);
						cubes.add(new Cube(new Vector3f(x, y, z), color));
					}
				}
			}
		}
	}
	
	public void update(float deltaTime) {
		camera.onUpdate(deltaTime);

		for(Cube cube : cubes){
			cube.render(camera.getViewProjection());
		}

		Input.endFrame();
	}
	
	public void resize(float width, float height) {
		camera.resize(width, height);
	}
	
	public void shutdown() {
		
	}
}
