package org.example;

import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Operation implements Runnable {
    public static final String OPERATION_REGEX = "([a-z])\\s*=\\s*(\\d*)([a-z])\\s*([*\\-+])\\s*(\\d*)([a-z])";
    private final Runnable operation;
    // The purpose of storing the variables is to be able to generate dependencies between operations
    private final Set<Character> variablesUsedInOperation;

    public Operation(String expression, Map<Character, Integer> variables) {
        Pattern pattern = Pattern.compile(OPERATION_REGEX);
        Matcher matcher = pattern.matcher(expression);

        if (!matcher.matches()) throw new IllegalArgumentException();

        char varToModify = matcher.group(1).charAt(0);
        int multiplier1 = matcher.group(2).isEmpty() ? 1 : Integer.parseInt(matcher.group(2));
        char var1 = matcher.group(3).charAt(0);
        char operator = matcher.group(4).charAt(0);
        int multiplier2 = matcher.group(5).isEmpty() ? 1 : Integer.parseInt(matcher.group(5));
        char var2 = matcher.group(6).charAt(0);
        this.variablesUsedInOperation = Set.of(varToModify, var1, var2);

        this.operation = () -> {
            int value1 = multiplier1 * variables.get(var1);
            int value2 = multiplier2 * variables.get(var2);
            int result = switch (operator) {
                case '+' -> value1 + value2;
                case '-' -> value1 - value2;
                case '*' -> value1 * value2;
                default -> throw new IllegalArgumentException("Unsupported operator: " + operator);
            };
            variables.put(varToModify, result);
        };
    }

    @Override
    public void run() {
        operation.run();
    }

    public Set<Character> getVariablesUsedInOperation() {
        return variablesUsedInOperation;
    }
}
