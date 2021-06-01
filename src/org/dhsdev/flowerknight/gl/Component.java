package org.dhsdev.flowerknight.gl;

/**
 * Represents a flat ui component object
 * @author cameron
 */
public abstract class Component implements Renderable {

    private String name;

    /**
     * Creates a new instance of ui component
     * @param name the name of the componenet
     */
    protected Component(String name) {
        this.name = name;

    }

}
