import edu.princeton.cs.algs4.In;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.MinPQ;

public class Board {
    private int [][] grid;
    private final int N;
    public Board(int[][] blocks) {          // construct a board from an N-by-N array of blocks
        N = blocks.length;
        grid = new int[N][N];
        for (int i = 0; i < N; i++) {                                           // (where blocks[i][j] = block in row i, column j)
            for (int j = 0; j < N; j++) {                                           // (where blocks[i][j] = block in row i, column j)
                grid[i][j] = blocks[i][j];
            }
        }
    }

    public int dimension() {                // board dimension N
        return this.N;
    }

    public int hamming() {                  // number of blocks out of place
        int value;
        int hd = 0;
        for (int i = 0; i < this.N; i++) {
            for (int j = 0; j < this.N; j++) {
                if (grid[i][j] == 0) {
                    continue;
                }
                if (grid[i][j] != (i*this.N + j + 1)) {
                    hd++;
                }
            }
        }
        return hd;
    }


    public int manhattan() {                 // sum of Manhattan distances between blocks and goal
        int md = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int value = this.grid[i][j];
                if (value != 0) {
                    int x = (value - 1) / N;
                    int y = (value - 1) % N;
                    md = md + Math.abs(x - i) + Math.abs(y - j);
                }
            }
        }
        return md;
    }

    public boolean isGoal() {               // is this board the goal board?
        return (this.manhattan() == 0); 
    }



    public Board twin() {                   // a board that is obtained by exchanging any pair of blocks
        int[][] newGrid = new int[N][N];
        for (int i = 0; i < N; i++) {                                           // (where blocks[i][j] = block in row i, column j)
            for (int j = 0; j < N; j++) {                                           // (where blocks[i][j] = block in row i, column j)
                newGrid[i][j] = grid[i][j];
            }
        }
        if (newGrid[0][0] * newGrid[0][1] == 0) {
            entryExchange(newGrid, 1, 0, 1, 1);
        }
        else {
            entryExchange(newGrid, 0, 0, 0, 1);
        }
        return (new Board(newGrid));
    }


    public boolean equals(Object y) {       // does this board equaly?
        if (!(y instanceof Board)) return false;
        Board that = (Board) y;
        if (this.N != that.N) return false;
        for (int i = 0; i < N; i++) {               // (where blocks[i][j] = block in row i, column j)
            for (int j = 0; j < N; j++) {           // (where blocks[i][j] = block in row i, column j)
                if (this.grid[i][j] != that.grid[i][j])
                    return false;
            }
        }
        return true;
    }

    public Iterable<Board> neighbors() {     // all neighboring boards
        return new Iterable<Board> () {
            public Iterator<Board> iterator() {
                List<Board> neighbouringBoards = new ArrayList<>();
                int possiblities = 0, value = 10, xZero = 0, yZero = 0;
                for (int i = 0; i < N; i++) {
                    for (int j = 0; j < N; j++) {
                        if ((value = grid[i][j]) == 0) {
                            xZero = i;
                            yZero = j;
                            break;
                        }
                    }
                    if (value == 0) {
                        break;
                    }
                }

                if (xZero + 1 < N) {
                    neighbouringBoards.add(entryExchange(grid, xZero, yZero, xZero + 1, yZero));
                    entryExchange(grid, xZero + 1, yZero, xZero, yZero);
                }

                if (yZero + 1 < N) {
                    neighbouringBoards.add(entryExchange(grid, xZero, yZero, xZero, yZero + 1));
                    entryExchange(grid, xZero, yZero + 1, xZero, yZero);
                }

                if (xZero - 1 >= 0) {
                    neighbouringBoards.add(entryExchange(grid, xZero, yZero, xZero - 1, yZero));
                    entryExchange(grid, xZero - 1, yZero, xZero, yZero);
                }

                if (yZero - 1 >= 0) {
                    neighbouringBoards.add(entryExchange(grid, xZero, yZero, xZero, yZero - 1));
                    entryExchange(grid, xZero, yZero - 1, xZero, yZero);
                }

                return neighbouringBoards.iterator();
            }
        };
    }

    private Board entryExchange(int[][] previousBoard, int x1, int y1, int x2, int y2) {
        int temp = previousBoard[x1][y1];
        previousBoard[x1][y1] = previousBoard[x2][y2];
        previousBoard[x2][y2] = temp;
        return (new Board(previousBoard));
    }

    public String toString() {                      // string representation of this board (in the output format specified below)
        String s = "";
        s = s.concat(N + "\n");
        for (int i = 0; i < N; i++) {               // (where blocks[i][j] = block in row i, column j)
            for (int j = 0; j < N; j++) {           // (where blocks[i][j] = block in row i, column j)
                s = s.concat(grid[i][j] + "\t");
            }
            s = s.concat("\n");
        }
        return s;
    }

    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
