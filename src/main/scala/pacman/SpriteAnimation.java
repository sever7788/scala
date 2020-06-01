package pacman;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class SpriteAnimation extends Transition {
    private final ImageView imageView;
    private final int count;
    private final int columns;
    private int offsetX;
    private int offsetY;
    private int width;
    private int height;
    public int direction;
    public SpriteAnimation(ImageView imageView,
                           Duration duration,
                           int count, int columns,
                           int offsetX, int offsetY,
                           int width, int height){
        this.imageView = imageView;
        this.count = count;
        this.columns = columns;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.width = width;
        this.height = height;
        this.direction = 2;
        setCycleDuration(duration);
        setCycleCount(Animation.INDEFINITE);
        setInterpolator(Interpolator.LINEAR);
    }
    public void reflectImage(int newDiection){

        Rotate rotate = new Rotate();
        rotate.setPivotX((width-2)/2);
        rotate.setPivotY((height-2)/2);
        switch(direction - newDiection){
            case -1: rotate.setAngle(90); imageView.getTransforms().addAll(rotate); break;
            case -2: rotate.setAngle(180); imageView.getTransforms().addAll(rotate); break;
            case -3: rotate.setAngle(-90); imageView.getTransforms().addAll(rotate); break;
            case 1: rotate.setAngle(-90); imageView.getTransforms().addAll(rotate); break;
            case 2: rotate.setAngle(180); imageView.getTransforms().addAll(rotate); break;
            case 3: rotate.setAngle(90); imageView.getTransforms().addAll(rotate); break;
        }
        direction = newDiection;
    }

    @Override
    protected void interpolate(double v) {
        final int index = Math.min((int) Math.floor(v*count), count - 1);
        final int x = (index % columns) * width + offsetX;
        final int y = (index /columns) * height + offsetY;
        imageView.setViewport(new Rectangle2D(x, y, width, height));
    }
}
