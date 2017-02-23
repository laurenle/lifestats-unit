/**
 * Created by laurenleung on 2/20/17.
 */

public interface Graph {

    /**
     * Returns the title of a com.example.laurenleung.myapplication.Graph as a String.
     *
     * @return  the title of the current com.example.laurenleung.myapplication.Graph object. If the title is null,
     *          returns an empty String
     */
    public String getTitle();

    /**
     * Paints the com.example.laurenleung.myapplication.Graph object on the screen.
     */
    public void render();
}
