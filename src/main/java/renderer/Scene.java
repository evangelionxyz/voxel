package renderer;

import core.Input;
import org.joml.*;

import java.util.ArrayList;

public class Scene {
	private Camera camera = new Camera(30.0f, 16.0f / 9.0f, 0.1f, 1000.0f);
	private Chunk chunkA, chunkB;
	private Shader shader;

	private FastNoiseLite noise;
	private ArrayList<Float> noises = new ArrayList<>();

	public Scene(){
		Renderer.enableDepthTest();
		Renderer.enableBlend();

		noise = new FastNoiseLite(1312);
		noise.SetNoiseType(FastNoiseLite.NoiseType.OpenSimplex2S);
		for(int i = 0; i < 16 * 16; i++)
		{
			noise.SetFrequency(((float)i%16.0f) * 0.01f);
			noises.add(noise.GetNoise(16.0f, 16));
		}

		camera.setPosition(new Vector3f(23.0f, 18.0f, 23.0f));
		chunkA = new Chunk(new Vector3f(0.0f), noises);
		chunkB = new Chunk(new Vector3f(1.0f, 0.0f, 0.0f), noises);
		shader = new Shader("assets/shaders/default.glsl");
	}
	
	public void update(float deltaTime) {
		Renderer.clear();
		Renderer.clearColor(new Vector4f(0.1f, 0.1f, 0.1f, 1.0f));

		camera.onUpdate(deltaTime);

		chunkA.draw(shader, camera.getViewProjection());
		chunkB.draw(shader, camera.getViewProjection());

		Input.endFrame();
	}
	
	public void resize(float width, float height) {
		camera.resize(width, height);
	}
	
	public void shutdown() {
		
	}
}
