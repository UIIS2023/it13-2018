 package mvc;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Stack;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import adapter.HexagonAdapter;
import command.AddShapeToModelCmd;
import command.BringToBackCmd;
import command.BringToFrontCmd;
import command.Command;
import command.RemoveShapeFromModelCmd;
import command.ToBackCmd;
import command.ToFrontCmd;
import dialogs.DlgCircle;
import dialogs.DlgDonut;
import dialogs.DlgHexagon;
import dialogs.DlgLine;
import dialogs.DlgPoint;
import dialogs.DlgRectangle;
import command.ModifyCircleCmd;
import command.ModifyDonutCmd;
import command.ModifyHexagonCmd;
import command.ModifyLineCmd;
import command.ModifyPointCmd;
import command.ModifyRectangleCmd;
import geometry.Circle;
import geometry.Donut;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import geometry.Shape;
import observer.SelectList;
import observer.SelectListObserver;
import strategy.SaveDrawing;
import strategy.SaveLog;
import strategy.SaveManager;


public class DrawingController {

	
	public DrawingController() {

	}

	private DrawingModel model = new DrawingModel();

	public DrawingModel getModel() {
		return model;
	}
		
	private DrawingFrame frame;

	
	private Shape selectedShape;
	private ArrayList<Shape> select = new ArrayList<Shape>();
	private Point startPoint;
	
	private Stack<Command> stackUndo = new Stack<Command>();
	private Stack<Command> stackRedo = new Stack<Command>();
	
	private SelectListObserver observer;
	private SelectList observable = new SelectList();
	
