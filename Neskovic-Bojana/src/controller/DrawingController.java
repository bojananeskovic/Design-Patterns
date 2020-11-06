package controller;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Stack;

import javax.swing.DefaultListModel;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import adapter.HexagonAdapter;
import command.CmdAddShape;
import command.CmdRemoveShape;
import command.CmdSelectShape;
import command.CmdUpdateCircle;
import command.CmdUpdateDonut;
import command.CmdUpdateLine;
import command.CmdUpdatePoint;
import command.CmdUpdateRectangle;
import command.Command;
import command.CmdUpdateHexagon;
import command.CmdBringToBack;
import command.CmdToBack;
import command.CmdBringToFront;
import command.CmdToFront;
import dialogs.DlgCircle;
import dialogs.DlgLine;
import dialogs.DlgPoint;
import dialogs.DlgRectangle;
import hexagon.Hexagon;
import dialogs.DlgDonut;
import dialogs.DlgHexagon;
import view.DrawingFrame;
import model.DrawingModel;
import shapes.Circle;
import shapes.Donut;
import shapes.Line;
import shapes.Point;
import shapes.Rectangle;
import shapes.Shape;
import strategy.SerializableFile;
import strategy.LogFile;
import strategy.ManagerFile;
import strategy.SaveDraw;


public class DrawingController {
	
	private DrawingModel model;
	private DrawingFrame frame;
	private Point initialPointOfLine;
	private Color edgeColor = Color.BLACK;
	private Color interiorColor = Color.WHITE;
	private PropertyChangeSupport propertyChangeSupport;
	private Color choosenEdgeColor;
	private Color choosenInteriorColor;
	private int counterOfSelectedShapes = 0;
	private DefaultListModel<String> log;
	private Stack<Command> commands;
	private Stack<Command> undoCommands;
	private ManagerFile manager;
	
	public DrawingController(DrawingModel model, DrawingFrame frame) {
		
		this.model = model;
		this.frame = frame;
		initialPointOfLine = null;		
		propertyChangeSupport = new PropertyChangeSupport(this);
		log = frame.getList();
		commands = new Stack<>();
		undoCommands = new Stack<>();
	}
	
	public void addPropertyChangedListener(PropertyChangeListener propertyChangeListener) {
		propertyChangeSupport.addPropertyChangeListener(propertyChangeListener);
	}
	
	public Color btnEdgeColorClicked() {
		choosenEdgeColor = JColorChooser.showDialog(null, "Colors pallete", edgeColor);
		if (choosenEdgeColor != null) {
			if (choosenEdgeColor.equals(Color.WHITE)) {
				JOptionPane.showMessageDialog(null, "Background is white");
				return null;
			}
			edgeColor = choosenEdgeColor;
			return edgeColor;
		}
		return choosenEdgeColor;
	}
	

	public Color btnInteriorColorClicked() {
		choosenInteriorColor = JColorChooser.showDialog(null, "Colors pallete", interiorColor);
		if (choosenInteriorColor != null) {
			interiorColor = choosenInteriorColor;
			return interiorColor;
		}
		return choosenEdgeColor;
	}

	public void btnAddPointClicked(MouseEvent click) {
		Point point = new Point(click.getX(), click.getY(), edgeColor);
		Command cmd=new CmdAddShape(point, model);
		executeCommand(cmd);
		log.addElement(cmd.toLogText());
	}
	

	public void btnAddLineClicked(MouseEvent click) {
		if(initialPointOfLine == null) initialPointOfLine = new Point(click.getX(), click.getY(), edgeColor);
		else {
			Line line = new Line(initialPointOfLine, new Point(click.getX(), click.getY()), edgeColor);
			Command cmd=new CmdAddShape(line, model);
			executeCommand(cmd);
			log.addElement(cmd.toLogText());
			initialPointOfLine = null;
		}
	}
	

	public void btnAddRectangleClicked(MouseEvent click) {
		DlgRectangle dlgRectangle = new DlgRectangle();
		dlgRectangle.write(click.getX(), click.getY(), frame.getView().getWidth(), frame.getView().getHeight());
		dlgRectangle.deleteButtons();
		dlgRectangle.setVisible(true);
		if (dlgRectangle.isConfirmed()) {
			Rectangle rectangle = new Rectangle(new Point(click.getX(), click.getY()), dlgRectangle.getRectangleWidth(), dlgRectangle.getRectangleHeight(), edgeColor, interiorColor);
			Command cmd=new CmdAddShape(rectangle, model);
			executeCommand(cmd);
			log.addElement(cmd.toLogText());
		}
	}
	
