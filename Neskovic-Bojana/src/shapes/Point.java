package shapes;

import java.awt.Color;
import java.awt.Graphics;


public class Point extends Shape {

	private static final long serialVersionUID = 1L;
	private int xCoordinate;
    private int yCoordinate;

    public Point() {}

    public Point(int xCoordinate, int yCoordinate) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    public Point(int xCoordinate, int yCoordinate, Color color) {
        this(xCoordinate, yCoordinate);
        setColor(color);
    }


    public void draw(Graphics graphics) {
        graphics.setColor(getColor());
        graphics.drawLine(xCoordinate - 2, yCoordinate, xCoordinate + 2, yCoordinate);
        graphics.drawLine(xCoordinate, yCoordinate + 2, xCoordinate, yCoordinate - 2);
        if (isSelected()) selected(graphics);
    }
    

    @Override
	public boolean equals(Object obj) {
		if (obj instanceof Point) {
			Point point = (Point) obj;
			return xCoordinate == point.getXcoordinate() && yCoordinate == point.getYcoordinate();
		}
		return false;
	}
    

	@Override
	public int compareTo(Shape shape) {
		if (shape instanceof Point) {
			Point startCoordinates = new Point(0, 0);
			return (int) (distance(startCoordinates) - ((Point) shape).distance(startCoordinates));
		}
		return 0;
	}
    

    @Override
    public String toString() {
        return "Point: x=" + xCoordinate + "; y=" + yCoordinate + "; color=" + getColor().toString().substring(14).replace('=', '-');
    }
    

    public void moveTo(int xCoordinate, int yCoordinate) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }


    public void selected(Graphics graphics) {
        graphics.setColor(Color.BLUE);
        graphics.drawRect(xCoordinate - 3, yCoordinate - 3, 6, 6);
    }

 
    public boolean containsClick(int xCoordinate, int yCoordinate) {
        if (new Point(xCoordinate, yCoordinate).distance(this) <= 2) return true;
        return false;
    }
    
    public Point clone() {
    	return new Point(xCoordinate, yCoordinate, getColor());
    }


    public double distance(Point point) {
        return Math.sqrt(Math.pow(xCoordinate - point.xCoordinate, 2) + Math.pow(yCoordinate - point.yCoordinate, 2));
    }

    public int getXcoordinate() {
        return xCoordinate;
    }

    public void setXcoordinate(int xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public int getYcoordinate() {
        return yCoordinate;
    }

    public void setYcoordinate(int yCoordinate) {
        this.yCoordinate = yCoordinate;
    }
}