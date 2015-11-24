import java.util.Arrays;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collections;
public class BruteCollinearPoints {
    private List<LineSegment> lines = new ArrayList<>();
    private HashMap<Double, List<Point>> foundSegments = new HashMap<>();
    private List<Point> newPoints;
    public BruteCollinearPoints(Point[] points)
    {   
        checkDuplicatedEntries(points);
        int N = points.length;
        double slope1; double slope2; double slope3;
        for (int i= 0; i<N-3; i++)
        {
            for (int j= i+1; j<N-2;j++)
            {
                slope1 = points[i].slopeTo(points[j]);
                for (int k= j+1; k<N-1; k++)
                {
                    slope2 = points[i].slopeTo(points[k]);
                    if (slope1 == slope2)
                        for (int n= k+1; n<N; n++) {
                            slope3 = points[i].slopeTo(points[n]); 
                            if (slope1 == slope3)
                            {
                                newPoints = new ArrayList<>();
                                newPoints.add(points[i]);
                                newPoints.add(points[j]);
                                newPoints.add(points[k]);
                                newPoints.add(points[n]);
                                Collections.sort(newPoints);
                                Point firstPoint = newPoints.get(0);
                                Point lastPoint = newPoints.get(newPoints.size() - 1);
                                List<Point> endPoints = foundSegments.get(slope1);
                                if (endPoints == null)
                                {
                                    endPoints = new ArrayList<>();
                                    endPoints.add(lastPoint);
                                    foundSegments.put(slope1, endPoints); 
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
                                        foundSegments.put(points[i].slopeTo(points[j]), endPoints); 
                                        lines.add(new LineSegment(firstPoint, lastPoint)); 
                                    }
                                }
                                newPoints = null;
                                endPoints = null;
                            }
                        }
                }
            }
        }
    }
    public int numberOfSegments() {  // correct...
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
