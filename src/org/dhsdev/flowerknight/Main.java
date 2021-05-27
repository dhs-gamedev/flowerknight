package org.dhsdev.flowerknight;

import static org.lwjgl.glfw.GLFW.*;
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

        // GLFW creates an OpenGL context by default later, but we don't actually
        // need it if we're using Vulkan.
        glfwWindowHint(GLFW_CLIENT_API, GLFW_NO_API);

        // Don't ask me what the two NULLs are for. I have no clue.
        long windowHandle = glfwCreateWindow(500, 500, "FlowerKnight", NULL, NULL);

        glfwShowWindow(windowHandle);

        while ( !glfwWindowShouldClose(windowHandle) ) {
            // While it's open, check for events.
            glfwPollEvents();
        }

        // Not necessary, because the OS will delete everything anyway,
        // but still good practice.
        glfwDestroyWindow(windowHandle);
        glfwTerminate();

        // All done!

    }

}
