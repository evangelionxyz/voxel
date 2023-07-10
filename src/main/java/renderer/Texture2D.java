package renderer;

import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.stb.STBImage.*;

public class Texture2D {

    private int mRendererID;
    public Texture2D(String filepath) {

        IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer height = BufferUtils.createIntBuffer(1);
        IntBuffer bpp = BufferUtils.createIntBuffer(1);

        ByteBuffer image = stbi_load(filepath, width, height, bpp, 0);
        stbi_set_flip_vertically_on_load(true);

        if(image == null)
        {
            assert false : "Failed to load Texture '" + filepath + "'";
        }

        mRendererID = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, mRendererID);
        if(bpp.get(0) == 4)
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width.get(0), height.get(0), 0, GL_RGBA, GL_UNSIGNED_BYTE, image);
        else if(bpp.get(0) == 3)
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width.get(0), height.get(0), 0, GL_RGB, GL_UNSIGNED_BYTE, image);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);

        stbi_image_free(image);
    }

    public void bind(){
        glActiveTexture(GL_TEXTURE0 + mRendererID);
        glBindTexture(GL_TEXTURE_2D, mRendererID);
    }

    public void unbind(){
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    public int getID(){
        return mRendererID;
    }
}
