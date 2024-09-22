package renderer;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Block {
    private Sprite front, back, left, right, top, bottom;
    public Vector3f position;
    public BlockType type;
    public Block(Vector3f position, BlockType type) {
        this.type = type;
        this.position = position;
        front = new Sprite(new Vector3f(position.x, position.y, position.z + 0.5f), new Vector3f(0.0f));
        back = new Sprite(new Vector3f(position.x, position.y, position.z - 0.5f), new Vector3f(0.0f, (float) Math.toRadians(180.0f), 0.0f));
        left = new Sprite(new Vector3f(position.x-0.5f, position.y, position.z), new Vector3f(0.0f, (float)  Math.toRadians(-90.0f), 0.0f));
        right = new Sprite(new Vector3f(position.x+0.5f, position.y, position.z), new Vector3f(0.0f, (float) Math.toRadians(90.0f), 0.0f));
        top = new Sprite(new Vector3f(position.x, position.y+0.5f, position.z), new Vector3f((float) Math.toRadians(-90.0f), 0.0f, 0.0f));
        bottom = new Sprite(new Vector3f(position.x, position.y-0.5f, position.z), new Vector3f((float)Math.toRadians(90.0f), 0.0f, 0.0f));
    }

    public void setType(BlockType type) {
        this.type = type;
    }

    public void drawFace(int face, Texture2D texture, Shader shader)
    {
        switch (face)
        {
            case 0:
                drawRight(texture, shader);
                break;
            case 1:
                drawLeft(texture, shader);
                break;
            case 2:
                drawTop(texture, shader);
                break;
            case 3:
                drawBottom(texture, shader);
                break;
            case 4:
                drawFront(texture, shader);
                break;
            case 5:
                drawBack(texture, shader);
                break;
        }
    }

    public void drawFront(Texture2D texture, Shader shader) {
        Matrix4f transform = new Matrix4f();
        transform.setTranslation(front.Position).setRotationXYZ(front.Rotation.x, front.Rotation.y, front.Rotation.z);
        shader.setMatrix("uTransform", transform);
        texture.bind();
        shader.setInt("uTexture", texture.getID());
        front.draw();
    }

    public void drawBack(Texture2D texture, Shader shader) {
        Matrix4f transform = new Matrix4f();
        transform.setTranslation(back.Position).setRotationXYZ(back.Rotation.x, back.Rotation.y, back.Rotation.z);
        shader.setMatrix("uTransform", transform);
        shader.setInt("uTexture", texture.getID());
        back.draw();
    }

    public void drawLeft(Texture2D texture, Shader shader) {
        Matrix4f transform = new Matrix4f();
        transform.setTranslation(left.Position).setRotationXYZ(left.Rotation.x, left.Rotation.y, left.Rotation.z);
        shader.setMatrix("uTransform", transform);
        shader.setInt("uTexture", texture.getID());
        left.draw();
    }

    public void drawRight(Texture2D texture, Shader shader) {
        Matrix4f transform = new Matrix4f();
        transform.setTranslation(right.Position).setRotationXYZ(right.Rotation.x, right.Rotation.y, right.Rotation.z);
        shader.setMatrix("uTransform", transform);
        shader.setInt("uTexture", texture.getID());
        right.draw();
    }

    public void drawTop(Texture2D texture, Shader shader) {
        Matrix4f transform = new Matrix4f();
        transform.setTranslation(top.Position).setRotationXYZ(top.Rotation.x, top.Rotation.y, top.Rotation.z);
        shader.setMatrix("uTransform", transform);
        shader.setInt("uTexture", texture.getID());
        top.draw();
    }

    public void drawBottom(Texture2D texture, Shader shader) {
        Matrix4f transform = new Matrix4f();
        transform.setTranslation(bottom.Position).setRotationXYZ(bottom.Rotation.x, bottom.Rotation.y, bottom.Rotation.z);
        shader.setMatrix("uTransform", transform);
        shader.setInt("uTexture", texture.getID());
        bottom.draw();
    }
}
