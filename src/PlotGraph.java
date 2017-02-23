import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by laurenleung on 2/20/17.
 */

public class PlotGraph implements Graph {

    private String title;
    private TreeMap<Date, Double> data;

    public PlotGraph(String title) {
        if (title == null) {
            throw new IllegalArgumentException("Error initializing PlotGraph - Graph cannot have a null title");
        }
        this.title = title;
        this.data = new TreeMap<Date, Double>();
    }

    /**
     * Given a start date, an end date, and whether the border dates should be included, 
     * return a list of points containing all the data stored in the graph.
     *
     * @param   start_date  date of the earliest possible point in the graph (inclusive)
     * @param   end_date    date of the latest possible point in the graph (exclusive)
     * 
     * @return              List<Point>
     */
    public List<Point> getGraph(Date start_date, Date end_date) {
        List<Point> ordered_points = new ArrayList<>();
        SortedMap<Date, Double> values_in_range = 
                this.data.subMap(setTime(start_date, 0, 0), setTime(end_date, 0, 0));
        
        for (Date d : values_in_range.keySet()) {
            int value_at_date_as_int = (int) Math.floor(values_in_range.get(d));
            Point p = new Point(getDifferenceInDays(start_date, d), value_at_date_as_int);
            ordered_points.add(p);
        }
        return ordered_points;
    }
    
    private static Date setTime(Date d, int hours, int minutes) {
        Calendar c = Calendar.getInstance(); 
        c.setTime(d); 
        c.set(Calendar.HOUR_OF_DAY, hours);
        c.set(Calendar.MINUTE, minutes);
        return c.getTime();
    }

    /**
     * Given a list of Points with the origin at 0,
     * scale/manipulate the data so it is centered around a specified
     * point and follows given height/width constraints.
     *
     * @param   original_graph  list of original, unscaled points
     * @param   origin          desired origin point for the new data.
     * @param   x_max           desired length of the x axis
     * @param   y_max           desired height of the y axis
     * @return                  List<Point>
     */
    public static List<Point> getScaledGraph(List<Point> original_graph, Point origin, int x_max,
                                  int y_max) {
        List<Point> scaled_graph = new ArrayList<Point>(original_graph);
        double original_max_x = (double) getMaxX(original_graph);
        double original_max_y = (double) getMaxY(original_graph);
        Double x_ratio = x_max / original_max_x;
        Double y_ratio = y_max / original_max_y;
        for (Point p : scaled_graph) {
            p.x *= x_ratio;
            p.y *= y_ratio;
            p.offset(origin.x, origin.y);
        }
        return scaled_graph;
    }

    private static int getMaxY(List<Point> point_list) {
        int max_y_value = Integer.MIN_VALUE;
        for (Point p : point_list) {
            if (p.y > max_y_value) {
                max_y_value = p.y;
            }
        }
        return max_y_value;
    }

    private static int getMaxX(List<Point> point_list) {
        int max_x_value = Integer.MIN_VALUE;
        for (Point p : point_list) {
            if (p.x > max_x_value) {
                max_x_value = p.x;
            }
        }
        return max_x_value;
    }

    /**
     * Given a date and a value, add them to the current com.example.laurenleung.myapplication.Graph dataset.
     *
     * @param   current_date    date of desired datapoint, will be treated as x-coordinate
     * @param   current_value   value of desired datapoint, will be treated as y-coordinate
     */
    public void addPoint(Date current_date, Double current_value) {
        this.data.put(current_date, current_value);
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public void render() {
        // TODO
    }

    /**
     * Given two Date objects, return the number of days between them
     * Assumes both dates are in the same timezone.
     *
     * @param   date1   the first date you want to compare
     * @param   date2   the second date you want to compare
     * @return          int
     */
    private static int getDifferenceInDays(Date date1, Date date2) {
        long ms_per_day = 86400000;
        long difference_in_ms = date2.getTime() - date1.getTime();
        return (int) Math.floor(difference_in_ms / ms_per_day);
    }
}
