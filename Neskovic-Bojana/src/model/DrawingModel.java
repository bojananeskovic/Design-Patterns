package model;


import java.io.Serializable;
import java.util.ArrayList;
import shapes.Shape;

public class DrawingModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Shape> shapes;
	
	public DrawingModel() {
		shapes = new ArrayList<Shape>();
	}
	

	public void add(Shape shape) {
		shapes.add(shape);
	}
	
	//Dodavanje oblika na specificnu poziciju
	public void addToIndex(int index, Shape shape) {
		shapes.add(index, shape);
	}

	public void addMultiple(ArrayList<Shape> shapes) {
		this.shapes.addAll(shapes);
	}
	

	public void remove(Shape shape) {
		shapes.remove(shape);
	}

	public void removeAtIndex(int index) {
		shapes.remove(index);
	}
	

	public void removeMultiple(ArrayList<Shape> shapes) {
		this.shapes.removeAll(shapes);
	}


	public void removeAll() {
		shapes.clear();
	}
	
	public Shape getByIndex(int index) {
		return shapes.get(index);
	}
	
	public int getIndexOf(Shape shape) {
		return shapes.indexOf(shape);
	}
	
	public ArrayList<Shape> getAll() {
		return shapes;
	}
}