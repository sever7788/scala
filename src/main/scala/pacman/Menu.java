package pacman;
import javafx.animation.FadeTransition;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

import javafx.scene.layout.StackPane;

import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Menu {
    public Game game;
    public Stage stage;
    public Scene scene;
    public Stats stats;
    public Replay replay;
    public MenuItem el1;
    public MenuItem el2;
    public MenuItem el3;
    public MenuItem el4;
    public Boolean flStats = false;
    public ArrayList<File> lst;
    public File[] arrFiles;
    public Menu(Stage stage, Game game){
        this.game = game;
        this.stage = stage;
        this.replay = null;
    }
    public void initMenu(){
        File dir = new File("./"); //path указывает на директорию
        arrFiles = dir.listFiles();
        lst = new ArrayList<File>(Arrays.asList(arrFiles));
        StackPane root = new StackPane();
        Image imageFon = new Image(getClass().getResourceAsStream("./img/5.gif"));
        ImageView imgFon = new ImageView(imageFon);
        Image imageLogo = new Image(getClass().getResourceAsStream("./img/6.png"));
        ImageView imgLogo = new ImageView(imageLogo);

        imgFon.setFitHeight(600);
        imgFon.setFitWidth(900);
        imgLogo.setFitHeight(121);
        imgLogo.setFitWidth(512);
        StackPane.setMargin(imgLogo, new Insets(25, 0, 0, 0));
        StackPane.setAlignment(imgLogo, Pos.TOP_CENTER);

        MenuItem listReplays = new MenuItem("СПИСОК ПОВТОРОВ");
        MenuItem listStats = new MenuItem("СТАТИСТИКА ИГР");
        MenuItem newGame = new MenuItem("НОВАЯ ИГРА");
        MenuItem exitGame = new MenuItem("ВЫХОД");
        SubMenu mainMenu = new SubMenu(
                newGame, listReplays, listStats, exitGame
        );

        el1 = new MenuItem("ПУСТО");
        el2 = new MenuItem("ПУСТО");
        el3 = new MenuItem("ПУСТО");
        el4 = new MenuItem("ПУСТО");
        MenuItem listBack = new MenuItem("НАЗАД");
        SubMenu replaysMenu = new SubMenu(
                el1,el2,el3,el4,listBack
        );

        MenuBox menuBox = new MenuBox(mainMenu);
        menuBox.setTranslateX(250);
        menuBox.setTranslateY(200);

        el1.setOnMouseClicked(event->buttonReplay(el1.text.getText()));
        el2.setOnMouseClicked(event->buttonReplay(el2.text.getText()));
        el3.setOnMouseClicked(event->buttonReplay(el3.text.getText()));
        el4.setOnMouseClicked(event->buttonReplay(el4.text.getText()));
        listStats.setOnMouseClicked(event->{
            flStats = true;
            menuBox.setSubMenu(replaysMenu);
            sort();
            initList();
        });
        listReplays.setOnMouseClicked(event->{
            menuBox.setSubMenu(replaysMenu);
            sort();
            initList();
        });
        listBack.setOnMouseClicked(event->{menuBox.setSubMenu(mainMenu); flStats = false;});
        newGame.setOnMouseClicked(event->{
            replay = new Replay();
            game = new Game(stage, this, replay);
            game.initGame("");
            game = null;
            replay = null;
        });
        exitGame.setOnMouseClicked(event->System.exit(0));
        root.getChildren().addAll(imgFon,imgLogo, menuBox);

        scene = new Scene(root, 900, 600);
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                FadeTransition ft = new FadeTransition(Duration.seconds(1),menuBox);
                if (!menuBox.isVisible()) {
                    ft.setFromValue(0);
                    ft.setToValue(1);
                    ft.play();
                    menuBox.setVisible(true);
                }
                else{
                    ft.setFromValue(1);
                    ft.setToValue(0);
                    ft.setOnFinished(evt ->menuBox.setVisible(false));
                    ft.play();

                }
            }
        });
        initList();
        stage.setScene(scene);
    }
    public void buttonReplay(String path){
        System.out.println(path);
        if(path!="ПУСТО" && flStats == false) {
            replay = new Replay();
            game = new Game(stage, this, replay);
            game.flReplay = true;
            game.initGame(path);
            replay = null;
        }else{
            stats = new Stats(stage, this);
            stats.statsInit(path);
        }
    }

    public void  initList(){
        int number_element = 0;
        int countFile = 0;
        this.lst.trimToSize();
        Iterator<File> iter = lst.iterator();
        while(iter.hasNext()){
            File f = iter.next();
            if(f.toString().contains(".bin")){
                number_element++;
                switch(number_element) {
                    case 1: el1.text.setText(lst.get(countFile).toString()); break;
                    case 2: el2.text.setText(lst.get(countFile).toString());break;
                    case 3: el3.text.setText(lst.get(countFile).toString()); break;
                    case 4: el4.text.setText(lst.get(countFile).toString());break;
                }
            }
            countFile++;
        }
    }

    public void sort(){
        Iterator<File> iter = lst.iterator();
        while (iter.hasNext()){
            File f = iter.next();
            if(f.toString().contains(".bin")) {
                String fileName = f.toString().replaceAll(".\\\\", "");

            }else{
                iter.remove();
            }
        }
        File [] Files = new File[lst.size()];
        Files = lab5.main(lst.toArray(Files), lst.size());
        lst = new ArrayList<File>(Arrays.asList(Files));
    }
}
