package com.melimelo.graphs;

import static com.melimelo.utils.BigDecimalUtils.INFINITY;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

import com.melimelo.queues.LinkedListQueue;
import com.melimelo.unionfind.listbased.UnionFind;
import com.melimelo.utils.BigDecimalUtils;
import com.melimelo.utils.MapUtils;
import com.melimelo.validation.ValidationUtils;

/**
 * Implement common algorithms for graph traversal and path searching.
 */
public final class Algorithms {

    private static final int UNDEFINED_INDEX = -1;
    private static final IAction<Vertex> NO_OP_ACTION = new NoOpAction<Vertex>();
    private static final List<Path> NO_PATH = Collections.<Path> emptyList();

    /**
     * Perform a breadth-first search on a graph.
     * 
     * @param graph the graph on which to perform the search.
     * @param source the vertex of the graph where to start the graph traversal
     * @param action the action to apply on edges covered and or vertices
     *            visited.
     */
    public static void bfs(final Graph graph, final Vertex source,
            final IAction<Vertex> action) {
        validateArgs(graph, source, action);
        Map<Vertex, VertexStatus> verticesStatus = new HashMap<Vertex, VertexStatus>();
        for (Vertex vertex : graph.getVertices()) {
            verticesStatus.put(vertex, VertexStatus.UNDISCOVERED);
        }

        AdjacencyList adjacencyList = graph.asAdjacencyList();
        Queue<Vertex> queue = new LinkedList<Vertex>();
        verticesStatus.put(source, VertexStatus.DISCOVERED);

        queue.add(source);
        while (!queue.isEmpty()) {
            Vertex vertex = queue.poll();
            for (Vertex adjacent : adjacencyList.getAdjacentVertices(vertex)) {
                if (verticesStatus.get(adjacent) == VertexStatus.UNDISCOVERED) {
                    verticesStatus.put(adjacent, VertexStatus.DISCOVERED);
                    action.processEdge(vertex, adjacent);
                    queue.add(adjacent);
                }
            }
            verticesStatus.put(vertex, VertexStatus.PROCESSED);
        }
    }

    /**
     * Perform a stack based depth-first search on a graph.
     * 
     * @param graph the graph on which to perform the search.
     * @param source the vertex of the graph where to start the graph traversal
     * @param action the action to apply on edges covered and or vertices
     *            visited.
     */
    public static void stackBasedDfs(final Graph graph, final Vertex source,
            final IAction<Vertex> action) {
        validateArgs(graph, source, action);
        AdjacencyList adjacencyList = graph.asAdjacencyList();
        Map<Vertex, VertexStatus> verticesStatus = new HashMap<Vertex, VertexStatus>();
        Stack<Vertex> stack = new Stack<Vertex>();

        // iterators are used to avoid pushing the same element multiple times
        Map<Vertex, Iterator<Vertex>> adjacentsIterators = new HashMap<Vertex, Iterator<Vertex>>();
        for (Vertex vertex : graph.getVertices()) {
            verticesStatus.put(vertex, VertexStatus.UNDISCOVERED);
            adjacentsIterators.put(vertex, adjacencyList.getAdjacentVertices(vertex).iterator());
        }

        verticesStatus.put(source, VertexStatus.DISCOVERED);
        stack.push(source);

        while (!stack.empty()) {
            Vertex top = stack.peek();
            Iterator<Vertex> adjacentsIterator = adjacentsIterators.get(top);

            // is it possible to miss a vertex ?
            if (adjacentsIterator.hasNext()) {
                Vertex adjacent = adjacentsIterator.next();
                if (verticesStatus.get(adjacent) == VertexStatus.UNDISCOVERED) {
                    verticesStatus.put(adjacent, VertexStatus.DISCOVERED);
                    action.processEdge(top, adjacent);
                    stack.push(adjacent);
                }
            } else {
                verticesStatus.put(top, VertexStatus.PROCESSED);
                stack.pop();
            }
        }
    }

