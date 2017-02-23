import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Test;

public class PlotGraphTest {

    private static final Date TODAY = new Date();
    private static final Date YESTERDAY = incrementDate(TODAY, -1);
    private static final Date TOMORROW = incrementDate(TODAY, 1);
    private static final Date LAST_WEEK = incrementDate(TODAY, -7);
    private static final Date NEXT_WEEK = incrementDate(TODAY, 7);
    
    @Test
    public void getTitle() {
        String title = "Testing123";
        PlotGraph plot = new PlotGraph(title);
        assertEquals(title, plot.getTitle());
    }
    
    @Test
    public void getGraphReturnsSinglePoint() {
        String title = "Testing123";
        int weight = 155;
        PlotGraph plot = new PlotGraph(title);
        plot.addPoint(TODAY, (double) weight);
        assertEquals(1, plot.getGraph(YESTERDAY, TOMORROW).size());
    }
    
    @Test
    public void getGraphReturnsCorrectDate() {
        String title = "Testing123";
        int weight = 155;
        PlotGraph plot = new PlotGraph(title);
        plot.addPoint(TODAY, (double) weight);
        Point point_of_interest = plot.getGraph(YESTERDAY, TOMORROW).get(0);
        assertEquals(1, point_of_interest.x);
    }
    
    @Test
    public void getGraphReturnsCorrectWeight() {
        String title = "Testing123";
        int weight = 155;
        PlotGraph plot = new PlotGraph(title);
        plot.addPoint(TODAY, (double) weight);
        Point point_of_interest = plot.getGraph(YESTERDAY, TOMORROW).get(0);
        assertEquals(weight, point_of_interest.y);
    }
    
    @Test
    public void getGraphReturnsCorrectDateMultiple() {
        String title = "Testing123";
        int weight = 155;
        PlotGraph plot = new PlotGraph(title);
        plot.addPoint(YESTERDAY, (double) weight);
        plot.addPoint(TODAY, (double) weight + 1);
        plot.addPoint(TOMORROW, (double) weight - 1);
        assertEquals(2, plot.getGraph(YESTERDAY, incrementDate(TOMORROW, 1)).get(2).x);
    }
    
    @Test
    public void getGraphReturnsCorrectWeightMultiple() {
        String title = "Testing123";
        int weight = 155;
        PlotGraph plot = new PlotGraph(title);
        plot.addPoint(YESTERDAY, (double) weight);
        plot.addPoint(TODAY, (double) weight + 1);
        plot.addPoint(TOMORROW, (double) weight - 1);
        assertEquals(weight - 1, plot.getGraph(LAST_WEEK, NEXT_WEEK).get(2).y);
    }
    
    @Test
    public void getGraphReturnsMultiplePoints() {
        String title = "Testing123";
        int weight = 155;
        PlotGraph plot = new PlotGraph(title);
        plot.addPoint(YESTERDAY, (double) weight);
        plot.addPoint(TODAY, (double) weight + 1);
        plot.addPoint(TOMORROW, (double) weight - 1);
        assertEquals(3, plot.getGraph(LAST_WEEK, NEXT_WEEK).size());
    }
    
    @Test
    public void getGraphIncludesStartDate() {
        String title = "Testing123";
        int weight = 155;
        Date date = setTime(YESTERDAY, 0, 0);
        PlotGraph plot = new PlotGraph(title);
        plot.addPoint(date, (double) weight);
        assertEquals(1, plot.getGraph(YESTERDAY, TOMORROW).size());
    }
    
    @Test
    public void getGraphIncludesEndDate() {
        String title = "Testing123";
        int weight = 155;
        Date date = setTime(TODAY, 23, 59);
        PlotGraph plot = new PlotGraph(title);
        plot.addPoint(date, (double) weight);
        assertEquals(1, plot.getGraph(YESTERDAY, TOMORROW).size());
    }
    
    @Test
    public void getGraphExcludesDateAfterRange() {
        String title = "Testing123";
        int weight = 155;
        PlotGraph plot = new PlotGraph(title);
        plot.addPoint(TOMORROW, (double) weight);
        assertEquals(0, plot.getGraph(YESTERDAY, TODAY).size());
    }
    
    @Test
    public void getGraphExcludesDateBeforeRange() {
        String title = "Testing123";
        int weight = 155;
        PlotGraph plot = new PlotGraph(title);
        plot.addPoint(YESTERDAY, (double) weight);
        assertEquals(0, plot.getGraph(TODAY, TOMORROW).size());
    }
    
    @Test
    public void scaleGraphPreservesOrderingFirst() {
        String title = "Testing123";
        int weight = 155;
        PlotGraph plot = new PlotGraph(title);
        plot.addPoint(TODAY, (double) weight);
        plot.addPoint(TOMORROW, (double) weight / 2);
        List<Point> uncompressed_graph = plot.getGraph(TODAY, NEXT_WEEK);
        List<Point> compressed_graph = PlotGraph.getScaledGraph(uncompressed_graph, 
                new Point(0, 0), 2, 4);
        assertEquals(4, compressed_graph.get(0).y);
    }
    
