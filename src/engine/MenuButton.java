package engine;

import game.Game;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Menu button represented by a rectangle with or without text. The rectangle is transparent and can be filled with a
 * color when mouse is held over it.
 */
public class MenuButton {
    private final Vector2 position;
    private final int width, height;
    private Color border, mouseOver;
    private String text;
    private Font font;

    // Constructor initializing all variables
    public MenuButton(double x, double y, int width, int height, Color border, Color mouseOver,
                      String text, Font font) {
        position = new Vector2(x, y);
        this.width = width;
        this.height = height;
        this.border = border;
        this.mouseOver = mouseOver;
        this.text = text;
        this.font = font;
    }

    // Constructor without Color mouseOver
    public MenuButton(double x, double y, int width, int height, Color border, String text, Font font) {
        this(x, y, width, height, border, null, text, font);
    }

    // Constructor without Color mouseOver, text and font
    public MenuButton(double x, double y, int width, int height, Color border) {
        this(x, y, width, height, border, null, null, null);
    }

    /**
     * Check if the button is clicked.
     *
     * @param e MouseEvent
     * @return boolean
     */
    public boolean isClicked(MouseEvent e) {
        return e.getX() > position.x
                && e.getX() < position.x + width
                && e.getY() > position.y
                && e.getY() < position.y + height;
    }

    /**
     * Draw the button.
     */
    public void draw() {
        if (isMouseOver() && mouseOver != null) {
            Game.renderer.getGraphics2D().setColor(mouseOver);
            Game.renderer.getGraphics2D().fillRect((int) position.x, (int) position.y, width, height);
        }

        Game.renderer.getGraphics2D().setColor(border);
        Game.renderer.getGraphics2D().drawRect((int) position.x, (int) position.y, width, height);

        if (text != null) {
            int textWidth = Game.renderer.getGraphics2D().getFontMetrics(font).stringWidth(text);
            int textHeight = Game.renderer.getGraphics2D().getFontMetrics(font).getMaxAscent();
            int xPosition = (int) position.x + (width / 2) - (textWidth / 2);
            int yPosition = (int) position.y + (height / 2) + (textHeight / 2);
            Game.renderer.getGraphics2D().setFont(font);
            Game.renderer.getGraphics2D().drawString(text, xPosition, yPosition);
        }
    }

    /**
     * Check if mouse is held over the button.
     *
     * @return boolean
     */
    private boolean isMouseOver() {
        return Game.inputManager.getMousePosition().x > position.x
                && Game.inputManager.getMousePosition().x < position.x + width
                && Game.inputManager.getMousePosition().y > position.y
                && Game.inputManager.getMousePosition().y < position.y + height;
    }

    // Setter methods

    public void setMouseOver(Color mouseOver) {
        this.mouseOver = mouseOver;
    }

    public void setBorder(Color border) {
        this.border = border;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setFont(Font font) {
        this.font = font;
    }
}