    /**
     * Perform a recursive based depth-first search on a graph.
     * 
     * @param graph the graph on which to perform the search.
     * @param source the vertex of the graph where to start the graph traversal
     * @param action the action to apply on edges covered and or vertices
     *            visited.
     */
    public static void recursiveDfs(final Graph graph, final Vertex source,
            final IAction<Vertex> action) {
        validateArgs(graph, source, action);
        Map<Vertex, VertexStatus> verticesStatus = new HashMap<Vertex, VertexStatus>();
        for (Vertex vertex : graph.getVertices()) {
            verticesStatus.put(vertex, VertexStatus.UNDISCOVERED);
        }
        AdjacencyList adjacencyList = graph.asAdjacencyList();
        for (Vertex vertex : graph.getVertices()) {
            if (verticesStatus.get(vertex) == VertexStatus.UNDISCOVERED) {
                recursiveDfs(adjacencyList, verticesStatus, vertex, action);
            }

        }
    }

    /**
     * Helper method used to perform a stack based depth-first search on a
     * graph.
     * 
     * @param adjacencyList the adjacency list graph
     * @param verticesStatus the status of the vertex
     * @param vertex the current vertex being processed
     * @param action the action to apply on edges covered and or vertices
     *            visited.
     */
    private static void recursiveDfs(final AdjacencyList adjacencyList,
            final Map<Vertex, VertexStatus> verticesStatus, final Vertex vertex,
            final IAction<Vertex> action) {
        verticesStatus.put(vertex, VertexStatus.DISCOVERED);
        for (Vertex adjacent : adjacencyList.getAdjacentVertices(vertex)) {
            if (verticesStatus.get(adjacent) == VertexStatus.UNDISCOVERED) {
                action.processEdge(vertex, adjacent);
                recursiveDfs(adjacencyList, verticesStatus, adjacent, action);
            }
        }
        verticesStatus.put(vertex, VertexStatus.PROCESSED);
    }

    /**
     * Perform a topological sort of vertices from a graph.
     * 
     * @param graph the graph with the vertices to sort.
     * @param source the first vertex from where to start the sorting.
     * @return A list with the vertices in their topological order. Empty if the
     *         graph has no vertices.
     */
    public static List<Vertex> topologicalSort(final Graph graph, final Vertex source) {
        return topologicalSort(graph, source, NO_OP_ACTION);
    }

    /**
     * Perform a topological sort of vertices from a graph.
     * 
     * @param graph the graph with the vertices to sort.
     * @param source the first vertex from where to start the sorting.
     * @param action the action to perform on edges covered during the
     *            topological sorting.
     * @return A list with the vertices in their topological order. Empty if the
     *         graph has no vertices.
     */
    public static List<Vertex> topologicalSort(final Graph graph, final Vertex source, 
            final IAction<Vertex> action) {

        validateArgs(graph, source, action);

        if (!graph.isDirected()) {
            throw new IllegalArgumentException("The graph must be directed !");
        }
        LinkedList<Vertex> sortedVertices = new LinkedList<Vertex>();
        AdjacencyList adjacencyList = graph.asAdjacencyList();
        Map<Vertex, VertexStatus> verticesStatus = new HashMap<Vertex, VertexStatus>();
        Map<Vertex, Iterator<Vertex>> adjacentsIterators = new HashMap<Vertex, Iterator<Vertex>>();
        Map<Vertex, Set<Vertex>> ancestors = new HashMap<Vertex, Set<Vertex>>();
        for (Vertex vertex : graph.getVertices()) {
            verticesStatus.put(vertex, VertexStatus.UNDISCOVERED);
            adjacentsIterators.put(vertex, adjacencyList.getAdjacentVertices(vertex).iterator());
            ancestors.put(vertex, new HashSet<Vertex>());
        }

        verticesStatus.put(source, VertexStatus.DISCOVERED);

        Stack<Vertex> stack = new Stack<Vertex>();
        stack.push(source);
        while (!stack.empty()) {
            Vertex top = stack.peek();
            Iterator<Vertex> adjacentsIterator = adjacentsIterators.get(top);

            if (adjacentsIterator.hasNext()) {
                Vertex adjacent = adjacentsIterator.next();
                if (verticesStatus.get(adjacent) == VertexStatus.UNDISCOVERED) {
                    // once moved to a specific class, calling
                    // addAncestor(ancestor)
                    // will add ancestors of ancestor also
                    ancestors.get(adjacent).addAll(ancestors.get(top));
                    ancestors.get(adjacent).add(top);
                    verticesStatus.put(adjacent, VertexStatus.DISCOVERED);
                    action.processEdge(top, adjacent);
                    stack.push(adjacent);
                } else if (ancestors.get(top).contains(adjacent)) {
                    throw new IllegalArgumentException(
                            "The graph can't contains cycle !");
                }
            } else {
                verticesStatus.put(top, VertexStatus.PROCESSED);
                sortedVertices.addFirst(stack.pop());
            }
        }
        return sortedVertices;
    }

