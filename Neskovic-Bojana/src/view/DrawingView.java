package view;

import java.awt.Graphics;
import java.util.Iterator;
import javax.swing.JPanel;
import model.DrawingModel;
import shapes.Shape;


public class DrawingView extends JPanel {

	private static final long serialVersionUID = 1L;
	private DrawingModel model;
	
	public DrawingView() {}

	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if (model != null) {
			Iterator<Shape> it = model.getAll().iterator();
			while (it.hasNext()) it.next().draw(g);
		}
	}
	
	public void setModel(DrawingModel model) {
		this.model = model;
	}
}