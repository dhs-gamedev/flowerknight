package org.dhsdev.flowerknight.gl.comp;

import org.dhsdev.flowerknight.gl.*;
import org.dhsdev.flowerknight.util.Logger;
import org.dhsdev.flowerknight.util.Severity;

import java.io.IOException;

/**
 * A label that can display text WIP
 * Right now it just creates an image
 * @author cameron
 */
public class Label extends Component  {

    private String text;
    private float x;
    private float y;
    private float offsetX;
    private float offsetY;
    private Mesh mesh;
    private TextureEnum tex;
    private Shader shader;

    /**
     * Creates a new instance of a label
     * @param name the name of this component
     * @param text the text to be displayed
     * @param x the x location
     * @param y the y location
     * @param offsetX the x offset
     * @param offsetY the y offset
     */
    public Label(String name, String text, float x, float y, float offsetX, float offsetY) {
        super(name);
        this.text = text;
        this.x = x;
        this.y = y;

        shader = Shader.getSpotlightShader();

        var positions = new float[] {
                -x + offsetX, -y + offsetY,
                -x + offsetX,  y + offsetY,
                x + offsetX,  y + offsetY,
                x + offsetX, -y + offsetY,
        };

        var indices = new int[] {
                0, 1, 3, 3, 1, 2,
        };

        mesh = new Mesh(positions, indices);

    }

    @Override
    public void draw() {
        shader.bind();
        mesh.render(TextureEnum.LOGO);
    }

    @Override
    public void clear() {
        mesh.destroy();
        shader.destroy();
    }

}
