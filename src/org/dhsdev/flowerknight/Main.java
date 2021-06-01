package org.dhsdev.flowerknight;

import org.dhsdev.flowerknight.util.Logger;
import org.dhsdev.flowerknight.util.Severity;

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
        Logger.log("Main initialization", Severity.NORMAL);

        FlowerKnight.mainloop();
        FlowerKnight.exit();
        Logger.log("Main closed", Severity.NORMAL);
    }

}
