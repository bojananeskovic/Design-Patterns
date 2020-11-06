package command;

import shapes.Rectangle;

public class CmdUpdateRectangle implements Command{

	private Rectangle oldState;
	private Rectangle newState;
	private Rectangle originalState;
	
	public CmdUpdateRectangle(Rectangle oldState, Rectangle newState) {
		this.oldState = oldState;
		this.newState = newState;
	}
	
	
	@Override
	public void execute() {
		originalState = oldState.clone();
		oldState.setUpLeft(newState.getUpLeft().clone());
		oldState.setWidth(newState.getWidth());
		oldState.setHeight(newState.getHeight());
		oldState.setColor(newState.getColor());
		oldState.setInteriorColor(newState.getInteriorColor());
	}

	
	@Override
	public void unexecute() {
		oldState.setUpLeft(originalState.getUpLeft());
		oldState.setWidth(originalState.getWidth());
		oldState.setHeight(originalState.getHeight());
		oldState.setColor(originalState.getColor());
		oldState.setInteriorColor(originalState.getInteriorColor());
	}


	@Override
	public String toLogText() {
		return "Updated->" + oldState.toString() + "->" + newState.toString();
	}

}