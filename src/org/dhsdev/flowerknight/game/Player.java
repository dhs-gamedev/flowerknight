package org.dhsdev.flowerknight.game;

import org.dhsdev.flowerknight.gl.Mesh;
import org.dhsdev.flowerknight.gl.Renderable;
import org.dhsdev.flowerknight.gl.Texture;

/**
 * A player that exists.
 */
public class Player extends GameObject implements Renderable {

    private Mesh mesh;

    /**
     * Adds the object to the list.
     * @param xP the x location
     * @param yP the y location
     */
    public Player(float xP, float yP) {
        super(xP, yP);
    }

    @Override
    public void draw() {
        mesh = new Mesh(
                new float[] {
                        (float) (x - 0.1), (float) (y - 0.1),
                        (float) (x - 0.1), (float) (y + 0.1),
                        (float) (x + 0.1), (float) (y + 0.1),
                        (float) (x + 0.1), (float) (y - 0.1),
                },
                new int[] {
                        0, 1, 3, 3, 1, 2
                }
        );
        mesh.render(Texture.ARDEN);
    }

    @Override
    public void clear() {
        // Nothing for now.
    }
}
