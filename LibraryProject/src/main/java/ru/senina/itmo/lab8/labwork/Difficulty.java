package ru.senina.itmo.lab8.labwork;

import java.util.stream.Stream;

public enum Difficulty {
    VERY_EASY(0),
    NORMAL(1),
    VERY_HARD(2),
    INSANE(3),
    HOPELESS(4);

    private final int value;
    private final String str;

    Difficulty(int difficulty) {
        this.value = difficulty;
        this.str = super.name();
    }

    public int getDifficulty() {
        return value;
    }

    public static Difficulty of(int difficulty) {
        return Stream.of(Difficulty.values())
                .filter(p -> p.getDifficulty() == difficulty)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public String toString() {
        return str;
    }
}