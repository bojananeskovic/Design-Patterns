package command;

import model.DrawingModel;
import shapes.Shape;

public class CmdBringToBack implements Command {

	private DrawingModel model;
	private Shape shape;
	private int index;
	
	public CmdBringToBack(DrawingModel model, Shape shape) {
		this.model = model;
		this.shape = shape;
	}

	
	//Vraca index oblika, uklanja ga sa te pozicije i dodaje na prvo mesto
	@Override
	public void execute() {
		index =  model.getIndexOf(shape);
		model.removeAtIndex(index);
		model.addToIndex(0, shape);
	}

	
	//Uklanja oblik sa prvog mesta i vraca ga na prethodnu poziciju
	@Override
	public void unexecute() {
		model.removeAtIndex(0);
		model.addToIndex(index, shape);
	}


	@Override
	public String toLogText() {
		return "Bringed to back->"+shape.toString();
	}

}