    /**
     * Build a graph minimum spanning tree using Prim algorithm.
     * 
     * @param graph the graph from which to build the minimum spanning tree.
     * @param start the vertex from which to start building the spanning tree.
     * @return the minimum spanning tree built.
     * @throws GraphOperationException if an error occurs while building the
     *             spanning tree.
     */
    public static MinimumSpaningTree primMST(final Graph graph, final Vertex start) 
            throws GraphOperationException {
        validateArgs(graph, start);
        if (graph.isDirected()) {
            throw new IllegalArgumentException("The graph must be undirected !");
        }
        MinimumSpaningTree mst = new MinimumSpaningTree(graph.isWeighted());
        AdjacencyList adjacencyList = graph.asAdjacencyList();
        Map<Vertex, BigDecimal> verticesDistances = MapUtils.createMap(graph.getVertices(), INFINITY);
        Map<Vertex, Vertex> parents = new HashMap<Vertex, Vertex>();
        verticesDistances.put(start, Edge.ZERO_WEIGHT);
        LinkedListQueue<Vertex> queue = new LinkedListQueue<Vertex>(graph.getVertices(),
                new VerticesComparator<BigDecimal>(verticesDistances));
        
        while (!queue.empty()) {
            Vertex vertex = queue.poll();
            Set<Vertex> adjacents = adjacencyList.getAdjacentVertices(vertex);
            for (Vertex adjacent : adjacents) {
                Edge edge = graph.getEdge(vertex, adjacent);
                if (queue.contains(adjacent) && 
                    BigDecimalUtils.isBigger(verticesDistances.get(adjacent), edge.getWeight())) {
                    parents.put(adjacent, vertex);
                    verticesDistances.put(adjacent, edge.getWeight());
                }
            }
        }

        // now we need to add the edge based the parentVertices array
        for (Vertex child : graph.getVertices()) {
            if (!child.equals(start)) {
                Vertex parent = parents.get(child);
                mst.addEdge(graph.getEdge(parent, child));
            }
        }
        return mst;
    }

    /**
     * Build a graph minimum spanning tree using Kruskal algorithm.
     * 
     * @param graph the graph from which to build the minimum spanning tree.
     * @param start the vertex from which to start building the spanning tree.
     * @return the minimum spanning tree built.
     */
    public static MinimumSpaningTree kruskalMST(final Graph graph, final Vertex start) {
        validateArgs(graph, start);
        if (graph.isDirected()) {
            throw new IllegalArgumentException("The graph must be undirected !");
        }
        MinimumSpaningTree mst = new MinimumSpaningTree(graph.isWeighted());
        Queue<Edge> edgesQueue = new PriorityQueue<Edge>(graph.edgesCount(),
                new EdgesComparator());

        // initialize all components
        UnionFind<String> unionFind = new UnionFind<String>();
        for (Vertex vertex : graph.getVertices()) {
            unionFind.create(vertex.label());
        }

        for (Edge egde : graph.getEdges()) {
            // in undirected graph edge (a,b) is the same as edge(b,a) both both
            // are stored as 2 different edges
            // we need only one of them, so we must make sure that the reversed
            // has not been added yet.
            // TODO :fix this design problem i.e keep one edge using one edge
            // not 2 different edges
            if (!edgesQueue.contains(egde.reversed())) {
                edgesQueue.offer(egde);
            }
        }

        int coveredVerticesCount = 0;
        while (coveredVerticesCount < graph.verticesCount() - 1) {
            Edge edge = edgesQueue.poll();
            String startVertexLabel = edge.getStartVertex().label();
            String endVertexLabel = edge.getEndVertex().label();
            if (unionFind.find(startVertexLabel) != unionFind.find(endVertexLabel)) {
                unionFind.union(startVertexLabel, endVertexLabel);
                mst.addEdge(edge);
                coveredVerticesCount++;
            }
        }
        return mst;
    }

