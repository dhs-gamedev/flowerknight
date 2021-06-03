package org.dhsdev.flowerknight.util;

import org.dhsdev.flowerknight.gl.Mesh;
import org.dhsdev.flowerknight.gl.Shader;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;

public class RenderUtil {
    /**
     * Draws a region of a texture
     * @param origin the size and location of the quad
     * @param texture the size and location of the portion of texture to be drawn
     * @param textureDimensions the total size of the texture
     * @param color the color to be drawn in or null
     */
    public static void drawRegion(int tex, Rectangle origin, Rectangle texture, Dimension textureDimensions, Color color) {
        float x1 = origin.x;
        float y1 = origin.y;
        float x2 = origin.x + origin.width;
        float y2 = origin.y + origin.height;

        float s1 = texture.x / (float) textureDimensions.width;
        float t1 = texture.y / (float) textureDimensions.height;
        float s2 = (texture.x + texture.width) / (float) textureDimensions.width;
        float t2 = (texture.y + texture.height) / (float) textureDimensions.height;

        /*
        glBegin(GL_QUADS);

        if (color != null) {
            glColor3f(
                    ((float) color.getRed()),
                    ((float) color.getGreen()),
                    ((float) color.getBlue())
            );
        }

        glTexCoord2f(s1, t2);
        glVertex2f(x1, y1);

        glTexCoord2f(s2, t2);
        glVertex2f(x2, y1);

        glTexCoord2f(s2, t1);
        glVertex2f(x2, y2);

        glTexCoord2f(s1, t1);
        glVertex2f(x1, y2);
        glEnd();

        // Wtf why does this need to be here idk
        if (color != null) {
            glColor3f(1,1,1);
        }*/
        int[] indices = { 0, 1, 3, 3, 1, 2};
        float[] coords = { x1, y1, x2, y1, x2, y2, x1, y2 };
        float[] texcoords = { s1, t2, s2, t2, s2, t1, s1, t1 };

        Shader shader = Shader.SPOTLIGHT_SHADER;
        shader.bind();
        Mesh mesh = new Mesh(coords, indices, texcoords);
        mesh.render(tex); // You need to use a texture here
// OR you can make your own fragment shader that outputs a constant color
        mesh.destroy(); // MAKE SURE TO HAVE THIS
    }
}
