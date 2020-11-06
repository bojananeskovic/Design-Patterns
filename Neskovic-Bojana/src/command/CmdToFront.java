package command;

import model.DrawingModel;
import shapes.Shape;

public class CmdToFront implements Command{

	private DrawingModel model;
	private Shape shape;
	private int index;

	public CmdToFront(DrawingModel model, Shape shape) {
		this.model = model;
		this.shape = shape;
	}

	//Vraca index oblika, uklanja ga sa te pozicije i dodaje na poziciju vise
	@Override
	public void execute() {
		index =  model.getIndexOf(shape);
		model.removeAtIndex(index);
		model.addToIndex(index + 1, shape);
	}

	//Vraca index oblika, uklanja ga sa prethodne pozicije i vraca na staru
	@Override
	public void unexecute() {
		model.removeAtIndex(index + 1);
		model.addToIndex(index, shape);
	}

	@Override
	public String toLogText() {
		return "Moved to front->" + shape.toString();
	}

}