    /**
     * Find the shortest path starting from a specific vertex to all other
     * vertices of the graph.
     * 
     * @param graph the graph with the vertices for which to find path. The
     *            graph can have negative edges.
     * @param start the vertex from which all the paths start.
     * @return A list of {@link Path} from the start to each other vertex of the
     *         graph. Empty if the graph has no edges.
     * @throws GraphOperationException if an error occurs while searching the
     *             paths.
     */
    public static List<Path> singleSourceShortestPaths(final Graph graph,
            final Vertex start) throws GraphOperationException {
        validateArgs(graph, start);
        if (!graph.hasEdges()) {
            return NO_PATH;
        }

        Map<Vertex, BigDecimal> distances = MapUtils.createMap(graph.getVertices(), INFINITY);
        distances.put(start, Edge.ZERO_WEIGHT);
        if (graph.isDirected()) {
            return directedSingleSourceShortestPaths(graph, start, distances);
        }
        return undirectedSingleSourceShortestPaths(graph, start, distances);
    }

    /**
     * Find the shortest path starting from a specific vertex to all other
     * vertices of a directed graph
     * 
     * @param graph the graph with the vertices for which to find path
     * @param start the vertex from which all the paths start.
     * @param distances the distances to the vertices of the graph, starting
     *            from the start vertex.
     * @return A list of {@link Path} from the start to each other vertex of the
     *         graph. Empty if the graph has no edges.
     * @throws GraphOperationException if an error occurs while searching the
     *             paths.
     */
    private static List<Path> directedSingleSourceShortestPaths(final Graph graph, 
            final Vertex start, final Map<Vertex, BigDecimal> distances)
                    throws GraphOperationException {
        List<Vertex> sortedVertices = topologicalSort(graph, start, NO_OP_ACTION);
        Map<Vertex, Vertex> parents = new HashMap<Vertex, Vertex>();
        AdjacencyList adjacencyList = graph.asAdjacencyList();
        for (Vertex vertex : sortedVertices) {
            for (Vertex adjacent : adjacencyList.getAdjacentVertices(vertex)) {
                Edge edge = adjustWeight(graph.getEdge(vertex, adjacent));
                BigDecimal distance = distances.get(vertex).add(edge.getWeight());
                if (BigDecimalUtils.isBigger(distances.get(adjacent), distance)) {
                    distances.put(adjacent, distance);
                    parents.put(adjacent, vertex);
                }
            }
        }

        if (hasNegativeCycle(graph, distances)) {
            return NO_PATH;
        }
        return buildPaths(graph, start, distances, parents);
    }

    /**
     * Find the shortest path starting from a specific vertex to all other
     * vertices of an undirected graph.
     * 
     * @param graph the graph with the vertices for which to find path
     * @param start the vertex from which all the paths start.
     * @param distances the distances to the vertices of the graph, starting
     *            from the start vertex.
     * @return A list of {@link Path} from the start to each other vertex of the
     *         graph. Empty if the graph has no edges.
     */
    private static List<Path> undirectedSingleSourceShortestPaths(final Graph graph, 
            final Vertex start, final Map<Vertex, BigDecimal> distances) {
        Map<Vertex, Vertex> parents = new HashMap<Vertex, Vertex>();
        for (int i = 0; i < graph.verticesCount() - 1; i++) {
            for (Edge edge : graph.getEdges()) {
                edge = adjustWeight(edge);
                Vertex startVertex = edge.getStartVertex();
                Vertex endVertex = edge.getEndVertex();
                BigDecimal distance = distances.get(startVertex).add(edge.getWeight());
                if (BigDecimalUtils.isBigger(distances.get(endVertex), distance)) {
                    distances.put(endVertex, distance);
                    parents.put(endVertex, startVertex);
                }
            }
        }
        if (hasNegativeCycle(graph, distances)) {
            return NO_PATH;
        }
        return buildPaths(graph, start, distances, parents);
    }

