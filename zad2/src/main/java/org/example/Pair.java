package org.example;

public record Pair<T>(T key1, T key2) {
    @Override
    public String toString() {
        return "(" + key1 + "," + key2 + ")";
    }
}
