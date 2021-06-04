package org.dhsdev.flowerknight;

import org.dhsdev.flowerknight.gl.Renderable;
import org.dhsdev.flowerknight.gl.TextureAtlas;
import org.dhsdev.flowerknight.gl.comp.Label;
import org.dhsdev.flowerknight.game.Camera;
import org.dhsdev.flowerknight.game.GameObject;
import org.dhsdev.flowerknight.gl.Shader;
import org.dhsdev.flowerknight.gl.Window;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL33.*;

/**
 * This class contains all main control routines of the game. The "init",
 * "mainloop", and "exit" functions are all called in Main.
 * @author adamhutchings
 */
public final class FlowerKnight {

    private FlowerKnight() {
        throw new IllegalStateException("FlowerKnight Class");
    }

    private static Window window;

    /**
     * Perform the setup for the game. This initializes GLFW and creates a
     * window handle.
     */
    public static void init() {

        // Set up GLFW. Errors should be checked here later.
        glfwInit();

        // macOS needs to request a forward profile for a later
        // version of OpenGL explicitly. Version 3.3, to be specific.
        if (GetOSType.getOSType() == OSType.MACOS) {
            glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
            glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
            glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
            glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        }

        window = new Window();

        Shader.init();

        Camera.init();

        Renderable.renderables.add(new Label("Test", "Test Text", 0.4f,0.4f, 0f, 0f));

        Shader.SPOTLIGHT_SHADER.registerUniform("time");

        TextureAtlas.loadAllTextures();

    }

    /**
     * Run the "core body" of the game. Right now, this checks for events while
     * the window is open and nothing else.
     */
    public static void mainloop() {

        // While it's open, clear screen and check for events.
        while (window.isOpen()) {

            Shader.SPOTLIGHT_SHADER.bind();
            Shader.SPOTLIGHT_SHADER.setUniform("time", (float) glfwGetTime());

            Camera.updateShaders();

            GameObject.updateAll();

            window.clear();

            // Iterate and draw all renderable object
            for (Renderable renderable : Renderable.renderables) {
                renderable.draw();
            }


            // Render everything to screen at once
            window.displayAllUpdates();

            window.callEventHandlers();

        }

    }

    /**
     * Clean up everything just for good practice.
     */
    public static void exit() {

        for (Renderable renderable : Renderable.renderables) {
            renderable.clear();
        }

        // Not necessary, because the OS will delete everything anyway,
        // but still good practice.
        window.delete();

        TextureAtlas.delete();

        glfwTerminate();
    }

}
