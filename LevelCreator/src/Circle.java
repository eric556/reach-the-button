import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Circle extends Shape {
    public double radius;

    public Circle(String name, double posX, double posY, double radius){
        this.name = name;
        this.x = posX;
        this.y = posY;
        this.radius = radius;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setStroke(Color.BLACK);
        gc.strokeOval(x - radius,y - radius,radius * 2, radius * 2);
    }

    @Override
    public String toString() {
        return "" + name + "={position={x="+x+",y="+y+"},shape={type=\"circle\",s=love.physics.newCircleShape("+radius+")},type=\"static\",name=" + name + "}";
    }

    @Override
    public boolean contains(double x, double y) {
        double tx = x;
        double ty = y;
        System.out.println("Transformed: " + tx + ", " + ty);
        System.out.println("VS: " + this.x + ", " + this.y);
        return (((tx-this.x) * (tx-this.x)) + ((ty - this.y) * (ty - this.y))) < radius * radius;
    }
}
