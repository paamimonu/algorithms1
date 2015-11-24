import edu.princeton.cs.algs4.In;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.MinPQ;

public class Solver {
    private boolean isSolvable;
    private int totalMoves;
    private searchNode previousNode;

    private class searchNode implements Comparable <searchNode>{
        private Board currentBoard;
        private int moves;
        private searchNode previousNode;
        private int priority;

        public int compareTo(searchNode node) {
            if (this.priority < node.priority) return -1;
            if (this.priority > node.priority) return 1;
            return 0;
        }

        public searchNode (Board board, int mov, searchNode previous) {
            currentBoard = board;
            moves = mov;
            previousNode = previous;
            priority = currentBoard.manhattan() + moves;
        }   
    }

    public Solver(Board initial) {          // find a solution to the initial board (using the A* algorithm)
        MinPQ<searchNode> pq = new MinPQ<searchNode>(); 
        MinPQ<searchNode> pqTwin = new MinPQ<searchNode>(); 
        searchNode begin = new searchNode(initial, 0, null);
        searchNode beginTwin = new searchNode(initial.twin(), 0, null);
        pq.insert(begin);
        pqTwin.insert(beginTwin);

        while(true) {
            searchNode node = pq.delMin();
            searchNode nodeTwin = pqTwin.delMin();

            if (node.currentBoard.isGoal()) {
                previousNode = node;
                isSolvable = true;
                totalMoves = node.moves;
                break;
            }

            if (nodeTwin.currentBoard.isGoal()) {
                previousNode = node;
                isSolvable = false;
                totalMoves = -1;
                break;
            }
            enqueueSearchNodes(node, pq);
            enqueueSearchNodes(nodeTwin, pqTwin);
        }
    }
    public boolean isSolvable() {            // is the initial board solvable?
        return isSolvable;
    }

    public int moves() {                    // min number of moves to solve initial board; -1 if unsolvable
        return totalMoves;
    }

    private void enqueueSearchNodes(searchNode node, MinPQ<searchNode> pq) {
        for (Board nextBoard : node.currentBoard.neighbors()) {
            if ((node.previousNode == null) || (!nextBoard.equals(node.previousNode.currentBoard))) {
                pq.insert(new searchNode(nextBoard, node.moves + 1, node));
            }
        }
    }


    public Iterable<Board> solution() {      // sequence of boards in a shortest solution; null if unsolvable
        Stack<Board> solution = new Stack<Board>();
        if (!isSolvable) {
            solution = null;
        }

        else {
            searchNode s = previousNode;
            while (s != null) {
                solution.push(s.currentBoard);
                s = s.previousNode;
            }
        }
        return solution;
    }

//    public static void main(String[] args) // solve a slider puzzle (given below)
}
