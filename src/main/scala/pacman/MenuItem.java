package pacman;

import javafx.animation.Animation;
import javafx.animation.FillTransition;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class MenuItem extends StackPane {
    public Text text;
    public MenuItem(String name){
        Rectangle bg = new Rectangle(400, 60, Color.rgb(240,130,0));
        bg.setArcWidth(20);
        bg.setArcHeight(20);
        bg.setOpacity(1);
        text = new Text(name);
        text.setFill(Color.rgb(59,31,135));
        text.setFont(Font.font("Arial", FontWeight.BOLD,28));

        setAlignment(Pos.CENTER);
        getChildren().addAll(bg,text);
        FillTransition st = new FillTransition(Duration.seconds(0.5), bg);//анимация
        setOnMouseEntered(event->{
            st.setFromValue(Color.rgb(240,130,0));
            st.setToValue(Color.rgb(252,207,0));
            st.setCycleCount(Animation.INDEFINITE);
            st.setAutoReverse(true);
            st.play();
        });
        setOnMouseExited(event->{
            st.stop();
            bg.setFill(Color.rgb(240,130,0));
        });
    }
}
