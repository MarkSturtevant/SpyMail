package markszhouc.spam.gui;

import markszhouc.spam.encoding.EmailController;
import markszhouc.spam.gui.sprite.*;

import java.util.ArrayList;
import java.util.List;

public class GUIScreen {

    static List<Sprite> sprites;
    static SpriteButton[] buttons;

    public static void init() {
        sprites = new ArrayList<>();
        sprites.add(new SpriteBackground2());
        addButtons();
        sprites.add(new SpriteHoverEffect());
    }

    public static void render(double mouseX, double mouseY, int frameSpeed) {
        for (Sprite s : sprites)
            s.render(mouseX, mouseY, frameSpeed);
        // update email controller while in loop.
        EmailController.update(frameSpeed);
    }

    public static void onMouseClick(double mouseX, double mouseY) {
        for (Sprite s : sprites)
            s.testClick(mouseX, mouseY);
    }

    private static void addButtons() {
        buttons = new SpriteButton[16];
        sprites.add(buttons[0] = new SpriteButton(25, 75, true, "Set Address", "", SpriteButtonMethods::setAddress));
        sprites.add(buttons[1] = new SpriteButton(25, 115, true, "Set Sender", "", SpriteButtonMethods::setSender));
        sprites.add(buttons[2] = new SpriteButton(25, 155, true, "Set Sender Password", "", SpriteButtonMethods::setSenderPassword));
        sprites.add(buttons[3] = new SpriteButton(25, 205, true, "Set # of emails", "", SpriteButtonMethods::setNumToSend));
        sprites.add(buttons[4] = new SpriteButton(25, 245, true, "Add file to send", "0", SpriteButtonMethods::addFile));
        sprites.add(buttons[5] = new SpriteButton(25, 285, true, "Set image", "", SpriteButtonMethods::setLogo));
        sprites.add(buttons[6] = new SpriteButton(25, 325, true, "Set color reading data", "DEFAULT", SpriteButtonMethods::setColorReadingData));
        sprites.add(buttons[7] = new SpriteButton(25, 365, true, "Set time between emails", "60", SpriteButtonMethods::setTimeBetween));
        sprites.add(buttons[8] = new SpriteButton(25, 415, true, "Begin", "", SpriteButtonMethods::begin));
        sprites.add(buttons[9] = new SpriteButton(25, 455, true, "Pause", "", SpriteButtonMethods::pause));
        sprites.add(buttons[10] = new SpriteButton(25, 495, true, "Clear", "", SpriteButtonMethods::clear));
        sprites.add(buttons[11] = new SpriteButton(25, 535, true, "View Queue", "", SpriteButtonMethods::viewQueue));

        sprites.add(buttons[12] = new SpriteButton(875, 75, false, "Set File to Read", "", SpriteButtonMethods::setLogo));
        sprites.add(buttons[13] = new SpriteButton(875, 115, false, "Set Output Path", "", SpriteButtonMethods::setOutputPath));
        sprites.add(buttons[14] = new SpriteButton(875, 155, false, "Set color reading data", "DEFAULT", SpriteButtonMethods::setColorReadingData));
        sprites.add(buttons[15] = new SpriteButton(875, 205, false, "Begin", "", SpriteButtonMethods::decode));
    }

    public static SpriteButton[] getButtons() {
        return buttons;
    }

}
