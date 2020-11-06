package shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

public class Donut extends Circle{

	private static final long serialVersionUID = 1L;
	private int innerRadius;
	
	public Donut() {}
	
	public Donut(Point center, int r, int innerRadius, Color edgeColor, Color interiorColor) {
		super(center, r);
		this.innerRadius = innerRadius;	
		setColor(edgeColor);
        setInteriorColor(interiorColor);
	}
	
	//https://www.codota.com/code/java/classes/java.awt.geom.Ellipse2D
	//https://www.codota.com/code/java/classes/java.awt.geom.Graphics2D
	//https://www.codota.com/code/java/classes/java.awt.geom.Area
	public void draw(Graphics g) {

		Graphics2D g2D=(Graphics2D)g;
		Shape outer=new Ellipse2D.Double(center.getXcoordinate() - radius, center.getYcoordinate() - radius, 2 * radius, 2 * radius);
		Shape inner=new Ellipse2D.Double(this.getCenter().getXcoordinate() - this.getInnerRadius(), this.getCenter().getYcoordinate() - this.getInnerRadius(), this.getInnerRadius()*2, this.getInnerRadius()*2);
		Area circle=new Area(outer);
		circle.subtract(new Area(inner));
		g2D.setColor(getInteriorColor());
		g2D.fill(circle);
		g2D.setColor(getColor());
		g2D.draw(circle);
		
		
		if (isSelected()) selected(g);
	}
	
	public boolean equals(Object obj) {
		if (obj instanceof Donut) {
			Donut d = (Donut) obj;
			if (this.getCenter().equals(d.getCenter()) &&
					this.getRadius() == d.getRadius()
					&& this.innerRadius == d.getInnerRadius()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	//https://stackoverflow.com/questions/12828359/converting-a-color-object-to-a-string
	public String toString() {
		return "Donut: radius=" + radius + "; x=" + center.getXcoordinate() + "; y=" + center.getYcoordinate() + "; edge color=" + getColor().toString().substring(14).replace('=', '-') + "; area color=" + getInteriorColor().toString().substring(14).replace('=', '-') + "; inner radius=" + innerRadius;
	}
	
	public void selected(Graphics g) {	
		super.selected(g);
		g.drawRect(getCenter().getXcoordinate() - 3, getCenter().getYcoordinate() - 3, 6, 6);
		g.drawRect(getCenter().getXcoordinate() + getInnerRadius() - 3, getCenter().getYcoordinate() - 3, 6, 6);
		g.drawRect(getCenter().getXcoordinate() - getInnerRadius() - 3, getCenter().getYcoordinate() - 3, 6, 6);
		g.drawRect(getCenter().getXcoordinate()- 3, getCenter().getYcoordinate() + getInnerRadius() - 3, 6, 6);
		g.drawRect(getCenter().getXcoordinate() - 3, getCenter().getYcoordinate() - getInnerRadius() - 3, 6, 6);
		g.setColor(Color.BLACK);
	}
	
	public boolean containsClick(Point p) {
		double dFromCenter = this.getCenter().distance(p);
		return super.containsClick(p.getXcoordinate(), p.getYcoordinate()) && dFromCenter > innerRadius;
	}
	
	public Donut clone() {
    	return new Donut(center.clone(), radius, innerRadius, getColor(), getInteriorColor());
    }
	

	public int getInnerRadius() {
		return innerRadius;
	}

	public void setInnerRadius(int innerRadius) {
		this.innerRadius = innerRadius;
	}

}