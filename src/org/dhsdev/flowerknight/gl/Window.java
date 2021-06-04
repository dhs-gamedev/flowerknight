package org.dhsdev.flowerknight.gl;

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

        // Right now the window is non-resizable.
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);

        // Hint MSAA
        glfwWindowHint(GLFW_SAMPLES, samples);

        // Get the size of the screen height as a basis for window size.
        int monitorHeight = Objects.requireNonNull(glfwGetVideoMode(glfwGetPrimaryMonitor())).height();
        int screenHeight = (int) (monitorHeight * 0.75f);
        int screenWidth = (int) (monitorHeight * 0.75f);
        // Don't ask me what the two NULLs are for. I have no clue.
        handle = glfwCreateWindow(screenWidth, screenHeight, "FlowerKnight", NULL, NULL);

        glfwMakeContextCurrent(handle);
        GL.createCapabilities();

        // Enable MSAA
        glEnable(GL_MULTISAMPLE);

        glfwShowWindow(handle);
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

}
