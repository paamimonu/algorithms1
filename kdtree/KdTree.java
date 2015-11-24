import edu.princeton.cs.algs4.RectHV;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import java.util.Iterator;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import edu.princeton.cs.algs4.StdRandom;

public class KdTree {
    private Node root;
    private int N;
//    private Point2D nearest;
    private class Node {
        private Point2D p;      // the point
        private RectHV rect; 
        private Node lb;        // the left/bottom subtree
        private Node rb;        // the right/top subtree
        private int level;

        public Node(Point2D p, RectHV rect, int level) {
            this.p = p;
            this.rect = rect;
            this.level = level;
        }

        public int getLevel() {
            return level;
        }
        public double getXmin() {
            return rect.xmin();
        }
        public double getXmax() {
            return rect.xmax();
        }
        public double getYmin() {
            return rect.ymin();
        }
        public double getYmax() {
            return rect.ymax();
        }
        public double getX() {
            return p.x();
        }

        public double getY() {
            return p.y();
        }
    }

    public KdTree() {   

    }

    private int kdcompare(Node node, Point2D p) {
        if (node.getLevel() % 2 == 0) {
            if ((p.x()) < node.getX()) {
                return -1;
            }
            else if ((p.x()) > node.getX()) {
                return +1;
            }
            else {
                return 0;
            }
        }
        else {
            if ((p.y()) < node.getY()) {
                return -1;
            }
            if ((p.y()) > node.getY()) {
                return +1;
            }
            else {
                return 0;
            }
        }
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
        root = add(p, root, 0, null); 
    }

    private Node add(Point2D point, Node node, int level, Node parent) {
        if (node == null) {
            RectHV kdrectangle;
            if (N == 0) {
                kdrectangle = new RectHV(0, 0, 1, 1);
                N++;
                //                StdDraw.setPenColor(StdDraw.GREEN);
                //                kdrectangle.draw();
                //                StdDraw.setPenColor(StdDraw.BLUE);
                //                point.draw();
                return new Node(point, kdrectangle, level);
            }
            else {
                double xmin = parent.getXmin();
                double ymin = parent.getYmin();
                double xmax = parent.getXmax();
                double ymax = parent.getYmax();
                N++;
                if ((parent.getLevel() % 2 == 0) && (kdcompare(parent, point) < 0)) {
                    kdrectangle = new RectHV(xmin, ymin, parent.getX(), ymax);
                    //                    StdDraw.setPenColor(StdDraw.GREEN);
                    //                    kdrectangle.draw();
                    //                    StdDraw.setPenColor(StdDraw.BLUE);
                    //                    point.draw();
                    return new Node(point, kdrectangle, level);
                }
                if ((parent.getLevel() % 2 == 0) && (kdcompare(parent, point) >= 0)) {
                    kdrectangle = new RectHV(parent.getX(), ymin, xmax, ymax);
                    //                    StdDraw.setPenColor(StdDraw.GREEN);
                    //                    kdrectangle.draw();
                    //                    StdDraw.setPenColor(StdDraw.BLUE);
                    //                    point.draw();
                    return new Node(point, kdrectangle, level);
                }
                if ((Math.abs(parent.getLevel()) % 2 == 1) && (kdcompare(parent, point) < 0)) {
                    kdrectangle = new RectHV(xmin, ymin, xmax, parent.getY());
                    //                    StdDraw.setPenColor(StdDraw.GREEN);
                    //                    kdrectangle.draw();
                    //                    StdDraw.setPenColor(StdDraw.BLUE);
                    //                    point.draw();
                    return new Node(point, kdrectangle, level);
                }
                if ((Math.abs(parent.getLevel()) % 2 == 1) && (kdcompare(parent, point) >= 0)) {
                    kdrectangle = new RectHV(xmin, parent.getY(), xmax, ymax);
                    //                    StdDraw.setPenColor(StdDraw.GREEN);
                    //                    kdrectangle.draw();
                    //                    StdDraw.setPenColor(StdDraw.BLUE);
                    //                    point.draw();
                    return new Node(point, kdrectangle, level);
                }
            }
        }

        if (node !=null && node.getX() == point.x() && node.getY() == point.y()) {
            return node;
        }

        if (node !=null && kdcompare(node, point) < 0) {
            node.lb  =  add(point, node.lb, (level + 1) % 2, node);
        }
        else {
            if (node != null) {
                node.rb  =  add(point, node.rb, (level + 1) % 2, node);
            }    
        }
        return node;
    }

