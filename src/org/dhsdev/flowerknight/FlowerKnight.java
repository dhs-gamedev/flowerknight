package org.dhsdev.flowerknight;

import org.dhsdev.flowerknight.gl.Renderable;
import org.dhsdev.flowerknight.gl.comp.Label;
import org.dhsdev.flowerknight.util.TestImage;
import org.lwjgl.opengl.GL;

import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * This class contains all main control routines of the game. The "init",
 * "mainloop", and "exit" functions are all called in Main.
 * @author adamhutchings
 */
public final class FlowerKnight {

    private FlowerKnight() {
        throw new IllegalStateException("FlowerKnight Class");
    }

    private static long windowHandle;

    private static ArrayList<Renderable> renderables = new ArrayList<Renderable>();

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

        // Right now the window is non-resizable.
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);

        // Get the size of the screen height as a basis for window size.
        int monitorHeight = Objects.requireNonNull(glfwGetVideoMode(glfwGetPrimaryMonitor())).height();
        int screenHeight = (int) (monitorHeight * 0.75f);
        int screenWidth = (int) (monitorHeight * 0.75f);

        // Don't ask me what the two NULLs are for. I have no clue.
        windowHandle = glfwCreateWindow(screenWidth, screenHeight, "FlowerKnight", NULL, NULL);

        glfwMakeContextCurrent(windowHandle);
        GL.createCapabilities();

        glfwShowWindow(windowHandle);

        //TestImage.init();

        renderables.add(new Label("Test", "Test Text", 0.1f,0.1f, 0.5f, 0.1f));

    }

    /**
     * Run the "core body" of the game. Right now, this checks for events while
     * the window is open and nothing else.
     */
    public static void mainloop() {

        // While it's open, clear screen and check for events.
        while ( !glfwWindowShouldClose(windowHandle) ) {

            glClear(GL_COLOR_BUFFER_BIT);

            // Render test
            //TestImage.render();

            // Iterate and draw all renderable object
            for (Renderable renderable : renderables) {
                renderable.draw();
            }


            // Render everything to screen at once
            glfwSwapBuffers(windowHandle);

            glfwPollEvents();

        }

    }

    /**
     * Clean up everything just for good practice.
     */
    public static void exit() {

        for (Renderable renderable : renderables) {
            renderable.clear();
        }
        //TestImage.delete();

        // Not necessary, because the OS will delete everything anyway,
        // but still good practice.
        glfwDestroyWindow(windowHandle);
        glfwTerminate();

    }

}
