package org.example;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Map<Character, Integer> variables = new HashMap<>(Map.of('x', 5, 'y', 10, 'z', 7));
        List<Character> A = List.of('a', 'b', 'c', 'd');
        Map<Character, Operation> availableOperations = new HashMap<>();
        List<String> operationsInput = List.of("x = x + y", "y = y + 2z", "x = 3x + z", "z = y - z");

        for (int i = 0; i < operationsInput.size(); i++) {
            availableOperations.put(A.get(i), new Operation(operationsInput.get(i), variables));
        }

        char[] word = "baadcb".toCharArray();
        for(char action: word) {
            System.out.println(variables);
            availableOperations.get(action).run();
        }
        System.out.println(variables);
    }
}