    public boolean contains(Point2D p) {           // does the set contain point p? 
        if (p == null) {
            throw new NullPointerException("the point is null");
        }
        return get(p) != null;
    }

    private Point2D get(Point2D p) {
        return get(root, p);
    }

    private Point2D get(Node node,  Point2D p) {
        if (node == null) {
            return null;
        }
        int cmp = kdcompare(node, p);
        if      (cmp < 0) return get(node.lb, p);
        else if (cmp > 0) return get(node.rb, p);
        else {
            if ((node.getX() == p.x()) && (node.getY() == p.y())) {
                return p;
            }
            else {
                return get(node.rb, p);
            }
        }
    }

    //   public Iterable<Point2D> keys() {
    //       return keys(min(), max());
    //   }
    //   private Iterable<Point2D> keys(Point2D lo, Point2D hi) {
    //       Queue<Point2D> queue = new Queue<Point2D>();
    //       keys(root, queue, lo, hi);
    //       return queue;
    //   } 
    //   private void keys(Node node, Queue<Point2D> queue, Point2D lo, Point2D hi) { 
    //       if (node == null) return; 
    //       int cmplo = kdcompare(node, lo); 
    //       int cmphi = kdcompare(node, hi); 
    //       if (cmplo <= 0 && cmphi >= 0) queue.enqueue(node.p); 
    //       if (cmplo < 0) keys(node.lb, queue, lo, hi); 
    //       if (cmphi > 0) keys(node.rb, queue, lo, hi); 
    //   } 



    private Iterable<Point2D> keys() {
        return new Iterable<Point2D>() {
            public Iterator<Point2D> iterator() {
                Queue<Point2D> keys = new Queue<Point2D>();
                Queue<Node> queue = new Queue<Node>();
                queue.enqueue(root);
                while (!queue.isEmpty()) {
                    Node x = queue.dequeue();
                    if (x == null) continue;
                    keys.enqueue(x.p);
                    queue.enqueue(x.lb);
                    queue.enqueue(x.rb);
                }
                return keys.iterator();
            }
        };
    }

    //    private Point2D min() {
    //        if (isEmpty()) throw new NoSuchElementException("empty tree");
    //        return min(root).p;
    //    } 
    //
    //    private Node min(Node x) { 
    //        if (x.lb == null) return x; 
    //        else                return min(x.lb); 
    //    } 
    //
    //    private Point2D max() {
    //        if (isEmpty()) throw new NoSuchElementException("empty tree");
    //        return max(root).p;
    //    } 
    //
    //    private Node max(Node x) {
    //        if (x.rb == null) return x; 
    //        else return max(x.rb); 
    //    } 

    public void draw() {
        for (Point2D p : keys()) {
            p.draw();
        }        // draw all points to standard draw 
    }


    public Iterable<Point2D> range(final RectHV rect) {
        if (isEmpty()) throw new NoSuchElementException("empty tree");
        if (rect == null) {
            throw new NullPointerException("the rectangle is null");
        }
        return new Iterable<Point2D> () {
            public Iterator<Point2D> iterator() {
                List<Point2D> pointList = new ArrayList<>();
                search(root, rect, pointList); 
                return pointList.iterator(); 
            }
        };
    }

