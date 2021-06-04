package org.dhsdev.flowerknight.gl;

import static org.lwjgl.opengl.GL33.*;

import de.matthiasmann.twl.utils.PNGDecoder;
import org.dhsdev.flowerknight.util.Logger;
import org.dhsdev.flowerknight.util.Severity;
import org.lwjgl.system.MemoryUtil;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.EnumMap;
import java.util.Map;

/**
 * Contains all textures.
 * @author adamhutchings
 */
public final class TextureAtlas {

    /**
     * NO!
     */
    private TextureAtlas() {}

    /**
     * The map from textures to paths.
     */
    private static final Map<TextureEnum, String> paths = new EnumMap<>(TextureEnum.class);

    static {
        paths.put(TextureEnum.LOGO, "res/logo.png");
    }

    /**
     * What size every texture must be.
     */
    private static final int TEX_SIZE = 1024;

    /**
     * The single tex ID.
     */
    private static int atlasId;

    public static void loadAllTextures() {

        // All of the data from the textures.
        var atlasData = ByteBuffer.allocateDirect(4 * TEX_SIZE * TEX_SIZE * TextureEnum.values().length);

        // Decode each texture into the buffer
        for (TextureEnum tex : TextureEnum.values()) {

            String fileName = paths.get(tex);

            PNGDecoder decoder = null;

            try {
                decoder = new PNGDecoder(new FileInputStream(fileName));
            } catch (IOException e) {
                Logger.log("Could not load texture " + fileName, Severity.ERROR);
            }

            // Should never happen.
            if (decoder == null) {
                Logger.log("Decoder was null", Severity.ERROR);
            }

            int width = decoder.getWidth();
            int height = decoder.getHeight();

            // Check to make sure it will evenly fit into the atlas.
            if ( (width != TEX_SIZE) || (height != TEX_SIZE) ) {
                Logger.log("Image " + fileName + " was not 1024x1024, was " + width + "x" + height + "instead.", Severity.ERROR);
            }

            try {
                decoder.decode(atlasData, TEX_SIZE * 4 * TextureEnum.values().length, PNGDecoder.Format.RGBA);
                } catch (IOException e) {
                Logger.log("Could not decode texture " + fileName, Severity.ERROR);
            }

        }

        atlasData.flip();

        // This is the only texture that should ever be loaded.
        atlasId = glGenTextures();

        glBindTexture(GL_TEXTURE_2D, atlasId);

        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, TEX_SIZE,
                TEX_SIZE, 0, GL_RGBA, GL_UNSIGNED_BYTE, atlasData
        );

        glGenerateMipmap(GL_TEXTURE_2D);

        // This will always be the active texture.
        glActiveTexture(GL_TEXTURE0);

        MemoryUtil.memFree(atlasData);

    }

    public static void delete() {
        glDeleteTextures(atlasId);
    }

}
