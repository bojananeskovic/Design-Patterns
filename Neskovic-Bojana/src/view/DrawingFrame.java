package view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import controller.DrawingController;
import view.DrawingFrame;
import observer.Observer;
import view.DrawingView;
import java.awt.event.*;

//Sadrzi view i sve gui komponente aplikacije i obavestava kontroler kada korisnik izvrsi neku akciju
public class DrawingFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel mainPanel;
	private final ButtonGroup buttonsGroup;
	private DrawingView view;
	private DrawingController controller;
	private Color color = Color.BLACK;
	private JToggleButton tglBtnSelect;
	private JToggleButton tglBtnDrawPoint;
	private JToggleButton tglBtnDrawCircle;
	private JToggleButton tglBtnDrawLine;
	private JToggleButton tglBtnDrawDonut;
	private JToggleButton tglBtnDrawRectangle;
	private JToggleButton tglBtnDrawHexagon;
	private JButton btnUpdate;
	private JButton btnDelete;
	private JButton btnUndo;
	private JButton btnRedo;
	private JButton btnToFront;
	private JButton btnToBack;
	private JButton btnBringToFront;
	private JButton btnBringToBack;
	private JButton btnNewDraw;
	private JButton btnSaveDraw;
	private JButton btnLog;
	private JButton btnInteriorColor;
	private JButton btnEdgeColor;
	private MouseAdapter mouseAdapterEdgeColor;
	private MouseAdapter mouseAdapterInteriorColor;
	private MouseAdapter mouseAdapterUpdate;
	private MouseAdapter mouseAdapterDelete;
	private MouseAdapter mouseAdapterUndo;
	private MouseAdapter mouseAdapterRedo;
	private MouseAdapter mouseAdapterNewDraw;
	private MouseAdapter mouseAdapterSaveDrawing;
	private MouseAdapter mouseAdapterLog;
	private MouseAdapter mouseAdapterToFront;
	private MouseAdapter mouseAdapterToBack;
	private MouseAdapter mouseAdapterBringToFront;
	private MouseAdapter mouseAdapterBringToBack;
	private JList<String> activityLog;
	private DefaultListModel <String> dlmList;
	private JScrollPane scrollPane;

	public DrawingFrame() {
		
		setForeground(Color.BLUE);
		setBackground(Color.CYAN);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setLocationRelativeTo(null);
		
		mainPanel = new JPanel();
		mainPanel.setBackground(Color.WHITE);
		mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		mainPanel.setLayout(new BorderLayout(0, 0));
		setContentPane(mainPanel);
		dlmList = new DefaultListModel<String>();
		scrollPane = new JScrollPane();

		JPanel buttonsPanelForDrawing = new JPanel();
		buttonsPanelForDrawing.setBackground(Color.WHITE);
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setBackground(Color.WHITE);

		view = new DrawingView();
		view.setBackground(Color.WHITE);
		mainPanel.add(buttonsPanel, BorderLayout.NORTH);
		mainPanel.add(buttonsPanelForDrawing, BorderLayout.SOUTH);
		mainPanel.add(view, BorderLayout.CENTER);
		buttonsGroup = new ButtonGroup();

		tglBtnDrawPoint = new JToggleButton();
		tglBtnDrawPoint.setFont(new Font("Dotum", Font.BOLD, 12));
		tglBtnDrawPoint.setText("Point");
		tglBtnDrawPoint.setSelected(true);
		buttonsGroup.add(tglBtnDrawPoint);	
		buttonsPanelForDrawing.add(tglBtnDrawPoint);


		tglBtnDrawLine = new JToggleButton();
		tglBtnDrawLine.setFont(new Font("Dotum", Font.BOLD, 12));
		tglBtnDrawLine.setText("Line");
		buttonsGroup.add(tglBtnDrawLine);
		buttonsPanelForDrawing.add(tglBtnDrawLine);

		tglBtnDrawCircle = new JToggleButton();
		tglBtnDrawCircle.setFont(new Font("Dotum", Font.BOLD, 12));
		tglBtnDrawCircle.setText("Circle");
		buttonsGroup.add(tglBtnDrawCircle);
		buttonsPanelForDrawing.add(tglBtnDrawCircle);
		
		tglBtnDrawDonut = new JToggleButton();
		tglBtnDrawDonut.setFont(new Font("Dotum", Font.BOLD, 12));
		tglBtnDrawDonut.setText("Donut");
		buttonsGroup.add(tglBtnDrawDonut);
		buttonsPanelForDrawing.add(tglBtnDrawDonut);
		
		tglBtnDrawRectangle = new JToggleButton();
		tglBtnDrawRectangle.setFont(new Font("Dotum", Font.BOLD, 12));
		tglBtnDrawRectangle.setText("Rectangle");
		buttonsGroup.add(tglBtnDrawRectangle);
		buttonsPanelForDrawing.add(tglBtnDrawRectangle);
		
		tglBtnDrawHexagon = new JToggleButton();
		tglBtnDrawHexagon.setFont(new Font("Dotum", Font.BOLD, 12));
		tglBtnDrawHexagon.setText("Hexagon");
		buttonsGroup.add(tglBtnDrawHexagon);	
		buttonsPanelForDrawing.add(tglBtnDrawHexagon);
		
		btnSaveDraw = new JButton();
		btnSaveDraw.setEnabled(false);
		btnSaveDraw.setText("Save");
		buttonsPanelForDrawing.add(btnSaveDraw);
		
		btnNewDraw = new JButton();
		btnNewDraw.setText("New draw");
		btnNewDraw.setEnabled(false);
		buttonsPanelForDrawing.add(btnNewDraw);
		
		JButton btnOpenDraw = new JButton();
		btnOpenDraw.setText("Open");
		buttonsPanelForDrawing.add(btnOpenDraw);
		
		btnLog = new JButton();
		btnLog.setEnabled(false);
		btnLog.setText("Log");
		buttonsPanelForDrawing.add(btnLog);

		tglBtnSelect = new JToggleButton();
		tglBtnSelect.setText("Select");
		tglBtnSelect.setEnabled(true);
		buttonsGroup.add(tglBtnSelect);
		buttonsPanel.add(tglBtnSelect);

		btnUpdate = new JButton();
		btnUpdate.setText("Update");
		btnUpdate.setEnabled(false);
		buttonsGroup.add(btnUpdate);
		buttonsPanel.add(btnUpdate);

		btnDelete = new JButton();
		btnDelete.setText("Delete");
		btnDelete.setEnabled(false);
		buttonsGroup.add(btnDelete);
		buttonsPanel.add(btnDelete);
		
		mouseAdapterEdgeColor = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent click) {
				color = controller.btnEdgeColorClicked();
				if (color != null) btnEdgeColor.setBackground(color);
			}
		};
		
		mouseAdapterInteriorColor = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent click) {
				color = controller.btnInteriorColorClicked();
				if (color != null) {
					if (color.equals(Color.BLACK)) btnInteriorColor.setForeground(Color.WHITE);
					else if (color.equals(Color.WHITE)) btnInteriorColor.setForeground(Color.BLACK);
					btnInteriorColor.setBackground(color);
				}
			}
		};

		btnEdgeColor = new JButton();
		btnEdgeColor.setForeground(Color.WHITE);
		btnEdgeColor.setText("Edge color");
		btnEdgeColor.setBackground(Color.BLACK);
		btnEdgeColor.addMouseListener(mouseAdapterEdgeColor);
		buttonsPanel.add(btnEdgeColor);

		btnInteriorColor = new JButton();
		btnInteriorColor.setText("Area color");
		btnInteriorColor.setForeground(Color.BLACK);
		btnInteriorColor.setBackground(Color.WHITE);
		btnInteriorColor.addMouseListener(mouseAdapterInteriorColor);
		buttonsPanel.add(btnInteriorColor);
		
		btnUndo = new JButton();
		btnUndo.setEnabled(false);
		btnUndo.setText("Undo");
		buttonsPanel.add(btnUndo);
		
		btnRedo = new JButton();
		btnRedo.setEnabled(false);
		btnRedo.setText("Redo");
		buttonsPanel.add(btnRedo);
		
		btnToFront = new JButton();
		btnToFront.setText("To front");
		btnToFront.setEnabled(false);		
		buttonsPanel.add(btnToFront);
		
		btnToBack = new JButton();
		btnToBack.setText("To back");
		btnToBack.setEnabled(false);		
		buttonsPanel.add(btnToBack);
		
		btnBringToFront = new JButton();
		btnBringToFront.setText("Bring to front");
		btnBringToFront.setEnabled(false);	
		buttonsPanel.add(btnBringToFront);
		
		btnBringToBack = new JButton();
		btnBringToBack.setText("Bring to back");
		btnBringToBack.setEnabled(false);	
		buttonsPanel.add(btnBringToBack);
		
		activityLog = new JList<String>();
		activityLog.setEnabled(false);
		activityLog.setModel(dlmList);
		activityLog.setFont(new Font("Lucida Console", Font.BOLD, 12));
		scrollPane.setViewportView(activityLog);

		mouseAdapterUpdate = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent click) {
				controller.updateShapeClicked();
			}
		};
		
		mouseAdapterDelete = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent click) {
				controller.btnDeleteShapeClicked();
			}
		};
		
		mouseAdapterUndo = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent click) {
				controller.undo();
			}
		};
		
		mouseAdapterRedo = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent click) {
				controller.redo();
			}
		};
		
		mouseAdapterNewDraw = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent click) {
				controller.newDraw();
			}
		};
		
		mouseAdapterBringToBack = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent click) {
				controller.bringToBack();
			}
		};
		
		mouseAdapterBringToFront = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent click) {
				controller.bringToFront();
			}
		};
		
		mouseAdapterToBack = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent click) {
				controller.toBack();
			}
		};
		
		mouseAdapterToFront = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent click) {
				controller.toFront();
			}
		};
		
		mouseAdapterSaveDrawing = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent click) {
				controller.save();
			}
		};
		
		mouseAdapterLog = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent click) {
				if (btnLog.getText().equals("Log")) {
					mainPanel.remove(view);
					mainPanel.add(scrollPane, BorderLayout.CENTER);
					btnLog.setText("Draw");
					
				} else if (btnLog.getText().equals("Draw")) {
					mainPanel.remove(scrollPane);
					mainPanel.add(view, BorderLayout.CENTER);
					btnLog.setText("Log");
				}			
				repaint();
			}
		};
		
		btnOpenDraw.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.open();
			}
		});

		view.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent click) {
				if (tglBtnDrawPoint.isSelected()) controller.btnAddPointClicked(click);
				else if (tglBtnDrawLine.isSelected()) controller.btnAddLineClicked(click);
				else if (tglBtnDrawDonut.isSelected()) controller.btnAddDonutClicked(click);
				else if (tglBtnDrawRectangle.isSelected()) controller.btnAddRectangleClicked(click);
				else if (tglBtnDrawCircle.isSelected()) controller.btnAddCircleClicked(click);
				else if (tglBtnSelect.isSelected()) controller.btnSelectShapeClicked(click);
				else if (tglBtnDrawHexagon.isSelected()) controller.btnAddHexagonClicked(click);
			}
		});
	}
	
	public DefaultListModel<String> getList() {
		return dlmList;
	}
	
	public DrawingView getView() {
		return view;
	}

	public JToggleButton getTglBtnSelect() {
		return tglBtnSelect;
	}

	public JButton getBtnUpdate() {
		return btnUpdate;
	}

	public JButton getBtnDelete() {
		return btnDelete;
	}

	public JButton getBtnUndo() {
		return btnUndo;
	}

	public JButton getBtnRedo() {
		return btnRedo;
	}

	public JButton getBtnToFront() {
		return btnToFront;
	}

	public JButton getBtnToBack() {
		return btnToBack;
	}

	public JButton getBtnBringToFront() {
		return btnBringToFront;
	}

	public JButton getBtnBringToBack() {
		return btnBringToBack;
	}

	public JButton getBtnNewDraw() {
		return btnNewDraw;
	}

	public JButton getBtnSaveDraw() {
		return btnSaveDraw;
	}

	public JButton getBtnLog() {
		return btnLog;
	}

	public MouseAdapter getMouseAdapterUpdate() {
		return mouseAdapterUpdate;
	}

	public MouseAdapter getMouseAdapterDelete() {
		return mouseAdapterDelete;
	}

	public MouseAdapter getMouseAdapterUndo() {
		return mouseAdapterUndo;
	}

	public MouseAdapter getMouseAdapterRedo() {
		return mouseAdapterRedo;
	}

	public MouseAdapter getMouseAdapterNewDraw() {
		return mouseAdapterNewDraw;
	}

	public MouseAdapter getMouseAdapterSaveDrawing() {
		return mouseAdapterSaveDrawing;
	}

	public MouseAdapter getMouseAdapterLog() {
		return mouseAdapterLog;
	}

	public MouseAdapter getMouseAdapterToFront() {
		return mouseAdapterToFront;
	}

	public MouseAdapter getMouseAdapterToBack() {
		return mouseAdapterToBack;
	}

	public MouseAdapter getMouseAdapterBringToFront() {
		return mouseAdapterBringToFront;
	}

	public MouseAdapter getMouseAdapterBringToBack() {
		return mouseAdapterBringToBack;
	}

	public JToggleButton getTglBtnDrawPoint() {
		return tglBtnDrawPoint;
	}

	public JToggleButton getTglBtnDrawCircle() {
		return tglBtnDrawCircle;
	}


	public JToggleButton getTglBtnDrawLine() {
		return tglBtnDrawLine;
	}

	public JToggleButton getTglBtnDrawDonut() {
		return tglBtnDrawDonut;
	}

	public JToggleButton getTglBtnDrawRectangle() {
		return tglBtnDrawRectangle;
	}
	
	public JToggleButton getTglBtnDrawHexagon() {
		return tglBtnDrawHexagon;
	}

	public JButton getBtnInteriorColor() {
		return btnInteriorColor;
	}

	public JButton getBtnEdgeColor() {
		return btnEdgeColor;
	}

	public MouseAdapter getMouseAdapterEdgeColor() {
		return mouseAdapterEdgeColor;
	}

	public MouseAdapter getMouseAdapterInteriorColor() {
		return mouseAdapterInteriorColor;
	}
	
	public void setController(DrawingController controller) {
		this.controller = controller;	
		controller.addPropertyChangedListener(new Observer(this));
	}
}