package markszhouc.spam.gui;

import static markszhouc.spam.gui.GUIHandler.gc;
import static markszhouc.spam.gui.GUIHandler.stageH;
import static markszhouc.spam.gui.GUIHandler.stageW;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Rotate;

public class CanvasUtils {
	
	private static double referenceWidth;
	private static double referenceHeight;
	
	/**
	 * Sets the width and height of the canvas every operation
	 * will be referenced through.  When using CanvasUtils, input 
	 * coordinate points should be as if the canvas is the size
	 * of the reference values.  Each operation's height, width,
	 * etc. will be adjusted accordingly.
	 * @param refW the default width of the canvas
	 * @param refH the default height of the canvas
	 */
	public static void setReferences(double refW, double refH) {
		referenceWidth = refW;
		referenceHeight = refH;
	}
	
	/**
	 * Outputs the default width CanvasUtils uses as a reference.
	 * @return the referenced width of the CanvasUtils canvas.
	 */
	public static double getReferenceWidth() {
		return referenceWidth;
	}
	
	/**
	 * Outputs the default height CanvasUtils uses as a reference.
	 * @return the referenced height of the CanvasUtils canvas.
	 */
	public static double getReferenceHeight() {
		return referenceHeight;
	}
	
	/**
	 * Translates the Canvas [Reference] x-coordinate into window coordinates
	 * @param canvasX the x-coordinate on reference coordinates
	 * @return the x-coordinate on the window
	 */
	public static double getWindowX(double canvasX) {
		return canvasX * stageW / referenceWidth;
	}
	
	/**
	 * Translates the Canvas [Reference] y-coordinate into window coordinates
	 * @param canvasY the y-coordinate on reference coordinates
	 * @return the y-coordinate on the window
	 */
	public static double getWindowY(double canvasY) {
		return canvasY * stageH / referenceHeight;
	}
	
	/**
	 * Translates the Window x-coordinate into reference coordinates
	 * @param windowX the x-coordinate on the window
	 * @return the x-coordinate in reference [canvas] coordinates
	 */
	public static double getReferenceX(double windowX) {
		return windowX / stageW * referenceWidth;
	}
	
	/**
	 * Translates the Window y-coordinate into reference coordinates
	 * @param windowY the y-coordinate on the window
	 * @return the y-coordinate in reference [canvas] coordinates
	 */
	public static double getReferenceY(double windowY) {
		return windowY / stageH * referenceHeight;
	}
	
	/**
	 * Draws text onto the canvas at an inputed x and y value.  The font
	 * of the text will automatically be adjusted to fit the input height, also
	 * in decimal.  This method draws slightly differently than javafx does, as
	 * the input variables x and y define the top-left point of the canvas.
	 * @param x the x location on the canvas, leftmost of the text.
	 * @param y the y location on the canvas.  This value should define the
	 * top (highest point) of where the text will be drawn.
	 * @param height the height for the text to be
	 * @param text the input text to be drawn
	 * @param c the color the text fill will be set to
	 */
	public static void drawText(double x, double y, double height, String text, Color c) {
		double adjX = x * stageW / referenceWidth, adjH = height * stageH / referenceHeight, adjY = y * stageH / referenceHeight + adjH;
		gc.setFill(c);
		gc.setFont(Font.font("Consolas", 4.0 * adjH / 3.0 + 2.0));
		gc.fillText(text, adjX, adjY);
	}
	
	/**
	 * Draws text onto the canvas at an inputed x and y value.  The font
	 * of the text will automatically be adjusted to fit the input height, also
	 * in decimal.  This method draws slightly differently than javafx does, as
	 * the input variables x and y define the top-left point of the canvas.
	 * @param x the x location on the canvas, center of the text.
	 * @param y the y location on the canvas, center of the text.
	 * @param height the height for the text to be
	 * @param text the input text to be drawn
	 * @param c the color the text fill will be set to
	 */
	public static void drawTextAlign(double x, double y, double height, String text, Color c, TextAlignment ta) {
		double adjX = x * stageW / referenceWidth, adjH = height * stageH / referenceHeight, adjY = y * stageH / referenceHeight + adjH / 2;
		gc.setFill(c);
		gc.setFont(Font.font("Consolas", 4.0 * adjH / 4.0 - 6.0));
		gc.setTextAlign(ta);
		gc.fillText(text, adjX, adjY);
	}
	
	/**
	 * Draws a filled rectangle inside the box formed by the inputed points.
	 * @param x1 the x value of the leftmost side of the rectangle
	 * @param y1 the y value of the upmost side of the rectangle
	 * @param x2 the x value of the rightmost side of the rectangle
	 * @param y2 the y value of the bottommost side of the rectangle
	 * @param c the color the rectangle should be filled with
	 */
	public static void drawRect1(double x1, double y1, double x2, double y2, Color c) {
		if (x1 > x2) {
			double temp = x1;
			x1 = x2;
			x2 = temp;
		}
		if (y1 > y2) {
			double temp = y1;
			y1 = y2;
			y2 = temp;
		}
		gc.setFill(c);
		double adjX = x1 * stageW / referenceWidth, adjY = y1 * stageH / referenceHeight;
		gc.fillRect(adjX, adjY, x2 * stageW / referenceWidth - adjX, y2 * stageH / referenceHeight - adjY);
	}
	
