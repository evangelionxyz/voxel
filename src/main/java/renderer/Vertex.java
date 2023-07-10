package renderer;
import org.joml.*;

public class Vertex {
	
	public Vector3f Position;
	public Vector4f Color;
	
	public Vertex(Vector3f position, Vector4f color) {
		this.Position = position;
		this.Color = color;
	}
}
