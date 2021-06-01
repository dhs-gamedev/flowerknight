package org.dhsdev.flowerknight.gl;

public abstract class Component implements Renderable {

    private String name;

    public Component(String name) {
        this.name = name;
    }

}
