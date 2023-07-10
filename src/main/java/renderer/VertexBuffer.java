package renderer;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;

public class VertexBuffer {
	private int mVboID = 0;
	float[] mVertices;
	private IndexBuffer mIndexBuffer;

	private BufferLayout mLayout;
	
	public VertexBuffer(float[] vertices) {
		mVertices = vertices;

		mVboID = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, mVboID);

		FloatBuffer buffer = BufferUtils.createFloatBuffer(vertices);
		glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
	}

	public void setLayout(BufferLayout layout){
		mLayout = layout;
	}

	public BufferLayout getLayout() {
		return mLayout;
	}

	public void bind(){
		glBindBuffer(GL_ARRAY_BUFFER, mVboID);
	}

	public void unbind(){
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}

	public IndexBuffer getIndexBuffer(){
		return mIndexBuffer;
	}

	public float[] getVertices(){
		return mVertices;
	}
}
