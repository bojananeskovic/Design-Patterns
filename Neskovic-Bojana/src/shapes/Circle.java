package shapes;

import java.awt.Color;
import java.awt.Graphics;


public class Circle extends SurfaceShape {

	private static final long serialVersionUID = 1L;
	protected Point center;
    protected int radius;

    public Circle() {}
    
    public Circle(Point center, int radius){
		this.center = center;
		this.radius = radius;
	}

    public Circle(Point center, int r, Color edgeColor, Color interiorColor) {
    	this(center, r);
        setColor(edgeColor);
        setInteriorColor(interiorColor);
    }
    
 
    public void draw(Graphics g) {
        g.setColor(getColor());//crna boja
        g.drawOval(center.getXcoordinate() - radius, center.getYcoordinate() - radius, 2 * radius, 2 * radius);
        fillUpShape(g);
        if (isSelected()) selected(g);
    }
    

    @Override
	public boolean equals(Object obj) {
		if (obj instanceof Circle) {
			Circle circle = (Circle) obj;
			return center.equals(circle.getCenter()) && radius == circle.getRadius();
		}
		return false;
	}
    

    @Override
	public int compareTo(Shape shape) {
		if (shape instanceof Circle) return radius - ((Circle) shape).getRadius();
		return 0;
	}
    

    @Override
    public String toString() {
    	return "Circle: radius=" + radius + "; x=" + center.getXcoordinate() + "; y=" + center.getYcoordinate() + "; edge color=" + getColor().toString().substring(14).replace('=', '-') + "; area color=" + getInteriorColor().toString().substring(14).replace('=', '-');
    }
    

    public void moveTo(int xCoordinate, int yCoordinate) {
        center.moveTo(xCoordinate, yCoordinate);
    }


    public void selected(Graphics g) {
        new Line(new Point(center.getXcoordinate(), center.getYcoordinate() - radius), new Point(center.getXcoordinate(), center.getYcoordinate() + radius)).selected(g);
        new Line(new Point(center.getXcoordinate() - radius, center.getYcoordinate()), new Point(center.getXcoordinate() + radius, center.getYcoordinate())).selected(g);
    }
    

    public boolean containsClick(int xCoordinate, int yCoordinate) {
        if (new Point(xCoordinate, yCoordinate).distance(getCenter()) <= radius) return true;
        return false;
    }
    
    public Circle clone() {
    	return new Circle(center.clone(), radius, getColor(), getInteriorColor());
    }


    public void fillUpShape(Graphics g) {
        g.setColor(getInteriorColor());//bela boja
        g.fillOval(center.getXcoordinate() - radius + 1, center.getYcoordinate() - radius + 1, 2 * radius - 2, 2 * radius - 2);
    }

    public Point getCenter() {
        return center;
    }

    public void setCenter(Point center) {
        this.center = center;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int r) {
        radius = r;
    }

}