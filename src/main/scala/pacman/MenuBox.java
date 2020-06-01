package pacman;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class MenuBox extends Pane {
    SubMenu subMenu;
    public MenuBox(SubMenu subMenu){
        this.subMenu = subMenu;

        setVisible(true);
        Rectangle bg = new Rectangle(900,600, Color.LIGHTBLUE);
        bg.setOpacity(0);
        getChildren().addAll(bg,subMenu);
    }
    public void setSubMenu(SubMenu subMenu){
        getChildren().remove(this.subMenu);
        this.subMenu = subMenu;
        getChildren().add(this.subMenu);
    }
}