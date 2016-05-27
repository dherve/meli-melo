package com.melimelo.graphs.applications;


import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.Arrays;
public class Maze {

    // #TODO print path
    // how to select end and start, such that they are accessible ?
    private static final char WALL_CELL = 'O';
    private static final char EMPTY_CELL = '.';
    private static final char START_CELL = 'S';
    private static final char END_CELL = 'E';
    private static final char SOLUTION_PATH_CELL = '+';
    private static final Random RANDOM_GENERATOR = new Random();
    private static final int NEIGHBOR_CELL_DISTANCE = 2;
    private static final int WALL_CELL_DISTANCE = 1;
    private static boolean mazeSolved = false;

    private static class Cell {
        public final int row;
        public final int col;
        public Cell(final int row, final int col) {
            this.row = row;
            this.col = col;
        }

        public String toString() {
            return "[row:" + row + ", col:" + col + "]";
        }
    }

    private static class CellNode extends Cell {
        public final CellNode parent;

        public CellNode(final CellNode parent, final int row, final int col) {
            super(row, col);
            this.parent = parent;
        }

        public String toString() {
            return super.toString();
        }
    }

    private static boolean isOutOfBound(Cell cell, char [][] maze) {
        return isOutOfBound(cell.row, cell.col, maze);
    }

    private static boolean isOutOfBound(int row, int col, char [][] maze) {
        return row < 0 || col < 0 || row >= maze.length || col >= maze[0].length; 
    }

    private static boolean isInnerCell(Cell cell, char [][] maze) {
        return cell.row > 0 && cell.col > 0 && cell.row < maze.length -1 && cell.col < maze[0].length -1;
    }
    
    private static boolean hasWallIntacts(Cell cell, char[][] maze, boolean [][] visited) {
        List<Cell> walls = getCellsAtDistance(cell, maze, WALL_CELL_DISTANCE);
        for(Cell wall : walls) {
            if(visited[wall.row][wall.col]){
                return false;
            }
        }
        return true;
    }

    private static List<Cell> getNeighborsWithIntactWalls(Cell cell, char[][] maze, boolean [][] visited) {
        List<Cell> cells = getCellsAtDistance(cell, maze, NEIGHBOR_CELL_DISTANCE);
        List<Cell> neighbors = new ArrayList<Cell>();
        for(Cell neighbor : cells) {
            if(!visited[neighbor.row][neighbor.col] && hasWallIntacts(neighbor, maze, visited)) {
                neighbors.add(neighbor);
            }
        }
        return neighbors;
    }

    private static List<Cell> getCellsAtDistance(Cell cell, char[][] maze, int distance) {
        Cell [] cells = new Cell[]{ new Cell(cell.row + distance, cell.col),  
                                    new Cell(cell.row - distance, cell.col ), 
                                    new Cell(cell.row, cell.col - distance),
                                    new Cell(cell.row, cell.col + distance) };
        List<Cell> distantCells = new ArrayList<Cell>();
        for(Cell neighbor : cells) {
            if(!isOutOfBound(neighbor, maze)){
                distantCells.add(neighbor);
            }
        }
        return distantCells;
    }

    private static int random(int limit) {
        return RANDOM_GENERATOR.nextInt(limit);
    }

    private static void breakWallBetweenCells(Cell first, Cell second, char[][] maze, boolean[][]visited) {
        // System.out.println("break the wall between " + first + " + " + second);
        int wallRow = first.row;
        int wallCol = first.col;
        if(wallRow == second.row) {
            if(wallCol < second.col) {
                wallCol += WALL_CELL_DISTANCE;
            } else if(wallCol > second.col) {
                wallCol -= WALL_CELL_DISTANCE;
            }
        } else if(wallCol == second.col) {
            if(wallRow < second.row) {
                wallRow += WALL_CELL_DISTANCE;
            } else if(wallRow > second.row) {
                wallRow -= WALL_CELL_DISTANCE;
            }
        }
        maze[wallRow][wallCol] = EMPTY_CELL;
        visited[wallRow][wallCol] = true;
        maze[second.row][second.col] = EMPTY_CELL;
        visited[second.row][second.col] = true;
    }

    private static char[][] generate(int width, int height, int startRow, int startCol) {
        char[][] maze = new char[width][height];
        boolean [][] visited = new boolean[width][height];
        for(int row = 0; row < height; row ++) {
            for(int col = 0; col < width; col++) {
                maze[row][col] = WALL_CELL;
            }
        }

        Stack<Cell> stack = new Stack<Cell>();
        Cell current = new Cell(startRow, startCol);
        visited[startRow][startCol] = true;
        maze [startRow][startCol] = START_CELL;
        stack.push(current);
        while(!stack.empty()) {
            List<Cell> neighbors = getNeighborsWithIntactWalls(current, maze, visited);
            if(!neighbors.isEmpty()) {
                int nextCellIndex = random(neighbors.size());
                Cell next  = neighbors.get(nextCellIndex);
                //System.out.println("current " + current + " next => " + next);
                breakWallBetweenCells(current, next, maze, visited);
                stack.push(current);
                current = next;
            } else {
                if(!stack.empty()) {
                    current = stack.pop();
                }
            }
        }

        return maze;
    }

    private static void printSolutionPath(char[][] maze, CellNode cell) {
        CellNode predecessor = cell.parent;
        //System.out.println("printSolutionPath" + cell + " parent " + predecessor); 
        while(predecessor != null) {
            maze[predecessor.row][predecessor.col] = SOLUTION_PATH_CELL;
            predecessor = predecessor.parent;
            //System.out.println("printSolutionPath => predecessor " + predecessor); 
            print(maze);
            System.out.println();
        }
    }

    //# TODO finish this, not working ? because marked seen can't go there
    private static void explore(char[][] maze, int row, int col, boolean[][] visited, CellNode parent) {
        if(mazeSolved || isOutOfBound(row, col, maze) || maze[row][col] == WALL_CELL || visited[row][col]) {
            return;
        }
       
        
        CellNode cell = new CellNode(parent, row, col);
        if(maze[row][col] == END_CELL) {
            System.out.println("SOLVED");
            //printSolutionPath(maze, cell);
            mazeSolved = true;

            return;
        }
         // maze[row][col] = 'V';
         // print(maze);
        visited[row][col] = true;
        explore(maze, row + 1, col , visited, cell);
        explore(maze, row - 1, col , visited, cell);
        explore(maze, row, col + 1, visited, cell);
        explore(maze, row, col - 1, visited, cell);
    }

    private static void solve(char [][] maze, int row, int col) {
        CellNode root = new CellNode(null, row,col);
        mazeSolved = false;
        int height = maze.length;
        int width = maze[0].length;
        boolean [][] visited = new boolean[height][width];
        explore(maze, row, col, visited, root);
    }

    private static void print(char [][] maze) {
        for(char [] row : maze) {
            System.out.print("[");
            for(char cell : row) {
                System.out.print(cell);
            }
            System.out.println("]");
            // System.out.println(Arrays.toString(row));
        }
        System.out.println();
    }

    public static void main(String [] args) {
        int width = Integer.valueOf(args[0]);
        int height = Integer.valueOf(args[1]);
        int startRow = RANDOM_GENERATOR.nextInt(height);
        int startCol = RANDOM_GENERATOR.nextInt(width);
        if(startRow > startCol) {
            startRow = 0;
        } else {
            startCol = 0;
        }
        char [][] maze = generate(width, height, 0, 0);
        maze[width -1][height-1] = END_CELL;
        print(maze);
        solve(maze, startRow, startCol);
    }
}