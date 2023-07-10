package renderer;

import org.joml.*;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL30.*;

public class Cube {
	private Shader mShader;

	private float[] mVertices;
	private int[] mIndices;

	private VertexArray vao;
	private VertexBuffer vbo;
	private IndexBuffer ibo;
	
	public Cube() {
		
		mVertices = new float[]{
            // position             // color (RGB)
            -0.5f, -0.5f, 0.5f,     1.0f, 1.0f, 1.0f,  // Front bottom left
             0.5f, -0.5f, 0.5f,     1.0f, 0.0f, 1.0f,  // Front bottom right
            -0.5f, 0.5f, 0.5f,      1.0f, 1.0f, 1.0f,  // Front top left
             0.5f, 0.5f, 0.5f,      1.0f, 1.0f, 0.0f   // Front top right
        };

        mIndices = new int[] {
            0, 1, 2,  // Front triangle 1
            2, 1, 3   // Front triangle 2
        };

		ArrayList<BufferElement> elements = new ArrayList<BufferElement>();
		elements.add(new BufferElement(ShaderDataType.Float3,  "aPos", false));
		elements.add(new BufferElement(ShaderDataType.Float3,  "aColor", false));
		BufferLayout layout = new BufferLayout(elements);

		vao = new VertexArray();
		vbo = new VertexBuffer(mVertices);
		vbo.setLayout(layout);

		vao.addVertexBuffer(vbo);

		ibo = new IndexBuffer();
		ibo.addIndices(mIndices);
		vao.addIndexBuffer(ibo);
		
		mShader = new Shader("shaders/default.glsl");
		mShader.bind();
	}
	
	public void render(Matrix4f viewProjection) {
		mShader.bind();
		mShader.setMatrix("uViewProjection", viewProjection);
		Renderer.drawElements(vao, ibo.getCount());
		mShader.unbind();
	}
}