    private void search(Node root, RectHV rect, List<Point2D> pointList) {
        if (root == null) return;
        if (root.getLevel() % 2 == 0) {
            if ((rect.xmin() <= root.p.x()) && (root.p.x() <= rect.xmax())) {
                if (rect.contains(root.p)) {
                    pointList.add(root.p);
                }
                search(root.lb, rect, pointList);
                search(root.rb, rect, pointList);
            }
            if (root.p.x() < rect.xmin()) {
                search(root.rb, rect, pointList);
            }
            if (root.p.x() > rect.xmax()) {
                search(root.lb, rect, pointList);
            }
        }
        if (Math.abs(root.getLevel()) % 2 == 1) {
            if ((rect.ymin() <= root.p.y()) && (root.p.y() <= rect.ymax())) {
                if (rect.contains(root.p)) {
                    pointList.add(root.p);
                }
                search(root.lb, rect, pointList);
                search(root.rb, rect, pointList);
            }
            if (root.p.y() < rect.ymin()) {
                search(root.rb, rect, pointList);
            }
            if (root.p.y() > rect.ymax()) {
                search(root.lb, rect, pointList);
            }
        }
    }

    //    public Iterable<Point2D> range(final RectHV rect) {  
    //        if (rect == null) {
    //            throw new NullPointerException("the rectangle is null");
    //        }
    //        return new Iterable<Point2D>() {
    //            public Iterator<Point2D> iterator() {
    //                List<Point2D>  pointList = new ArrayList<>();
    //                for (Point2D p : keys()) {
    //                    if (rect.contains(p)) {
    //                        pointList.add(p);
    //                    }
    //                }
    //                return pointList.iterator(); 
    //            }
    //        };
    //    }



    public Point2D nearest(Point2D p)    
    {
        return nearest(root, p, 1);
    }

    private Point2D nearestPoint;

    private Point2D nearest(Node node, Point2D point, int level) 
    {
        if (node == null) return null;

        if(nearestPoint == null || node.p.distanceTo(point) > node.p.distanceTo(nearestPoint)) {
            nearestPoint = node.p;
        }

        if (Math.abs(level) % 2 == 1) {
            if ( node.p.x() < point.x() && node.rb != null) {
                nearest(node.rb, point, level + 1);
            }
            else if ( node.p.x() >= point.x() && node.lb != null) {
                nearest(node.lb, point, level + 1);
            }
        }
        else {
            if ( node.p.y() < point.y() && node.rb != null) {
                nearest(node.rb, point, level + 1);
            }
            else if (node.p.y() >= point.y() && node.lb != null) {
                nearest(node.lb, point, level + 1);
            }
        }

        return nearestPoint;
    }

