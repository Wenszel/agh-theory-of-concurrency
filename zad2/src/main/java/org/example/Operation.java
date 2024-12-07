package org.example;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Operation implements Runnable {
    public static final String OPERATION_REGEX = "([a-z])\\s*=\\s*(\\d*)([a-z]*)\\s*([*\\-+])\\s*(\\d*)([a-z]*)";
    public static final char NO_VARIABLE = '-';
    private final char symbol;
    private final char varToModify;
    private Runnable operation;
    // The purpose of storing the variables is to be able to generate dependencies between operations
    private Set<Character> variablesUsedInOperation;

    public Operation(char symbol, String expression, Map<Character, Integer> variables) {
        this.symbol = symbol;
        Pattern pattern = Pattern.compile(OPERATION_REGEX);
        Matcher matcher = pattern.matcher(expression);

        if (!matcher.matches()) throw new IllegalArgumentException();

        varToModify = matcher.group(1).charAt(0);
        int multiplier1 = matcher.group(2).isEmpty() ? 1 : Integer.parseInt(matcher.group(2));
        char var1 =  matcher.group(3).isEmpty() ? NO_VARIABLE : matcher.group(3).charAt(0);
        char operator = matcher.group(4).charAt(0);
        int multiplier2 = matcher.group(5).isEmpty() ? 1 : Integer.parseInt(matcher.group(5));
        char var2 = matcher.group(6).isEmpty() ? NO_VARIABLE : matcher.group(6).charAt(0);

        initVariablesUsedInOperation(var1, var2, varToModify);
        initOperation(variables, multiplier1, var1, multiplier2, var2, operator, varToModify);
    }

    private void initOperation(Map<Character, Integer> variables, int multiplier1, char var1, int multiplier2, char var2, char operator, char varToModify) {
        operation = () -> {
            int value1 = multiplier1 * variables.getOrDefault(var1, 1);
            int value2 = multiplier2 * variables.getOrDefault(var2, 1);
            int result = switch (operator) {
                case '+' -> value1 + value2;
                case '-' -> value1 - value2;
                case '*' -> value1 * value2;
                default -> throw new IllegalArgumentException("Unsupported operator: " + operator);
            };
            variables.put(varToModify, result);
        };
    }

    private void initVariablesUsedInOperation(char... variables) {
        variablesUsedInOperation = new HashSet<>();
        for (char variable : variables) {
            variablesUsedInOperation.add(variable);
        }
    }

    @Override
    public void run() {
        operation.run();
    }

    public boolean isDependent(Operation operation) {
        return operation.variablesUsedInOperation.contains(varToModify);
    }

    public char getSymbol() {
        return symbol;
    }
}
