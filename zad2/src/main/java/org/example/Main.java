package org.example;

import guru.nidi.graphviz.attribute.Label;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.model.MutableNode;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static guru.nidi.graphviz.model.Factory.mutGraph;
import static guru.nidi.graphviz.model.Factory.mutNode;

public class Main {

    public static void main(String[] args) throws IOException {
        Map<Character, Integer> variables = new HashMap<>(Map.of('x', 5, 'y', 10, 'z', 7));
        List<Character> A = List.of('a', 'b', 'c', 'd');
        List<String> operationsInput = List.of("x = x + y", "y = y + 2z", "x = 3x + z", "z = y - z");
        char[] word = "baadcb".toCharArray();
        run(variables, A, operationsInput, word);

//        variables = new HashMap<>(Map.of('x', 5, 'y', 10, 'z', 7, 'w', 3, 'v', 2)); A = List.of('a', 'b', 'c', 'd', 'e', 'f');
//        operationsInput = List.of("x = x + 1", "y = y + 2z", "x = 3x + z", "w = w + v", "z = y - z", "v = x + v");
//        word = "acdcfbbe".toCharArray();
//        run(variables, A, operationsInput, word);
    }

    public static void run(Map<Character, Integer> variables, List<Character> A, List<String> operationsInput, char[] word) throws IOException {
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

        List<List<Character>> FNF = calculateFoataClasses(word, dependencies);
        System.out.println(FNF);
        MutableGraph graph = generateFoataGraph(FNF, dependencies);
        Graphviz.fromGraph(graph).render(Format.PNG).toFile(new File("foata-graph.png"));
        System.out.println("The graph has been generated as foata-graph.png");
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

    private static MutableGraph generateFoataGraph(List<List<Character>> fnf, List<Pair<Character>> dependencies) {
        MutableGraph graph = mutGraph("Foata Graph").setDirected(true);
        Map<MyNode, Character> nodes = new HashMap<>();
        List<List<MyNode>> nodesInClasses = new ArrayList<>();

        for (int i = 0; i < fnf.size(); i++) {
            nodesInClasses.add(new ArrayList<>());
        }

        int index = 0;

        for (int classIndex = 0; classIndex < fnf.size(); classIndex++) {
            List<Character> foataClass = fnf.get(classIndex);
            for (char el : foataClass) {
                MutableNode node = mutNode(String.valueOf(index)).add(Label.of(String.valueOf(el)));
                MyNode myNode = new MyNode(index, el, node, false);
                nodesInClasses.get(classIndex).add(myNode);
                nodes.put(myNode, el);
                graph.add(node);
                index++;
            }
        }

        for (int classIndex = 1; classIndex < nodesInClasses.size(); classIndex++) {
            for (MyNode currentNode : nodesInClasses.get(classIndex)) {
                Character currentChar = nodes.get(currentNode);
                boolean flag = true;
                for (int olderClassIndex = classIndex - 1; olderClassIndex >= 0; olderClassIndex--) {
                    for (MyNode olderNode: nodesInClasses.get(olderClassIndex)) {
                        Character olderChar = nodes.get(olderNode);
                        if (isDependent(olderChar, currentChar, dependencies) && (!olderNode.haveEdge() || flag) ) {
                            System.out.println("Adding edge from " + olderChar + " to " + currentChar);
                            olderNode.node().addLink(currentNode.node());
                            olderNode.setHaveEdge(true);
                            flag = false;
                        }
                    }
                }
            }
        }

        return graph;
    }

    // Funkcja pomocnicza sprawdzająca, czy dwie zmienne są zależne na podstawie listy dependencies
    private static boolean isDependent(char from, char to, List<Pair<Character>> dependencies) {
        return dependencies.contains(new Pair<>(from, to));
    }

    private static class MyNode {
        private final int index;
        private final char value;
        private final MutableNode node;
        private boolean haveEdge;
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            MyNode myNode = (MyNode) o;
            return index == myNode.index && value == myNode.value;
        }

        @Override
        public int hashCode() {
            return Objects.hash(index, value);
        }

        public MyNode(int index, char value, MutableNode node, boolean haveEdge) {
            this.index = index;
            this.value = value;
            this.node = node;
            this.haveEdge = haveEdge;
        }
        public MutableNode node() {
            return node;
        }

        public boolean haveEdge() {
            return haveEdge;
        }

        public void setHaveEdge(boolean haveEdge) {
            this.haveEdge = haveEdge;
        }

    }
}
