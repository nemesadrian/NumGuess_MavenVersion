/**
 * Created by condor on 29/11/14.
 * FastTrackIT, 2015
 */


/* didactic purposes
rem
Some items are intentionally not optimized or not coded in the right way

FastTrackIT 2015

*/

package org.fasttrackit.dev.lesson1.numgenerator;

import java.util.Random;

 class NumGenerator {

    public static int generate(int maximumAllowed) {
        return Math.abs(new Random().nextInt() % maximumAllowed) + 1;
    }
}