    //     public Point2D nearest(Point2D qp) {
    //         if (qp == null) {
    //             throw new NullPointerException("the point is null");
    //         }
    //         this.nearest = root.p;
    //         double referenceDistance = Double.POSITIVE_INFINITY;
    //         if (kdcompare (root, qp) < 0) {
    //             getNearestPointLeft(root, qp, this.nearest, referenceDistance);
    //         }
    //         else {
    //             getNearestPointRight(root, qp, this.nearest, referenceDistance);
    //         }
    //         return this.nearest;
    //     }
    // 
    //     private void getNearestPointLeft(Node node, Point2D point, Point2D nearest, double referenceDistance) {
    //         if (node == null) return;
    // 
    //         System.out.println("calculating distance between " + node.p + "and " + point);
    //         if (node.p.distanceTo(point) < referenceDistance) {
    //             referenceDistance = node.p.distanceTo(point);
    //             System.out.println("updating nearest to " + node.p);
    //             this.nearest = node.p;
    //         }
    // 
    //         getNearestPointLeft(node.lb, point, this.nearest, referenceDistance);
    // 
    //         if (Math.abs(node.getLevel()) % 2 == 1) {
    //             getNearestPointLeft(node.rb, point, this.nearest, referenceDistance);
    //         }
    // 
    //         if (node.getLevel() % 2 == 0) {
    //             if ((node.rb != null) && (perpendicularDistanceLeft(node, point) < referenceDistance)) {
    //                 getNearestPointLeft(node.rb, point, this.nearest, referenceDistance);
    //             }
    //         }
    //     }
    // 
    // 
    //     private void getNearestPointRight(Node node, Point2D point, Point2D nearest, double referenceDistance) {
    //         if (node == null) return;
    // 
    //        System.out.println("calculating distance between " + node.p + "and " + point + " distance = " + node.p.distanceTo(point));
    //         if (node.p.distanceTo(point) < referenceDistance) {
    //             referenceDistance = node.p.distanceTo(point);
    //           System.out.println("updating nearest to " + node.p);
    //             this.nearest = node.p;
    //         }
    // 
    //         getNearestPointRight(node.rb, point, this.nearest, referenceDistance);
    // 
    //         if (Math.abs(node.getLevel()) % 2 == 1) {
    //             getNearestPointRight(node.lb, point, this.nearest, referenceDistance);
    //         }
    // 
    //         if (node.getLevel() % 2 == 0) {
    //             if ((node.lb != null) && (perpendicularDistanceRight(node, point) < referenceDistance)) {
    //                 getNearestPointRight(node.lb, point, this.nearest, referenceDistance);
    //             }
    //         }
    //     }
    // 
    //     private double perpendicularDistanceLeft(Node node, Point2D point) {
    //         double distance;
    // 
    //         if ((kdcompare(node, point) == kdcompare(node,node.rb.p)) && (kdcompare(node,point)==1)) {
    //             return Math.abs(node.rb.getX() - point.x());
    //         }
    //         else if ((kdcompare(node,point) == kdcompare(node,node.lb.p)) && (kdcompare(node,point) == -1)) {
    //             return Math.abs(node.lb.getX() - point.x());
    //         }
    //         else if ((kdcompare(node,point)==-1) && kdcompare(node,node.rb.p)==1) {
    //             return point.distanceTo(new Point2D(node.rb.getX(),node.getY()));
    //          }
    //         else {
    //             return point.distanceTo(new Point2D(node.lb.getX(),node.getY()));
    //         }
    //     }
    // 
    //     private double perpendicularDistanceRight(Node node, Point2D point) {
    //         double distance;
    // 
    //         if ((kdcompare(node, point) == kdcompare(node,node.rb.p)) && (kdcompare(node,point)==1)) {
    //             return Math.abs(node.rb.getX() - point.x());
    //         }
    //         else if ((kdcompare(node,point) == kdcompare(node,node.lb.p)) && (kdcompare(node,point) == -1)) {
    //             return Math.abs(node.lb.getX() - point.x());
    //         }
    //         else if ((kdcompare(node,point)==-1) && kdcompare(node,node.rb.p)==1) {
    //             return point.distanceTo(new Point2D(node.rb.getX(),node.getY()));
    //         }
    //         else {// ((kdcompare(node,point)==1) && kdcompare(node,node.lb.p) == -1){
    //             return point.distanceTo(new Point2D(node.lb.getX(),node.getY()));
    //         }
    //     }
    // look left
    // if perpendicularDistance < lastBestDistance
    // look right

    //    public Point2D nearest(Point2D p) {
    //        if (p == null) {
    //            throw new NullPointerException("the point is null");
    //        }
    //        double minDistance = Double.POSITIVE_INFINITY;
    //        Point2D nearestPoint = p;
    //        double limit = 0.0;
    //        double xmin = p.x(), xmax = p.x(), ymin = p.y(), ymax = p.y();
    //        while(minDistance == Double.POSITIVE_INFINITY) {
    //            limit = limit + 0.25;
    //            if((xmin - limit ) > 0) xmin = xmin - limit; else xmin = 0.0;
    //            if((xmax + limit ) < 1) xmax = xmax + limit; else xmax = 1.0;
    //            if((ymin - limit ) > 0) ymin = ymin - limit; else ymin = 0.0;
    //            if((ymax + limit ) < 1) ymax = ymax + limit; else ymax = 1.0;
    //            System.out.println (p); 
    //            System.out.println ("xmin = " + xmin +"ymin = " + ymin + "xmax = " + xmax + "ymax = " + ymax); 
    //            RectHV test = new RectHV(xmin, ymin,  xmax, ymax);
    //            for (Point2D thatP : range(test)) {
    //                if (p.distanceTo(thatP) < minDistance) {
    //                    minDistance = p.distanceTo(thatP);
    //                    nearestPoint = thatP;
    //                }
    //            }
    //        }
    //        if (minDistance == Double.POSITIVE_INFINITY) return null;
    //        return nearestPoint;
    //    }





