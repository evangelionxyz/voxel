package renderer;

import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15C.*;

public class IndexBuffer {
    private int mIboID;
    private int[] mIndices;

    public IndexBuffer(){
        mIboID = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, mIboID);
    }

    public void addIndices(int[] indices){
        mIndices = indices;
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, BufferUtils.createIntBuffer(indices), GL_STATIC_DRAW);
    }

    public void bind(){
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, mIboID);
    }

    public void unbind(){
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    public int getCount(){
        return mIndices.length;
    }
}
