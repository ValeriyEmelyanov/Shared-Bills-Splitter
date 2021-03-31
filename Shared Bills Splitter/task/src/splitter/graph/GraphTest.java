package splitter.graph;

import splitter.model.Person;

import java.math.BigDecimal;
import java.util.List;

public class GraphTest {
    public static void main1(String[] args) {
        Person pBob = new Person("Bob");
        Person pChuck = new Person("Chuck");
        Person pDiana = new Person("Diana");
        Person pTim = new Person("Tim");

        Vertex vBob = new Vertex(pBob);
        Vertex vChuck = new Vertex(pChuck);
        Vertex vDiana = new Vertex(pDiana);
        Vertex vTim = new Vertex(pTim);

        Graph graph = new Graph();
        graph.addEdge(vBob, vChuck, new BigDecimal("70.00"));
        graph.addEdge(vBob, vTim, new BigDecimal("90.00"));
        graph.addEdge(vChuck, vDiana, new BigDecimal("100.00"));
        graph.addEdge(vDiana, vBob, new BigDecimal("100.00"));

        List<Vertex> cylce = graph.findCylce();
        cylce.stream()
                .map(Vertex::getPerson)
                .map(Person::getName)
                .forEach(System.out::println);

        BigDecimal throughputSum = graph.getThroughputSum(cylce);
        System.out.println(throughputSum);

        graph.reducePathSumsAndEdges(cylce, throughputSum);
        System.out.println("!");
    }
}
