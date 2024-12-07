package org.example;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Map<Character, Integer> variables = new HashMap<>(Map.of('x', 5, 'y', 10, 'z', 7));
        List<Character> A = List.of('a', 'b', 'c', 'd');
        List<String> operationsInput = List.of("x = x + y", "y = y + 2z", "x = 3x + z", "z = y - z");

        Map<Character, Operation> availableOperations = new HashMap<>();

        for (int i = 0; i < operationsInput.size(); i++) {
            availableOperations.put(A.get(i), new Operation(A.get(i), operationsInput.get(i), variables));
        }

        List<Pair<Character>> dependencies = new ArrayList<>();
        List<Pair<Character>> independencies = new ArrayList<>();
        for (Operation operation1 : availableOperations.values()) {
            for (Operation operation2 : availableOperations.values()) {
                Pair<Character> pair = new Pair<>(operation1.getSymbol(), operation2.getSymbol());
                if (operation1.isDependent(operation2) || operation2.isDependent(operation1)) {
                    dependencies.add(pair);
                } else {
                    independencies.add(pair);
                }
            }
        }
        System.out.println("D = {" + dependencies + "}");
        System.out.println("I = {" + independencies + "}");


        char[] word = "baadcb".toCharArray();
        System.out.println(calculateFoataClasses(word, dependencies));
    }

    private static List<List<Character>> calculateFoataClasses(char[] word, List<Pair<Character>> dependencies) {
        int numberOfActions = word.length;
        int takenActions = 0;
        List<List<Character>> foataClasses = new ArrayList<>();
        boolean[] taken = new boolean[numberOfActions];
        List<Character> currentClass = new ArrayList<>();
        while (takenActions < numberOfActions) {
            for (int i = 0; i < numberOfActions; i++) {
                if (taken[i]) continue;
                if (canAddToCurrentClass(i, word, taken, currentClass, dependencies )) {
                    currentClass.add(word[i]);
                    taken[i] = true;
                    takenActions++;
                }
            }
            foataClasses.add(currentClass);
            currentClass = new ArrayList<>();
        }
        return foataClasses;
    }

    private static boolean canAddToCurrentClass(int index, char[] word, boolean[] taken, List<Character> currentClass, List<Pair<Character>> dependencies) {
        // czy w obecnej klasie sa jakies ktore przeszkadzaja
        for (char w: currentClass) {
            if (dependencies.contains(new Pair<>(w, word[index]))){
                return false;
            }
        }
        // czy przed ta klasa te ktore nie sa wziete przeszakdzaja
        for (int i = index-1; i >= 0; i--) {
            if (taken[i]) continue;
            if (dependencies.contains(new Pair<>(word[index], word[i] ))) {
                return false;
            }
        }
        return true;
    }
}
