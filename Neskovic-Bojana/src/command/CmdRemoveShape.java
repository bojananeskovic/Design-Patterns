package command;

import java.util.ArrayList;

import model.DrawingModel;
import shapes.Shape;

public class CmdRemoveShape implements Command{

	private ArrayList<Shape> shapes;
	private DrawingModel model;
	
	public CmdRemoveShape(ArrayList<Shape> shapes, DrawingModel model) {
		this.shapes = shapes;
		this.model = model;
	}
	
	public CmdRemoveShape(Shape shape, DrawingModel model) {
		this.shapes = new ArrayList<Shape>();
		this.shapes.add(shape);
		this.model = model;	
	}


	@Override
	public void execute() { 
		if (shapes != null) model.removeMultiple(shapes);
	}

	
	@Override
	public void unexecute() {
		if (shapes != null) model.addMultiple(shapes);
	}
	
	//Vraca broj oblika
	public int getSize() {
		return shapes.size();
	}

	@Override
	public String toLogText() {
		return "Deleted->"+shapes.toString();	 
	}

}