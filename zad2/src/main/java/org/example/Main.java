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

        char[] word = "baadcb".toCharArray();
        for(char action: word) {
            System.out.println(variables);
            availableOperations.get(action).run();
        }
        System.out.println(variables);


        List<Pair<Operation>> dependencies = new ArrayList<>();
        List<Pair<Operation>> independencies = new ArrayList<>();
        for (Operation operation1: availableOperations.values()) {
            for (Operation operation2:  availableOperations.values()) {
                Pair<Operation> pair = new Pair(operation1, operation2);
                if (operation1.isDependent(operation2) || operation2.isDependent(operation1)) {
                    dependencies.add(pair);
                } else {
                    independencies.add(pair);
                }
            }
        }
        System.out.println(dependencies);
        System.out.println(independencies);
    }
}