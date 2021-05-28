package org.dhsdev.flowerknight;

/**
 * Main driver code for the FlowerKnight game. Right now this has all of the
 * windowing code stuffed inside - later it should be moved somewhere else.
 * @author adamhutchings
 */
public class Main {

    /**
     * Main driver code for the application.
     * @param args command line arguments - currently unused
     */
    public static void main(String[] args) {
        FlowerKnight.init();
        FlowerKnight.mainloop();
        FlowerKnight.exit();
    }

}
