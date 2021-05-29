package org.dhsdev.flowerknight.gl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.lwjgl.opengl.GL33.*;

/**
 * This represents one OpenGL instance of a shader. It provides a better interface
 * to shaders that can remove repetitive code from elsewhere.
 * @author adamhutchings
 */
public final class Shader {

    private final int id;

    /**
     * Create a shader from its two source files.
     * @param vertLoc the file path containing the vertex shader code
     * @param fragLoc the file path containing the fragment shader code
     * @throws Exception if any error occurs during compilation
     */
    public Shader(String vertLoc, String fragLoc) throws GLException, IOException {

        id = glCreateProgram();
        if (id == 0) {
            throw new GLException("Could not initialize shader");
        }

        int vertShader = loadShader(
                loadFileAsString(vertLoc), GL_VERTEX_SHADER
        );

        int fragShader = loadShader(
                loadFileAsString(fragLoc), GL_FRAGMENT_SHADER
        );

        this.linkProgram(vertShader, fragShader);

    }

    /**
     * Compile a shader with OpenGL.
     * @param code the string containing the shader code.
     * @param type which type of shader (vertex, fragment, etc)
     * @return the OpenGL handle of the shader
     * @throws Exception if the shader doesn't compile
     */
    private int loadShader(String code, int type) throws GLException {

        int progId = glCreateShader(type);

        // TODO - check for error
        // if (id == 0) { ... }

        glShaderSource(progId, code);
        glCompileShader(progId);

        if (glGetShaderi(progId, GL_COMPILE_STATUS) == 0) {
            // Check for error - if so, ...
            String msg = glGetShaderInfoLog(progId, 1 << 10);
            // TODO - use msg to log an error. For now ...
            throw new GLException("Unable to compile shader, reason:\n" + msg);
        }

        return progId;

    }

    /**
     * Link the vertex and fragment shaders together into one program.
     * @param vertID the vertex shader ID
     * @param fragID the fragment shader ID
     * @throws Exception if some error occurs during linking
     */
    private void linkProgram(int vertID, int fragID) throws GLException {

        // Attach the individual programs to the main shader program
        glAttachShader(id, vertID);
        glAttachShader(id, fragID);

        // Link it all together
        glLinkProgram(id);

        // Check for fatal errors. Non-fatal errors are fine. For sure.
        if (glGetProgrami(id, GL_LINK_STATUS) == 0) {
            throw new GLException("Error linking shader: " + glGetProgramInfoLog(id, 1 << 10));
        }

        // We don't need the individual shaders clogging up memory any longer.
        // Their code is already bound to the main shader.
        glDetachShader(id, vertID);
        glDetachShader(id, fragID);

    }

    /**
     * Read a file and return its contents as a string, the way OpenGL expects
     * the shader code to be passed as input.
     * @param fileName which file to read
     * @return the complete contents of the file
     * @throws Exception if the file doesn't exist or another I/O error
     */
    private static String loadFileAsString(String fileName) throws IOException {
        return Files.readString(Path.of(fileName));
    }

    /**
     * Bind this shader, so that rendering operations will use it.
     */
    public void bind() {
        GLStates.bindID(this.id);
    }

    /**
     * Destroy the shader and clear memory.
     */
    public void destroy() {
        // No need to unbind, because nothing should be rendering after anyway.
        glDeleteShader(id);
    }

}
