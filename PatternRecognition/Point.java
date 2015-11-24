import java.util.Arrays;
import java.util.Comparator;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class Point implements Comparable<Point> {
    //    public final Comparator<Point> SLOPE_ORDER = new slopeOrder(); 
    private final int x; 	// x-coordinate of this point
    private final int y; 	// y-coordinate of this point

    /*
     * Initializes a new point.
     *
     * @param  x the <em>x</em>-coordinate of the point
     * @param  y the <em>y</em>-coordinate of the point
     */ 

    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }


    /**
     * Draws this point to standard draw.
     */

    public void draw()
    {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */

    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /*
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertcal;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param  that the other point
     * @return the slope between this point and the specified point
     */

    public double slopeTo(Point that) {
        if (this.y == that.y && this.x == that.x) return Double.NEGATIVE_INFINITY;
        if (this.x == that.x) return Double.POSITIVE_INFINITY;
        if (this.y == that.y) return 0.0;
        return ((double)(this.y - that.y)/(double)(this.x - that.x));
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param  that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     *     	point (x0 = x1 and y0 = y1);
     *     	a negative integer if this point is less than the argument
     *     	point; and a positive integer if this point is greater than the
     *     	argument point
     */

    public int compareTo(Point that) {
        int dx = x - that.x;
        int dy = y - that.y;
        if (dy == 0 && dx == 0)
            return 0;
        if (dy < 0) return -1;
        if (dy == 0)
            if (dx < 0) return -1;
        return 1;
    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     **/


    public Comparator<Point> slopeOrder()
    {                
        return new Comparator<Point>()
        { 
            public int compare(Point p1, Point p2) 
            {
                double slope1, slope2;
                if (y == p1.y && x == p1.x) slope1 = Double.NEGATIVE_INFINITY;
                else if (x == p1.x) slope1 = Double.POSITIVE_INFINITY;
                else if (y == p1.y) slope1 = 0.0;
                else slope1 =  (double)(y - p1.y)/(double)(x - p1.x);

                if (y == p2.y && x == p2.x) slope2 = Double.NEGATIVE_INFINITY;
                else if (x == p2.x) slope2 = Double.POSITIVE_INFINITY;
                else if (y == p2.y) slope2 = 0.0;
                else slope2 =  (double)(y - p2.y)/(double)(x - p2.x);

                if (slope1 < slope2) return -1;
                if (slope2 < slope1) return 1;
                return 0;
            }
        };
    }

    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        String s = "";
        s = s.concat("this point is \n");
        s = s.concat("(" + x + ", " + y + ")");
        return s;
    }

    /**
     * Unit tests the Point data type.
     */




    public static void main(String[] args) {

        // read the N points from a file
        In in = new In(args[0]);
        int N = in.readInt();
        Point p1 = new Point(44, 88);
        Point[] points = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.show(0);
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
       FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        System.out.println(p1);
    }
}
