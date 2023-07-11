package renderer;

import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL45.*;
import static org.lwjgl.stb.STBImage.*;

public class Texture2D {
    private int m_RendererID;
    private int m_Width = 1;
    private int m_Height = 1;
    private int m_InternalFormat;
    private int m_DataFormat;

    public Texture2D(){

        m_InternalFormat = GL_RGBA8;
        m_DataFormat = GL_RGBA;

        m_RendererID = glCreateTextures(GL_TEXTURE_2D);
        glBindTexture(GL_TEXTURE_2D, m_RendererID);
        glTextureStorage2D(m_RendererID, 1, m_InternalFormat, m_Width, m_Height);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
    }

    public Texture2D(String filepath){
        System.out.println("Trying to load Texture '" + filepath + "'");

        IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer height = BufferUtils.createIntBuffer(1);
        IntBuffer bpp = BufferUtils.createIntBuffer(1);

        ByteBuffer image = stbi_load(filepath, width, height, bpp, 0);
        stbi_set_flip_vertically_on_load(true);

        m_Width = width.get(0);
        m_Height = height.get(0);

        assert image != null : "Failed to load Texture '" + filepath + "'";

        int internalFormat = 0;
        int dataFormat = 0;

        if(bpp.get(0) == 4)
        {
            internalFormat = GL_RGBA8;
            dataFormat = GL_RGBA;
        }
        else if(bpp.get(0) == 3){
            internalFormat = GL_RGB8;
            dataFormat = GL_RGB;
        }

        m_InternalFormat = internalFormat;
        m_DataFormat = dataFormat;

        m_RendererID = glCreateTextures(GL_TEXTURE_2D);
        glBindTexture(GL_TEXTURE_2D, m_RendererID);
        glTextureStorage2D(m_RendererID, 1, m_InternalFormat, m_Width, m_Height);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);

        glTextureSubImage2D(m_RendererID, 0, 0, 0, m_Width, m_Height, m_DataFormat, GL_UNSIGNED_BYTE, image);

        stbi_image_free(image);
    }

    public void setData(ByteBuffer data, int size ){
        int bpp = m_DataFormat == GL_RGBA ? 4 : 3;
        assert size == m_Width * m_Height * bpp : "Data must be Entire Texture";
        glTextureSubImage2D(m_RendererID, 0, 0, 0, m_Width, m_Height, m_DataFormat, GL_UNSIGNED_BYTE, data);
    }

    public void bind(){
        glActiveTexture(GL_TEXTURE0 + m_RendererID);
        glBindTexture(GL_TEXTURE_2D, m_RendererID);
    }

    public void unbind(){
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    public int getID(){
        return m_RendererID;
    }
}
