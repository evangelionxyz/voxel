package renderer;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11C.*;

public class Chunk {
    private final int SIZE = 16;
    private final int HEIGHT = 16;
    private Vector3f position;
    private Block[][][] blocks = new Block[SIZE][HEIGHT][SIZE];
    private Texture2D texture;

    private int[][] FACE_OFFSETS;

    public Chunk(Vector3f pos, ArrayList<Float> noises) {
        position = pos;
        FACE_OFFSETS = new int[][]
        {
            { 1, 0, 0},
            {-1, 0, 0},
            { 0, 1, 0},
            { 0,-1, 0},
            { 0, 0, 1},
            { 0, 0,-1}
        };

        texture = new Texture2D("assets/textures/block.png");

        for(int z = 0; z < SIZE; z++) {
            for(int x = 0; x < SIZE; x++) {
                int globalZ = (int)position.z * SIZE + z;
                int globalX = (int)position.x * SIZE + x;

                float noiseValue = noises.get(x + z * SIZE);
                int height = (int)((noiseValue + 1.0f) * 0.5f * HEIGHT);
                for(int y = 0; y < HEIGHT; y++) {
                    int globalY = (int)position.y * HEIGHT + y;
                    if(y < height)
                    {
                        blocks[x][y][z] = new Block(new Vector3f(globalX, globalY, globalZ),  BlockType.Dirt);
                    }
                    else
                    {
                        blocks[x][y][z] = new Block(new Vector3f(globalX, globalY, globalZ),  BlockType.Air);
                    }
                }
            }
        }
    }

    public void draw(Shader shader, Matrix4f viewProjection) {
        glEnable(GL_CULL_FACE);
        glCullFace(GL_BACK);

        shader.bind();
        shader.setMatrix("uViewProjection", viewProjection);

        for(int x = 0; x < SIZE; x++) {
            for(int z = 0; z < SIZE; z++) {
                for(int y = 0; y < HEIGHT; y++) {
                    Block block = blocks[x][y][z];

                    if(block.type == BlockType.Air) {
                        continue;
                    }

                    for(int face = 0; face < 6; face++)
                    {
                        int nx = (int)block.position.x + FACE_OFFSETS[face][0];
                        int ny = (int)block.position.y + FACE_OFFSETS[face][1];
                        int nz = (int)block.position.z + FACE_OFFSETS[face][2];

                        if (nx < position.x * SIZE || nx >= SIZE * (position.x + 1)
                                || ny < 0 || ny >= SIZE
                                || nz < position.z * SIZE || nz >= SIZE * (position.z + 1)) {
                            block.drawFace(face, texture, shader);
                        }
                        else
                        {
                            Block neighborBlock = blocks[nx % SIZE][ny][nz % SIZE];
                            if (neighborBlock.type == BlockType.Air)
                                block.drawFace(face, texture, shader);
                        }
                    }
                }
            }
        }

        shader.unbind();
        glDisable(GL_CULL_FACE);
    }
}
