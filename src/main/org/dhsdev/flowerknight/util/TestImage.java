package org.dhsdev.flowerknight.util;

import org.dhsdev.flowerknight.gl.Mesh;
import org.dhsdev.flowerknight.gl.Shader;
import org.dhsdev.flowerknight.gl.Texture;

import java.io.IOException;

/**
 * A small test image to render.
 * @author adamhutchings
 */
public final class TestImage {

    /**
     * NO!
     */
    private TestImage() {}

    /**
     * The texture object
     */
    private static Texture tex;

    /**
     * The mesh object
     */
    private static Mesh mesh;

    /**
     * The shader object
     */
    private static Shader shader;

    /**
     * Initialize the texture, mesh, and shader.
     */
    public static void init() {

        shader = Shader.TRIVIAL_SHADER;

        var positions = new float[] {
            -0.5f, -0.5f,
            -0.5f,  0.5f,
             0.5f,  0.5f,
             0.5f, -0.5f,
        };

        var indices = new int[] {
            0, 1, 3, 3, 1, 2,
        };

        var texCoords = new float[] {
            0, 1, 0, 0, 1, 0, 1, 1,
        };

        mesh = new Mesh(positions, indices, texCoords);

        try {
            tex = new Texture("res/logo.png");
        } catch (IOException e) {
            Logger.log("Could not load texture", Severity.ERROR);
        }

    }

    /**
     * Render this object.
     */
    public static void render() {
        shader.bind();
        mesh.render(tex.id());
    }

    /**
     * Cleanup at the end - destroy mesh, shader, and texture.
     */
    public static void delete() {
        mesh.destroy();
        shader.destroy();
        tex.delete();
    }

}