	public void btnAddCircleClicked(MouseEvent click) {
		DlgCircle dlgCircle = new DlgCircle();
		dlgCircle.write(click.getX(), click.getY(), frame.getView().getWidth(), frame.getView().getHeight());
		dlgCircle.deleteButtons();
		dlgCircle.setVisible(true);
		if (dlgCircle.isConfirmed()) {
			Circle circle = new Circle(new Point(click.getX(), click.getY()), dlgCircle.getRadiusLength(), edgeColor, interiorColor);
			Command cmd=new CmdAddShape(circle, model);
			executeCommand(cmd);
			log.addElement(cmd.toLogText());
		}
	}
	
	public void btnAddDonutClicked(MouseEvent click) {
		DlgDonut dlgDonut = new DlgDonut();
		dlgDonut.write(click.getX(), click.getY(), frame.getView().getWidth(), frame.getView().getHeight());
		dlgDonut.deleteButtons();
		dlgDonut.setVisible(true);
		if (dlgDonut.isConfirmed()) {
			Donut donut = new Donut(new Point(click.getX(), click.getY()), dlgDonut.getRadiusLength(), dlgDonut.getInnerRadiusLength(), edgeColor, interiorColor);
			Command cmd=new CmdAddShape(donut, model);
			executeCommand(cmd);
			log.addElement(cmd.toLogText());
			}
	}
	
	public void btnAddHexagonClicked(MouseEvent click) {
		DlgHexagon dlgHexagon = new DlgHexagon();
		dlgHexagon.write(click.getX(), click.getY(), frame.getView().getWidth(), frame.getView().getHeight());
		dlgHexagon.deleteButtons();
		dlgHexagon.setVisible(true);
		if (dlgHexagon.isConfirmed()) {
			Hexagon hexagon = new Hexagon(click.getX(), click.getY(), dlgHexagon.getRadiusLength());
			hexagon.setBorderColor(edgeColor);
			hexagon.setAreaColor(interiorColor);
			HexagonAdapter hexagonAdapter = new HexagonAdapter(hexagon);
			Command cmd=new CmdAddShape(hexagonAdapter, model);
			executeCommand(cmd);
			log.addElement(cmd.toLogText());
		}
	}
	

	public void btnSelectShapeClicked(MouseEvent click) {
		Iterator <Shape> iterator = model.getAll().iterator();	
		ArrayList<Integer> tempListOfShapes = new ArrayList<>();
		
		while(iterator.hasNext()) {
			Shape shapeForSelection = iterator.next();
			if(shapeForSelection.containsClick(click.getX(), click.getY())) tempListOfShapes.add(model.getIndexOf(shapeForSelection));
		}
		
		if (!tempListOfShapes.isEmpty()) {
			Shape shape = model.getByIndex(Collections.max(tempListOfShapes));

			if (!shape.isSelected()) {
				++counterOfSelectedShapes;
				Command cmd=new CmdSelectShape(shape, true);
				executeCommand(cmd);
				log.addElement(cmd.toLogText());
			}
			else {
				--counterOfSelectedShapes;
				Command cmd=new CmdSelectShape(shape, false);
				executeCommand(cmd);
				log.addElement(cmd.toLogText());
			}
			handleSelectButtons();
		}
		
		frame.getView().repaint();	
	}
	
	public void handleSelect(String s, String command) {
		if (command.equals("redo")) {
			if (s.equals("Selected")) ++counterOfSelectedShapes;
			else --counterOfSelectedShapes;
			handleSelectButtons();
		} else if (command.equals("undo")) {
			if (s.equals("Selected")) --counterOfSelectedShapes;
			else ++counterOfSelectedShapes;
			handleSelectButtons();
		} else if (command.equals("parser")) {
			if (s.equals("Selected")) ++counterOfSelectedShapes;
			else --counterOfSelectedShapes;
		}
	}
	
