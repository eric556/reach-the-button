import javafx.scene.canvas.GraphicsContext;
import sun.security.provider.SHA;

import java.io.*;
import java.util.ArrayList;

public class Map {
    public ArrayList<Shape> shapes;
    public static int id = 0;

    Map(){
        shapes = new ArrayList<>();
    }

    public void addShape(Shape shape){
        shapes.add(shape);
    }

    public void addRectangle(double x, double y, double width, double height, String name){
        shapes.add(new Rectangle(name, x, y, width, height));
    }

    public void addCircle(double x, double y, double radius, String name){
        shapes.add(new Circle(name, x, y, radius));
    }

    public void removeShape(Shape shape){
        shapes.remove(shape);
    }

    public void save(String filename){
        try(FileOutputStream fout = new FileOutputStream(filename); ObjectOutputStream out = new ObjectOutputStream(fout)) {
            for(Shape shape : shapes){
                out.writeObject(shape);
            }
            out.writeObject(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load(String filename){
        try(FileInputStream fin = new FileInputStream(filename); ObjectInputStream in = new ObjectInputStream(fin)) {
            shapes.clear();
            Object o = in.readObject();
            while(o != null){
                Shape s = (Shape)o;
                shapes.add(s);
                o = in.readObject();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void export(String filename){
        try(PrintWriter writer = new PrintWriter(filename)){
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Entities={");
            stringBuilder.append("\n");
            for(Shape shape : shapes) {
                stringBuilder.append(shape.toString());
                stringBuilder.append(",");
                stringBuilder.append("\n");
            }
            stringBuilder.append("}");
            writer.print(stringBuilder.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void draw(GraphicsContext gc){
        shapes.forEach(shape -> shape.draw(gc));
    }
}
