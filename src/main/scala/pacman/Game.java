package pacman;

import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.SepiaTone;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.util.ArrayList;

public class Game {
    public Pacman pacman;
    public Stage stage;
    public Scene scene;
    public MapData map;
    public Label labelCoin;
    public int coins = 0;
    public int capital = 0;
    public Image imageWin;
    public Image imageLose;
    public ImageView imageViewWin;
    public ImageView imageViewLose;
    public Pane root;
    public ArrayList<Block>objects = new ArrayList<>();
    public ArrayList<Ghost>ghosts = new ArrayList<>();
    boolean flX = true;
    boolean flY = false;
    boolean lose = false;
    boolean gameEnd = false;
    boolean ghostMode = false;
    boolean flReplay = false;
    String path;
    AnimationTimer timer;

    public Menu menu;
    public Replay replay;
    Game(Stage stage, Menu menu, Replay replay){
        this.menu = menu;
        this.stage = stage;
        this.replay = replay;
    }

    public void initGame(String path){
        this.path = path;
        imageWin = new Image(getClass().getResourceAsStream("./img/win.png"));
        imageLose = new Image(getClass().getResourceAsStream("./img/lose.png"));
        imageViewWin = new ImageView(imageWin);
        imageViewLose = new ImageView(imageLose);
        imageViewWin.setViewport(new Rectangle2D(0,0,512,394));
        imageViewLose.setViewport(new Rectangle2D(0,0,512,286));
        labelCoin = new Label("bitCoins: "+capital+"/"+coins);
        labelCoin.setTranslateY(-7);
        labelCoin.setFont(Font.font("mv boli",24));
        labelCoin.setTextFill(Color.YELLOW);
        this.root = new Pane();
        root.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        scene = new Scene(root, 900, 576);
        map = new MapData(root, objects, ghosts, this);
        map.drawMap();
        pacman = new Pacman(root, scene);
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if(!gameEnd)update();
            }
        };

        root.getChildren().addAll(labelCoin);
        pacman.imageView.setTranslateX(33);
        pacman.imageView.setTranslateY(65);
        stage.setScene(scene);
        if(flReplay){
            SepiaTone sep = new SepiaTone();
            root.setEffect(sep);
            replay.inputFile(path);
        }
        timer.start();
    }
    public void end(){
        gameEnd = true;
        Pane root = new Pane();
        scene = new Scene(root, 900, 576);
        root.setBackground(new Background(new BackgroundFill(Color.BLUEVIOLET, CornerRadii.EMPTY, Insets.EMPTY)));
        if(!flReplay){
            replay.outputFile();
            flReplay = true;
        }
        if(!lose) {
            root.getChildren().add(imageViewWin);
            imageViewWin.setTranslateX(194);
            imageViewWin.setTranslateY(100);
            stage.setScene(scene);

        }else{
            root.getChildren().add(imageViewLose);
            imageViewLose.setTranslateX(194);
            imageViewLose.setTranslateY(100);
            stage.setScene(scene);
        }
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                stage.setScene(menu.scene);
                replay = null;
                timer.stop();
                pacman.animation.stop();
                Thread.interrupted();
            }
        });
    }
    public void check(int direction){
        Block block = null, removeBlock = null;
        int removeIndex = 0;
        for(int indexObject = 0; indexObject < objects.size();indexObject++){
            block = objects.get(indexObject);
            if(pacman.imageView.getBoundsInParent().intersects(block.imageView.getBoundsInParent())){
                if(block.blockType == '1' ||block.blockType == 'd'){
                    switch(direction){
                        case 1: flY=false; pacman.moveY(2);  break;
                        case 2: flX=false; pacman.moveX(-2); break;
                        case 3: flY=false; pacman.moveY(-2); break;
                        case 4: flX=false; pacman.moveX(2);  break;
                    }
                }
                if(block.blockType == '2'|| block.blockType == 'z'){
                    removeBlock = block;
                    removeIndex = indexObject;
                }
            }
        }

        if(pacman.imageView.getBoundsInParent().intersects(map.imageView.getBoundsInParent())||lose){
            end();
        }

        if(flReplay&&replay.count == replay.screens.size()) {
            timer.stop();
            stage.setScene(menu.scene);
            menu.game = null;
        }

        if(removeBlock!=null) {
            char symbol = removeBlock.blockType;
            switch(symbol){
                case '2': capital++; break;
                case 'z': ghostMode = true; break;
            }
            if(coins==capital)
                for (int indexObject = 0; indexObject < objects.size(); indexObject++) {
                    if(objects.get(indexObject).blockType == 'd') {
                        root.getChildren().remove(objects.get(indexObject).imageView);
                        objects.remove(indexObject);
                    }
                }
            labelCoin.setText("bitCoins: "+capital+"/"+coins);
            root.getChildren().remove(removeBlock.imageView);
            objects.remove(removeIndex);
        }
    }
    public void replayUpdate() {
        int dirPacman;
        pacman.imageView.setTranslateX(replay.screens.get(replay.count).x);
        pacman.imageView.setTranslateY(replay.screens.get(replay.count).y);
        dirPacman = replay.screens.get(replay.count).direction;
        replay.count++;
        Ghost ghost;
        for (int indexObject = 0; indexObject < ghosts.size(); indexObject++) {
            ghost = ghosts.get(indexObject);
            ghost.setX(replay.screens.get(replay.count).x);
            ghost.setY(replay.screens.get(replay.count).y);
            replay.count++;
        }
        pacman.animation.reflectImage(dirPacman);
        check(dirPacman);
    }
    public void update() {
        if(!flReplay) {
            replay.addReplayData(pacman.getX(), pacman.getY(), pacman.animation.direction);
            Ghost ghost;
            for (int indexObject = 0; indexObject < ghosts.size(); indexObject++) {
                ghost = ghosts.get(indexObject);
                replay.addReplayData(ghost.getX(), ghost.getY());
            }
            pacman.control();
            pacman.movePacman(pacman.animation.direction);
            check(pacman.animation.direction);
            flX = true;
            flY = true;
            if (pacman.followingDirection != pacman.animation.direction) {
                pacman.movePacman(pacman.followingDirection);
                check(pacman.followingDirection);
            }
            if (flX && (pacman.followingDirection == 2 || pacman.followingDirection == 4)) {
                pacman.animation.reflectImage(pacman.followingDirection);
                pacman.followingDirection = 0;
            }
            if (flY && (pacman.followingDirection == 1 || pacman.followingDirection == 3)) {
                pacman.animation.reflectImage(pacman.followingDirection);
                pacman.followingDirection = 0;
            }
        }else{
            replayUpdate();
        }
    }
}
