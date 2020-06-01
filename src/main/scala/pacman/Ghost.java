package pacman;

import javafx.animation.AnimationTimer;
import javafx.concurrent.Task;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class Ghost extends NPC{
    AnimationTimer timer;
    int counter;
    enum Direction{UP,RIGHT, DOWN, LEFT, NULL};
    Direction directionX = Direction.NULL, directionY = Direction.DOWN;
    public ArrayList<Block>objects;
    public Game game;
    public int coordX;
    public int coordY;
    public int speed = 2;
    public char type;
    public boolean mode = false;
    Thread th;
    Ghost(Pane pane, ArrayList<Block> objects, Game game, int x, int y, char type){
        super(pane,"./img/tileset.png");
        coordX = x;
        coordY = y;
        this.type = type;
        this.game = game;
        this.objects = objects;
        initGhost();
        if(!game.flReplay) {
            th = new Thread(task);
            th.setDaemon(false);
            th.start();
        }
    }

    public void initGhost(){
        if(type == 'g'){
            OFFSET_X = 412;
            OFFSET_Y = 586;
            WIDTH = 26;
            HEIGHT = 29;
        }
        else{
        OFFSET_X = 679;
        OFFSET_Y = 631;
        WIDTH = 26;
        HEIGHT = 29;}
        initNPC();
        imageView.setTranslateX(coordX*32); imageView.setTranslateY(coordY*32);
        pane.getChildren().add(imageView);
        if(type == 'g')
            timer = new AnimationTimer() {
                @Override
                public void handle(long now) {
                    logicsA();
                }
            };
        else
            timer = new AnimationTimer() {
                @Override
                public void handle(long now) {
                    logicsB();
                }
            };
    }

    public void logicsA(){
        if(game.ghostMode&&!mode){
            mode= true;
            speed *= -1;
            counter = 0;
        }
        if(counter==300 && mode == true){
            game.ghostMode = false;
            mode = false;
            speed*=-1;
        }
        counter++;
        double pacX = game.pacman.imageView.getTranslateX();
        double pacY = game.pacman.imageView.getTranslateY();
        if(pacX> imageView.getTranslateX())
            directionX = Direction.RIGHT;
        else if(pacX< imageView.getTranslateX())
            directionX = Direction.LEFT;
        else if(pacX== imageView.getTranslateX())
            directionX = Direction.NULL;

        if(pacY< imageView.getTranslateY())
            directionY = Direction.UP;
        else if(pacY> imageView.getTranslateY())
            directionY = Direction.DOWN;
        else if(pacY== imageView.getTranslateY())
            directionY = Direction.NULL;

        moveGhost(directionX);
        check(directionX);
        moveGhost(directionY);
        check(directionY);
        if(game.pacman.imageView.getBoundsInParent().intersects(imageView.getBoundsInParent())){
            game.lose = true;
        }
    }
    public void logicsB(){
        counter++;
        if(counter>50) {
            counter = 0;
            int dirX = (int) ( Math.random() * 2 );
            int dirY = (int) ( Math.random() * 2 );
            if(dirX == 0) directionX = Direction.RIGHT;
            else directionX = Direction.LEFT;
            if(dirY == 0) directionY = Direction.UP;
            else directionY = Direction.DOWN;
        }
        moveGhost(directionX);
        check(directionX);
        moveGhost(directionY);
        check(directionY);
        if(game.pacman.imageView.getBoundsInParent().intersects(imageView.getBoundsInParent())){
            game.lose = true;
        }
    }

    public void moveGhost(Direction direction){
        switch(direction){
            case DOWN:  moveY(speed);  break;
            case LEFT:  moveX(-speed); break;
            case UP:    moveY(-speed); break;
            case RIGHT: moveX(speed); break;
        }
    }
    public void check(Direction direction){
        Block block = null;
        for(int indexObject = 0; indexObject < objects.size();indexObject++){
            block = objects.get(indexObject);
            if(imageView.getBoundsInParent().intersects(block.imageView.getBoundsInParent())){
                if(block.blockType == '1' || block.blockType == 'd'){
                    switch(direction){
                        case DOWN:  moveY(-speed);  break;
                        case LEFT:  moveX(speed); break;
                        case UP:    moveY(speed); break;
                        case RIGHT: moveX(-speed);  break;
                    }

                }
            }
        }
    }

    Task<Void> task = new Task<Void>() {
        @Override protected Void call() throws Exception {
            timer.start();
            return null;
        }
    };
}