	SaveManager save;
	SaveLog saveLog;
	SaveDrawing saveDrawing;
	int counter = 0;
	int index = 0;
	
	

	
	public DrawingController(DrawingModel model, DrawingFrame frame) {
		this.model = model;
		this.frame = frame;
		observer = new SelectListObserver(frame);
		observable.addPropertyChangeListener(observer);
	}


	
	protected void thisMouseClicked(MouseEvent e) { 
		Shape newShape = null;
		if (frame.getTglbtnSelection().isSelected()) {
			selectedShape = null;
			Iterator<Shape> it = model.getShapes().iterator();
			
			while (it.hasNext()) {
				Shape shape = it.next();
				
				if (shape.contains(e.getX(), e.getY())) {
					selectedShape = shape;
				}
			}
			if (selectedShape != null )
			{
				if (selectedShape.isSelected()) {
					selectedShape.setSelected(false);
					frame.dlm.addElement("Deselected " + selectedShape.toString());

					select.remove(selectedShape);
					observable.setListSize(select.size());
					
					if(observable.getListSize() != 0)
					{
					selectedShape = select.get(observable.getListSize() - 1);
					}
				} else {
					selectedShape.setSelected(true);
					frame.dlm.addElement("Selected " + selectedShape.toString());
					
					select.add(selectedShape);
					observable.setListSize(select.size());

				}
			}
		} 
		else if (frame.getTglbtnPoint().isSelected()) {
			DlgPoint dlg = new DlgPoint();
			dlg.getTxtX().setText(Integer.toString(e.getX()));
			dlg.getTxtY().setText(Integer.toString(e.getY()));
			
			dlg.getTxtX().setEditable(false);
			dlg.getTxtY().setEditable(false);
			dlg.getBtnSetColor().setBackground(frame.getBtnOuterColor().getBackground());
			
			dlg.setModal(true);
			dlg.setVisible(true);
			
			if (!dlg.isOk())
				return;
			newShape = new Point(e.getX(), e.getY(), false, dlg.getBtnSetColor().getBackground());
		}

		else if (frame.getTglbtnLine().isSelected()) {
			if (startPoint == null) {
				startPoint = new Point(e.getX(), e.getY());
			}else {
				Line line = new Line(startPoint, new Point(e.getX(), e.getY()));
				startPoint = null;
				DlgLine dlg = new DlgLine();
				dlg.getTxtStartPointX().setText(Integer.toString(line.getStartPoint().getX()));
				dlg.getTxtStartPointY().setText(Integer.toString(line.getStartPoint().getY()));
				dlg.getTxtEndPointX().setText(Integer.toString(line.getEndPoint().getX()));
				dlg.getTxtEndPointY().setText(Integer.toString(line.getEndPoint().getY()));
				
				dlg.getTxtStartPointX().setEditable(false);
				dlg.getTxtStartPointY().setEditable(false);
				dlg.getTxtEndPointX().setEditable(false);
				dlg.getTxtEndPointY().setEditable(false);
				dlg.getBtnSetColor().setBackground(frame.getBtnOuterColor().getBackground());

				dlg.setModal(true);
				dlg.setVisible(true);
				
				line.setColor(dlg.getBtnSetColor().getBackground());

				if (!dlg.isOk())
					return;
				
				newShape=line;
			}
		} 
		
		else if(frame.getTglbtnRectangle().isSelected()) {
				DlgRectangle dlg = new DlgRectangle();
				dlg.getTxtUpperLeftPointX().setText(Integer.toString(e.getX()));
				dlg.getTxtUpperLeftPointY().setText(Integer.toString(e.getY()));
				dlg.getBtnSetOuterColor().setBackground(frame.getBtnOuterColor().getBackground());
				dlg.getBtnSetInnerColor().setBackground(frame.getBtnInnerColor().getBackground());
				
				dlg.getTxtUpperLeftPointX().setEditable(false);
				dlg.getTxtUpperLeftPointY().setEditable(false);
				
				dlg.setModal(true);
				dlg.setVisible(true);
				
				if (!dlg.isOk())
					return;
				
				try {
						Rectangle rectangle = new Rectangle(new Point(e.getX(), e.getY()), 
								Integer.parseInt(dlg.getTxtHeight().getText()), Integer.parseInt(dlg.getTxtWidth().getText()), 
								false, dlg.getBtnSetOuterColor().getBackground(), dlg.getBtnSetInnerColor().getBackground());
						if(rectangle.getHeight() <= 0 || rectangle.getWidth() <=0) {
							JOptionPane.showMessageDialog(frame, "Both width and height must be greater than zero!", "Error", JOptionPane.ERROR_MESSAGE);
							return;
					}
					newShape = rectangle;
					
				} catch (NumberFormatException e2) {
					JOptionPane.showMessageDialog(frame, "Check your inputs!", "Error", JOptionPane.ERROR_MESSAGE);
					return;
					
				}
				
			} else if(frame.getTglbtnCircle().isSelected()) {
				DlgCircle dlg = new DlgCircle();
				dlg.getTxtCenterX().setText(Integer.toString(e.getX()));
				dlg.getTxtCenterY().setText(Integer.toString(e.getY()));
				dlg.getBtnSetInnerColor().setBackground(frame.getBtnInnerColor().getBackground());
				dlg.getBtnSetOuterColor().setBackground(frame.getBtnOuterColor().getBackground());
				
				dlg.getTxtCenterX().setEditable(false);
				dlg.getTxtCenterY().setEditable(false);
				
				dlg.setModal(true);
				dlg.setVisible(true);
				
				if (!dlg.isOk())
					return;
				
				try{
				
						Circle circle = new Circle(new Point(e.getX(), e.getY()), 
								Integer.parseInt(dlg.getTxtRadius().getText()), false, 
								dlg.getBtnSetOuterColor().getBackground(), dlg.getBtnSetInnerColor().getBackground());
						if(circle.getRadius() <= 0) {
							JOptionPane.showMessageDialog(frame, "Radius must be greater than zero!", "Error", JOptionPane.ERROR_MESSAGE);
							return;
						}
						newShape = circle;						
					
				}catch(NumberFormatException e2) {
					JOptionPane.showMessageDialog(frame, "Check your inputs!", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				
			} else if(frame.getTglbtnDonut().isSelected()) {
				DlgDonut dlg = new DlgDonut();
				dlg.getTxtCenterX().setText(Integer.toString(e.getX()));
				dlg.getTxtCenterY().setText(Integer.toString(e.getY()));
				dlg.getBtnSetInnerColor().setBackground(frame.getBtnInnerColor().getBackground());
				dlg.getBtnSetOuterColor().setBackground(frame.getBtnOuterColor().getBackground());
				
				dlg.getTxtCenterX().setEditable(false);
				dlg.getTxtCenterY().setEditable(false);
				
				dlg.setModal(true);
				dlg.setVisible(true);
				
					
				if (!dlg.isOk())
					return;
				
				try {
						
						Donut donut = new Donut(new Point(e.getX(), e.getY()), 
								Integer.parseInt(dlg.getTxtOuterRadius().getText()), Integer.parseInt(dlg.getTxtInnerRadius().getText()), false, 
								dlg.getBtnSetOuterColor().getBackground(), dlg.getBtnSetInnerColor().getBackground());
						if(donut.getInnerRadius() >= donut.getRadius()) {
							JOptionPane.showMessageDialog(frame, "Outer radius must be greater than the inner one!", "Error", JOptionPane.ERROR_MESSAGE);
							return;
					}
					if(donut.getInnerRadius() <= 0 || donut.getRadius() <= 0) {
						JOptionPane.showMessageDialog(frame, "Both inner radius and outer radius must be greater than zero!", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
					newShape = donut;					
					
				} catch (NumberFormatException e2) {
					JOptionPane.showMessageDialog(frame, "Wrong data type!", "Error", JOptionPane.ERROR_MESSAGE);
				}
			} else if (frame.getTglbtnHexagon().isSelected()) {
				DlgHexagon dlg = new DlgHexagon();
				dlg.getBtnSetInnerColor().setBackground(frame.getBtnInnerColor().getBackground());
				dlg.getBtnSetOuterColor().setBackground(frame.getBtnOuterColor().getBackground());
				dlg.getTxtCenterX().setText((Integer.toString(e.getX())));
				dlg.getTxtCenterY().setText((Integer.toString(e.getY())));
				
				dlg.getTxtCenterX().setEditable(false);
				dlg.getTxtCenterY().setEditable(false);
				
				dlg.setModal(true);
				dlg.setVisible(true);
				
				if (!dlg.isOk())
					return;
				
				try {				
						HexagonAdapter hexagon = new HexagonAdapter(new Point(e.getX(), e.getY()),Integer.parseInt(dlg.getTxtRadius().getText()), 
								dlg.getBtnSetOuterColor().getBackground(), dlg.getBtnSetInnerColor().getBackground(), false);
						if(Integer.parseInt(dlg.getTxtRadius().getText()) <= 0) {
							JOptionPane.showMessageDialog(frame, "Radius must be greater than zero!", "Error", JOptionPane.ERROR_MESSAGE);
							return;
					}

					newShape = hexagon;					

				} catch (Exception ex) {
					JOptionPane.showMessageDialog(frame, "Wrong data type", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		
	   if (newShape != null) { 
			AddShapeToModelCmd addShape = new AddShapeToModelCmd(newShape, model);
			addShape.execute();
			frame.dlm.addElement("Add " + newShape.toString());
			
			stackUndo.push(addShape);
			frame.getBtnUndo().setVisible(true);
			frame.getBtnRedo().setVisible(false);
			stackRedo.clear();
	  }
	  
		frame.repaint();
			
	}

	
	
	
	
	protected void delete() {
	
		if(selectedShape!=null)
		{
			RemoveShapeFromModelCmd removeShape = new RemoveShapeFromModelCmd(model, select);
			frame.dlm.addElement("Delete " + select.toString());
			removeShape.execute();
			observable.setListSize(select.size());
			
			stackUndo.push(removeShape);
			stackRedo.clear();
			frame.getBtnUndo().setVisible(true);
			frame.getBtnRedo().setVisible(false);
			stackRedo.clear();		
			
			frame.repaint();
				
		}
	}
	
		
	
	
	protected void modify()
	{

		if(selectedShape!=null)
		{
			if(selectedShape instanceof Point)
			{
				Point point=(Point) selectedShape;
				DlgPoint dlg= new DlgPoint();
				dlg.getTxtX().setText(Integer.toString(point.getX()));
				dlg.getTxtY().setText(Integer.toString(point.getY()));
				dlg.getBtnSetColor().setBackground(point.getColor());

				dlg.setModal(true);
				dlg.setVisible(true);
				
				if (!dlg.isOk())
					return;
				
				try {
				Point pt = new Point(Integer.parseInt(dlg.getTxtX().getText()), Integer.parseInt(dlg.getTxtY().getText()));
				pt.setColor(dlg.getBtnSetColor().getBackground());
				
				if(pt.getX() <= 0 || pt.getY() <= 0) {
					JOptionPane.showMessageDialog(null, "Both coordinates must be greater than zero!", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				ModifyPointCmd modifyPoint = new ModifyPointCmd(point, pt);
				modifyPoint.execute();
				
				stackUndo.push(modifyPoint);
				stackRedo.clear();
				frame.getBtnUndo().setVisible(true);
				frame.getBtnRedo().setVisible(false);
				
				}
				 catch (Exception NumberFormatException) {
					JOptionPane.showMessageDialog(null, "Check your inputs!", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				 }
			}
			
			else if(selectedShape instanceof Donut) {
			
			Donut donut=(Donut) selectedShape;
			DlgDonut dlg=new DlgDonut();
			dlg.getTxtInnerRadius().setText(Integer.toString(donut.getInnerRadius()));
			dlg.getTxtOuterRadius().setText(Integer.toString(donut.getRadius()));
			dlg.getTxtCenterX().setText(Integer.toString(donut.getCenter().getX()));
			dlg.getTxtCenterY().setText(Integer.toString(donut.getCenter().getY()));
			dlg.getBtnSetOuterColor().setBackground(donut.getColor());
			dlg.getBtnSetInnerColor().setBackground(donut.getInnerColor());
			
			dlg.setModal(true);
			dlg.setVisible(true);
			
			if (!dlg.isOk())
				return;
			
			try {
				Donut dnt = new Donut(new Point(Integer.parseInt(dlg.getTxtCenterX().getText()), Integer.parseInt(dlg.getTxtCenterY().getText())) ,
						Integer.parseInt(dlg.getTxtOuterRadius().getText()),Integer.parseInt(dlg.getTxtInnerRadius().getText()), false, dlg.getBtnSetOuterColor().getBackground(), dlg.getBtnSetInnerColor().getBackground());
				
				if(dnt.getInnerRadius() >= dnt.getRadius()) {
					JOptionPane.showMessageDialog(null, "Outer radius must be greater than the inner one!", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if(dnt.getCenter().getX() <= 0 || dnt.getCenter().getY() <= 0) {
					JOptionPane.showMessageDialog(null, "Coordinates of center point must be greater than zero!", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				ModifyDonutCmd modifyDonut = new ModifyDonutCmd(donut, dnt);
				modifyDonut.execute();
				
				stackUndo.push(modifyDonut);
				stackRedo.clear();
				frame.getBtnUndo().setVisible(true);
				frame.getBtnRedo().setVisible(false);
				
			}
				
			 catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "Check your inputs!", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			 }
			}
		
			else if(selectedShape instanceof Line)
			{
				Line line=(Line) selectedShape;
				DlgLine dlg=new DlgLine();
				dlg.getTxtStartPointX().setText(Integer.toString(line.getStartPoint().getX()));
				dlg.getTxtStartPointY().setText(Integer.toString(line.getStartPoint().getY()));
				dlg.getTxtEndPointX().setText(Integer.toString(line.getEndPoint().getX()));
				dlg.getTxtEndPointY().setText(Integer.toString(line.getEndPoint().getY()));
				dlg.getBtnSetColor().setBackground(line.getColor());				
				dlg.setModal(true);
				dlg.setVisible(true);
				
				if (!dlg.isOk())
					return;
				
				
				Line ln = new Line(new Point(Integer.parseInt(dlg.getTxtStartPointX().getText()), Integer.parseInt(dlg.getTxtStartPointY().getText())), 
						new Point(Integer.parseInt(dlg.getTxtEndPointX().getText()), Integer.parseInt(dlg.getTxtEndPointY().getText())));
				ln.setColor(dlg.getBtnSetColor().getBackground());
				
				if(ln.getStartPoint().getX() < 0 || ln.getStartPoint().getY() <=0 || ln.getEndPoint().getX() <= 0 || ln.getEndPoint().getY() < 0) {
					JOptionPane.showMessageDialog(null, "Coordinates of both start point and end point must be greater than zero!", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				
				}
				ModifyLineCmd modifyLine = new ModifyLineCmd(line, ln);
				modifyLine.execute();
				
				stackUndo.push(modifyLine);
				stackRedo.clear();
				frame.getBtnUndo().setVisible(true);
				frame.getBtnRedo().setVisible(false);
				
			}
			
			else if(selectedShape instanceof Rectangle)
			{
				Rectangle rectangle=(Rectangle) selectedShape;
				DlgRectangle dlg= new DlgRectangle();
				dlg.getTxtUpperLeftPointX().setText(Integer.toString(rectangle.getUpperLeftPoint().getX()));
				dlg.getTxtUpperLeftPointY().setText(Integer.toString(rectangle.getUpperLeftPoint().getY()));
				dlg.getTxtWidth().setText(Integer.toString(rectangle.getWidth()));
				dlg.getTxtHeight().setText(Integer.toString(rectangle.getHeight()));
				dlg.getBtnSetInnerColor().setBackground(rectangle.getInnerColor());
				dlg.getBtnSetOuterColor().setBackground(rectangle.getColor());
			
				dlg.setModal(true);
				dlg.setVisible(true);
				
				if (!dlg.isOk())
					return;
				

				try {
					Rectangle rct = new Rectangle(new Point(Integer.parseInt(dlg.getTxtUpperLeftPointX().getText()), Integer.parseInt(dlg.getTxtUpperLeftPointY().getText())), Integer.parseInt(dlg.getTxtHeight().getText()), Integer.parseInt(dlg.getTxtWidth().getText()), false, 
							dlg.getBtnSetOuterColor().getBackground(), dlg.getBtnSetInnerColor().getBackground());

					if(rct.getHeight() <= 0 || rct.getWidth() <=0) {
						JOptionPane.showMessageDialog(null, "Width and height must be greater than zero!", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					if(rct.getUpperLeftPoint().getX() <= 0 || rct.getUpperLeftPoint().getY() <= 0) {
						JOptionPane.showMessageDialog(null, "Coordinates of upper left point must be greater than zero!", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					ModifyRectangleCmd modifyRectangle = new ModifyRectangleCmd(rectangle, rct);
					modifyRectangle.execute();
					
					stackUndo.push(modifyRectangle);
					stackRedo.clear();
					frame.getBtnUndo().setVisible(true);
					frame.getBtnRedo().setVisible(false);
					
					
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "Check your inputs!", "Error", JOptionPane.ERROR_MESSAGE);
				}
				
			}			
			else if(selectedShape instanceof Circle)
			{
				Circle circle=(Circle) selectedShape;
				DlgCircle dlg = new DlgCircle();
				dlg.getTxtRadius().setText(Integer.toString(circle.getRadius()));
				dlg.getTxtCenterX().setText(Integer.toString(circle.getCenter().getX()));
				dlg.getTxtCenterY().setText(Integer.toString(circle.getCenter().getY()));
				dlg.getBtnSetInnerColor().setBackground(circle.getInnerColor());
				dlg.getBtnSetOuterColor().setBackground(circle.getColor());
				
				dlg.setModal(true);
				dlg.setVisible(true);
				
				if (!dlg.isOk())
					return;
				
				Circle crl = new Circle(new Point(Integer.parseInt(dlg.getTxtCenterX().getText()), Integer.parseInt(dlg.getTxtCenterY().getText())), Integer.parseInt(dlg.getTxtRadius().getText()), false, dlg.getBtnSetOuterColor().getBackground(), dlg.getBtnSetInnerColor().getBackground());
				
				if(crl.getRadius() <= 0) {
					JOptionPane.showMessageDialog(null, "Radius must be greater than zero!", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if(crl.getCenter().getX() <= 0 || crl.getCenter().getY() <= 0) {
					JOptionPane.showMessageDialog(null, "Center coordinates must be greater than ziro!", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				if(dlg.isOk()) {
				ModifyCircleCmd modifyCircle = new ModifyCircleCmd(circle, crl);
				modifyCircle.execute();
				
				stackUndo.push(modifyCircle);
				stackRedo.clear();
				frame.getBtnUndo().setVisible(true);
				frame.getBtnRedo().setVisible(false);
				}
			}
			
		 else if (selectedShape instanceof HexagonAdapter) {
			HexagonAdapter hexagonAdapter = (HexagonAdapter) selectedShape;
			DlgHexagon dlg = new DlgHexagon();
			dlg.getTxtCenterX().setText(Integer.toString(((HexagonAdapter) selectedShape).getHexagon().getX()));
			dlg.getTxtCenterY().setText(Integer.toString(((HexagonAdapter) selectedShape).getHexagon().getY()));
			dlg.getTxtRadius().setText(Integer.toString(((HexagonAdapter) selectedShape).getHexagon().getR()));

			dlg.getBtnSetInnerColor().setBackground(hexagonAdapter.getHexagon().getAreaColor());
			dlg.getBtnSetOuterColor().setBackground(hexagonAdapter.getHexagon().getBorderColor());
			dlg.setModal(true);
			dlg.setVisible(true);
			
			if (!dlg.isOk())
				return;
			
			if(Integer.parseInt(dlg.getTxtRadius().getText()) <= 0) {
				JOptionPane.showMessageDialog(frame, "Radius must be greater than zero!", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			HexagonAdapter hexAdapter = new HexagonAdapter(new Point(Integer.parseInt(dlg.getTxtCenterX().getText()), Integer.parseInt(dlg.getTxtCenterY().getText())),
					Integer.parseInt(dlg.getTxtRadius().getText()), dlg.getBtnSetOuterColor().getBackground(), dlg.getBtnSetInnerColor().getBackground(), false);
			
			ModifyHexagonCmd modifyHexagon = new ModifyHexagonCmd(hexagonAdapter, hexAdapter);
			modifyHexagon.execute();
			
			stackUndo.push(modifyHexagon);
			stackRedo.clear();
			frame.getBtnUndo().setVisible(true);
			frame.getBtnRedo().setVisible(false);
		
		
		}
		
		else {
			JOptionPane.showMessageDialog(null, "No shape selected!", "Error", JOptionPane.ERROR_MESSAGE);

		}
			
		frame.dlm.addElement("Modify " + selectedShape.toString());
		
		frame.repaint();
		}
	}
	
	
	
	
	protected void undo() {
		
		try {

			Command undoCommand = stackUndo.pop();

			undoCommand.unexecute();
			frame.dlm.addElement("Undo");

			stackRedo.push(undoCommand);
			frame.repaint();
			frame.getBtnRedo().setVisible(true);
			if (stackUndo.isEmpty()) {
				frame.getBtnUndo().setVisible(false);
			}

		}

		catch (Exception e) {
			JOptionPane.showMessageDialog(null, "No more actions to undo!");
		}
	}

	protected void redo() {
		try {
			Command redoCommand = stackRedo.pop();
			redoCommand.execute();		
			
			frame.dlm.addElement("Redo");

			stackUndo.push(redoCommand);
			frame.repaint();
			frame.getBtnUndo().setVisible(true);
			if (stackRedo.isEmpty()) {
				frame.getBtnRedo().setVisible(false);
			}
	}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, "No more actions to redo!");
		}
	}
		

	protected void toFront() {
			ToFrontCmd toFront = new ToFrontCmd(model, selectedShape);
			toFront.execute();
			frame.repaint();
			stackUndo.push(toFront);
			frame.getBtnUndo().setVisible(true);
			frame.getBtnRedo().setVisible(false);
			frame.dlm.addElement("ToFront " + selectedShape.toString());
			stackRedo.clear();

	}

	protected void toBack() {
			ToBackCmd toBack = new ToBackCmd(model, selectedShape);
			toBack.execute();
			frame.dlm.addElement("ToBack " + selectedShape.toString());
			frame.repaint();
			stackUndo.push(toBack);
			frame.getBtnUndo().setVisible(true);
			frame.getBtnRedo().setVisible(false);
			frame.dlm.addElement("ToBack " + selectedShape.toString());
			stackRedo.clear();			

			
	}

	protected void bringToFront() {
		
		if(model.getShapes().lastIndexOf(selectedShape) == model.getShapes().size() - 1) {
			JOptionPane.showMessageDialog(null, "Shape is already in the front!", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
			BringToFrontCmd bringToFront = new BringToFrontCmd(model, selectedShape);
			bringToFront.execute();
			frame.repaint();
			stackUndo.push(bringToFront);
			frame.getBtnUndo().setVisible(true);
			frame.getBtnRedo().setVisible(false);
			frame.dlm.addElement("BringToFront " + selectedShape.toString());
			stackRedo.clear();


	}

	protected void bringToBack() {
		
		if(model.getShapes().lastIndexOf(selectedShape) == 0) {
			JOptionPane.showMessageDialog(null, "Shape is already in the back!", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
			BringToBackCmd bringToBack = new BringToBackCmd(model, selectedShape);
			bringToBack.execute();
			frame.repaint();
			stackUndo.push(bringToBack);
			frame.getBtnUndo().setVisible(true);
			frame.getBtnRedo().setVisible(false);
			frame.dlm.addElement("BringToBack " + selectedShape.toString());
			stackRedo.clear();



	}
	
	
	
	protected void saveLog() {
		saveLog = new SaveLog();

		save = new SaveManager(saveLog);
		save.save(frame.dlm);

	}

	protected void saveDrawing() {
		saveDrawing = new SaveDrawing();
		save = new SaveManager(saveDrawing);
		save.save(model.getShapes());
	}
	

	
	
	@SuppressWarnings("unchecked")
	public void loadDrawing() throws ClassNotFoundException {
		
		
		JFileChooser fileChooser = new JFileChooser();
				
			if(fileChooser.showOpenDialog(null) == JFileChooser.OPEN_DIALOG) {
				
				FileInputStream f;
				
				try {
					f = new FileInputStream(fileChooser.getSelectedFile());
					ObjectInputStream ois = new ObjectInputStream(f);
					model.getShapes().addAll((ArrayList<Shape>)ois.readObject());
					ois.close();
					f.close();
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "A problem has occured.", "Error", JOptionPane.ERROR_MESSAGE);

				}
				
				
			}
			
			frame.repaint();
		
	}

	
	public void loadLog() {
		JFileChooser fileChooser = new JFileChooser();

		if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

			try {
				int index = 0;

				@SuppressWarnings("resource")
				Scanner scan = new Scanner(fileChooser.getSelectedFile());
				while (scan.hasNext()) {
					frame.dlm.add(index, scan.nextLine());
					index++;
				}
				frame.getBtnLoadNext().setVisible(true);

			} catch (FileNotFoundException e) {

				JOptionPane.showMessageDialog(null, "You must select file!");
			}

		}

	}

	public void loadNext() {
		Command command = null;
		Shape shape = null;
		counter++;
		frame.getList().setSelectedIndex(counter - 1);
		
		frame.getList().ensureIndexIsVisible(counter);
		

		if (index < frame.dlm.getSize()) {

			String line = frame.dlm.get(index);
			String[] words = line.split(" ");
			
			String commandWord = words[0];


			if (commandWord.equals("Add")) {

				shape = this.check(words);

				command = new AddShapeToModelCmd(shape, model);
			}

			if (commandWord.equals("Delete")) {

				command = new RemoveShapeFromModelCmd(model, select);

			}
			if (commandWord.equals("Modify")) {

				Shape oldShape = select.get(0);

				Shape newShape = this.check(words);

				if (oldShape instanceof Point) {
					command = new ModifyPointCmd((Point) oldShape, (Point) newShape);
				}

				else if (oldShape instanceof Line) {
					command = new ModifyLineCmd((Line) oldShape, (Line) newShape);
				} else if (oldShape instanceof Circle) {
					command = new ModifyCircleCmd((Circle) oldShape, (Circle) newShape);
				} else if (oldShape instanceof Rectangle) {
					command = new ModifyRectangleCmd((Rectangle) oldShape, (Rectangle) newShape);
				} else if (oldShape instanceof Donut) {
					command = new ModifyDonutCmd((Donut) oldShape, (Donut) newShape);
				} else if (oldShape instanceof HexagonAdapter) {
					command = new ModifyHexagonCmd((HexagonAdapter) oldShape, (HexagonAdapter) newShape);
				}

			}

			if (commandWord.equals("Selected")) {
				Shape selectedShape = this.check(words);
				int index = model.getShapes().indexOf(selectedShape);
				shape = model.get(index);

				shape.setSelected(true);
				select.add(shape);
				
				observable.setListSize(select.size());

			}
			
			if (commandWord.equals("Deselected")) {
				Shape selectedShape = this.check(words);
				int index = model.getShapes().indexOf(selectedShape);
				shape = model.get(index);
				shape.setSelected(false);
				select.remove(shape);

				observable.setListSize(select.size());

			}

			if (commandWord.equals("ToFront")) {

				shape = select.get(0);

				command = new ToFrontCmd(model, shape);
			}

			if (commandWord.equals("ToBack")) {
				shape = select.get(0);
				command = new ToBackCmd(model, shape);
			}

			if (commandWord.equals("BringToFront")) {
				shape = select.get(0);
				command = new BringToFrontCmd(model, shape);
			}

			if (commandWord.equals("BringToBack")) {
				shape = select.get(0);
				command = new BringToBackCmd(model, shape);
			}

			if (command != null) {

				command.execute();
				stackUndo.push(command); //!!!!!

			}

			if (commandWord.equals("Undo")) {
				Command undoCmd = stackUndo.pop();
				undoCmd.unexecute();

				stackRedo.push(undoCmd);
			}

			if (commandWord.equals("Redo")) {
				Command redoCmd = stackRedo.pop();
				redoCmd.execute();
				stackUndo.push(redoCmd);
			}

			index++;
			frame.repaint();

		}
		if (counter == frame.dlm.getSize()) {
			frame.getBtnLoadNext().setVisible(false);
		}

	}


	public Shape check(String[] words) {
		
		String shapeWord = words[1];

		if (shapeWord.equals("Point")) {
			int x = Integer.parseInt(words[3]);
			int y = Integer.parseInt(words[5]);
			Color color = new Color(Integer.parseInt(words[8]));
			return new Point(x,y,false,color);				
		
		} else if (shapeWord.equals("Line")) {
			Point startPoint = new Point(Integer.parseInt(words[6]), Integer.parseInt(words[8]));
			Point endPoint = new Point(Integer.parseInt(words[13]), Integer.parseInt(words[15]));
			return new Line(startPoint, endPoint, false, new Color(Integer.parseInt(words[18])));

		} else if (shapeWord.equals("Rectangle")) {
			Point upperLeftPoint = new Point(Integer.parseInt(words[7]), Integer.parseInt(words[9]));
			return new Rectangle(upperLeftPoint, Integer.parseInt(words[14]), Integer.parseInt(words[12]), false,
					new Color(Integer.parseInt(words[17])), new Color(Integer.parseInt(words[20])));
		} else if (shapeWord.equals("Circle")) {
			Point center = new Point(Integer.parseInt(words[5]), Integer.parseInt(words[7]));
			return new Circle(center, Integer.parseInt(words[10]), false, new Color(Integer.parseInt(words[13])),
					new Color(Integer.parseInt(words[16])));
		} else if (shapeWord.equals("Donut")) {
			Point center = new Point(Integer.parseInt(words[5]), Integer.parseInt(words[7]));
			return new Donut(center, Integer.parseInt(words[14]), Integer.parseInt(words[11]),false,
					new Color(Integer.parseInt(words[17])), new Color(Integer.parseInt(words[20])));
		} else if (shapeWord.equals("Hexagon")) {
			Point center = new Point(Integer.parseInt(words[5]), Integer.parseInt(words[7]));
			return new HexagonAdapter(center, Integer.parseInt(words[10]), new Color(Integer.parseInt(words[13])),
					new Color(Integer.parseInt(words[16])), false);
		}

		return null;

	}


	
}
	
