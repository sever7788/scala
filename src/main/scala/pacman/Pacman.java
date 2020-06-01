package pacman;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.input.KeyCode;
import java.util.HashMap;

public class Pacman extends NPC {
    private HashMap<KeyCode, Boolean> keys = new HashMap<>();
    public int followingDirection = 2;
    public Pacman(Pane pane, Scene scene){
        super(pane, "./img/tileset.png");
        initNPC();
        imageView.setFitWidth(30);
        imageView.setFitHeight(30);
        scene.setOnKeyPressed(event->keys.put(event.getCode(),true));
        scene.setOnKeyReleased(event-> { keys.put(event.getCode(), false);
        });
        pane.getChildren().add(imageView);
        animation.play();
    }

    public void movePacman(int direction){
        switch(direction) {
            case 1: moveY(-2); break;
            case 2: moveX(2);  break;
            case 3: moveY(2);  break;
            case 4: moveX(-2); break;
    }
}
    public void control() {
        if (isPressed(KeyCode.UP)) {
           followingDirection = 1;
        } else if (isPressed(KeyCode.DOWN)) {
            followingDirection = 3;
        } else if (isPressed(KeyCode.RIGHT)) {
            followingDirection = 2;
        } else if (isPressed(KeyCode.LEFT)) {
            followingDirection = 4;
        }
    }


    public boolean isPressed(KeyCode key) {
        return keys.getOrDefault(key, false);
    }


}
