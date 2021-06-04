package org.dhsdev.flowerknight.gl;

import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL33.*;

/**
 * A mesh contains a collection of arrays containing the data that will be sent
 * to the GPU to be rendered.
 * @author adamhutchings
 * @author Brandon Qi
 */
public final class Mesh {

    /**
     * The dimension of the game.
     */
    private static final int GAME_DIMENSION = 2;

    /**
     * Similarly to the shader, this is the ID that all of the other sets of
     * data are "bound" to. Except that in this case, we need to retain handles
     * to the rest of the data.
     */
    private final int vaoId;

    /**
     * All of the positions that need to be rendered.
     */
    private final int posVboId;

    /**
     * The indices of the position buffer that will be rendered. For example, if
     * this is set to { 2, 4, 3, 1, 6, 4 }, the positions 2, 3, and 6 will be
     * combined into one triangle and rendered, and then the same for 1, 6, 4.
     */
    private final int idxVboId;

    /**
     * Avoid repeated calculations by storing this number.
     */
    private final int vertexCount;

    /**
     * Construct a Mesh from the sets of data - game positions, indices of the
     * buffers to draw, and texture coordinates.
     * @param positions the game positions
     * @param indices the indices to draw
     */
    public Mesh(float[] positions, int[] indices) {

        vertexCount = indices.length;

        vaoId = glGenVertexArrays();
        glBindVertexArray(vaoId);

        // Vertices
        posVboId = getVboId(positions);
        glVertexAttribPointer(0, GAME_DIMENSION, GL_FLOAT, false, 0, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        // Indices
        idxVboId = getVboId(indices);

        // We're not placing data into the vao anymore
        // TODO - use GLStates
        glBindVertexArray(0);

    }

    private int getVboId(float[] arr) {
        final int vboId = glGenBuffers();
        FloatBuffer buffer;
        buffer = MemoryUtil.memAllocFloat(arr.length);
        buffer.put(arr).flip();
        glBindBuffer(GL_ARRAY_BUFFER, vboId);
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
        MemoryUtil.memFree(buffer);
        return vboId;
    }

    private int getVboId(int[] arr) {
        final int vboId = glGenBuffers();
        IntBuffer buffer;
        buffer = MemoryUtil.memAllocInt(arr.length);
        buffer.put(arr).flip();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboId);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
        MemoryUtil.memFree(buffer);
        return vboId;
    }

    /**
     * Render this mesh. This is useless right now because we don't have texture
     * code.
     * @param texture the OpenGL textureID to use.
     */
    public void render(TextureEnum texture) {

        int pos = texture.ordinal();
        int texMax = TextureEnum.values().length;

        float stride = 1 / (float) texMax;

        // We select a square from the large texture atlas image.
        var texCoords = new float[] {
            pos * stride,       1,
            pos * stride,       0,
            (pos + 1) * stride, 0,
            (pos + 1) * stride, 1,
        };

        glBindVertexArray(vaoId);

        // Texture coordinates
        int texVboId = getVboId(texCoords);
        glVertexAttribPointer(1, GAME_DIMENSION, GL_FLOAT, false, 0, 0);

        // Draw the mesh
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glDrawElements(GL_TRIANGLES, vertexCount, GL_UNSIGNED_INT, 0);

        // Clear everything for next mesh
        glDisableVertexAttribArray(0);
        glBindVertexArray(0);

        glDeleteBuffers(texVboId);

    }

    /**
     * Clear all of the data sent to OpenGL. This should be called on EVERY
     * instance of a Mesh, or else 1. memory is wasted 2. game exit times are
     * far improved - they can be on the order of 10 seconds otherwise.
     */
    public void destroy() {

        glDisableVertexAttribArray(0);

        // Clear the VBO
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        // Flush all data
        glDeleteBuffers(posVboId);
        glDeleteBuffers(idxVboId);

        // Clear the VAO
        glBindVertexArray(0);
        glDeleteVertexArrays(vaoId);

    }

}
