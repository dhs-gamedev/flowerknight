package org.dhsdev.flowerknight.gl;

import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL33.*;

/**
 * A mesh contains a collection of arrays containing the data that will be sent
 * to the GPU to be rendered.
 * @author adamhutchings
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
     * Instead of positions in the game world, this is the positions inside the
     * currently bound texture that will be rendered.
     */
    private final int texVboId;

    /**
     * Avoid repeated calculations by storing this number.
     */
    private final int vertexCount;

    /**
     * Construct a Mesh from the sets of data - game positions, indices of the
     * buffers to draw, and texture coordinates.
     * @param positions the game positions
     * @param indices the indices to draw
     * @param texCoords the texture coordinates
     */
    public Mesh(float[] positions, int[] indices, float[] texCoords) {

        // The buffers we'll place the data in
        FloatBuffer verticesBuffer;
        FloatBuffer texBuffer;
        IntBuffer indexBuffer;

        vertexCount = indices.length;

        vaoId = glGenVertexArrays();
        glBindVertexArray(vaoId);

        // Vertices
        posVboId = glGenBuffers();
        verticesBuffer = MemoryUtil.memAllocFloat(positions.length);
        verticesBuffer.put(positions).flip();
        glBindBuffer(GL_ARRAY_BUFFER, posVboId);
        glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);
        glVertexAttribPointer(0, GAME_DIMENSION, GL_FLOAT, false, 0, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        MemoryUtil.memFree(verticesBuffer);

        // Indices
        idxVboId = glGenBuffers();
        indexBuffer = MemoryUtil.memAllocInt(indices.length);
        indexBuffer.put(indices).flip();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, idxVboId);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indexBuffer, GL_STATIC_DRAW);
        MemoryUtil.memFree(indexBuffer);

        // Texture coordinates
        texVboId = glGenBuffers();
        texBuffer = MemoryUtil.memAllocFloat(texCoords.length);
        texBuffer.put(texCoords).flip();
        glBindBuffer(GL_ARRAY_BUFFER, texVboId);
        glBufferData(GL_ARRAY_BUFFER, texBuffer, GL_STATIC_DRAW);
        glVertexAttribPointer(1, GAME_DIMENSION, GL_FLOAT, false, 0, 0);
        MemoryUtil.memFree(texBuffer);

        // We're not placing data into the vao anymore
        // TODO - use GLStates
        glBindVertexArray(0);

    }

    /**
     * Render this mesh. This is useless right now because we don't have texture
     * code.
     * @param textureID the OpenGL textureID to use.
     */
    public void render(int textureID) {

        // TODO - use GLStates

        // Activate first texture unit
        glActiveTexture(GL_TEXTURE0);
        // Bind the texture
        glBindTexture(GL_TEXTURE_2D, textureID);

        // Draw the mesh
        glBindVertexArray(vaoId);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glDrawElements(GL_TRIANGLES, vertexCount, GL_UNSIGNED_INT, 0);

        // Clear everything for next mesh
        glDisableVertexAttribArray(0);
        glBindVertexArray(0);

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
        glDeleteBuffers(texVboId);

        // Clear the VAO
        glBindVertexArray(0);
        glDeleteVertexArrays(vaoId);

    }

}
