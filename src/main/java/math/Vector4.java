package math;

public class Vector4 {
	
	public float x = 0, y = 0, z = 0, w = 0;
	
	public Vector4() {
		x = y = z = w = 0;
	}
	
	public Vector4(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	    
    public Vector4 add(Vector4 other) {
        float newX = this.x + other.x;
        float newY = this.y + other.y;
        float newZ = this.z + other.z;
        float newW = this.w + other.w;
        return new Vector4(newX, newY, newZ, newW);
    }
    
    public Vector4 subtract(Vector4 other) {
        float newX = this.x - other.x;
        float newY = this.y - other.y;
        float newZ = this.z - other.z;
        float newW = this.w - other.w;
        return new Vector4(newX, newY, newZ, newW);
    }
    
    public Vector4 multiply(float scalar) {
        float newX = this.x * scalar;
        float newY = this.y * scalar;
        float newZ = this.z * scalar;
        float newW = this.w * scalar;
        return new Vector4(newX, newY, newZ, newW);
    }
    
    public float dotProduct(Vector4 other) {
        float dotX = this.x * other.x;
        float dotY = this.y * other.y;
        float dotZ = this.z * other.z;
        float dotW = this.w * other.w;
        return dotX + dotY + dotZ + dotW;
    }
    
    // Additional methods and operators can be added as needed
    
    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ", " + w + ")";
    }
}
