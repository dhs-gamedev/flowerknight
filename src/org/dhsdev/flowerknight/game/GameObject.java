package org.dhsdev.flowerknight.game;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents one in-game object.
 * @author adamhutchings
 */
public class GameObject {

    /**
     * The strength of gravity. Until we get an actual game object, we have no
     * clue how fast this will make things fall, so keep it zero for now.
     */
    public static final float GRAVITY_STRENGTH = 0.0f;

    /**
     * The x-position.
     */
    protected float x;

    /**
     * The y-position.
     */
    protected float y;

    /**
     * The x velocity.
     */
    protected float xVelocity;

    /**
     * The y velocity.
     */
    protected float yVelocity;

    /**
     * Whether this thing falls.
     */
    protected boolean hasGravity = true;

    /**
     * Adds the objects to the list.
     * @param xP the x location
     * @param yP the y location
     */
    public GameObject(float xP, float yP) {
        this.x = xP;
        this.y = yP;
        objects.add(this);
    }

    /**
     * Override this to say what this object does on its tick. External behavior
     * like collision detection, gravity, do not need to be included.
     */
    protected void tick() {

    }

    /**
     * This updates the object according to external forces. Please only make an
     * override to this if you have to, and you MUST call super.update() or else
     * things will break.
     */
    protected void update() {

        // Update gravity
        if (hasGravity)
            yVelocity -= GameObject.GRAVITY_STRENGTH;

        // TODO - collision, etc.

    }

    /**
     * This contains all game objects in the game.
     */
    private static final List<GameObject> objects = new ArrayList();

    /**
     * Tick everything, then update.
     */
    public static void updateAll() {

        for (var obj : objects) {
            obj.tick();
        }

        for (var obj : objects) {
            obj.update();
        }

    }

}
