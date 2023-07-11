package renderer;

import org.joml.*;
import org.lwjgl.BufferUtils;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.file.*;
import java.util.Map;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

public class Shader {
	
	private String m_VertexSrc;
	private String m_FragmentSrc;
	private String m_Filepath;
	private int m_RendererID;
	private boolean m_IsUsed = false;

	public Shader(String vertexShaderPath, String fragmentShaderPath){
		m_VertexSrc = vertexShaderPath;
		m_FragmentSrc = fragmentShaderPath;
		compile();
	}
	
	public Shader(String path) {
		this.m_Filepath = path;
		
		try {
			String source = new String(
					Files.readAllBytes(Paths.get(m_Filepath)));
			String[] splitString = source.split("(//type)( )+([a-zA-Z]+)");
			
			int index = source.indexOf("//type")+7;
			int eol = source.indexOf("\r\n", index);
			String firstShader = source.substring(index, eol).trim();
			
			index = source.indexOf("//type", eol)+7;
			eol = source.indexOf("\r\n", index);
			String secondShader = source.substring(index, eol).trim();
			
			// Find first shader
			if(firstShader.equals("vertex"))
				m_VertexSrc = splitString[1];
			else if(firstShader.equals("fragment"))
				m_FragmentSrc = splitString[1];
			else
				throw new IOException("Cannot Find" + firstShader);
			
			// Find second shader
			if(secondShader.equals("vertex") || secondShader.equals("Vertex"))
				m_VertexSrc = splitString[2];
			else if(secondShader.equals("fragment") || secondShader.equals("Fragment"))
				m_FragmentSrc = splitString[2];
			else
				throw new IOException("Cannot Find" + secondShader);
			
			
		} catch(IOException e) {
			e.printStackTrace();
			assert false : "Error failed to Open GLSL Shader" + m_Filepath;
		}
		
		compile();
	}
	
	private void compile() {
		int vertexID = glCreateShader(GL_VERTEX_SHADER);
		glShaderSource(vertexID, m_VertexSrc);
		glCompileShader(vertexID);
		int length;
		int success = glGetShaderi(vertexID, GL_COMPILE_STATUS);
		if(success == GL_FALSE){
			length = glGetShaderi(vertexID, GL_INFO_LOG_LENGTH);
			System.out.println("ERROR: '" + m_Filepath +"'\n\tvertex SHADER compilation failed");
            assert false : "Failed to Compile Vertex Shader";
		}
		
		int fragmentID = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(fragmentID, m_FragmentSrc);
		glCompileShader(fragmentID);
		success = glGetShaderi(fragmentID, GL_COMPILE_STATUS);
		if (success == GL_FALSE) {
            length = glGetShaderi(fragmentID, GL_INFO_LOG_LENGTH);
            System.out.println("ERROR: '" + m_Filepath +"'\n\tfragment SHADER compilation failed");
            assert false : "Failed to Compile Fragment Shader";
        }
		
		m_RendererID = glCreateProgram();
		glAttachShader(m_RendererID, vertexID);
		glAttachShader(m_RendererID, fragmentID);
		glLinkProgram(m_RendererID);
		
		success = glGetProgrami(m_RendererID, GL_LINK_STATUS);
        if (success == GL_FALSE) {
            length = glGetProgrami(m_RendererID, GL_INFO_LOG_LENGTH);
            System.out.println("ERROR: '" + m_Filepath +"'\n\t shader program LINKING failed");
            System.out.println(glGetProgramInfoLog(m_RendererID, length));
            assert false : "Failed to Link Program Shader";
        }
	}
	
	public void bind() {
		if(!m_IsUsed)
			glUseProgram(m_RendererID);
	}
	
	public void unbind() {
		glUseProgram(0);
		m_IsUsed = false;
	}
	
	public int getUniformLocation(String name) {
		int location = glGetUniformLocation(m_RendererID, name);
		if(location == -1)
			System.out.println("Warning : uniform '" + name + "' doesn't exist in main method!");
		return location;
	}
	
	public void setMatrix(String name, Matrix4f matrix) {
		FloatBuffer matBuffer = BufferUtils.createFloatBuffer(16);
		matrix.get(matBuffer);
		glUniformMatrix4fv(getUniformLocation(name), false, matBuffer);
	}
	
	public void setMatrix(String name, Matrix3f matrix) {
		FloatBuffer matBuffer = BufferUtils.createFloatBuffer(9);
		matrix.get(matBuffer);
		glUniformMatrix3fv(getUniformLocation(name), false, matBuffer);
	}
	
	public void setMatrix(String name, Matrix2f matrix) {
		FloatBuffer matBuffer = BufferUtils.createFloatBuffer(4);
		matrix.get(matBuffer);
		glUniformMatrix2fv(getUniformLocation(name), false, matBuffer);
	}
	
	public void setVector(String name, Vector4f vector) {
		glUniform4f(getUniformLocation(name), vector.x, vector.y, vector.z, vector.w);
	}
	
	public void setVector(String name, Vector3f vector) {
		glUniform3f(getUniformLocation(name), vector.x, vector.y, vector.z);
	}
	
	public void setVector(String name, Vector2f vector) {
		glUniform2f(getUniformLocation(name), vector.x, vector.y);
	}
	
	public void setFloat(String name, float v) {
		glUniform1f(getUniformLocation(name), v);
	}
	
	public void setInt(String name, int v) {
		glUniform1i(getUniformLocation(name), v);
	}
}
