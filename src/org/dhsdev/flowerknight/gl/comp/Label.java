package org.dhsdev.flowerknight.gl.comp;

import org.dhsdev.flowerknight.gl.*;
import org.dhsdev.flowerknight.gl.Component;
import org.dhsdev.flowerknight.util.Logger;
import org.dhsdev.flowerknight.util.Severity;
import org.lwjgl.system.MemoryUtil;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

/**
 * A label that can display text WIP
 * Right now it just creates an image
 * @author cameron
 */
public class Label extends Component  {

    private String text;
    private int x;
    private int y;
    private KFont font;

    /**
     * Creates a new instance of a label
     * @param name the name of this component
     * @param text the text to be displayed
     * @param x the x location
     * @param y the y location
     * @param offsetX the x offset
     * @param offsetY the y offset
     */
    public Label(KFont font, String name, String text, int x, int y) {
        super(name);
        this.font = font;
        this.text = text;
        this.x = x;
        this.y = y;
    }

    @Override
    public void draw() {
        font.drawText(text, x, y, Color.WHITE);
    }

    @Override
    public void clear() {
    }


}