	/**
	 * Draws a filled rectangle at a point with a specified width and height
	 * @param x the x value of the leftmost side of the rectangle
	 * @param y the y value of the upmost side of the rectangle
	 * @param w the width value of the width of the rectangle
	 * @param h the height value of the height of the rectangle
	 * @param c the color the rectangle should be filled with
	 */
	public static void drawRect2(double x, double y, double w, double h, Color c) {
		gc.setFill(c);
		gc.fillRect(x * stageW / referenceWidth, y * stageH / referenceHeight, w * stageW / referenceWidth, h * stageH / referenceHeight);
	}
	
	/**
	 * Draws an outlined rectangle at a point with a specified width and height
	 * @param x the x value of the leftmost side of the rectangle
	 * @param y the y value of the upmost side of the rectangle
	 * @param w the width value of the width of the rectangle
	 * @param h the height value of the height of the rectangle
	 * @param c the color the rectangle should be filled with
	 * @param strokeSize the width of the outline, or stroke, in pixels
	 */
	public static void drawRectOutline2(double x, double y, double w, double h, Color c, double strokeSize) {
		gc.setStroke(c);
		gc.setLineWidth(strokeSize);
		gc.strokeRect(x * stageW / referenceWidth, y * stageH / referenceHeight, w * stageW / referenceWidth, h * stageH / referenceHeight);
		gc.setStroke(null);
	}
	
	/**
	 * Connects two inputed coordinate points on the canvas by a 
	 * straight line of specified color and width
	 * @param x1 the x value of point 1
	 * @param y1 the y value of point 1
	 * @param x2 the x value of point 2
	 * @param y2 the y value of point 2
	 * @param width the width (thickness) of the line.
	 * @param c the color of the line
	 */
	public static void drawLine(double x1, double y1, double x2, double y2, double width, Color c) {
		gc.setStroke(c);
		gc.setLineWidth(width * stageH / referenceHeight);
		gc.strokeLine(x1 * stageW / referenceWidth, y1 * stageH / referenceHeight, x2 * stageW / referenceWidth, y2 * stageH / referenceHeight);
	}
	
	/**
	 * Clears the canvas.
	 */
	public static void clear() {
		gc.clearRect(0.0, 0.0, stageW, stageH);
	}
	
	/**
	 * Draws an image inside the bounding box formed by the inputed points.
	 * @param img the image to draw, from the Images enumeration.
	 * @param x1 the x value of the leftmost side of the image
	 * @param y1 the y value of the upmost side of the image
	 * @param x2 the x value of the rightmost side of the image
	 * @param y2 the y value of the bottommost side of the image
	 */
	public static void drawImage1(Image img, double x1, double y1, double x2, double y2) {
		double adjX = x1 * stageW / referenceWidth, adjY = y1 * stageH / referenceHeight;
		gc.drawImage(img, adjX, adjY, x2 * stageW / referenceWidth - adjX, y2 * stageH / referenceHeight - adjY);
	}
	
	/**
	 * Draws an image at a point with a specified width and height
	 * @param img the image to draw, from the Images enumeration.
	 * @param x the x value of the leftmost side of the image
	 * @param y the y value of the upmost side of the image
	 * @param w the width value of the width of the image
	 * @param h the height value of the height of the image
	 */
	public static void drawImage2(Image img, double x, double y, double w, double h) {
		gc.drawImage(img, x * stageW / referenceWidth, y * stageH / referenceHeight, w * stageW / referenceWidth, h * stageH / referenceHeight);
	}
	
	/**
	 * Draws an image centered on a point with an input width and height.
	 * @param img the image to draw, from the Images enumeration.
	 * @param centerX the x value of the center of the image
	 * @param centerY the y value of the center of the image
	 * @param w the width value of the width of the image
	 * @param h the height value of the height of the image
	 */
	public static void drawImage3(Image img, double centerX, double centerY, double w, double h) {
		double adjW = w * stageW / referenceWidth, adjH = h * stageH / referenceHeight;
		gc.drawImage(img, centerX * stageW / referenceWidth - adjW / 2, centerY * stageH / referenceHeight - adjH / 2, adjW, adjH);
	}
	
