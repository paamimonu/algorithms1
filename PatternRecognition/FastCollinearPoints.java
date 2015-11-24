import java.util.Arrays;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collections;
public class FastCollinearPoints {
    private HashMap<Double, List<Point>> foundSegments = new HashMap<>();
    private List<LineSegment> lines = new ArrayList<>();

    private List<Point> newPoints;
    public FastCollinearPoints(Point[] points) {

        checkDuplicatedEntries(points);
        int N = points.length;
        Point[] copyPoints = new Point[N];
        System.arraycopy(points, 0, copyPoints, 0, points.length);
        int m = 0;
        for (int a = 0; a < N; a++) {
            int[] index = new int[N];
            double[] slope = new double[N];
            for (int k = 0; k < points.length; k++) {
                slope[k] = points[a].slopeTo(points[k]);
            }

            Arrays.sort(slope);
            Arrays.sort(copyPoints, points[a].slopeOrder());
            int k = 0;
            int i = 1; 
            index[k++] = 0;
            while (i < N - 1) {
                index[k] = i;
                while ((i < N - 1) && (slope[i] == slope[i+1])) {
                    index[++k] = i + 1;
                    i++;
                }
                if (k < 3) {
                    index = null;
                    index = new int[N];
                }
                else {
                    newPoints = new ArrayList<>();
                    for (int p = 0; p < k + 1; p++) {
                        newPoints.add(copyPoints[index[p]]);
                    }
//                    Collections.sort(newPoints);
//                    Point firstPoint = newPoints.get(0);
//                    Point lastPoint = newPoints.get(newPoints.size() - 1);
                    Point firstPoint = Collections.min(newPoints);
                    Point lastPoint = Collections.max(newPoints);
                    List<Point> endPoints = foundSegments.get(slope[i]);
                    if (endPoints == null) {
                        endPoints = new ArrayList<>();
                        endPoints.add(lastPoint);
                        foundSegments.put(slope[i], endPoints); 
                        lines.add(new LineSegment(firstPoint, lastPoint)); 
                    }
                    else {
                        boolean flag = false;
                        for(Point currentPoint : endPoints) {
                            if (currentPoint.compareTo(lastPoint) == 0) {
                                flag = true;
                                break;
                            }
                        }
                        if (!flag) {
                            endPoints.add(lastPoint);
                            foundSegments.put(slope[i], endPoints);
                            lines.add(new LineSegment(firstPoint, lastPoint));
                        }
                    }
                    k = 0;
                    newPoints = null;
                    endPoints = null;
                    index[k++] = 0;
                }
                i++;
                k = 1;
            }
            index = null;
            slope = null;
        }
    }

    public int numberOfSegments() { 
        return lines.size();
    }

    public LineSegment[] segments() {
        return lines.toArray(new LineSegment[lines.size()]);
    }

    private void checkDuplicatedEntries(Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException("Duplicated entries in given points.");
                }
            }
        }
    }
}
