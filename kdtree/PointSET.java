import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RedBlackBST;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import edu.princeton.cs.algs4.StdRandom;

public class PointSET {

    private RedBlackBST<Point2D, Integer> points;
    private int N;

    public PointSET() {      
        points = new RedBlackBST<Point2D, Integer>();
        this.N = 0;
    }

    public boolean isEmpty() {                     // is the set empty? 
        return N == 0;
    }

    public int size() {                        // number of points in the set 
        return N;
    }

    public void insert(Point2D p) { 
        if (p == null) {
           throw new NullPointerException("the point is null");
        }
        if (points.contains(p)) {
            return;
        }
        this.N++;
        points.put(p, N);
    }

    public boolean contains(Point2D p) {           // does the set contain point p? 
        if (p == null) {
           throw new NullPointerException("the point is null");
        }
        return points.contains(p);
    }

    public void draw() {                        // draw all points to standard draw 
        for (Point2D p : points.keys()) {
            p.draw();
        }
    }


    public Iterable<Point2D> range(final RectHV rect) {
       if (rect == null) {
           throw new NullPointerException("the rectangle is null");
       }
       return new Iterable<Point2D>() {
            public Iterator<Point2D> iterator() {
                List<Point2D>  pointList = new ArrayList<>();
                for (Point2D p : points.keys()) {
                    if (rect.contains(p)) {
                        pointList.add(p);
                    }
                }
                return pointList.iterator(); 
            }
        };
    }

    public Point2D nearest(Point2D p) {
       if (p == null) {
           throw new NullPointerException("the point is null");
       }
        double minDistance = Double.POSITIVE_INFINITY;
        Point2D nearestPoint = p;
        for (Point2D thatP : points.keys()) {
            if (p.distanceTo(thatP) < minDistance) {
                minDistance = p.distanceTo(thatP);
                nearestPoint = thatP;
            }
        }
        if (minDistance == Double.POSITIVE_INFINITY) return null;
        return nearestPoint;
    }

    public static void main(String[] args) {
        StdDraw.setCanvasSize(800, 800);
        StdDraw.setXscale(0, 100);
        StdDraw.setYscale(0, 100);
        StdDraw.setPenRadius(.02);
        StdDraw.setPenColor(StdDraw.BLUE);
        int N = 200;
        RectHV rectangle = new RectHV(20, 20, 60, 50);
        PointSET plane = new PointSET();
        Point2D testPoint = new Point2D(50, 50);
        System.out.println(plane.isEmpty());
        for (int i = 0; i < N; i++) {
            int x = StdRandom.uniform(100);
            int y = StdRandom.uniform(100);
            Point2D point = new Point2D(x, y);
            plane.insert(point);
            point = null;
        }
        rectangle.draw();
        StdDraw.setPenColor(StdDraw.RED);
        plane.draw();
        System.out.println("The points inside the rectangle are the following:");
        for (Point2D p : plane.range(rectangle)) {
            System.out.println(p);
        }
        StdDraw.setPenColor(StdDraw.GREEN);
        testPoint.draw();
        System.out.println("NP of  "+ testPoint + " is " + plane.nearest(testPoint));
    }
}
