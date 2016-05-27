package com.melimelo.graphs.algorithms;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.melimelo.graphs.Algorithms;
import com.melimelo.graphs.Graph;
import com.melimelo.graphs.Path;

/**
 * unit tests for single source shortest paths algorithm.
 */
@SuppressWarnings("serial")
public class SingleSourceShortestPathsTest extends ShortestPathsTestBase {

    @Test(expected = IllegalArgumentException.class)
    public void testSingleSourceShortestPathsOnNullGraph() throws Exception {
        Algorithms.singleSourceShortestPaths(GraphFactory.createNullGraph(), SOURCE_VERTEX);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSingleSourceShortestPathsWithNullVertex() throws Exception {
        Algorithms.singleSourceShortestPaths(GraphFactory.createEmptyGraph(), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSingleSourceShortestPathsOnEmptyGraph() throws Exception {
        Algorithms.singleSourceShortestPaths(GraphFactory.createEmptyGraph(), SOURCE_VERTEX);
    }

    @Test
    public void testSingleSourceShortestPathsOnSingletonGraph() throws Exception {
        Graph graph = GraphFactory.createSingletonGraph();
        List<Path> paths = Algorithms.singleSourceShortestPaths(graph, SOURCE_VERTEX);
        assertTrue(paths.isEmpty());
    }


    @Test
    public void testSingleSourceShortestPathsOnUnDirectedWeightedCyclicGraph() throws Exception {
        Graph graph = GraphFactory.createUnDirectedWeightedCyclicGraph();
        List<Path> shortestPaths = Algorithms.singleSourceShortestPaths(graph, SOURCE_VERTEX);
        // With the provided graph, the expected paths are
        // a->b, 1
        // a->b->c, 6
        // a->b->d, 4
        // a->b->d->e, 6
        // a->b->d->e->g, 7
        // a->b->d->e->g->f, 10
        // a->b->d->e->g->f->h, 13
        // a->b->d->e->g->j, 8
        // a->b->d->e->g->j->i, 12
        Map<String, ShortestPath> expectedPaths = new HashMap<String, ShortestPath>(){{
          put("B", new ShortestPath("A", "B", 1));
          put("C", new ShortestPath("A", "C", 6, "B")); 
          put("D", new ShortestPath("A", "D", 4, "B"));
          put("E", new ShortestPath("A", "E", 6, "B", "D"));
          put("G", new ShortestPath("A", "G", 7, "B", "D", "E"));
          put("F", new ShortestPath("A", "F", 10, "B", "D", "E", "G"));
          put("H", new ShortestPath("A", "H", 9, "B", "D", "E", "G", "J"));
          put("J", new ShortestPath("A", "J", 8, "B", "D", "E", "G"));
          put("I", new ShortestPath("A", "I", 12, "B", "D", "E", "G", "J"));
        }};
        verifyShortestPaths(expectedPaths, graph, shortestPaths);
    }

    @Test
    public void testSingleSourceShortestPathsOnUnDirectedUnWeightedCyclicGraph() throws Exception{
        Graph graph = GraphFactory.createUnDirectedUnWeightedCyclicGraph();
        List<Path> shortestPaths = Algorithms.singleSourceShortestPaths(graph, SOURCE_VERTEX);
        // With the provided graph, the expected paths are:
        // a->b, 1
        // a->d, 1
        // a->b->c, 2
        // a->b->e, 2
        // a->b->c->f, 3
        // a->d->g, 2
        // a->d->g->h, 3
        // a->d->g->i, 3
        // a->d->g->h->j, 4
        // the width are the number of edges edges covered. 
        // In this case the length will be zero since the graph is unweighted
        Map<String, ShortestPath> expectedPaths = new HashMap<String, ShortestPath>(){{
          put("B", new ShortestPath("A", "B", 0));
          put("D", new ShortestPath("A", "D", 0));
          put("C", new ShortestPath("A", "C", 0, "B")); 
          put("E", new ShortestPath("A", "E", 0, "B"));
          put("F", new ShortestPath("A", "F", 0, "B", "C"));
          put("G", new ShortestPath("A", "G", 0, "D"));          
          put("H", new ShortestPath("A", "H", 0, "D", "G"));
          put("I", new ShortestPath("A", "I", 0, "D", "G"));
          put("J", new ShortestPath("A", "J", 0, "D", "G", "H"));
        }};
        
        verifyShortestPaths(expectedPaths, graph, shortestPaths);
    }

    @Test
    public void testSingleSourceShortestPathsOnUnDirectedWeightedAcyclicGraph() throws Exception {
        Graph graph = GraphFactory.createUnDirectedWeightedAcyclicGraph();
        List<Path> shortestPaths = Algorithms.singleSourceShortestPaths(graph, SOURCE_VERTEX);
        // With the provided graph, the expected paths are:
        // a->b, 1
        // a->d, 8
        // a->b->c, 6
        // a->b->e, 11
        // a->b->e->f, 20
        // a->b->e->g, 12
        // a->b->e->g->h, 21
        // a->b->e->g->h->1, 22
        // a->b->e->g->h->i->j, 26
        Map<String, ShortestPath> expectedPaths = new HashMap<String, ShortestPath>(){{
          put("B", new ShortestPath("A", "B", 1));
          put("D", new ShortestPath("A", "D", 8));
          put("C", new ShortestPath("A", "C", 6, "B")); 
          put("E", new ShortestPath("A", "E", 11, "B"));
          put("F", new ShortestPath("A", "F", 20, "B", "E"));
          put("G", new ShortestPath("A", "G", 12, "B", "E"));          
          put("H", new ShortestPath("A", "H", 21, "B", "E", "G"));
          put("I", new ShortestPath("A", "I", 26, "B", "E", "G", "H", "J"));
          put("J", new ShortestPath("A", "J", 22, "B", "E", "G", "H"));
        }};
        
        verifyShortestPaths(expectedPaths, graph, shortestPaths);
    }

    @Test
    public void testSingleSourceShortestPathsOnUnDirectedUnWeightedAcyclicGraph() throws Exception {
        Graph graph = GraphFactory.createUnDirectedUnWeightedAcyclicGraph();
        List<Path> shortestPaths = Algorithms.singleSourceShortestPaths(graph, SOURCE_VERTEX);
        // With the provided graph, the expected paths are:
        // a->b, 1
        // a->d, 1
        // a->b->c, 2
        // a->b->e, 2
        // a->b->e->f, 3
        // a->b->e->g, 3
        // a->b->e->g->h, 4
        // a->b->e->g->h->1, 5
        // a->b->e->g->h->i->j, 6
        // the width are the number of edges edges covered. 
        // In this case the length will be zero since the graph is unweighted
        
        Map<String, ShortestPath> expectedPaths = new HashMap<String, ShortestPath>(){{
          put("B", new ShortestPath("A", "B", 0));
          put("D", new ShortestPath("A", "D", 0));
          put("C", new ShortestPath("A", "C", 0, "B")); 
          put("E", new ShortestPath("A", "E", 0, "B"));
          put("F", new ShortestPath("A", "F", 0, "B", "E"));
          put("G", new ShortestPath("A", "G", 0, "B", "E"));          
          put("H", new ShortestPath("A", "H", 0, "B", "E", "G"));
          put("I", new ShortestPath("A", "I", 0, "B", "E", "G", "H", "J"));
          put("J", new ShortestPath("A", "J", 0, "B", "E", "G", "H"));
        }};
        
        verifyShortestPaths(expectedPaths, graph, shortestPaths);
    }

    @Test(expected= IllegalArgumentException.class)
    public void testSingleSourceShortestPathsOnDirectedWeightedCyclicGraph() throws Exception {
        Graph graph = GraphFactory.createDirectedWeightedCyclicGraph();
        Algorithms.singleSourceShortestPaths(graph, SOURCE_VERTEX);
    }

    @Test(expected= IllegalArgumentException.class)
    public void testSingleSourceShortestPathsOnDirectedUnWeightedCyclicGraph() throws Exception {
        Graph graph = GraphFactory.createDirectedUnWeightedCyclicGraph();
        Algorithms.singleSourceShortestPaths(graph, SOURCE_VERTEX);
    }

    @Test
    public void testSingleSourceShortestPathsOnDirectedWeightedAcyclicGraph() throws Exception {
        Graph graph = GraphFactory.createDirectedWeightedAcyclicGraph();
        List<Path> shortestPaths = Algorithms.singleSourceShortestPaths(graph, SOURCE_VERTEX);
        Map<String, ShortestPath> expectedPaths = new HashMap<String, ShortestPath>(){{
            put("B", new ShortestPath("A", "B", 1));
            put("D", new ShortestPath("A", "D", 4, "B"));
            put("C", new ShortestPath("A", "C", 6, "B")); 
            put("E", new ShortestPath("A", "E", 6, "B", "D"));
            put("F", new ShortestPath("A", "F", 10, "B", "D", "E", "G"));
            put("G", new ShortestPath("A", "G", 7, "B", "D", "E"));          
            put("H", new ShortestPath("A", "H", 12, "B", "D", "E", "G", "F"));
            put("I", new ShortestPath("A", "I", 13, "B", "D", "E", "G"));
            put("J", new ShortestPath("A", "J", 13, "B", "D", "E", "G", "F", "H"));
          }};
          
          verifyShortestPaths(expectedPaths, graph, shortestPaths);
    }

    @Test
    public void testSingleSourceShortestPathsOnDirectedUnWeightedAcyclicGraph() throws Exception {
        Graph graph = GraphFactory.createDirectedUnWeightedAcyclicGraph();
        List<Path> shortestPaths = Algorithms.singleSourceShortestPaths(graph, SOURCE_VERTEX);
        Map<String, ShortestPath> expectedPaths = new HashMap<String, ShortestPath>(){{
            put("B", new ShortestPath("A", "B", 0));
            put("D", new ShortestPath("A", "D", 0));
            put("C", new ShortestPath("A", "C", 0, "B")); 
            put("E", new ShortestPath("A", "E", 0, "B"));
            put("F", new ShortestPath("A", "F", 0, "B", "E"));
            put("G", new ShortestPath("A", "G", 0, "D"));          
            put("H", new ShortestPath("A", "H", 0, "D", "G"));
            put("I", new ShortestPath("A", "I", 0, "D", "G"));
            put("J", new ShortestPath("A", "J", 0, "D", "G","I"));
          }};
          verifyShortestPaths(expectedPaths, graph, shortestPaths);
    }
}
