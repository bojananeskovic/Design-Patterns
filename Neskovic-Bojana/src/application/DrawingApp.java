package application;

import controller.DrawingController;
import model.DrawingModel;
import view.DrawingFrame;

public class DrawingApp {

	public static void main(String[] args) {
		
		DrawingFrame frame = new DrawingFrame();
		frame.setVisible(true);
		frame.setTitle("IT02-2017-Neskovic-Bojana");
		DrawingModel model = new DrawingModel();
		frame.getView().setModel(model);
		frame.setController(new DrawingController(model, frame));
	}

}