	public void handleSelectButtons() {
		if (counterOfSelectedShapes == 0) propertyChangeSupport.firePropertyChange("unselected", false, true);
		else if (counterOfSelectedShapes == 1)  {
			propertyChangeSupport.firePropertyChange("update turn on", false, true);
			propertyChangeSupport.firePropertyChange("selected", false, true);
		}  
		else if (counterOfSelectedShapes > 1) propertyChangeSupport.firePropertyChange("update turn off", false, true);
	}

	
	public void updateShapeClicked() {
		Shape shape = getSelectedShape();
		if (shape instanceof Point) btnUpdatePointClicked((Point) shape);
		else if (shape instanceof Line) btnUpdateLineClicked((Line) shape);
		else if (shape instanceof Rectangle) btnUpdateRectangleClicked((Rectangle) shape);
		else if (shape instanceof Donut) btnUpdateDonutClicked((Donut) shape);
		else if (shape instanceof Circle) btnUpdateCircleClicked((Circle) shape);
		else if (shape instanceof HexagonAdapter) btnUpdateHexagonClicked((HexagonAdapter) shape);
	}


	public void btnUpdatePointClicked(Point oldPoint) {
		DlgPoint dlgPoint = new DlgPoint();
		dlgPoint.write(oldPoint, frame.getView().getWidth(), frame.getView().getHeight());
		dlgPoint.setVisible(true);
		if(dlgPoint.isConfirmed()) {
			Point newPoint = new Point(dlgPoint.getXcoordinate(), dlgPoint.getYcoordinate(), dlgPoint.getColor());
			Command cmd=new CmdUpdatePoint(oldPoint, newPoint);
			log.addElement(cmd.toLogText());
			executeCommand(cmd);	
		}
	}
	
	public void btnUpdateLineClicked(Line oldLine) {
		DlgLine dlgLine = new DlgLine();
		dlgLine.write(oldLine);
		dlgLine.setVisible(true);
		if(dlgLine.isConfirmed()) {
			Line newLine =  new Line(new Point(dlgLine.getXcoordinateInitial(), dlgLine.getYcoordinateInitial()), new Point(dlgLine.getXcoordinateLast(), dlgLine.getYcoordinateLast()), dlgLine.getColor());
			Command cmd=new CmdUpdateLine(oldLine, newLine);
			log.addElement(cmd.toLogText());
			executeCommand(cmd);

		}
	}
	

	public void btnUpdateRectangleClicked(Rectangle oldRectangle) {
		DlgRectangle dlgRectangle = new DlgRectangle();
		dlgRectangle.fillUp(oldRectangle, frame.getView().getWidth(), frame.getView().getHeight());
		dlgRectangle.setVisible(true);
		if(dlgRectangle.isConfirmed()) {
			Rectangle newRectangle = new Rectangle(new Point(dlgRectangle.getXcoordinate(), dlgRectangle.getYcoordinate()), dlgRectangle.getRectangleWidth(), dlgRectangle.getRectangleHeight(), dlgRectangle.getEdgeColor(), dlgRectangle.getInteriorColor());
			Command cmd=new CmdUpdateRectangle(oldRectangle, newRectangle);
			log.addElement(cmd.toLogText());
			executeCommand(cmd);

		}
	}


	public void btnUpdateCircleClicked(Circle oldCircle) {
		DlgCircle dlgCircle = new DlgCircle();
		dlgCircle.fillUp(oldCircle, frame.getView().getWidth(), frame.getView().getHeight());
		dlgCircle.setVisible(true);
		if(dlgCircle.isConfirmed()) {
			Circle newCircle = new Circle(new Point(dlgCircle.getXcoordinateOfCenter(), dlgCircle.getYcoordinateOfCenter()), dlgCircle.getRadiusLength(), dlgCircle.getEdgeColor(), dlgCircle.getInteriorColor());
			Command cmd=new CmdUpdateCircle(oldCircle, newCircle);
			log.addElement(cmd.toLogText());
			executeCommand(cmd);	
		}
	}
	
	public void btnUpdateDonutClicked(Donut oldDonut) {
		DlgDonut dlgDonut = new DlgDonut();
		dlgDonut.fillUp(oldDonut, frame.getView().getWidth(), frame.getView().getHeight());
		dlgDonut.setVisible(true);
		if(dlgDonut.isConfirmed()) {
			Donut newDonut = new Donut(new Point(dlgDonut.getXcoordinateOfCenter(), dlgDonut.getYcoordinateOfCenter()), dlgDonut.getRadiusLength(), dlgDonut.getInnerRadiusLength(), dlgDonut.getEdgeColor(), dlgDonut.getInteriorColor());
			Command cmd=new CmdUpdateDonut(oldDonut, newDonut);
			log.addElement(cmd.toLogText());
			executeCommand(cmd);

		}
	}
	
