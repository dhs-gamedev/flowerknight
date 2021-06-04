package org.dhsdev.flowerknight.gl;

import org.dhsdev.flowerknight.util.Logger;
import org.dhsdev.flowerknight.util.Severity;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL33.*;

/**
 * This represents one OpenGL instance of a shader. It provides a better interface
 * to shaders that can remove repetitive code from elsewhere.
 * @author adamhutchings
 */
public final class Shader {

    private final int id;

    private static Shader trivialShader;
    private static Shader gameShader;
    private static Shader spotlightShader;

    /**
     * Create a shader from its two source files.
     * @param vertLoc the file path containing the vertex shader code
     * @param fragLoc the file path containing the fragment shader code
     * @throws RuntimeException if any error occurs during compilation
     */
    private Shader(String vertLoc, String fragLoc) throws IOException {

        id = glCreateProgram();
        if (id == 0) {
            Logger.log("Could not initialize shader", Severity.ERROR);
        }

        int vertShader = loadShader(
                loadFileAsString(vertLoc), GL_VERTEX_SHADER
        );

        int fragShader = loadShader(
                loadFileAsString(fragLoc), GL_FRAGMENT_SHADER
        );

        this.linkProgram(vertShader, fragShader);

        Logger.log("Successfully created shader", Severity.DEBUG);

    }

    /**
     * Compile a shader with OpenGL.
     * @param code the string containing the shader code.
     * @param type which type of shader (vertex, fragment, etc)
     * @return the OpenGL handle of the shader
     * @throws RuntimeException if the shader doesn't compile
     */
    private int loadShader(String code, int type){

        int progId = glCreateShader(type);

        if (id == 0)
            Logger.log("Could not initialize shader program", Severity.ERROR);

        glShaderSource(progId, code);
        glCompileShader(progId);

        if (glGetShaderi(progId, GL_COMPILE_STATUS) == 0) {
            // Check for error - if so, ...
            Logger.log(
                    "Error compiling shader: " + glGetShaderInfoLog(progId, 1 << 10)
                , Severity.ERROR
            );
        }

        return progId;

    }

    /**
     * Link the vertex and fragment shaders together into one program.
     * @param vertID the vertex shader ID
     * @param fragID the fragment shader ID
     * @throws RuntimeException if some error occurs during linking
     */
    private void linkProgram(int vertID, int fragID) {

        // Attach the individual programs to the main shader program
        glAttachShader(id, vertID);
        glAttachShader(id, fragID);

        // Link it all together
        glLinkProgram(id);

        // Check for fatal errors. Non-fatal errors are fine. For sure.
        if (glGetProgrami(id, GL_LINK_STATUS) == 0) {
            Logger.log(
                    "Error compiling shader: " + glGetProgramInfoLog(id, 1 << 10)
                    , Severity.ERROR
            );
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
     * @throws IOException if the file doesn't exist or another I/O error
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

    /**
     * The uniforms of this shader - map a uniform name to its location in the
     * shader program.
     */
    private final Map<String, Integer> uniforms = new HashMap<>();

    /**
     * Register a uniform name from the shader into the uniforms map so it can
     * be edited later.
     * @param name the name of the shader in the shader program
     */
    public void registerUniform(String name) {

        int loc = glGetUniformLocation(this.id, name);
        if (loc < 0) {
            Logger.log("Could not find uniform: " + name, Severity.ERROR);
        }

        uniforms.put(name, loc);

    }

    /**
     * Set a float uniform to a value.
     * @param name the uniform name
     * @param value the float value
     */
    public void setUniform(String name, float value) {
        glUniform1f(uniforms.get(name), value);
    }

    /**
     * Set a vec2 uniform to a value.
     * @param name the uniform name
     * @param value the array of two floats
     */
    public void setUniform(String name, float[] value) {
        glUniform2fv(uniforms.get(name), value);
    }

    public static Shader getTrivialShader(){
        return trivialShader;
    }

    public static Shader getGameShader(){
        return gameShader;
    }

    public static Shader getSpotlightShader() {
        return spotlightShader;
    }

    /**
     * Initialize the shaders.
     */
    public static void init() {
        try {
            trivialShader = new Shader("src/shader/trivial_vert.glsl", "src/shader/trivial_frag.glsl");
            gameShader = new Shader("src/shader/game_vert.glsl", "src/shader/trivial_frag.glsl");
            spotlightShader = new Shader("src/shader/trivial_vert.glsl", "src/shader/spotlight_frag.glsl");
        } catch (IOException e) {
            Logger.log("IOException creating shaders: ", Severity.ERROR);
        }
        Logger.log("All shaders successfully created", Severity.DEBUG);
    }

}
