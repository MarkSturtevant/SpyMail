package markszhouc.spam.gui.sprite;

import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import markszhouc.spam.gui.CanvasUtils;

public class SpriteButton extends Sprite {

    private static final Color[] HOVER_COL = new Color[] {
        Color.rgb(0, 132, 4), Color.rgb(0, 173, 5)
    };
    private static final int PERIOD = 5000;
    private static final double CHAR_WIDTH = 11, HEIGHT = 30;

    private int timeTracker, x, topY;
    private double width;
    private boolean leftAlign;
    private Runnable onClick;
    private String label, suppl;

    public SpriteButton(int x, int topY, boolean leftSide, String label, String suppl, Runnable onClick) {
        timeTracker = 0;
        leftAlign = leftSide;
        this.x = x;
        this.topY = topY;
        this.label = label;
        this.suppl = suppl;
        this.width = (label.length() + 0.5) * CHAR_WIDTH * (leftSide ? 1 : -1);
        this.onClick = onClick;
    }

    @Override
    public void render(double mouseX, double mouseY, int frameSpeed) {
        double xFix = leftAlign ? x : x + width;
        double widthFix = leftAlign ? width : -width;
        if (mouseY > topY && mouseY < topY + HEIGHT && (mouseX - x) * (mouseX - (x + width)) < 0) {
            // highlight box on mouse overlap
            timeTracker += frameSpeed;
            double colorShift = 0.5 * Math.sin(Math.PI * 2.0 * (timeTracker % PERIOD) / PERIOD) - 0.5;
            CanvasUtils.drawRect2(xFix, topY, widthFix, HEIGHT,
                    Color.color(
                            Math.max(0, HOVER_COL[0].getRed() + (HOVER_COL[1].getRed() - HOVER_COL[0].getRed()) * colorShift),
                            Math.max(0, HOVER_COL[0].getGreen() + (HOVER_COL[1].getGreen() - HOVER_COL[0].getGreen()) * colorShift),
                            Math.max(0, HOVER_COL[0].getBlue() + (HOVER_COL[1].getBlue() - HOVER_COL[0].getBlue()) * colorShift)
                    )
            );
        }
        // draw border
        CanvasUtils.drawRectOutline2(xFix, topY, widthFix, HEIGHT, Color.BLACK, 3);
        // draw text
        TextAlignment ta = leftAlign ? TextAlignment.LEFT : TextAlignment.RIGHT;
        double offset = leftAlign ? CHAR_WIDTH/2.0 : -CHAR_WIDTH/2.0;
        CanvasUtils.drawTextAlign(x + offset, topY + HEIGHT / 3, HEIGHT - 5, label, Color.BLACK, ta);
        // draw supplement
        CanvasUtils.drawTextAlign(x + width + offset, topY + HEIGHT / 3, HEIGHT - 5, suppl, Color.RED, ta);
    }

    public void setSuppl(String newSuppl) {
        this.suppl = newSuppl;
    }

    @Override
    public void testClick(double mouseX, double mouseY) {
        if (mouseY > topY && mouseY < topY + HEIGHT && (mouseX - x) * (mouseX - (x + width)) < 0)
            this.onClick.run();
    }
}