	/**
	 * Draws an image centered on a point with an input width and height.  This image will be rotated on counterclockwise about the center points at the input angle.
	 * @param img the image to draw, from the Images enumeration.
	 * @param centerX the x value of the center of the image
	 * @param centerY the y value of the center of the image
	 * @param w the width value of the width of the image
	 * @param h the height value of the height of the image
	 * @param angle the angle of rotation, in radians.
	 */
	public static void drawImageRotated(Image img, double centerX, double centerY, double w, double h, double angle) {
		double adjW = w * stageW / referenceWidth, adjH = h * stageH / referenceHeight;
		double adjX = centerX * stageW / referenceWidth, adjY = centerY * stageH / referenceHeight;
		gc.save();
		Rotate r = new Rotate(angle * -180 / Math.PI, adjX, adjY);
		gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
		gc.drawImage(img, adjX - adjW / 2, adjY - adjH / 2, adjW, adjH);
		gc.restore();
	}
	
	/**
	 * Draws a filled polygon formed by given coordinate points.
	 * @param c the color the polygon will be filled with
	 * @param points the xy points that make up the polygon.  It
	 * should be formatted with the first point x coord and then y
	 * coord, then the second point, etc.  The number of doubles inputed
	 * MUST be even.
	 */
	public static void drawPolygon(Color c, double... points) {
		gc.setFill(c);
		double[] xs = new double[points.length / 2], ys = new double[points.length / 2];
		for (int i = 0; i < points.length; i += 2) {
			xs[i / 2] = points[i] * stageW / referenceWidth;
			ys[i / 2] = points[i + 1] * stageH / referenceHeight;
		}
		gc.fillPolygon(xs, ys, xs.length);
	}
	
	/**	Draws an outlined oval of specified color and location.
	 * 
	 * @param c	the color of the outline
	 * @param centerX	the center x coordinate
	 * @param centerY	the center y coordinate
	 * @param width		the width [ diameter, NOT radius; full width!]
	 * @param height	the height [ diameter, NOT radius; full height!]
	 * @param strokeSize	the width of the outline, or stroke, in pixels
	 */
	public static void drawOvalOutline(Color c, double centerX, double centerY, double width, double height, double strokeSize) {
		double adjW = width * stageW / referenceWidth, adjH = height * stageH / referenceHeight;
		double adjX = centerX * stageW / referenceWidth, adjY = centerY * stageH / referenceHeight;
		gc.setStroke(c);
		gc.setLineWidth(strokeSize);
		gc.strokeOval(adjX - adjW / 2, adjY - adjH / 2, adjW, adjH);
		gc.setStroke(null);
	}
	
	/**	Draws an oval of specified color and location.
	 * 
	 * @param c	the color of the oval
	 * @param centerX	the center x coordinate
	 * @param centerY	the center y coordinate
	 * @param width		the width [ diameter, NOT radius; full width!]
	 * @param height	the height [ diameter, NOT radius; full height!]
	 */
	public static void drawOval(Color c, double centerX, double centerY, double width, double height) {
		double adjW = width * stageW / referenceWidth, adjH = height * stageH / referenceHeight;
		double adjX = centerX * stageW / referenceWidth, adjY = centerY * stageH / referenceHeight;
		gc.setFill(c);
		gc.fillOval(adjX - adjW / 2, adjY - adjH / 2, adjW, adjH);
	}
	
	/**	Draws an outlined circle of specified color and location.
	 * 
	 * @param c	the color of the outline
	 * @param centerX	the center x coordinate
	 * @param centerY	the center y coordinate
	 * @param radius	the radius of the circle
	 * @param strokeSize	the width of the outline, or stroke, in pixels
	 */
	public static void drawCircleOutline(Color c, double centerX, double centerY, double radius, double strokeSize) {
		drawOvalOutline(c, centerX, centerY, radius * 2, radius * 2, strokeSize);
	}
	
	/**	Draws an circle of specified color and location.
	 * 
	 * @param c	the color of the outline
	 * @param centerX	the center x coordinate
	 * @param centerY	the center y coordinate
	 * @param radius	the radius of the circle
	 */
	public static void drawCircle(Color c, double centerX, double centerY, double radius) {
		drawOval(c, centerX, centerY, radius * 2, radius * 2);
	}
	
	/** Draw text with an input font and height.  The height inputed will automatically adjust the font size to match
	 *  specifications, but the font name will remain the same.  The height containment algorithm may become buggy as
	 *  more fancy fonts are applied, so use with caution.
	 * 
	 * @param font the font for the text.  Font size will automatically be adjusted based on input height.
	 * @param c the color of the displayed text
	 * @param x the x-coordinate of the top-right "bounding box" of the text
	 * @param y the y-coordinate of the top-right "bounding box" of the text
	 * @param height how high the text should be.  Font size will be adjusted to fit the input height.
	 */
	public static void drawText2(Font font, String text, Color c, double x, double y, double height) {
		double adjX = x * stageW / referenceWidth, adjH = height * stageH / referenceHeight, adjY = y * stageH / referenceHeight + adjH * 3 / 4;
		gc.setFont(Font.font(font.getName(), adjH * 3.0 / 4));
		gc.setFill(c);
		gc.fillText(text, adjX, adjY);
	}
}
