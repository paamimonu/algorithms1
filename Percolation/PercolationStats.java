import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double [] thresholdArray;
    private Percolation perc;
    private int T, N;

    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0)
            throw new IllegalArgumentException("Enter a valid grid dimension!");
        thresholdArray = new double[T];
        double countOfOpenSites;
        this.T = T;
        this.N = N;
        for (int i = 0; i < T; i++) {
            perc = new Percolation(N);
            countOfOpenSites = 0;
            while (!perc.percolates()) {
                int tempI = StdRandom.uniform(1, N + 1);
                int tempJ = StdRandom.uniform(1, N + 1);
                if (!perc.isOpen(tempI, tempJ)) {
                    perc.open(tempI, tempJ);
                    countOfOpenSites++;
                }
            }
            thresholdArray[i] = countOfOpenSites; 
            perc = null;
        }
    }

    public double mean() { // sample mean of percolation threshold
        return StdStats.mean(thresholdArray) / (N * N);
    }

    public double stddev() { 
        return StdStats.stddev(thresholdArray) / (N * N);
    }

    public double confidenceLo() {
        double stdDev = StdStats.stddev(thresholdArray) / (N * N);
        double mean = StdStats.mean(thresholdArray) / (N * N);
        double sqrtOfT = Math.sqrt(T);
        return (mean - ((1.96 * stdDev)/sqrtOfT));
    }

    public double confidenceHi() {
        double stdDev = StdStats.stddev(thresholdArray) / (N * N);
        double mean = StdStats.mean(thresholdArray) / (N * N);
        double sqrtOfT = Math.sqrt(T);
        return (mean + ((1.96 * stdDev)/sqrtOfT));
    }

    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats percStat = new PercolationStats(N, T);
        StdOut.println("mean\t= " + percStat.mean());
        StdOut.println("stddev\t= " + percStat.stddev());
        StdOut.print("95% confidence interval\t= ");
        StdOut.println(percStat.confidenceLo() + ", " + percStat.confidenceHi());
    } // test client (described below)
}
