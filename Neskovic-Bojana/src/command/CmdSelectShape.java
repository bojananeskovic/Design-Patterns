package command;

import shapes.Shape;

public class CmdSelectShape implements Command{

	private Shape shape;
	private boolean selectedState;
	
	public CmdSelectShape(Shape shape, boolean selectedState) {
		this.shape = shape;
		this.selectedState = selectedState;
	}
	

	@Override
	public void execute() {
		shape.setSelected(selectedState);
	}


	@Override
	public void unexecute() {	
		if (shape.isSelected()) shape.setSelected(false);
		else shape.setSelected(true);
	}


	@Override
	public String toLogText() {
		if(shape.isSelected()) {
			return "Selected->" + shape.toString();
		}else {
			return "Unselected->" + shape.toString();
		}
	}

}