package pacman;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;


public class Block {
    Image image = new Image(getClass().getResourceAsStream("./img/map.png"));
    ImageView imageView;
    int width = 32;
    int height = 32;
    char blockType;
    Pane pane;
    public Block(char blockType, int xBlock, int yBlock, Pane pane) {
        this.pane = pane;
            imageView = new ImageView(image);
            this.pane.getChildren().addAll(imageView);
            imageView.setTranslateX(xBlock*width); imageView.setTranslateY(yBlock*height);
            this.blockType = blockType;
        switch (blockType) {
            case '0':
                imageView.setViewport(new Rectangle2D(0,64,width,height));
                break;
            case '1':
                imageView.setViewport(new Rectangle2D(160,32,width,height));
                break;
            case '2':
                imageView.setViewport(new Rectangle2D(417,193, 29,29));
                imageView.setFitWidth(22);
                imageView.setFitHeight(22);
                imageView.setTranslateX(imageView.getTranslateX()+4);imageView.setTranslateY(imageView.getTranslateY()+4);
                break;
            case 't':
                imageView.setViewport(new Rectangle2D(32,32, width,height));
                break;
            case 'd':
                imageView.setViewport(new Rectangle2D(128,96, width,height));
                break;
            case 'z':
                image = new Image(getClass().getResourceAsStream("./img/tileset.png"));
                imageView = new ImageView(image);
                imageView.setTranslateX(xBlock*width); imageView.setTranslateY(yBlock*height);

                imageView.setViewport(new Rectangle2D(656,741, width,height));
                imageView.setFitHeight(30);
                imageView.setTranslateX(imageView.getTranslateX()+4);imageView.setTranslateY(imageView.getTranslateY()+4);
                this.pane.getChildren().addAll(imageView);
                break;
        }
    }

}
