/**
 * Proxy class for Android's Point library
 */

public class Point {
    public int x;
    public int y;
    
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public void offset(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }
}
