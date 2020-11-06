package shapes;

import java.awt.Color;
import java.awt.Graphics;

// Apstraktna klasa za povrsinske oblike
public abstract class SurfaceShape extends Shape{
	
	private static final long serialVersionUID = 1L;
	private Color interiorColor = Color.WHITE;

   
    public abstract void fillUpShape(Graphics shapeForFillUp);

    public Color getInteriorColor() {
        return interiorColor;
    }

    public void setInteriorColor(Color interiorColor) {
        this.interiorColor = interiorColor;
    }
}