	public void btnUpdateHexagonClicked(HexagonAdapter oldHexagon) {
		DlgHexagon dlgHexagon = new DlgHexagon();
		dlgHexagon.fillUp(oldHexagon, frame.getView().getWidth(), frame.getView().getHeight());
		dlgHexagon.setVisible(true);
		if (dlgHexagon.isConfirmed()) {
			Hexagon hex = new Hexagon(dlgHexagon.getXcoordinate(), dlgHexagon.getYcoordinate(), dlgHexagon.getRadiusLength());
			hex.setAreaColor(dlgHexagon.getInteriorColor());
			hex.setBorderColor(dlgHexagon.getEdgeColor());
			HexagonAdapter newHexagon = new HexagonAdapter(hex);
			Command cmd=new CmdUpdateHexagon(oldHexagon, newHexagon);
			log.addElement(cmd.toLogText());
			executeCommand(cmd);

		}		
	}
	
	public void toFront() {
		Shape shape = getSelectedShape();
		if (model.getIndexOf(shape) == model.getAll().size() - 1) JOptionPane.showMessageDialog(null, "Shape is already on top!");
		else {
			Command cmd=new CmdToFront(model, shape);
			executeCommand(cmd);
			log.addElement(cmd.toLogText());
		}
	}
	
	public void bringToFront() {
		Shape shape = getSelectedShape();
		if (model.getIndexOf(shape) == model.getAll().size() - 1) JOptionPane.showMessageDialog(null, "Shape is already on top!");
		else {
			Command cmd=new CmdBringToFront(model, shape, model.getAll().size() - 1);
			executeCommand(cmd);
			log.addElement(cmd.toLogText());
		}
	}
	
	public void toBack() {
		Shape shape = getSelectedShape();
		if (model.getIndexOf(shape) == 0) JOptionPane.showMessageDialog(null, "Shape is already on bottom!");
		else {
			Command cmd=new CmdToBack(model, shape);
			executeCommand(cmd);
			log.addElement(cmd.toLogText());
		}
	}
	
	public void bringToBack() {
		Shape shape = getSelectedShape();
		if (model.getIndexOf(shape) == 0) JOptionPane.showMessageDialog(null, "Shape is already on bottom!");
		else {
			Command cmd=new CmdBringToBack(model, shape);
			executeCommand(cmd);
			log.addElement(cmd.toLogText());
		}
	}
	
	public Shape getSelectedShape() {
		Iterator<Shape> iterator = model.getAll().iterator();
		while(iterator.hasNext()) {
			Shape shapeForModification = iterator.next();
			if(shapeForModification.isSelected()) return shapeForModification;
		}
		return null;
	}


	public void btnDeleteShapeClicked() {
		if(JOptionPane.showConfirmDialog(null, "Are you sure that you want to delete selected shape?", "Warning!", JOptionPane.YES_NO_OPTION) == 0) {	
			DeleteSelected();
		}
	}
	
	public void DeleteSelected() {
		Iterator<Shape> it = model.getAll().iterator();
		ArrayList<Shape> shapesForDeletion = new ArrayList<Shape>();
		
		while (it.hasNext()) {
			Shape shape = it.next();
			if(shape.isSelected()) {
				shapesForDeletion.add(shape);
				counterOfSelectedShapes--;
				
			}	
		}
		Command cmd=new CmdRemoveShape(shapesForDeletion, model);
		executeCommand(cmd);
		log.addElement(cmd.toLogText());
		handleSelectButtons();
	}
	
	public void executeCommand(Command command) {
		command.execute();
		commands.push(command);
		
		if (!undoCommands.isEmpty()) {
			undoCommands.removeAllElements();
			propertyChangeSupport.firePropertyChange("redo turn off", false, true);
		}
		
		if (model.getAll().isEmpty()) propertyChangeSupport.firePropertyChange("don't exist", false, true);
		else if (model.getAll().size() == 1) propertyChangeSupport.firePropertyChange("exist", false, true);
		
		if (commands.isEmpty()) propertyChangeSupport.firePropertyChange("draw is empty", false, true);
		else if (commands.size() == 1) propertyChangeSupport.firePropertyChange("draw is not empty", false, true);
		frame.getView().repaint();
	}
	
