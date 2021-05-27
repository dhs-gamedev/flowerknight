package org.dhsdev.flowerknight;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
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

        // GLFW creates an OpenGL context by default later, but we don't actually
        // need it if we're using Vulkan.
        glfwWindowHint(GLFW_CLIENT_API, GLFW_NO_API);

        // Don't ask me what the two NULLs are for. I have no clue.
        windowHandle = glfwCreateWindow(500, 500, "FlowerKnight", NULL, NULL);

        glfwShowWindow(windowHandle);

    }

    /**
     * Run the "core body" of the game. Right now, this checks for events while
     * the window is open and nothing else.
     */
    public static void mainloop() {

        while ( !glfwWindowShouldClose(windowHandle) ) {
            // While it's open, check for events.
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
