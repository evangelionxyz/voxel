package renderer;

import static org.lwjgl.opengl.GL30.*;
import java.util.ArrayList;


public class VertexArray {
	private final int mVaoID;
	private ArrayList<VertexBuffer> mVertexBuffer;
	
	public VertexArray() {
		mVaoID = glGenVertexArrays();
		glBindVertexArray(mVaoID);
	}

	public void addVertexBuffer(VertexBuffer vertexBuffer){
		mVertexBuffer = new ArrayList<>();

		glBindVertexArray(mVaoID);
		vertexBuffer.bind();

		BufferLayout layout = vertexBuffer.getLayout();

		int mVertexBufferIndex = 0;
		for(BufferElement element : layout.getElements())
		{
			glEnableVertexAttribArray(mVertexBufferIndex);
			glVertexAttribPointer(mVertexBufferIndex,
					element.getComponentCount(), GL_FLOAT,
					element.Normalized,
					layout.Stride,
					element.Offset);
			mVertexBufferIndex++;
		}

		mVertexBuffer.add(vertexBuffer);
	}

	public void addIndexBuffer(IndexBuffer indexBuffer){
        indexBuffer.bind();
	}
	
	public void bind() {
		glBindVertexArray(mVaoID);
	}
	
	public void unbind() {
		glBindVertexArray(0);
	}

}
