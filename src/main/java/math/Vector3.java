package math;

public class Vector3 {
public float x = 0, y = 0, z = 0;
	
	public Vector3() {
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}
	
	public Vector3(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	    
    public Vector3 add(Vector3 other) {
        float newX = this.x + other.x;
        float newY = this.y + other.y;
        float newZ = this.z + other.z;
        return new Vector3(newX, newY, newZ);
    }
    
    public Vector3 subtract(Vector3 other) {
        float newX = this.x - other.x;
        float newY = this.y - other.y;
        float newZ = this.z - other.z;
        return new Vector3(newX, newY, newZ);
    }
    
    public Vector3 multiply(float scalar) {
        float newX = this.x * scalar;
        float newY = this.y * scalar;
        float newZ = this.z * scalar;
        return new Vector3(newX, newY, newZ);
    }
    
    public float dotProduct(Vector3 other) {
        float dotX = this.x * other.x;
        float dotY = this.y * other.y;
        float dotZ = this.z * other.z;
        return dotX + dotY + dotZ;
    }
    
    // Additional methods and operators can be added as needed
    
    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")";
    }
}