    public static void main(String[] args) {  
        StdDraw.setCanvasSize(600, 600);
        StdDraw.setXscale(0, 1);
        StdDraw.setYscale(0, 1);
        StdDraw.setPenRadius(.01);
        StdDraw.setPenColor(StdDraw.BLUE);
        int N = 16;
        RectHV rectangle = new RectHV(0.20, 0.20, 0.60, 0.50);
        KdTree plane = new KdTree();
        // Point2D testPoint = new Point2D(0.50, 0.50);
        // double rangeMin = 0.0;
        //double rangeMax = 1.0;
        //Random r = new Random();
        //        for (int i = 0; i < N; i++) {
        //            double x = (rangeMin + (rangeMax - rangeMin) * r.nextDouble());
        //            double y = (rangeMin + (rangeMax - rangeMin) * r.nextDouble());
        //            Point2D point = new Point2D(x, y);
        //            System.out.println(point);
        //            plane.insert(point);
        //            point = null;
        //        }

        Point2D testPoint2 = new Point2D(0.50, 0.50);
        Point2D testPoint3 = new Point2D(0.3, 0.4);
        Point2D testPoint4 = new Point2D(0.7, 0.45);
        Point2D testPoint5 = new Point2D(0.25, 0.2);
        Point2D testPoint6 = new Point2D(0.4, 0.6);
        Point2D testPoint7 = new Point2D(0.6, 0.3);
        Point2D testPoint8 = new Point2D(0.8, 0.7);
        Point2D testPoint9 = new Point2D(0.05, 0.1);
        Point2D testPoint10= new Point2D(0.35, 0.25);
        Point2D testPoint11= new Point2D(0.7, 0.2);
        plane.insert(testPoint2);
        plane.insert(testPoint3);
        plane.insert(testPoint4);
        plane.insert(testPoint5);
        plane.insert(testPoint6);
        plane.insert(testPoint7);
        plane.insert(testPoint8);
        plane.insert(testPoint9);
        plane.insert(testPoint10);
        plane.insert(testPoint11);
        // System.out.println("adding: " + (testPoint9));

        //    plane.insert(testPoint10);
        //   System.out.println("adding: " + (testPoint10));
        //        System.out.println("After addind: " + plane.contains(testPoint2));
        //        System.out.println("After addind: " + plane.contains(testPoint3));
        //        System.out.println("After addind: " + plane.contains(testPoint4));
        //        System.out.println("After addind: " + plane.contains(testPoint5));
        //        System.out.println("After addind: " + plane.contains(testPoint6));
        //        System.out.println("The size of the KdTree after adding" + plane.size());
        //        rectangle.draw();
        //        StdDraw.setPenColor(StdDraw.RED);
        //        plane.draw();
        //        System.out.println("The points inside the rectangle are the following:");

        for (Point2D p : plane.range(rectangle)) {
            System.out.println(p);
        }

        Point2D testPoint = new Point2D(0.55, 0.7);
        Point2D result = plane.nearest(testPoint);
        System.out.println("The nearest point is " + result);
        StdDraw.setPenRadius(.05);
        StdDraw.setPenColor(StdDraw.BLACK);
        result.draw();
        StdDraw.setPenRadius(.05);
        StdDraw.setPenColor(StdDraw.RED);
        testPoint.draw();
        //        System.out.println("NP of  "+ testPoint + " is " + plane.nearest(testPoint));
        //        for (Point2D p : plane.keys()) {
        //            System.out.println(p);
        //}
    }
    }
