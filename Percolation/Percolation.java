import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] state;
    private WeightedQuickUnionUF gridA, gridB;
    private int N;

    public Percolation(int N) { // create N-by-N grid, with all sites blocked
        if (N <= 0)
            throw new IllegalArgumentException("Enter a valid grid dimension!");
        state = new boolean[N][N];
        this.N = N;
        gridA = new WeightedQuickUnionUF(N * N + 2);
        gridB = new WeightedQuickUnionUF(N * N + 1);
        for (int i = 0; i < N; i++) {
            gridA.union(0, gridToArray(0, i));
            gridB.union(0, gridToArray(0, i));
            gridA.union(N * N + 1, gridToArray(N - 1, i));
        }
    }

    public void open(int i, int j) {
        int x = i - 1;
        int y = j - 1;
        if (x < 0 || y < 0 || x >= N || y >= N)
            throw new IndexOutOfBoundsException("Index is out of bounds!");
        if (!state[x][y]) {
            state[x][y] = true;  
            int presentArrayLocation = gridToArray(x, y);
            if ((x + 1) < N && state[x+1][y]) {
                gridA.union(gridToArray(x+1, y), presentArrayLocation);
                gridB.union(gridToArray(x+1, y), presentArrayLocation);
            }
            if ((y + 1) < N && state[x][y+1]) {
                gridA.union(gridToArray(x, y+1), presentArrayLocation);
                gridB.union(gridToArray(x, y+1), presentArrayLocation);
            }
            if ((x - 1) >= 0 && state[x - 1][y]) {
                gridA.union(gridToArray(x - 1, y), presentArrayLocation);
                gridB.union(gridToArray(x - 1, y), presentArrayLocation);
            }
            if ((y - 1) >= 0  && state[x][y - 1]) {
                gridA.union(gridToArray(x, y - 1), presentArrayLocation);
                gridB.union(gridToArray(x, y - 1), presentArrayLocation);
            }
        }
    }

    private int gridToArray(int i, int j) {
        return i * N + j + 1;
    }

    public boolean isOpen(int i, int j) {
        int x = i - 1;
        int y = j - 1;
        if (x < 0 || y < 0 || x >= N || y >= N)
            throw new IndexOutOfBoundsException("Index is out of bounds!");
        return state[x][y]; 
    }     // is site (row x, column j) open?

    public boolean isFull(int i, int j) {
        int x = i - 1;
        int y = j - 1;
        if (x < 0 || y < 0 || x >= N || y >= N)
            throw new IndexOutOfBoundsException("Index is out of bounds!");
        return (gridB.connected(0, gridToArray(x, y)) && state[x][y]);
    }     // is site (row i, column j) full?

    public boolean percolates() {
        if (N != 1)
            return gridA.connected(0, N * N + 1); 
        else
            return (gridA.connected(0, N * N + 1) && state[0][0]); 
    }             // does the system percolate?
}
