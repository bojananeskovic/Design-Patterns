package command;

import model.DrawingModel;
import shapes.Shape;

public class CmdBringToFront implements Command{

	private DrawingModel model;
	private Shape shape;
	private int size;
	private int index;
	
	public CmdBringToFront(DrawingModel model, Shape shape, int size) {
		this.model = model;
		this.shape = shape;
		this.size = size;
	}

	
	//Vraca index oblika, uklanja ga sa te pozicije i dodaje na poslednje mesto
	@Override
	public void execute() {
		index =  model.getIndexOf(shape);
		model.removeAtIndex(index);
		model.addToIndex(size, shape);
	}

	//Uklanja oblik sa poslednjeg mesta i vraca ga na prethodnu poziciju
	@Override
	public void unexecute() {
		model.removeAtIndex(size);
		model.addToIndex(index, shape);
	}


	@Override
	public String toLogText() {
		return "Bringed to front->"+shape.toString();
	}

}