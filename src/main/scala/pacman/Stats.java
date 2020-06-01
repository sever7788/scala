package pacman;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class Stats {
    public Stage stage;
    public Menu menu;
    public Pane root;
    public Scene scene;
    public Label headLabel;
    public Label xLabel;
    public Label yLabel;
    public ArrayList<ObjectData> screens = new ArrayList<>();
    String path;
    public Stats(Stage stage, Menu menu){
        this.menu = menu;
        this.stage = stage;
        //this.root = new Pane();
        root = new Pane();
        this.scene = new Scene(root, 900, 576);

    }

    public void statsInit(String path){
        this.path = path;

        headLabel = new Label("Наиболее посещаемые координаты: ");
        xLabel =  new Label();
        yLabel =  new Label();
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                stage.setScene(menu.scene);
            }
        });
        inputFile();
        stage.setScene(scene);
    }

    public void inputFile(){
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
            screens=((ArrayList<ObjectData>)ois.readObject());
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        ObjectData[] array = new ObjectData [screens.size()];
        screens.toArray(array);
        int coord[] = lab6.main(array);


        root.getChildren().addAll(headLabel,xLabel,yLabel);
        root.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

        xLabel.setText("X: " + coord[0]);
        yLabel.setText("Y: "+coord[1]);
        xLabel.setTranslateY(50);
        yLabel.setTranslateY(100);
        xLabel.setTranslateX(50);
        yLabel.setTranslateX(50);
        headLabel.setFont(Font.font("mv boli",24));
        headLabel.setTextFill(Color.GOLD);
        xLabel.setFont(Font.font("mv boli",24));
        xLabel.setTextFill(Color.GOLD);
        yLabel.setFont(Font.font("mv boli",24));
        yLabel.setTextFill(Color.GOLD);
    }
}
