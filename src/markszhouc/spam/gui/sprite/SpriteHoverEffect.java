package markszhouc.spam.gui.sprite;

import javafx.scene.image.Image;
import markszhouc.spam.gui.CanvasUtils;

public class SpriteHoverEffect extends Sprite {

    Image img = new Image(Sprite.class.getClassLoader().getResourceAsStream("resource/hoverEffect.png"));

    @Override
    public void render(double mouseX, double mouseY, int frameSpeed) {
        CanvasUtils.drawImage2(img, mouseX - 900, mouseY - 600, 1800, 1200);
    }

    @Override
    public void testClick(double mouseX, double mouseY) {

    }
}
