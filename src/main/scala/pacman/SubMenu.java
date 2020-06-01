package pacman;
import javafx.scene.layout.VBox;

public class SubMenu extends VBox {
    public SubMenu(MenuItem...items){
        setSpacing(15);

        for(MenuItem item : items){
            getChildren().addAll(item);
        }
    }
}