    /**
     * Check if a graph has a negative cycle.
     * 
     * @param graph the graph to check.
     * @param distances A map of distances to each vertex from the source
     *            vertex.
     * @return true if the graph has a negative cycle, false otherwise.
     */
    private static boolean hasNegativeCycle(final Graph graph, 
            final Map<Vertex, BigDecimal> distances) {
        if (graph.isWeighted()) {
            for (Edge edge : graph.getEdges()) {
                Vertex startVertex = edge.getStartVertex();
                Vertex endVertex = edge.getEndVertex();
                BigDecimal distance = distances.get(startVertex).add(edge.getWeight());
                if (BigDecimalUtils.isBigger(distances.get(endVertex), distance)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Perform djkistra algorithm to find the shortest path starting from a
     * specific vertex to all other vertices of a graph.
     * 
     * @param graph the graph with the vertices for which to find path. Can't
     *            have negative edges.
     * @param start the vertex from which all the paths start.
     * @return A list of {@link Path} from the start to each other vertex of the
     *         graph. Empty if the graph has no edges.
     * @throws GraphOperationException if an error occurs while finding the
     *             paths.
     */
    public static List<Path> djkistra(final Graph graph, final Vertex source)
            throws GraphOperationException {
        validateArgs(graph, source);
        if (!graph.hasEdges()) {
            return NO_PATH;
        }
        Map<Vertex, BigDecimal> verticesDistances = MapUtils.createMap(graph.getVertices(), INFINITY);
        Map<Vertex, Vertex> parentVertices = new HashMap<Vertex, Vertex>();
        verticesDistances.put(source, Edge.ZERO_WEIGHT);
        LinkedListQueue<Vertex> queue = new LinkedListQueue<Vertex>(graph.getVertices(),
                new VerticesComparator<BigDecimal>(verticesDistances));
        AdjacencyList adjacencyList = graph.asAdjacencyList();
        while (!queue.empty()) {
            Vertex vertex = queue.poll();
            for (Vertex adjacent : adjacencyList.getAdjacentVertices(vertex)) {
                Edge edge = adjustWeight(graph.getEdge(vertex, adjacent));
                if (BigDecimalUtils.isNegative(edge.getWeight())) {
                    throw new IllegalArgumentException("Edge weight can't be negative !");
                }
                BigDecimal distance = verticesDistances.get(vertex).add(edge.getWeight());
                if (BigDecimalUtils.isBigger(verticesDistances.get(adjacent),
                        distance)) {
                    verticesDistances.put(adjacent, distance);
                    parentVertices.put(adjacent, vertex);
                }
            }
        }
        return buildPaths(graph, source, verticesDistances, parentVertices);
    }

    /**
     * Build paths starting from a specific vertex to all other vertices of a
     * graph
     * 
     * @param graph the graph with the vertices for which to build paths.
     * @param start the vertex from which all paths starts.
     * @param distances a map of distances from the start vertex to each vertex
     *            of the graph.
     * @param parents a map of the direct parent to each vertex of the graph.
     * @return A list of paths from the start vertex to each vertex of teh
     *         graph.
     */
    private static List<Path> buildPaths(final Graph graph, final Vertex start,
            final Map<Vertex, BigDecimal> distances,
            final Map<Vertex, Vertex> parents) {
        List<Path> paths = new ArrayList<Path>();
        for (Vertex vertex : graph.getVertices()) {
            if (!vertex.equals(start)) {
                Path path = new Path(start, vertex, distances.get(vertex));
                Vertex parent = parents.get(vertex);
                LinkedList<Vertex> intermediates = new LinkedList<Vertex>();
                while (!(parent == null || parent.equals(start))) {
                    intermediates.addFirst(parent);
                    parent = parents.get(parent);
                }
                path.addIntermediateVertices(intermediates);
                paths.add(path);
            }
        }
        return paths;
    }

    /**
     * Find all shortest paths from each vertex of the graph to all the other
     * vertices.
     * 
     * @param graph the graph with the vertices for which to find shortest
     *            paths.
     * @return A map where the key is the vertex and the value a list of paths
     *         to the other vertices. Empty if no paths where founds.
     * @throws GraphOperationException if an error occurs while searching the
     *             paths.
     */
    public static Map<Vertex, List<Path>> allPairsShortestPaths(
            final Graph graph) throws GraphOperationException {
        ValidationUtils.validateNotNull(graph, "The graph can't be null");

        // no edges and no vertices then no path
        if (graph.isEmpty()) {
            return Collections.<Vertex, List<Path>> emptyMap();
        }

        VerticesIndexes verticesIndexes = new VerticesIndexes(
                graph.getVertices());
        final int verticesCount = graph.verticesCount();

        BigDecimal[][] distances = new BigDecimal[verticesCount][verticesCount];
        int[][] predecessors = new int[verticesCount][verticesCount];

        for (int start = 0; start < verticesCount; start++) {
            for (int end = 0; end < verticesCount; end++) {
                distances[start][end] = INFINITY;
                predecessors[start][end] = UNDEFINED_INDEX;
                Vertex from = verticesIndexes.getVertex(start);
                Vertex to = verticesIndexes.getVertex(end);
                if (graph.containsEdge(from, to)) {
                    distances[start][end] = adjustWeight(graph.getEdge(from, to)).getWeight();
                    predecessors[start][end] = start;
                }
                if (start == end) {
                    distances[start][end] = BigDecimal.ZERO;
                }
            }
        }

        for (int intermediate = 0; intermediate < verticesCount; intermediate++) {
            for (int start = 0; start < verticesCount; start++) {
                for (int end = 0; end < verticesCount; end++) {
                    BigDecimal newDistance = BigDecimalUtils.sum(
                            distances[start][intermediate],
                            distances[intermediate][end]);
                    if (BigDecimalUtils.isBigger(distances[start][end], newDistance)) {
                        distances[start][end] = newDistance;
                        predecessors[start][end] = predecessors[intermediate][end];
                    }
                }
            }
        }

        Map<Vertex, List<Path>> paths = new HashMap<Vertex, List<Path>>();
        for (int startVertex = 0; startVertex < verticesCount; startVertex++) {
            Vertex from = verticesIndexes.getVertex(startVertex);
            paths.put(from, new ArrayList<Path>());
            for (int endVertex = 0; endVertex < verticesCount; endVertex++) {
                if (startVertex != endVertex && 
                    predecessors[startVertex][endVertex] != UNDEFINED_INDEX) {

                    Vertex to = verticesIndexes.getVertex(endVertex);
                    Path path = new Path(from, to, distances[startVertex][endVertex]);
                    LinkedList<Vertex> intermediates = new LinkedList<Vertex>();
                    int predecessor = predecessors[startVertex][endVertex];
                    while (predecessor != startVertex) {
                        Vertex intermediate = verticesIndexes.getVertex(predecessor);
                        intermediates.addFirst(intermediate);
                        predecessor = predecessors[startVertex][predecessor];
                    }
                    path.addIntermediateVertices(intermediates);
                    paths.get(from).add(path);
                }
            }
        }
        return paths;
    }

    /**
     * Verify that the graph and the source vertex provided as argument to an
     * algorithm are not null and the source vertex is part of the graph.
     * 
     * @param graph the graph to check.
     * @param source the source vertex to check.
     */
    private static void validateArgs(final Graph graph, final Vertex source) {
        ValidationUtils.validateNotNull(graph, "The graph can't be null");
        ValidationUtils.validateNotNull(source,"The source vertex can't be null");
        if (!graph.containsVertex(source)) {
            throw new IllegalArgumentException("The source vertex must be part of the graph!");
        }
    }

    /**
     * Verify that the graph, the source vertex and the action provided as
     * argument to an algorithm are not null and the source vertex is part of
     * the graph.
     * 
     * @param graph the graph to check.
     * @param source the source vertex to check.
     * @param action the action to check.
     */
    private static void validateArgs(final Graph graph, final Vertex source,
            final IAction<Vertex> action) {
        validateArgs(graph, source);
        ValidationUtils.validateNotNull(action, "The action can't be null");
    }

    /**
     * Adjust the weight of an edge. If the edge has no weight then it will be
     * assigned a weight of one, otherwise it will keep the same weight.
     * 
     * @param edge the edge for which to adjust the weight.
     * @return A copy of the original edge with the adjusted weight. A copy is
     *         returned, so that the original edge do not get modified, since it
     *         might used elsewhere.
     */
    private static Edge adjustWeight(final Edge edge) {
        return edge.hasWeight() ?
               edge : new Edge(edge.getStartVertex(), edge.getEndVertex(),BigDecimal.ONE);
    }

    /**
     * Comparator for edges. Compare edges by weights.
     */
    private static final class EdgesComparator implements Comparator<Edge> {
        public int compare(final Edge firstEdge, final Edge secondEdge) {
            return firstEdge.getWeight().compareTo(secondEdge.getWeight());
        }
    }

    /**
     * Comparator for vertices. Compare vertices based on a specific set of
     * values provided.
     *
     * @param <T> the type of the value to be used for comparing the vertices.
     */
    private static final class VerticesComparator<T extends Comparable<T>>
            implements Comparator<Vertex> {
        private final Map<Vertex, T> m_values;

        public VerticesComparator(final Map<Vertex, T> values) {
            m_values = values;
        }

        public int compare(final Vertex first, final Vertex second) {
            int comparison = m_values.get(first)
                    .compareTo(m_values.get(second));
            return comparison == 0 ? first.compareTo(second) : comparison;
        }
    }
}