package org.riversun.ml.fakedatamaker;

import java.util.Random;

public class MyMath {
    private static long sSeed = 0;
    private static Random sRandom = null;

    public static void setSeed(long seed) {
        sSeed = seed;
        sRandom = new Random(sSeed);
    }

    public static double random() {
        if (sRandom == null) {
            sRandom = new Random();
        }
        return sRandom.nextDouble();
    }
}
