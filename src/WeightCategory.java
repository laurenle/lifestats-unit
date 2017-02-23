import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by laurenleung on 2/20/17.
 */

public class WeightCategory implements HealthCategory {

    private PlotGraph weight_progress;
    private Double goal;
    private static WeightCategory instance;

    private WeightCategory() {
        this.weight_progress = new PlotGraph("Weight");
    }

    /**
     * Returns the current instance of com.example.laurenleung.myapplication.WeightCategory.
     * Creates a new instance if no such instance exists.
     *
     * @return  com.example.laurenleung.myapplication.WeightCategory
     */
    public static WeightCategory getInstance() {
        if (instance == null) {
            instance = new WeightCategory();
        }
        return instance;
    }

    /**
     * Given a current date and weight, add the information to the com.example.laurenleung.myapplication.WeightCategory's underlying
     * graph
     *
     * @param   current_date    date of weight measurement
     * @param   current_weight  value of weight at specified date
     */
    public void addWeight(Date current_date, Double current_weight) {
        weight_progress.addPoint(current_date, current_weight);
    }

    /**
     * Given a goal weight, update the goal weight of the com.example.laurenleung.myapplication.WeightCategory
     *
     * @param goal
     */
    public void setGoal(Double goal) {
        this.goal = goal;
    }

    @Override
    public Set<Graph> getGraphs() {
        Set<Graph> graphs = new HashSet<Graph>();
        graphs.add(this.weight_progress);
        return graphs;
    }

    @Override
    public String toString() {
        return "Weight";
    }
}
