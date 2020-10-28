package sample;

import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.LinkedList;
import java.util.Stack;

public class oblig2 extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane pane = new BorderPane();
        LinkedList<Shape> shapes = new LinkedList<>();
        Stack figur = new Stack();

        // Knapper
        HBox knapperad = new HBox(20);

        ToggleButton firkantbtn = new ToggleButton("Firkant");
        ToggleButton sirkelbtn = new ToggleButton("Sirkel");
        ToggleButton linjebt = new ToggleButton("Linje");
        ToggleButton tekstbtn = new ToggleButton("Tekst");

        ToggleButton[] toolsarr = {firkantbtn,sirkelbtn,linjebt,tekstbtn};
        ToggleGroup group = new ToggleGroup();

        TextArea tekst = new TextArea("Skriv her");
        tekst.setPrefRowCount(1);

        knapperad.setStyle("-fx-border-color: BLACK");

        // Setter en størrelse og gjør mus om til en hånd for å vise at knapp er klikkbar samt setter alle i samme gruppe
        for(ToggleButton verk : toolsarr){
            verk.setMinWidth(90);
            verk.setToggleGroup(group);
            verk.setCursor(Cursor.HAND);
        }

       Canvas canvas = new Canvas(1080,720);
       GraphicsContext gc;
       gc = canvas.getGraphicsContext2D();
       gc.setLineWidth(1);

        ColorPicker cplinje = new ColorPicker(Color.BLACK);
        ColorPicker cpfill = new ColorPicker(Color.TRANSPARENT);

        Line linje = new Line();
        Rectangle firkant = new Rectangle();
        Circle sirkel = new Circle();

        knapperad.getChildren().addAll(firkantbtn,sirkelbtn,linjebt,tekstbtn,tekst,cpfill,cplinje);

        Pane panelinfo = new Pane();
        Text infotekst = new Text();

        canvas.setOnMousePressed(e -> {
            if (firkantbtn.isSelected()){
                gc.setStroke(cplinje.getValue());
                gc.setFill(cpfill.getValue());
                firkant.setY(e.getY());
                firkant.setX(e.getX());
            }
        });


        canvas.setOnMouseReleased(e -> {
            if (firkantbtn.isSelected()) {
                firkant.setWidth(Math.abs((e.getX() - firkant.getX())));
                firkant.setHeight(Math.abs((e.getY() - firkant.getY())));
                gc.fillRect(firkant.getX(), firkant.getY(), firkant.getWidth(), firkant.getHeight());
                gc.strokeRect(firkant.getX(), firkant.getY(), firkant.getWidth(), firkant.getHeight());

                shapes.push(new Rectangle(firkant.getX(),firkant.getY(),firkant.getHeight(),firkant.getWidth()));
            }
        });

        infotekst.setText(shapes.getFirst());

        pane.setCenter(canvas);
        pane.setRight(panelinfo);
        pane.setBottom(knapperad);
        Scene scene = new Scene(pane,1080,790);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
