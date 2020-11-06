package command;

import model.DrawingModel;
import shapes.Shape;

public class CmdAddShape implements Command{
	
	private Shape shape;
	private DrawingModel model;
	
	public CmdAddShape(Shape shape, DrawingModel model) {
		this.shape = shape;
		this.model = model;
	}

	@Override
	public void execute() {
		model.add(shape);
		
	}

	@Override
	public void unexecute() {
		model.remove(shape);
		
	}

	@Override
	public String toLogText() {
		return "Added->" + shape.toString();
	}

}