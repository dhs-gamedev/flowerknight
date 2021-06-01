package org.dhsdev.flowerknight.gl;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a renderable object
 * @author cameron
 */
public interface Renderable {
    List<Renderable> renderables = new ArrayList<>();

    /**
     * Draws this object
     */
    void draw();

    /**
     * Cleans up this object
     */
    void clear();

}
