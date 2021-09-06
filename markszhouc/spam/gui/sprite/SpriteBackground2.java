package markszhouc.spam.gui.sprite;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import markszhouc.spam.gui.CanvasUtils;

public class SpriteBackground2 extends Sprite {

    Image img = new Image(Sprite.class.getClassLoader().getResourceAsStream("resource/screen.png"));

    @Override
    public void render(double mouseX, double mouseY, int frameSpeed) {
        CanvasUtils.drawImage1(img, 0, 0, CanvasUtils.getReferenceWidth(), CanvasUtils.getReferenceHeight());
    }

    @Override
    public void testClick(double mouseX, double mouseY) {

    }
}
