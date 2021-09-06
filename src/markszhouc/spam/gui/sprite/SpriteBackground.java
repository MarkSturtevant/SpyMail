package markszhouc.spam.gui.sprite;

import javafx.scene.paint.Color;
import markszhouc.spam.gui.CanvasUtils;

import java.awt.*;
import java.util.Random;

public class SpriteBackground extends Sprite {

    private static final int NUM_COLS = 36;
    private static final int NUM_ROWS = 24;
    private static final int TRANSFORM_TIME = 500, PERIOD = 1500;
    public static final Color[] BASE_COLORS = new Color[] {
            Color.rgb(20, 20, 20), Color.rgb(100, 100, 100), // 22 73
            Color.rgb(235, 235, 235), Color.WHITE

    };
    private static final double[] X_MAP = new double[] {
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0.2, 0.4, 0.6, 0.8, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1
    };

    private double[][] b;
    private Color[][] cMin, cMax;
    private int transformTimeI, functionTime;
    private boolean swappingUp;

    public SpriteBackground() {
        b = new double[NUM_COLS][NUM_ROWS];
        cMin = new Color[NUM_COLS][NUM_ROWS];
        cMax = new Color[NUM_COLS][NUM_ROWS];
        transformTimeI = functionTime = 0;
        swappingUp = false;

        // set b values
        Random rand = new Random();
        for (int i = 0; i < NUM_COLS; ++i)
            for (int j = 0; j < NUM_ROWS; ++j)
                while (true) {
                    double doub = rand.nextDouble() * 2 * Math.PI;
                    if (i > 0 && (Math.abs(doub - b[i-1][j]) < 0.2 || Math.abs(doub - b[i-1][j]) > 2 * Math.PI - 0.2))
                        continue;
                    if (j > 0 && (Math.abs(doub - b[i][j-1]) < 0.2 || Math.abs(doub - b[i][j-1]) > 2 * Math.PI - 0.2))
                        continue;
                    b[i][j] = doub;
                    break;
                }

        // set color values
        for (int i = 0; i < NUM_COLS; ++i) {
            Color c1 = Color.color(
                    BASE_COLORS[2].getRed() + (BASE_COLORS[0].getRed() - BASE_COLORS[2].getRed()) * X_MAP[i],
                    BASE_COLORS[2].getGreen() + (BASE_COLORS[0].getGreen() - BASE_COLORS[2].getGreen()) * X_MAP[i],
                    BASE_COLORS[2].getBlue() + (BASE_COLORS[0].getBlue() - BASE_COLORS[2].getBlue()) * X_MAP[i]
            );
            Color c2 = Color.color(
                    BASE_COLORS[3].getRed() + (BASE_COLORS[1].getRed() - BASE_COLORS[3].getRed()) * X_MAP[i],
                    BASE_COLORS[3].getGreen() + (BASE_COLORS[1].getGreen() - BASE_COLORS[3].getGreen()) * X_MAP[i],
                    BASE_COLORS[3].getBlue() + (BASE_COLORS[1].getBlue() - BASE_COLORS[3].getBlue()) * X_MAP[i]
            );
            for (int j = 0; j < NUM_ROWS; ++j) {
                cMin[i][j] = c1;
                cMax[i][j] = c2;
            }
        }

    }

    @Override
    public void render(double mouseX, double mouseY, int frameSpeed) {
        final int SQ_WIDTH = (int) CanvasUtils.getReferenceWidth() / NUM_COLS;
        final int SQ_HEIGHT = (int) (SQ_WIDTH / (CanvasUtils.getReferenceWidth() / CanvasUtils.getReferenceHeight()) *
                (CanvasUtils.getWindowX(CanvasUtils.getReferenceWidth()) /
                CanvasUtils.getWindowY(CanvasUtils.getReferenceHeight())));
        // draw screen
        functionTime += frameSpeed;
        for (int i = 0; i < NUM_COLS; ++i)
            for (int j = 0; j < CanvasUtils.getReferenceHeight() / SQ_HEIGHT; ++j) {
                CanvasUtils.drawRect2(i * SQ_WIDTH, j * SQ_HEIGHT, SQ_WIDTH, SQ_HEIGHT, cMax[i][j%NUM_ROWS]);
                double colorShift = 0.5 * Math.sin(Math.PI * 2.0 * (functionTime % PERIOD) / PERIOD + b[i][j]) - 0.5;
                CanvasUtils.drawRect2(i * SQ_WIDTH, j * SQ_HEIGHT, SQ_WIDTH, SQ_HEIGHT,
                        Color.color(
                                Math.max(0, cMin[i][j%NUM_ROWS].getRed() + (cMax[i][j%NUM_ROWS].getRed() - cMin[i][j%NUM_ROWS].getRed()) * colorShift),
                                Math.max(0, cMin[i][j%NUM_ROWS].getGreen() + (cMax[i][j%NUM_ROWS].getGreen() - cMin[i][j%NUM_ROWS].getGreen()) * colorShift),
                                Math.max(0, cMin[i][j%NUM_ROWS].getBlue() + (cMax[i][j%NUM_ROWS].getBlue() - cMin[i][j%NUM_ROWS].getBlue()) * colorShift)
                        )
                );
            }
        // update colors
        if ((swappingUp && transformTimeI != TRANSFORM_TIME) || (! swappingUp && transformTimeI != 0)) {
            frameSpeed = Math.min(frameSpeed, swappingUp ? TRANSFORM_TIME - transformTimeI : transformTimeI);
            if (! swappingUp)
                frameSpeed *= -1;
            double[] shifts = new double[] {
                    BASE_COLORS[3].getRed() - BASE_COLORS[1].getRed(),
                    BASE_COLORS[3].getGreen() - BASE_COLORS[1].getGreen(),
                    BASE_COLORS[3].getBlue() - BASE_COLORS[1].getBlue(),
                    BASE_COLORS[2].getRed() - BASE_COLORS[0].getRed(),
                    BASE_COLORS[2].getGreen() - BASE_COLORS[0].getGreen(),
                    BASE_COLORS[2].getBlue() - BASE_COLORS[0].getBlue()
            };
            for (int i = 0; i < NUM_COLS; ++i) {
                double frac = X_MAP[NUM_COLS - i - 1] - X_MAP[i];
                for (int j = 0; j < NUM_ROWS; ++j) {
                    cMin[i][j] = Color.color(
                            cMin[i][j].getRed() + shifts[3] * frameSpeed * frac / TRANSFORM_TIME,
                            cMin[i][j].getGreen() + shifts[4] * frameSpeed * frac / TRANSFORM_TIME,
                            cMin[i][j].getBlue() + shifts[5] * frameSpeed * frac / TRANSFORM_TIME
                    );
                    cMax[i][j] = Color.color(
                            cMin[i][j].getRed() + shifts[0] * frameSpeed * frac / TRANSFORM_TIME,
                            cMin[i][j].getGreen() + shifts[1] * frameSpeed * frac / TRANSFORM_TIME,
                            cMin[i][j].getBlue() + shifts[2] * frameSpeed * frac / TRANSFORM_TIME
                    );
                }
            }

            transformTimeI += frameSpeed;
        }
    }

    @Override
    public void testClick(double mouseX, double mouseY) {
        if (mouseX > CanvasUtils.getReferenceWidth() / 2) // right half
            swappingUp = true;
        else // left half
            swappingUp = false;
    }
}
