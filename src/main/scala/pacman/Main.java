package pacman;
import javafx.application.Application;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import scala.*;

import java.io.File;
import java.lang.Long;
import java.util.*;

public class Main extends Application {
    public Game game;
    public Map<Long, String> map = new HashMap<Long, String>();
    public long[] sizes = new long[100];
    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Pacman");
        Menu menu = new Menu(primaryStage, game);
        menu.initMenu();
        primaryStage.show();
    }


}
