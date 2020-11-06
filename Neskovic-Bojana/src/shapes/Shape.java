package shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;


public abstract class Shape implements Moveable, Cloneable, Comparable<Shape>, Serializable {

	private static final long serialVersionUID = 1L;
	private boolean selected;
    private Color color = Color.BLACK;

    public Shape() {}

    public abstract void draw(Graphics graphics);


    public abstract void selected(Graphics graphics);

 
    public abstract boolean containsClick(int xCoordinate, int yCoordinate);

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    
    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}