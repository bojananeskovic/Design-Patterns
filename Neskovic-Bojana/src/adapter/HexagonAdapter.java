package adapter;

import java.awt.Color;
import java.awt.Graphics;

import hexagon.Hexagon;
import shapes.Point;
import shapes.Shape;
import shapes.SurfaceShape;

public class HexagonAdapter extends SurfaceShape{

	private static final long serialVersionUID = 1L;
	private Hexagon hexagon;
	
	public HexagonAdapter(Hexagon hexagon) {
		this.hexagon = hexagon;
	}
	
	public HexagonAdapter(Point startPoint, int radius) {
		
		hexagon = new Hexagon(startPoint.getXcoordinate(), startPoint.getYcoordinate(), radius);
	}

	@Override
	public void draw(Graphics graphics) {
		hexagon.paint(graphics);
		hexagon.setSelected(isSelected());
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof HexagonAdapter) {
			Hexagon hex = ((HexagonAdapter) obj).hexagon;
			return hexagon.getX() == hex.getX() && hexagon.getY() == hex.getY() && hexagon.getR() == hex.getR();
		}
		return false;
	}
	
	@Override
	public int compareTo(Shape hex) {
		if (hex instanceof HexagonAdapter) return hexagon.getR() - ((HexagonAdapter) hex).getR();
		return 0;
	}

	@Override
	public String toString() {
		return "Hexagon: radius=" + hexagon.getR() + "; x=" + hexagon.getX() + "; y=" + hexagon.getY() + "; edge color=" + getColor().toString().substring(14).replace('=', '-') + "; area color=" + getInteriorColor().toString().substring(14).replace('=', '-');
	}
	
	@Override
	public void moveTo(int x, int y) {
		hexagon.setX(x);
		hexagon.setY(y);
	}

	
	@Override
	public void selected(Graphics graphics) {}

	@Override
	public boolean containsClick(int xCoordinate, int yCoordinate) {
		return hexagon.doesContain(xCoordinate, yCoordinate);
	}
	
	public HexagonAdapter clone() {
		Hexagon h = new Hexagon(getXcoordinate(), getYcoordinate(), getR());
		h.setBorderColor(getColor());
		h.setAreaColor(getInteriorColor());
		return new HexagonAdapter(h);
	}
	
	@Override
	public void fillUpShape(Graphics shapeForFillUp) {}

	@Override
	public boolean isSelected() {
		return hexagon.isSelected();
	}

	@Override
	public void setSelected(boolean selected) {
		hexagon.setSelected(selected);
		super.setSelected(selected);
	}
	
	public Color getColor() {
		return hexagon.getBorderColor();
	}
	
	public void setColor(Color color) {
		hexagon.setBorderColor(color);
		super.setColor(color);
	}
	
	public Color getInteriorColor() {
		return hexagon.getAreaColor();
	}
	
	public void setInteriorColor(Color color) {
		hexagon.setAreaColor(color);
		super.setInteriorColor(color);
	}
	
	public int getR() {
		return hexagon.getR();
	}
	
	public void setR(int r) {
		hexagon.setR(r);
	}
	
	public int getXcoordinate() {
		return hexagon.getX();
	}
	
	public int getYcoordinate() {
		return hexagon.getY();
	}
	
}