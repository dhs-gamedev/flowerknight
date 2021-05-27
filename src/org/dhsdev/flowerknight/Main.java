package org.dhsdev.flowerknight;

import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.system.MemoryUtil.*;

/**
 * Main driver code for the FlowerKnight game. Right now this has all of the
 * windowing code stuffed inside - later it should be moved somewhere else.
 * @author adamhutchings
 */
public class Main {

    public static void main(String[] args) {

        // Set up GLFW. Errors should be checked here later.
        glfwInit();

        // Don't ask me what the two NULLs are for. I have no clue.
        long windowHandle = glfwCreateWindow(500, 500, "FlowerKnight", NULL, NULL);

        glfwShowWindow(windowHandle);
        glfwMakeContextCurrent(windowHandle);
        GL.createCapabilities(); // We'll need this at some point.

        while ( !glfwWindowShouldClose(windowHandle) ) {
            // While it's open, clear it and check for events.
            glClear(GL_COLOR_BUFFER_BIT);
            glfwSwapBuffers(windowHandle);
            glfwPollEvents();
        }

        // Not necessary, because the OS will delete everything anyway,
        // but still good practice.
        glfwDestroyWindow(windowHandle);
        glfwTerminate();

        // All done!

    }

}
