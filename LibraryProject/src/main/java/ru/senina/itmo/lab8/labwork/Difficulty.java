package ru.senina.itmo.lab8.labwork;

import java.util.stream.Stream;

public enum Difficulty {
    VERY_EASY(0),
    NORMAL(1),
    VERY_HARD(2),
    INSANE(3),
    HOPELESS(4);

    private final int value;

    Difficulty(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Difficulty of(int difficulty) {
        return Stream.of(Difficulty.values())
                .filter(p -> p.getValue() == difficulty)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}