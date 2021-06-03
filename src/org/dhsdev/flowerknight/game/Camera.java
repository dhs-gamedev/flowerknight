package org.dhsdev.flowerknight.game;

import org.dhsdev.flowerknight.gl.Shader;

/**
 * This class stores the camera x location, y location, and zoom, so these can
 * be passed to the game object shader.
 * @author adamhutchings
 */
public final class Camera {

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
    private static float zoom = 1.0f;

    /**
     * Register the two needed uniforms.
     */
    public static void init() {

        Shader.GAME_SHADER.registerUniform("cameraLoc");
        Shader.GAME_SHADER.registerUniform("zoom");

    }

    /**
     * Update the shader values, for when the camera position changes.
     */
    public static void updateShaders() {

        Shader.GAME_SHADER.bind();

        Shader.GAME_SHADER.setUniform("cameraLoc", new float[] { x, y });
        Shader.GAME_SHADER.setUniform("zoom", zoom);

    }

}
