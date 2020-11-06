package shapes;

import java.awt.Color;
import java.awt.Graphics;


public class Rectangle extends SurfaceShape {

	private static final long serialVersionUID = 1L;
	private Point upLeft;
	private int width;
	private int height;

    public Rectangle() {}

    public Rectangle(Point upLeft, int width, int height, Color edgeColor, Color interiorColor) {
        this.upLeft = upLeft;
        this.width = width;
        this.height = height;
        setColor(edgeColor);
        setInteriorColor(interiorColor);
    }
    
  
    @Override
    public void draw(Graphics g) {
        g.setColor(getColor());
        g.drawRect(upLeft.getXcoordinate(), upLeft.getYcoordinate(), height, width);
        fillUpShape(g);
        if (isSelected()) selected(g);
    }
    
 
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Rectangle) {
			Rectangle rectangle = (Rectangle) obj;
			return upLeft.equals(rectangle.upLeft) && width == rectangle.getWidth() && height == rectangle.getHeight();
		}
		return false;
	}
	
	
	@Override
	public int compareTo(Shape o) {
		if(o instanceof Rectangle){
			Rectangle r = (Rectangle) o;
			return (int) (this.area() - r.area());
		}
		else
			return 0;
	}

    @Override
    public String toString() {
    	return "Rectangle: x=" + upLeft.getXcoordinate() + "; y=" + upLeft.getYcoordinate() + "; height=" + height + "; width=" + width + "; edge color=" + getColor().toString().substring(14).replace('=', '-') + "; area color=" + getInteriorColor().toString().substring(14).replace('=', '-');
    }

	@Override
	public void moveTo(int x, int y) {
		upLeft.moveTo(x, y);
	}
 
    @Override
    public void selected(Graphics graphics) {
    	graphics.setColor(Color.BLUE);
        new Line(getUpLeft(), new Point(upLeft.getXcoordinate() + height, upLeft.getYcoordinate())).selected(graphics);
        new Line(getUpLeft(), new Point(upLeft.getXcoordinate(), upLeft.getYcoordinate() + width)).selected(graphics);
        new Line(new Point(getUpLeft().getXcoordinate() + height, upLeft.getYcoordinate()), diagonal().getLast()).selected(graphics);
        new Line(new Point(upLeft.getXcoordinate(), upLeft.getYcoordinate() + width), diagonal().getLast()).selected(graphics);
    }
    

 
    @Override
    public boolean containsClick(int xCoordinate, int yCoordinate) {
        if (upLeft.getXcoordinate() <= xCoordinate && xCoordinate <= (upLeft.getXcoordinate() + width) && upLeft.getYcoordinate() <= yCoordinate && yCoordinate <= upLeft.getYcoordinate() + height) return true;
        return false;
    }
    
    public Rectangle clone() {
    	return new Rectangle(upLeft.clone(), width, height, getColor(), getInteriorColor());
    }

    @Override
    public void fillUpShape(Graphics graphics) {
        graphics.setColor(getInteriorColor());
        graphics.fillRect(upLeft.getXcoordinate() + 1, upLeft.getYcoordinate() + 1, height - 1, width - 1);
    }
    
    public int area() {
		return width * height;
	}

    public Line diagonal() {
        return new Line(upLeft, new Point(upLeft.getXcoordinate() + height, upLeft.getYcoordinate() + width));
    }
    
    
    public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Point getUpLeft() {
        return upLeft;
    }

    public void setUpLeft(Point upLeft) {
        this.upLeft = upLeft;
    }
    
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }




}