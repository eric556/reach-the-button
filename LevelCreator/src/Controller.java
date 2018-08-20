import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;


public class Controller {

    enum Modes {
        RECT,
        CIRCLE,
        DELETE,
        NONE
    }

    @FXML private Canvas canvas;
    @FXML private Label modeLabel;
    private Map map;
    private Modes mode;
    boolean startRect = false;
    private double rectStartX;
    private double rectStartY;
    private double rectEndX;
    private double rectEndY;
    boolean startCircle = false;
    private double circleStartX;
    private double circleStarty;
    private double circleEndX;
    private double circleEndY;

    private int rectCount = 0;
    private int circleCount = 0;

    public void initialize(){
        map = new Map();
        //map.addRectangle(350,500,700,10,"floor");
        //map.addRectangle(350 + (700/2),450,10,100,"wall");
        map.draw(canvas.getGraphicsContext2D());
        mode = Modes.NONE;
    }


    public void loadButtonClick(ActionEvent actionEvent) {
        FileChooser chooser = new FileChooser();
        File choosen = chooser.showOpenDialog(null);
        map.load(choosen.getPath());
        map.draw(canvas.getGraphicsContext2D());
    }

    public void newButtonClick(ActionEvent actionEvent){
        Map.id++;
        map = new Map();
    }

    public void newSaveClick(ActionEvent actionEvent) {
        FileChooser chooser = new FileChooser();
        File choosen = chooser.showSaveDialog(null);
        map.save(choosen.getPath());
    }

    public void exportClick(ActionEvent actionEvent) {
        FileChooser chooser = new FileChooser();
        File choosen = chooser.showSaveDialog(null);
        map.export(choosen.getPath());
    }

    public void mouseClickHandler(MouseEvent mouseEvent) {
        if(mouseEvent.getButton() == MouseButton.SECONDARY){
            final ContextMenu contextMenu = new ContextMenu();
            MenuItem newRect = new MenuItem("Create Rectangle");

            newRect.setOnAction(event -> {
                modeLabel.setText("Mode: Create Rectangle");
                mode = Modes.RECT;
            });

            MenuItem newCircle = new MenuItem("Create Circle");

            newCircle.setOnAction(event -> {
                modeLabel.setText("Mode: Create Circle");
                mode = Modes.CIRCLE;
            });

            MenuItem deleteShape = new MenuItem("Delete Shape");

            deleteShape.setOnAction(event -> {
                modeLabel.setText("Mode: Delete");
                mode = Modes.DELETE;
            });

            mode = Modes.NONE;
            modeLabel.setText("Mode: None");

            contextMenu.getItems().addAll(newRect,newCircle,deleteShape);
            contextMenu.show(canvas, mouseEvent.getScreenX(), mouseEvent.getScreenY());
        }else if(mouseEvent.getButton() == MouseButton.PRIMARY){
            switch (mode){
                case RECT:
                    if(startRect){
                        rectEndX = mouseEvent.getX();
                        rectEndY = mouseEvent.getY();
                        double width = Math.abs(rectEndX - rectStartX);
                        double height = Math.abs(rectEndY- rectStartY);
                        map.addRectangle(rectStartX + width/2, rectStartY+height/2, width, height, "rect_" + rectCount + "_" + LocalDateTime.now().toString().hashCode());
                        rectCount++;
                        startRect = false;
                    }else{
                        rectStartX = mouseEvent.getX();
                        rectStartY = mouseEvent.getY();
                        startRect = true;
                    }
                    break;
                case CIRCLE:
                    if(startCircle){
                        circleEndX = mouseEvent.getX();
                        circleEndY = mouseEvent.getY();
                        double dX = Math.abs(circleEndX - circleStartX);
                        double dY = Math.abs(circleEndY - circleStarty);
                        double radius = (dX > dY)? dX : dY;
                        map.addCircle(circleStartX, circleStarty, radius, "circle_" + circleCount + "_" + LocalDateTime.now().toString().hashCode());
                        circleCount++;
                        startCircle = false;
                    }else{
                        circleStartX = mouseEvent.getX();
                        circleStarty = mouseEvent.getY();
                        startCircle = true;
                    }
                    break;
                case DELETE:
                    System.out.println("Checking: " + mouseEvent.getX() + ", " + mouseEvent.getY());
                    ArrayList<Shape> toRemove = new ArrayList<>();
                    map.shapes.forEach(shape -> {
                        if(shape.contains(mouseEvent.getX(),mouseEvent.getY())){
                            System.out.println(shape.toString());
                            toRemove.add(shape);
                        }
                    });
                    map.shapes.removeAll(toRemove);
                    break;
                case NONE:
                    break;
            }
            canvas.getGraphicsContext2D().clearRect(0,0,canvas.getWidth(),canvas.getHeight());
            map.draw(canvas.getGraphicsContext2D());
        }
    }
}
