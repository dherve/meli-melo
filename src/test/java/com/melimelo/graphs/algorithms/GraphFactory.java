package com.melimelo.graphs.algorithms;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.melimelo.graphs.Edge;
import com.melimelo.graphs.Graph;
import com.melimelo.graphs.Vertex;

/**
 * Create graphs and define constants values used for testing.
 */
@SuppressWarnings("serial")
public class GraphFactory {
    public static final String SINGLETON_GRAPH_VERTEX_LABEL = "A";
    public static final boolean DIRECTED = true;
    public static final boolean WEIGHTED = true;
    public static final BigDecimal DEFAULT_WEIGHT = BigDecimal.ONE;

    private static final Set<Edge> UNWEIGHTED_TREE_EDGES = new HashSet<Edge>() {
        {
            add(new Edge("A", "B"));
            add(new Edge("A", "D"));
            add(new Edge("B", "C"));
            add(new Edge("B", "E"));
            add(new Edge("E", "F"));
            add(new Edge("E", "G"));
            add(new Edge("G", "H"));
            add(new Edge("H", "J"));
            add(new Edge("I", "J"));
        }
    };
    private static final Set<Edge> WEIGHTED_TREE_EDGES = new HashSet<Edge>() {
        {
            add(new Edge("A", "B", BigDecimal.valueOf(1)));
            add(new Edge("A", "D", BigDecimal.valueOf(8)));
            add(new Edge("B", "C", BigDecimal.valueOf(5)));
            add(new Edge("B", "E", BigDecimal.valueOf(10)));
            add(new Edge("E", "F", BigDecimal.valueOf(9)));
            add(new Edge("E", "G", BigDecimal.valueOf(1)));
            add(new Edge("G", "H", BigDecimal.valueOf(9)));
            add(new Edge("H", "J", BigDecimal.valueOf(1)));
            add(new Edge("I", "J", BigDecimal.valueOf(4)));
        }
    };
    private static final Set<Edge> UNWEIGHTED_EDGES = new HashSet<Edge>() {
        {
            add(new Edge("A", "B"));
            add(new Edge("A", "D"));
            add(new Edge("B", "C"));
            add(new Edge("B", "D"));
            add(new Edge("B", "E"));
            add(new Edge("C", "F"));
            add(new Edge("D", "E"));
            add(new Edge("D", "G"));
            add(new Edge("E", "C"));
            add(new Edge("E", "F"));
            add(new Edge("E", "G"));
            add(new Edge("F", "H"));
            add(new Edge("G", "F"));
            add(new Edge("G", "I"));
            add(new Edge("G", "H"));
            add(new Edge("H", "J"));
            add(new Edge("I", "J"));
        }
    };

    private static final Set<Edge> WEIGHTED_EDGES = new HashSet<Edge>() {
        {
            add(new Edge("A", "B", BigDecimal.valueOf(1)));
            add(new Edge("A", "D", BigDecimal.valueOf(8)));
            add(new Edge("B", "C", BigDecimal.valueOf(5)));
            add(new Edge("B", "D", BigDecimal.valueOf(3)));
            add(new Edge("B", "E", BigDecimal.valueOf(10)));
            add(new Edge("C", "F", BigDecimal.valueOf(5)));
            add(new Edge("D", "E", BigDecimal.valueOf(2)));
            add(new Edge("D", "G", BigDecimal.valueOf(7)));
            add(new Edge("E", "C", BigDecimal.valueOf(1)));
            add(new Edge("E", "F", BigDecimal.valueOf(9)));
            add(new Edge("E", "G", BigDecimal.valueOf(1)));
            add(new Edge("F", "H", BigDecimal.valueOf(2)));
            add(new Edge("G", "F", BigDecimal.valueOf(3)));
            add(new Edge("G", "I", BigDecimal.valueOf(6)));
            add(new Edge("G", "H", BigDecimal.valueOf(9)));
            add(new Edge("H", "J", BigDecimal.valueOf(1)));
            add(new Edge("I", "J", BigDecimal.valueOf(4)));
        }
    };

    public static final Graph createNullGraph() {
        return null;
    }

    public static final Graph createEmptyGraph() {
        return new Graph(DIRECTED, WEIGHTED);
    }

    public static final Graph createSingletonGraph() {
        Set<Vertex> vertices = new HashSet<Vertex>();
        vertices.add(new Vertex(SINGLETON_GRAPH_VERTEX_LABEL));
        Graph graph = new Graph(!DIRECTED, WEIGHTED,
                Collections.<Edge> emptySet(), vertices);
        return graph;
    }

    public static final Graph createDirectedWeightedCyclicGraph() {
        Graph graph = new Graph(DIRECTED, WEIGHTED, WEIGHTED_EDGES);
        graph.addEdge(new Edge("J", "G", DEFAULT_WEIGHT));
        return graph;
    }

    public static final Graph createDirectedUnWeightedCyclicGraph() {
        Graph graph = new Graph(DIRECTED, !WEIGHTED, UNWEIGHTED_EDGES);
        graph.addEdge(new Edge("J", "G"));
        return graph;
    }

    public static final Graph createUnDirectedWeightedCyclicGraph() {
        Graph graph = new Graph(!DIRECTED, WEIGHTED, WEIGHTED_EDGES);
        graph.addEdge(new Edge("J", "G", DEFAULT_WEIGHT));
        return graph;
    }

    public static final Graph createUnDirectedUnWeightedCyclicGraph() {
        return new Graph(!DIRECTED, !WEIGHTED, UNWEIGHTED_EDGES);
    }

    public static final Graph createDirectedWeightedAcyclicGraph() {
        return new Graph(DIRECTED, WEIGHTED, WEIGHTED_EDGES);
    }

    public static final Graph createDirectedUnWeightedAcyclicGraph() {
        return new Graph(DIRECTED, !WEIGHTED, UNWEIGHTED_EDGES);
    }

    public static final Graph createUnDirectedWeightedAcyclicGraph() {
        return new Graph(!DIRECTED, WEIGHTED, WEIGHTED_TREE_EDGES);
    }

    public static final Graph createUnDirectedUnWeightedAcyclicGraph() {
        return new Graph(!DIRECTED, !WEIGHTED, UNWEIGHTED_TREE_EDGES);
    }
}
