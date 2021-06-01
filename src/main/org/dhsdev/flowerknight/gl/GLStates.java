package org.dhsdev.flowerknight.gl;

import static org.lwjgl.opengl.GL33.*;

/**
 * This keeps track of the global OpenGL states. In operations such as binding
 * shaders, OpenGL does not check if the same shader is bound twice, and binding
 * a shader is an expensive operation. Therefore, we check it beforehand, and so
 * on with other things.
 * @author adamhutchings
 */
public final class GLStates {

    /**
     * No one gets to create this.
     */
    private GLStates() {}

    /**
     * Which shader is bound.
     */
    private static int boundShader;

    /**
     * Bins a shader program only if it's not already bound.
     * @param id the ID to bind
     */
    public static void bindID(int id) {
        if (boundShader != id) {
            boundShader = id;
            glUseProgram(id);
        }
    }

    /**
     * Which texture is bound.
     */
    private static int boundTexture;

    /**
     * Binds a texture only if it's not already bound.
     * @param tex the texture ID to bind
     */
    public static void bindTexture(int tex) {
        if (boundTexture != tex) {
            boundTexture = tex;
            glBindTexture(GL_TEXTURE_2D, tex);
        }
    }

}
