package command;

import geometry.Shape;
import mvc.DrawingModel;

public class AddShapeToModelCmd implements Command {

	private Shape shape;
	private DrawingModel model;
	
	
	public AddShapeToModelCmd () {
		
	}
	
	public AddShapeToModelCmd (Shape shape, DrawingModel model) {
		this.shape = shape;
		this.model = model;
	}
	
	@Override
	public void execute() {
		model.add(shape);			
	}

	
	@Override
	public void unexecute() {
		model.remove(shape);

	}

}