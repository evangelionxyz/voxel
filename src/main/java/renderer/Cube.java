package renderer;
import math.Vector4;
import org.joml.*;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;
import java.util.ArrayList;

public class Cube {
	private Shader Shader;
	private Texture2D Texture;
	private Vector4f TintColor;
	private VertexArray vertexArray;
	private Vector3f Position;
	private Vector3f Size = new Vector3f(1.0f, 1.0f, 1.0f);
	private Vector3f Rotation = new Vector3f();
	private Matrix4f Transform = new Matrix4f().identity();

	public Cube(Vector3f position){
		Position = position;
		TintColor = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);

		Texture = new Texture2D();
		int whiteTextureData = 0xffffffff;
		ByteBuffer buffer = MemoryUtil.memAlloc(Integer.BYTES);
		buffer.putInt(whiteTextureData);
		buffer.flip();
		Texture.setData(buffer, Integer.BYTES);
		MemoryUtil.memFree(buffer);

		init();
	}

	public Cube(Vector3f position, Vector4f color) {
		Position = position;
		TintColor = color;
		Texture = new Texture2D();
		int whiteTextureData = 0xffffffff;
		ByteBuffer buffer = MemoryUtil.memAlloc(Integer.BYTES);
		buffer.putInt(whiteTextureData);
		buffer.flip();
		Texture.setData(buffer, Integer.BYTES);
		MemoryUtil.memFree(buffer);
		init();
	}

	public Cube(Vector3f position, String texturePath) {
		Position = position;
		Texture = new Texture2D(texturePath);
		TintColor = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);
		init();
	}

	public Cube(Vector3f position, Texture2D texture) {
		Position = position;
		Texture = texture;
		TintColor = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);
		init();
	}

	private void init(){
		float[] vertices = new float[] {
			// position             // color (RGB)       // tex coord
			-0.5f, -0.5f, 0.5f,     1.0f, 1.0f, 1.0f,   0.0f, 0.0f,  // Front bottom left
			0.5f, -0.5f, 0.5f,     1.0f, 0.0f, 1.0f,   1.0f, 0.0f,  // Front bottom right
			-0.5f,  0.5f, 0.5f,      1.0f, 1.0f, 1.0f,   0.0f, 1.0f,  // Front top left
			0.5f,  0.5f, 0.5f,      1.0f, 1.0f, 0.0f,   1.0f, 1.0f,  // Front top right

			-0.5f, -0.5f, -0.5f,    1.0f, 1.0f, 1.0f,   1.0f, 0.0f,  // Back bottom left
			0.5f, -0.5f, -0.5f,    1.0f, 0.0f, 1.0f,   0.0f, 0.0f,  // Back bottom right
			-0.5f,  0.5f, -0.5f,     1.0f, 1.0f, 1.0f,   1.0f, 1.0f,  // Back top left
			0.5f,  0.5f, -0.5f,     1.0f, 1.0f, 0.0f,   0.0f, 1.0f,  // Back top right

			-0.5f, -0.5f, -0.5f,    1.0f, 1.0f, 1.0f,   0.0f, 0.0f,  // Left bottom front
			-0.5f,  0.5f, -0.5f,    1.0f, 1.0f, 1.0f,   0.0f, 1.0f,  // Left top front
			-0.5f, -0.5f,  0.5f,    1.0f, 0.0f, 1.0f,   1.0f, 0.0f,  // Left bottom back
			-0.5f,  0.5f,  0.5f,    1.0f, 1.0f, 0.0f,   1.0f, 1.0f,  // Left top back

			0.5f, -0.5f, -0.5f,    1.0f, 1.0f, 1.0f,   1.0f, 0.0f,  // Right bottom front
			0.5f,  0.5f, -0.5f,    1.0f, 1.0f, 1.0f,   1.0f, 1.0f,  // Right top front
			0.5f, -0.5f, 0.5f,     1.0f, 0.0f, 1.0f,   0.0f, 0.0f,  // Right bottom back
			0.5f,  0.5f, 0.5f,     1.0f, 1.0f, 0.0f,   0.0f, 1.0f,  // Right top back

			-0.5f, -0.5f, -0.5f,    1.0f, 1.0f, 1.0f,   0.0f, 1.0f,  // Bottom left front
			0.5f, -0.5f, -0.5f,    1.0f, 0.0f, 1.0f,   1.0f, 1.0f,  // Bottom right front
			-0.5f, -0.5f, 0.5f,     1.0f, 1.0f, 0.0f,   0.0f, 0.0f,  // Bottom left back
			0.5f, -0.5f, 0.5f,     1.0f, 1.0f, 1.0f,   1.0f, 0.0f,  // Bottom right back

			-0.5f, 0.5f, -0.5f,     1.0f, 1.0f, 1.0f,   0.0f, 1.0f,  // Top left front
			0.5f, 0.5f, -0.5f,     1.0f, 0.0f, 1.0f,   1.0f, 1.0f,  // Top right front
			-0.5f, 0.5f,  0.5f,     1.0f, 1.0f, 0.0f,   0.0f, 0.0f,  // Top left back
			0.5f, 0.5f,  0.5f,     1.0f, 1.0f, 1.0f,   1.0f, 0.0f   // Top right back
		};

		int[] indices = new int[] {
			// Front face
			0, 1, 2,
			2, 1, 3,

			// Back face
			4, 5, 6,
			6, 5, 7,

			// Left face
			8, 9, 10,
			10, 9, 11,

			// Right face
			12, 13, 14,
			14, 13, 15,

			// Bottom face
			16, 17, 18,
			18, 17, 19,

			// Top face
			20, 21, 22,
			22, 21, 23
		};

		ArrayList<BufferElement> elements = new ArrayList<>();
		elements.add(new BufferElement(ShaderDataType.Float3,  "aPos", false));
		elements.add(new BufferElement(ShaderDataType.Float3,  "aColor", false));
		elements.add(new BufferElement(ShaderDataType.Float2,  "aTexCoord", false));
		BufferLayout layout = new BufferLayout(elements);

		vertexArray = new VertexArray();

		VertexBuffer vertexBuffer = new VertexBuffer(vertices);
		vertexBuffer.setLayout(layout);

		vertexArray.addVertexBuffer(vertexBuffer);

		IndexBuffer ibo = new IndexBuffer();
		ibo.addIndices(indices);
		vertexArray.addIndexBuffer(ibo);

		Shader = new Shader("assets/shaders/default.glsl");
		Shader.bind();
	}

	public void render(Matrix4f viewProjection) {
		Texture.bind();

		Shader.bind();
		Shader.setMatrix("uTransform", Transform);
		Shader.setMatrix("uViewProjection", viewProjection);
		Shader.setVector("uTintColor", TintColor);
		Shader.setInt("uTexture", Texture.getID());

		Renderer.drawElements(vertexArray, vertexArray.getIndexBuffer().getCount());

		Shader.unbind();

		Texture.unbind();

		updateTransform();
	}
	public void setPosition(Vector3f position){
		Position.set(position);
	}
	public void setSize(Vector3f size){
		Size.set(size);
	}

	public void setRotation(Vector3f rotation){
		Rotation.set(rotation);
	}

	private void updateTransform(){
		Transform.setTranslation(Position.x, Position.y, Position.z)
				.setRotationXYZ(Rotation.x, Rotation.y, Rotation.z)
				.scale(Size.x, Size.y, Size.z);
	}

	private void setColor(Vector4f color){
		TintColor = color;
	}
}
