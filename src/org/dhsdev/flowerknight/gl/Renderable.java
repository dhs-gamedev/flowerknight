package org.dhsdev.flowerknight.gl;

import java.util.ArrayList;

/**
 * Represents a renderable object
 * @author cameron
 */
public interface Renderable {
    public static ArrayList<Renderable> renderables = new ArrayList<Renderable>();

    /**
     * Draws this object
     */
    void draw();

    /**
     * Cleans up this object
     */
    void clear();

}
