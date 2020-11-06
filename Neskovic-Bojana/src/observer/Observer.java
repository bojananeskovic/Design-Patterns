package observer;

import java.awt.event.MouseAdapter;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JButton;
import view.DrawingFrame;

public class Observer implements PropertyChangeListener{
	
	DrawingFrame frame;
	
	public Observer(DrawingFrame frame) {
		this.frame = frame;
	}
	
	public void addListener(JButton button, MouseAdapter adapter) {
		if (!button.isEnabled()) {
			button.setEnabled(true);
			button.addMouseListener(adapter);
		}
	}
	
	public void removeListener(JButton button, MouseAdapter adapter) {
		if (button.isEnabled()) {
			button.removeMouseListener(adapter);
			button.setEnabled(false);
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		switch(evt.getPropertyName()) {
		case "selected":
			addListener(frame.getBtnDelete(), frame.getMouseAdapterDelete());
			break;
		case "exist":
			frame.getTglBtnSelect().setEnabled(true);
			break;
		case "unselected":
		case "don't exist":
			removeListener(frame.getBtnUpdate(), frame.getMouseAdapterUpdate());
			removeListener(frame.getBtnDelete(), frame.getMouseAdapterDelete());
			break;
		case "update turn off":
			removeListener(frame.getBtnToBack(), frame.getMouseAdapterToBack());
			removeListener(frame.getBtnToFront(), frame.getMouseAdapterToFront());
			removeListener(frame.getBtnBringToBack(), frame.getMouseAdapterBringToBack());
			removeListener(frame.getBtnBringToFront(), frame.getMouseAdapterBringToFront());
			removeListener(frame.getBtnUpdate(), frame.getMouseAdapterUpdate());
			break;
		case "update turn on": 
			addListener(frame.getBtnToBack(), frame.getMouseAdapterToBack());
			addListener(frame.getBtnToFront(), frame.getMouseAdapterToFront());
			addListener(frame.getBtnBringToBack(), frame.getMouseAdapterBringToBack());
			addListener(frame.getBtnBringToFront(), frame.getMouseAdapterBringToFront());
			addListener(frame.getBtnUpdate(), frame.getMouseAdapterUpdate());
			break;
		case "redo turn off": 
			removeListener(frame.getBtnRedo(), frame.getMouseAdapterRedo());
			break;
		case "redo turn on": 
			addListener(frame.getBtnRedo(), frame.getMouseAdapterRedo());
			break;
		case "draw is not empty":
			addListener(frame.getBtnSaveDraw(), frame.getMouseAdapterSaveDrawing());
			addListener(frame.getBtnNewDraw(), frame.getMouseAdapterNewDraw());
			addListener(frame.getBtnUndo(), frame.getMouseAdapterUndo());
			addListener(frame.getBtnLog(), frame.getMouseAdapterLog());
			break;
		case "draw is empty":
			removeListener(frame.getBtnSaveDraw(), frame.getMouseAdapterSaveDrawing());
			removeListener(frame.getBtnNewDraw(), frame.getMouseAdapterNewDraw());
			removeListener(frame.getBtnUndo(), frame.getMouseAdapterUndo());
			removeListener(frame.getBtnLog(), frame.getMouseAdapterLog());
			addListener(frame.getBtnEdgeColor(), frame.getMouseAdapterEdgeColor());
			addListener(frame.getBtnInteriorColor(), frame.getMouseAdapterInteriorColor());
			frame.getTglBtnDrawCircle().setEnabled(true);
			frame.getTglBtnDrawLine().setEnabled(true);
			frame.getTglBtnDrawPoint().setEnabled(true);
			frame.getTglBtnDrawDonut().setEnabled(true);
			frame.getTglBtnDrawRectangle().setEnabled(true);
			break;
		case "serialized draw opened":
			addListener(frame.getBtnNewDraw(), frame.getMouseAdapterNewDraw());
			removeListener(frame.getBtnSaveDraw(), frame.getMouseAdapterSaveDrawing());
			removeListener(frame.getBtnUndo(), frame.getMouseAdapterUndo());
			removeListener(frame.getBtnLog(), frame.getMouseAdapterLog());
			removeListener(frame.getBtnUpdate(), frame.getMouseAdapterUpdate());
			removeListener(frame.getBtnDelete(), frame.getMouseAdapterDelete());
			removeListener(frame.getBtnRedo(), frame.getMouseAdapterRedo());
			removeListener(frame.getBtnToBack(), frame.getMouseAdapterToBack());
			removeListener(frame.getBtnToFront(), frame.getMouseAdapterToFront());
			removeListener(frame.getBtnBringToBack(), frame.getMouseAdapterBringToBack());
			removeListener(frame.getBtnBringToFront(), frame.getMouseAdapterBringToFront());
			removeListener(frame.getBtnUpdate(), frame.getMouseAdapterUpdate());
			removeListener(frame.getBtnEdgeColor(), frame.getMouseAdapterEdgeColor());
			removeListener(frame.getBtnInteriorColor(), frame.getMouseAdapterInteriorColor());
			frame.getTglBtnDrawCircle().setEnabled(false);
			frame.getTglBtnDrawLine().setEnabled(false);
			frame.getTglBtnDrawPoint().setEnabled(false);
			frame.getTglBtnDrawDonut().setEnabled(false);
			frame.getTglBtnDrawRectangle().setEnabled(false);
			frame.getTglBtnSelect().setEnabled(false);
			frame.getTglBtnDrawHexagon().setEnabled(false);
			break;
		}
	}
}