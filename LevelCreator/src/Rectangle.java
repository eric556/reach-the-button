import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Rectangle extends Shape {
    public double width;
    public double height;

    public Rectangle(String name, double x, double y, double width, double height){
        this.name = name;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setStroke(Color.BLACK);
        gc.strokeRect(x - width/2, y-height/2, width, height);
    }

    @Override
    public String toString() {
        return "" + name + "={position={x="+x+",y="+y+"},shape={type=\"rectangle\",s=love.physics.newRectangleShape("+width+","+height+")},type=\"static\",name=\"" + name + "\"}";
    }

    @Override
    public boolean contains(double x, double y) {
        double tx = x + width/2;
        double ty = y + height/2;
        return tx > this.x && ty > this.y && tx < this.x + width && ty < this.y + height;
    }
}