	//Implementacija undo funkcionalnosti
	public void undo() {
		if(commands.isEmpty()) return;
		commands.peek().unexecute();
		log.addElement("Undo->" + commands.peek().toLogText());
		if (commands.peek() instanceof CmdSelectShape) handleSelect((log.get(log.size() - 1)).split("->")[0], "undo");
		undoCommands.push(commands.pop());
		
		if (undoCommands.size() == 1) propertyChangeSupport.firePropertyChange("redo turn on", false, true);
		frame.getView().repaint();
	}
	
	//Implementacija redo funkcionalnosti
	public void redo() {	
		if(undoCommands.isEmpty()) return;
		commands.push(undoCommands.pop());
		commands.peek().execute();
		log.addElement("Redo->" + commands.peek().toLogText());
		if (commands.peek() instanceof CmdSelectShape) handleSelect((log.get(log.size() - 1)).split("->")[0], "redo");

		if (undoCommands.isEmpty()) propertyChangeSupport.firePropertyChange("redo turn off", false, true);
		if (commands.size() == 1) {
			propertyChangeSupport.firePropertyChange("exist", false, true);
			propertyChangeSupport.firePropertyChange("draw is not empty", false, true);
			propertyChangeSupport.firePropertyChange("log turn on", false, true);
		}
		frame.getView().repaint();
	}
	
	public void save() {
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.SAVE_DIALOG);
	    chooser.setFileSelectionMode(JFileChooser.FILES_ONLY); 
		chooser.enableInputMethods(false);
		chooser.setMultiSelectionEnabled(false);
		chooser.setFileHidingEnabled(false);
		chooser.setEnabled(true);
		chooser.setDialogTitle("Save");
		chooser.setAcceptAllFileFilterUsed(false);
		if (!model.getAll().isEmpty()) {
			chooser.setFileFilter(new FileNameExtensionFilter("Serialized draw", "ser"));
			chooser.setFileFilter(new FileNameExtensionFilter("Picture", "jpeg"));
		}
		if (!commands.isEmpty()) chooser.setFileFilter(new FileNameExtensionFilter("Commands log", "log"));
		if(chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
			if (chooser.getFileFilter().getDescription().equals("Serialized draw")) manager = new ManagerFile(new SerializableFile(model));
			else if (chooser.getFileFilter().getDescription().equals("Commands log")) manager = new ManagerFile(new LogFile(frame, model, this));
			else manager = new ManagerFile(new SaveDraw(frame));
			manager.save(chooser.getSelectedFile());
		}
		chooser.setVisible(false);
	}
	
	public void open() {
		JFileChooser chooser = new JFileChooser();
		chooser.enableInputMethods(true);
		chooser.setMultiSelectionEnabled(false);
		chooser.setFileHidingEnabled(false);
		chooser.setEnabled(true);
		chooser.setAcceptAllFileFilterUsed(false);
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setFileSelectionMode(JFileChooser.OPEN_DIALOG);
		chooser.setFileFilter(new FileNameExtensionFilter("Serialized draw", "ser"));
		chooser.setFileFilter(new FileNameExtensionFilter("Commands log", "log"));
		if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			model.removeAll();
			log.removeAllElements();
			undoCommands.clear();
			commands.clear();
			frame.getView().repaint();
			if (chooser.getFileFilter().getDescription().equals("Serialized draw")) {
				manager = new ManagerFile(new SerializableFile(model));
				propertyChangeSupport.firePropertyChange("serialized draw opened", false, true);
			}
			else if (chooser.getFileFilter().getDescription().equals("Commands log")) manager = new ManagerFile(new LogFile(frame, model, this));
			manager.open(chooser.getSelectedFile());
		}	
		chooser.setVisible(false);
	}
	
	public void newDraw() {
		
		if(JOptionPane.showConfirmDialog(null, "Are you sure that you want to start new draw?", "Warning", JOptionPane.YES_NO_OPTION) == 0) {	
			model.removeAll();
			log.removeAllElements();
			undoCommands.clear();
			commands.clear();
			propertyChangeSupport.firePropertyChange("draw is empty", false, true);
			frame.getView().repaint();
		}
	}
	
}