    @Test
    public void scaleGraphPreservesOrderingLast() {
        String title = "Testing123";
        int weight = 155;
        PlotGraph plot = new PlotGraph(title);
        plot.addPoint(TODAY, (double) weight);
        plot.addPoint(TOMORROW, (double) weight / 2);
        List<Point> uncompressed_graph = plot.getGraph(TODAY, NEXT_WEEK);
        List<Point> compressed_graph = PlotGraph.getScaledGraph(uncompressed_graph, 
                new Point(1, 1), 2, 4);
        assertEquals(2, compressed_graph.get(1).y);
    }
    
    @Test
    public void scaleGraphCompressX() {
        String title = "Testing123";
        int weight = 155;
        PlotGraph plot = new PlotGraph(title);
        plot.addPoint(TODAY, (double) weight);
        plot.addPoint(incrementDate(TODAY, 3), (double) weight - 1);
        List<Point> uncompressed_graph = plot.getGraph(LAST_WEEK, NEXT_WEEK);
        List<Point> compressed_graph = PlotGraph.getScaledGraph(uncompressed_graph, 
                new Point(0, 0), 2, 5);
        assertEquals(2, compressed_graph.get(1).x);
    }
    
    @Test
    public void scaleGraphCompressY() {
        String title = "Testing123";
        int weight = 155;
        PlotGraph plot = new PlotGraph(title);
        plot.addPoint(TODAY, (double) weight);
        plot.addPoint(incrementDate(TODAY, 3), (double) weight - 1);
        List<Point> uncompressed_graph = plot.getGraph(LAST_WEEK, NEXT_WEEK);
        List<Point> compressed_graph = PlotGraph.getScaledGraph(uncompressed_graph, 
                new Point(0, 0), 2, 5);
        assertEquals(5, compressed_graph.get(0).y);
    }
    
    @Test
    public void scaleGraphWithOffsetX() {
        String title = "Testing123";
        int weight = 155;
        PlotGraph plot = new PlotGraph(title);
        plot.addPoint(TODAY, (double) weight);
        plot.addPoint(incrementDate(TODAY, 3), (double) weight - 1);
        List<Point> uncompressed_graph = plot.getGraph(TODAY, NEXT_WEEK);
        List<Point> compressed_graph = PlotGraph.getScaledGraph(uncompressed_graph, 
                new Point(1, 1), 2, 5);
        assertEquals(1, compressed_graph.get(0).x);
    }
    
    @Test
    public void scaleGraphWithOffsetY() {
        String title = "Testing123";
        int weight = 155;
        PlotGraph plot = new PlotGraph(title);
        plot.addPoint(TODAY, (double) weight);
        plot.addPoint(incrementDate(TODAY, 3), (double) weight - 1);
        List<Point> uncompressed_graph = plot.getGraph(TODAY, NEXT_WEEK);
        List<Point> compressed_graph = PlotGraph.getScaledGraph(uncompressed_graph, 
                new Point(1, 1), 2, 5);
        assertEquals(6, compressed_graph.get(0).y);
    }
    
    @Test
    public void testScaleGraphStretchX() {
        String title = "Testing123";
        int weight = 155;
        PlotGraph plot = new PlotGraph(title);
        plot.addPoint(TODAY, (double) weight);
        plot.addPoint(incrementDate(TODAY, 3), (double) weight - 1);
        List<Point> uncompressed_graph = plot.getGraph(LAST_WEEK, NEXT_WEEK);
        List<Point> compressed_graph = PlotGraph.getScaledGraph(uncompressed_graph, 
                new Point(0, 0), 30, 60);
        assertEquals(30, compressed_graph.get(1).x);
    }
    
    @Test
    public void testScaleGraphStretchY() {
        String title = "Testing123";
        int weight = 155;
        PlotGraph plot = new PlotGraph(title);
        plot.addPoint(TODAY, (double) weight);
        plot.addPoint(incrementDate(TODAY, 3), (double) weight - 1);
        List<Point> uncompressed_graph = plot.getGraph(LAST_WEEK, NEXT_WEEK);
        List<Point> compressed_graph = PlotGraph.getScaledGraph(uncompressed_graph, 
                new Point(0, 0), 30, 60);
        assertEquals(60, compressed_graph.get(0).y);
    }
    
    private static Date incrementDate(Date d, int days_forward) {
        Calendar c = Calendar.getInstance(); 
        c.setTime(d); 
        c.add(Calendar.DATE, days_forward);
        return c.getTime();
    }
    
    private static Date setTime(Date d, int hours, int minutes) {
        Calendar c = Calendar.getInstance(); 
        c.setTime(d); 
        c.set(Calendar.HOUR_OF_DAY, hours);
        c.set(Calendar.MINUTE, minutes);
        return c.getTime();
    }
}
