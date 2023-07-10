package renderer;

import org.joml.*;
import org.lwjgl.BufferUtils;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.file.*;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

public class Shader {
	
	private String mVertexSrc;
	private String mFragmentSrc;
	private String mFilepath;
	private int mRendererID;
	private boolean mIsUsed = false;

	public Shader(String vertexShaderPath, String fragmentShaderPath){
		mVertexSrc = vertexShaderPath;
		mFragmentSrc = fragmentShaderPath;
		compile();
	}
	
	public Shader(String path) {
		this.mFilepath = path;
		
		try {
			String source = new String(
					Files.readAllBytes(Paths.get(mFilepath)));
			String[] splitString = source.split("(//type)( )+([a-zA-Z]+)");
			
			int index = source.indexOf("//type")+7;
			int eol = source.indexOf("\r\n", index);
			String firstShader = source.substring(index, eol).trim();
			
			index = source.indexOf("//type", eol)+7;
			eol = source.indexOf("\r\n", index);
			String secondShader = source.substring(index, eol).trim();
			
			// Find first shader
			if(firstShader.equals("vertex"))
				mVertexSrc = splitString[1];
			else if(firstShader.equals("fragment"))
				mFragmentSrc = splitString[1];
			else
				throw new IOException("Cannot Find" + firstShader);
			
			// Find second shader
			if(secondShader.equals("vertex") || secondShader.equals("Vertex"))
				mVertexSrc = splitString[2];
			else if(secondShader.equals("fragment") || secondShader.equals("Fragment"))
				mFragmentSrc = splitString[2];
			else
				throw new IOException("Cannot Find" + secondShader);
			
			
		} catch(IOException e) {
			e.printStackTrace();
			assert false : "Error failed to Open GLSL Shader" + mFilepath;
		}
		
		compile();
	}
	
	private void compile() {
		int vertexID = glCreateShader(GL_VERTEX_SHADER);
		glShaderSource(vertexID, mVertexSrc);
		glCompileShader(vertexID);
		int length;
		int success = glGetShaderi(vertexID, GL_COMPILE_STATUS);
		if(success == GL_FALSE){
			length = glGetShaderi(vertexID, GL_INFO_LOG_LENGTH);
			System.out.println("ERROR: '" + mFilepath +"'\n\tvertex SHADER compilation failed");
            assert false : "Failed to Compile Vertex Shader";
		}
		
		int fragmentID = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(fragmentID, mFragmentSrc);
		glCompileShader(fragmentID);
		success = glGetShaderi(fragmentID, GL_COMPILE_STATUS);
		if (success == GL_FALSE) {
            length = glGetShaderi(fragmentID, GL_INFO_LOG_LENGTH);
            System.out.println("ERROR: '" + mFilepath +"'\n\tfragment SHADER compilation failed");
            assert false : "Failed to Compile Fragment Shader";
        }
		
		mRendererID = glCreateProgram();
		glAttachShader(mRendererID, vertexID);
		glAttachShader(mRendererID, fragmentID);
		glLinkProgram(mRendererID);
		
		success = glGetProgrami(mRendererID, GL_LINK_STATUS);
        if (success == GL_FALSE) {
            length = glGetProgrami(mRendererID, GL_INFO_LOG_LENGTH);
            System.out.println("ERROR: '" + mFilepath +"'\n\t shader program LINKING failed");
            System.out.println(glGetProgramInfoLog(mRendererID, length));
            assert false : "Failed to Link Program Shader";
        }
	}
	
	public void bind() {
		if(!mIsUsed)
			glUseProgram(mRendererID);
	}
	
	public void unbind() {
		glUseProgram(0);
		mIsUsed = false;
	}
	
	public int getUniformLocation(String name) {
		int location = glGetUniformLocation(mRendererID, name);
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
