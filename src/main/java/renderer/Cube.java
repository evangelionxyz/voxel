package renderer;
import org.joml.*;
import java.util.ArrayList;

public class Cube {
	private Shader shader;
	private Texture2D texture;
	private VertexArray vertexArray;
	private VertexBuffer vertexBuffer;

	private Vector3f position;
	private Vector3f size = new Vector3f(1.0f, 1.0f, 1.0f);
	private Vector3f rotation = new Vector3f();
	private Matrix4f transform = new Matrix4f().identity();

	public Cube(Vector3f position, String texturePath) {

		this.position = position;

		texture = new Texture2D(texturePath);
		texture.bind();

		float[] vertices = new float[]
		{
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

		int[] indices = new int[]
		{
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

		ArrayList<BufferElement> elements = new ArrayList<BufferElement>();
		elements.add(new BufferElement(ShaderDataType.Float3,  "aPos", false));
		elements.add(new BufferElement(ShaderDataType.Float3,  "aColor", false));
		elements.add(new BufferElement(ShaderDataType.Float2,  "aTexCoord", false));

		BufferLayout layout = new BufferLayout(elements);

		vertexArray = new VertexArray();

		vertexBuffer = new VertexBuffer(vertices);
		vertexBuffer.setLayout(layout);

		vertexArray.addVertexBuffer(vertexBuffer);

		IndexBuffer ibo = new IndexBuffer();
		ibo.addIndices(indices);
		vertexArray.addIndexBuffer(ibo);

		shader = new Shader("assets/shaders/default.glsl");
		shader.bind();
	}

	public void render(Matrix4f viewProjection) {
		shader.bind();
		texture.bind();

		shader.setMatrix("uTransform", transform);
		shader.setMatrix("uViewProjection", viewProjection);
		shader.setInt("uTexture", texture.getID());

		Renderer.drawElements(vertexArray, vertexArray.getIndexBuffer().getCount());

		texture.bind();
		shader.unbind();

		updateTransform();
	}
	public void setPosition(Vector3f position){
		this.position.set(position);
	}
	public void setSize(Vector3f size){
		this.size.set(size);
	}

	public void setRotation(Vector3f rotation){
		this.rotation.set(rotation);
	}

	private void updateTransform(){
		transform.setTranslation(-position.x, -position.y, -position.z)
				.setRotationXYZ(rotation.x, rotation.y, rotation.z)
				.scale(size.x, size.y, size.z / 2.0f);
	}
}
