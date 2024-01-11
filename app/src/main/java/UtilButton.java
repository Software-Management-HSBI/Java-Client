import com.raylib.Jaylib;

import static com.raylib.Raylib.*;

public class UtilButton extends Jaylib.Rectangle {
    private String text;
    private boolean select =false;







    /**
     * Creates a button with the given parameters
     *
     * @param x the x position of the button
     * @param y the y position of the button
     * @param width the width of the button
     * @param height the height of the button
     * @param text the text to be displayed on the button
     */
    public UtilButton(float x, float y, float width, float height, String text) {
        super(x, y, width, height);
        this.text = text;


    }




    /** Draws the button on the screen */
    public void draw() {
        GuiButton(this, text);

    }
    public void drawWithButton() {
        GuiButton(this, text);
    }

    /**
     * Checks if the button is clicked based on the current mouse position and left mouse button
     * state
     *
     * @return {@code true} if the mouse is over the button and the left mouse button is
     *     pressed, {@code false} otherwise
     */
    public boolean buttonClicked() {
        if (CheckCollisionPointRec(GetMousePosition(), this)) {
            return IsMouseButtonPressed(MOUSE_BUTTON_LEFT);
        }
        return false;
    }

    public void update(){
        if(buttonClicked()) {
            select =!select;
        }
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }
}