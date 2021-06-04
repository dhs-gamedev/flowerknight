package org.dhsdev.flowerknight.game;

import org.dhsdev.flowerknight.gl.Shader;

/**
 * This class stores the camera x location, y location, and zoom, so these can
 * be passed to the game object shader.
 * @author adamhutchings
 */
public final class Camera {

    private Camera() {
        // The camera
    }

    /**
     * The x-position of the camera.
     */
    private static float x;

    /**
     * The y-position of the camera.
     */
    private static float y;

    /**
     * The zoom of the camera.
     */
    private static float ZOOM = 1.0f;

    /**
     * Register the two needed uniforms.
     */
    public static void init() {

        Shader.getGameShader().registerUniform("cameraLoc");
        Shader.getGameShader().registerUniform("zoom");

    }

    /**
     * Update the shader values, for when the camera position changes.
     */
    public static void updateShaders() {

        Shader.getGameShader().bind();

        Shader.getGameShader().setUniform("cameraLoc", new float[] { x, y });
        Shader.getGameShader().setUniform("zoom", ZOOM);

    }

}
