package org.dhsdev.flowerknight;

import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * This class contains all main control routines of the game. The "init",
 * "mainloop", and "exit" functions are all called in Main.
 * @author adamhutchings
 */
public final class FlowerKnight {

    private static long windowHandle;

    /**
     * Perform the setup for the game. This initializes GLFW and creates a
     * window handle.
     */
    public static void init() {

        // Set up GLFW. Errors should be checked here later.
        glfwInit();

        // Right now the window is non-resizable.
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);

        // Get the size of the screen height as a basis for window size.
        int monitorHeight = glfwGetVideoMode(glfwGetPrimaryMonitor()).height();
        int screenHeight = (int) (monitorHeight * 0.75f);

        // Don't ask me what the two NULLs are for. I have no clue.
        windowHandle = glfwCreateWindow(screenHeight, screenHeight, "FlowerKnight", NULL, NULL);

        glfwMakeContextCurrent(windowHandle);
        GL.createCapabilities();

        glfwShowWindow(windowHandle);

    }

    /**
     * Run the "core body" of the game. Right now, this checks for events while
     * the window is open and nothing else.
     */
    public static void mainloop() {

        // While it's open, clear screen and check for events.
        while ( !glfwWindowShouldClose(windowHandle) ) {

            glClear(GL_COLOR_BUFFER_BIT);

            // Rendering goes here later

            // Render everything to screen at once
            glfwSwapBuffers(windowHandle);

            glfwPollEvents();

        }

    }

    /**
     * Clean up everything just for good practice.
     */
    public static void exit() {

        // Not necessary, because the OS will delete everything anyway,
        // but still good practice.
        glfwDestroyWindow(windowHandle);
        glfwTerminate();

    }

}
