package command;

import javax.swing.JOptionPane;

import geometry.Shape;
import mvc.DrawingModel;

public class ToBackCmd implements Command {
	
	private DrawingModel model;
	private Shape shape;
	private Shape tempShape;
	private int index;
	
	public ToBackCmd() {
		
	}
	
	public ToBackCmd(DrawingModel model, Shape shape) {
		this.model = model;
		this.shape = shape;
	}

	@Override
	public void execute() {
		try {
		index = model.getShapes().indexOf(shape);
		tempShape = model.getShapes().get(index-1);
		model.getShapes().set(index, tempShape);
		model.getShapes().set(index-1, shape);
	}
	catch (Exception ex) {
		JOptionPane.showMessageDialog(null, "Shape is already in the back!", "Error", JOptionPane.ERROR_MESSAGE);			
	}

	}

	@Override
	public void unexecute() {
		index = model.getShapes().indexOf(shape);
		tempShape = model.getShapes().get(index+1);
		model.getShapes().set(index, tempShape);
		model.getShapes().set(index+1, shape);

	}

}
