package splitter.graph;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Graph {
    private static final BigDecimal ZERO = new BigDecimal("0.00");
    private final Map<Vertex, Map<Vertex, BigDecimal>> graph;

    public Graph() {
        graph = new LinkedHashMap<>();
    }

    public Map<Vertex, Map<Vertex, BigDecimal>> getGraph() {
        return graph;
    }

    public void addVertex(Vertex vertex) {
        graph.put(vertex, new LinkedHashMap<>());
    }

    public void addEdge(Vertex from, Vertex to, BigDecimal sum) {
        graph.putIfAbsent(from, new LinkedHashMap<>());
        graph.putIfAbsent(to, new LinkedHashMap<>());
        Map<Vertex, BigDecimal> edges = graph.get(from);
        edges.put(to, sum);
    }

    public List<Vertex> findCylce() {
        boolean found = false;
        List<Vertex> path = new ArrayList<>();
        for (Vertex vertex : graph.keySet()) {
            if (vertex.getVisited() == VisitStatus.WHITE) {
                found = dfs(vertex, path);
                if (found) {
                    break;
                }
            }
        }

        if (found) {
            Vertex cycleStart = path.get(path.size() - 1);
            path.remove(path.size() - 1);
            while (path.size() > 0 && path.get(0) != cycleStart) {
                path.remove(0);
            }
        }

        return path;
    }

    public boolean dfs(Vertex vertex, List<Vertex> path) {
        vertex.setVisited(VisitStatus.GRAY);
        path.add(vertex);

        for (Vertex v : graph.get(vertex).keySet()) {
            if (v.getVisited() == VisitStatus.GRAY) {
                path.add(v);
                return true;
            }
            if (dfs(v, path)) {
                return true;
            }
        }

        vertex.setVisited(VisitStatus.BLACK);
        path.remove(vertex);
        return false;
    }

    public void clearVisited() {
        for (Vertex vertex : graph.keySet()) {
            vertex.setVisited(VisitStatus.WHITE);
        }
    }

    public BigDecimal getThroughputSum(List<Vertex> path) {
        BigDecimal minSum = null;
        Vertex from = null;
        for (Vertex to : path) {
            if (from == null) {
                from = to;
                continue;
            }
            Map<Vertex, BigDecimal> edges = graph.get(from);
            BigDecimal sum = edges.get(to);
            minSum = minSum == null ? sum : minSum.min(sum);
            from = to;
        }

        return minSum;
    }

    public void reducePathSumsAndEdges(List<Vertex> path, BigDecimal sum) {
        if (path.size() < 2) {
            return;
        }

        Vertex from = null;
        for (Vertex to : path) {
            if (from == null) {
                from = to;
                continue;
            }
            reduceEdge(from, to, sum);
            from = to;
        }
        if (from != null) {
            reduceEdge(from, path.get(0), sum);
        }
    }

    private void reduceEdge(Vertex from, Vertex to, BigDecimal sum) {
        Map<Vertex, BigDecimal> edges = graph.get(from);
        BigDecimal newSum = edges.get(to).subtract(sum);
        if (ZERO.equals(newSum)) {
            edges.remove(to);
        } else {
            edges.put(to, newSum);
        }
    }
}
