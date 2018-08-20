import javafx.scene.canvas.GraphicsContext;

import java.io.Serializable;

public abstract class Shape implements Serializable{
    public double x;
    public double y;
    public String name;

    public abstract void draw(GraphicsContext gc);
    public abstract String toString();
    public abstract boolean contains(double x, double y);
}
