package renderer;
import org.joml.*;

public class Scene {
	private Camera camera;
	private Cube cube;

	public Scene(){
		camera = new Camera(35.0f, 16.0f / 9.0f, 0.1f, 1000.0f);
		cube = new Cube();
	}
	
	public void update(float deltaTime) {
		camera.onUpdate(deltaTime);
		cube.render(camera.getViewProjection());
	}
	
	public void resize(float width, float height) {
		camera.resize(width, height);
	}
	
	public void shutdown() {
		
	}
}
