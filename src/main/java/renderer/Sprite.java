package renderer;

import org.joml.Vector3f;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL45.*;

public class Sprite {
    private VertexArray vao;
    private VertexBuffer vbo;
    public Vector3f Position;
    public Vector3f Rotation;

    public Sprite(Vector3f position, Vector3f rotation) {
        float[] vertices = {
          -0.5f, -0.5f, 0.5f, -0.5f, -0.5f, 0.5f, 0.5f, 0.5f
        };

        vao = new VertexArray();
        vbo = new VertexBuffer(vertices);
        vbo.bind();

        ArrayList<BufferElement> elements = new ArrayList<>();
        elements.add(new BufferElement(ShaderDataType.Float2, "position", false));
        vbo.setLayout(new BufferLayout(elements));
        vao.addVertexBuffer(vbo);

        Position = position;
        Rotation = rotation;
    }

    public void draw() {
        vao.bind();
        vbo.bind();
        glDrawArrays(GL_TRIANGLE_STRIP, 0, 4);
        vbo.unbind();
        vao.unbind();
    }
}
