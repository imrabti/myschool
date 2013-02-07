package com.gsr.myschool.server.util;

import java.util.Random;

public class UUIDGenerator {
    private static final int[] pins = new int[900000];
    private static int pinCount;
    private static final Random random = new Random();

    static {
        for (int i = 0; i < pins.length; i++) {
            pins[i] = 100000 + i;
        }
    }

    public static int generateUUID() {
        if (pinCount >= pins.length) {
            throw new IllegalStateException();
        }
        int index = random.nextInt(pins.length - pinCount) + pinCount;
        int pin = pins[index];
        pins[index] = pins[pinCount++];

        return pin;
    }
}
