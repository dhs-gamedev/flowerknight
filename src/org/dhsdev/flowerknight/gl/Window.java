package org.dhsdev.flowerknight.gl;

import org.dhsdev.flowerknight.game.Camera;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import java.util.Objects;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.system.MemoryUtil.*;

/**
 * This class is a wrapper around the OpenGL window.
 * @author adamhutchings, Shuzhengz
 */
public class Window {

    /**
     * The OpenGL window handle.
     */
    private final long handle;

    /**
     * Create a window. In more detail:
     *   - Make the window non-resizable.
     *   - Make a square window, side length 3/4 the height of the screen
     *   - Make it visible and create an OpenGL context for it.
     *   @param samples MSAA Samples to be applied
     */
    public Window(int samples) {

        // Hint MSAA
        glfwWindowHint(GLFW_SAMPLES, samples);

        // Get the size of the screen height as a basis for window size.
        int monitorHeight = Objects.requireNonNull(glfwGetVideoMode(glfwGetPrimaryMonitor())).height();
        int screenHeight = (int) (monitorHeight * 0.75f);
        int screenWidth = (int) (monitorHeight * 0.75f);
        // Don't ask me what the two NULLs are for. I have no clue.
        handle = glfwCreateWindow(screenWidth, screenHeight, "FlowerKnight", NULL, NULL);

        width  = screenWidth;
        height = screenHeight;

        glfwMakeContextCurrent(handle);
        GL.createCapabilities();

        // Enable MSAA
        glEnable(GL_MULTISAMPLE);

        glfwShowWindow(handle);

        // We want the window to immediately render as it is resized - the images
        // will scale correctly, but the spotlight shader will freeze and the
        // will not remain a square.
        glfwSetWindowSizeCallback(handle, (long window, int width, int height) -> {
            // Update the shaders and render.
            updateShadersOnResize(width, height);
            redraw();
        });

    }

    /**
     * Is this window open?
     * @return whether it is about to close
     */
    public boolean isOpen() {
        return !glfwWindowShouldClose(handle);
    }

    /**
     * Clear the window frame.
     */
    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT);
    }

    /**
     * Swap the buffers, to display all updates.
     */
    public void displayAllUpdates() {
        glfwSwapBuffers(handle);
    }

    /**
     * Call all key and mouse handlers, close and resize checks, etc.
     */
    public void callEventHandlers() {
        glfwPollEvents();
    }

    /**
     * Destroy this window.
     */
    public void delete() {
        // Make it close in case it hasn't yet
        glfwSetWindowShouldClose(handle, true);
        glfwDestroyWindow(handle);
    }

    /**
     * Update shader uniforms when the window gets resized.
     * @param width the new window width
     * @param height the new window height
     */
    public void updateShadersOnResize(float width, float height) {

        this.width  = width;
        this.height = height;

        // Update width and height for every shader
        for (var shader : new Shader[] {
                Shader.getGameShader(),
                Shader.getSpotlightShader(),
                Shader.getTrivialShader(),
        }) {
            shader.bind();
            shader.setUniform("width" , width);
            shader.setUniform("height", height);
        }

    }

    /**
     * Window width
     */
    private float width;

    /**
     * Get width
     */
    public float width() {
        return width;
    }

    /**
     * Window height
     */
    private float height;

    /**
     * Get height
     */
    public float height() {
        return height;
    }

    /**
     * Render everything.
     */
    public void redraw() {

        this.clear();

        // Iterate and draw all renderable object
        for (Renderable renderable : Renderable.renderables) {
            renderable.draw();
        }

        // Render everything to screen at once
        this.displayAllUpdates();

    }

    /**
     * Update the spotlight annd camera shaders which change with every tick.
     */
    public void updateNeededShaders() {

        Shader.getSpotlightShader().bind();
        Shader.getSpotlightShader().setUniform("time", (float) glfwGetTime());

        Camera.updateShaders();

    }

}
