package splitter.command;

import splitter.controller.Controller;
import splitter.graph.Graph;
import splitter.graph.Vertex;
import splitter.model.Person;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BalancePerfectCommand extends BalanceCommand implements Command {

    @Override
    public void execute(Controller controller) {
        Map<Person, Map<Person, BigDecimal>> balanceMap = getBalanceMap(controller);
        Graph graph = createGraph(balanceMap);
        while (true) {
            List<Vertex> cylce = graph.findCylce();
            if (cylce.isEmpty()) {
                break;
            }

            BigDecimal throughputSum = graph.getThroughputSum(cylce);
            graph.reducePathSumsAndEdges(cylce, throughputSum);

            graph.clearVisited();
        }

        List<String> balanceList = getBalanceList(graph);
        controller.getView().printBalance(balanceList);
    }

    private Graph createGraph(Map<Person, Map<Person, BigDecimal>> balanceMap) {
        Map<Person, Vertex> vertexes = new HashMap<>();
        for (Person person : balanceMap.keySet()) {
            vertexes.putIfAbsent(person, new Vertex(person));
        }
        Graph graph = new Graph();
        for (Map.Entry<Person, Map<Person, BigDecimal>> from : balanceMap.entrySet()) {
            for (Map.Entry<Person, BigDecimal> to : from.getValue().entrySet()) {
                graph.addEdge(
                        vertexes.get(from.getKey()),
                        vertexes.get(to.getKey()),
                        to.getValue());
            }
        }
        return graph;
    }

    private List<String> getBalanceList(Graph graph) {
        List<String> balanceList = new ArrayList<>();
        for (Map.Entry<Vertex, Map<Vertex, BigDecimal>> from : graph.getGraph().entrySet()) {
            for (Map.Entry<Vertex, BigDecimal> to : from.getValue().entrySet()) {
                balanceList.add(String.format("%s owes %s %s",
                        from.getKey().getPerson().getName(),
                        to.getKey().getPerson().getName(),
                        to.getValue().toString()));
            }
        }
        return balanceList;
    }
}
