package pacman;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class MapData {
    String map[];
    Image image;
    Pane pane;
    Block block;
    ImageView imageView;
    ArrayList<Block> objects;
    ArrayList<Ghost> ghosts;
    Game game;
    public MapData(Pane pane, ArrayList<Block> objects, ArrayList<Ghost> ghosts, Game game){
        this.game = game;
        this.ghosts = ghosts;
        image = new Image(getClass().getResourceAsStream("./img/map.png"));
        this.pane = pane;
        this.objects = objects;
        map = new String[]{
                "1111111111111111111111111111",
                "1000000000000000000000000001",
                "10111110111111011111011111G1",
                "1010z000000000000000000z0101",
                "1010111111111101111111110101",
                "10100G0000000000000000000101",
                "1010111011101111011101110101",
                "1010111011101  1011101110101",
                "100000000000t  t000000000001",
                "1011101111101111011111011101",
                "1010000000000000000000000101",
                "1010111111111111111111110101",
                "1010100000000000000000010101",
                "1g101011111101110111110101g1",
                "100000000000011100G000000001",
                "11111111111100z0011111111101",
                "1111111111111111111111111111"
        };
    }

    public void drawMap(){
        char symbol = 0;
        for(int yBlock = 0; yBlock < map.length; yBlock++)
            for(int xBlock = 0; xBlock < map[yBlock].length(); xBlock++){
                symbol = map[yBlock].charAt(xBlock);
                if(symbol != 'G' && symbol != 'g') {
                    block = new Block(symbol, xBlock, yBlock + 1, pane);
                    objects.add(block);
                }
                if(symbol=='0'){
                    game.coins++;
                    block = new Block('2', xBlock, yBlock+1, pane);
                    objects.add(block);
                }
                if(symbol=='t'){
                    block = new Block('d', xBlock, yBlock+1, pane);
                    objects.add(block);
                }
                if(symbol=='g' || symbol == 'G'){
                    game.coins++;
                    block = new Block('0', xBlock, yBlock+1, pane);
                    objects.add(block);
                    block = new Block('2', xBlock, yBlock+1, pane);
                    objects.add(block);
                }
            }
        for(int yBlock = 0; yBlock < map.length; yBlock++)
            for(int xBlock = 0; xBlock < map[yBlock].length(); xBlock++){
                symbol = map[yBlock].charAt(xBlock);
                if(symbol=='g'||symbol=='G'){
                    ghosts.add(new Ghost(pane,objects, game, xBlock, yBlock+1,symbol));
                }
            }
        image = new Image(getClass().getResourceAsStream("./img/tileset.png"));
            imageView = new ImageView(image);
            imageView.setViewport(new Rectangle2D(127,47,49,49));
            imageView.setTranslateX(13*32+8);
            imageView.setTranslateY(8*32+8);
            pane.getChildren().add(imageView);
    }
}
