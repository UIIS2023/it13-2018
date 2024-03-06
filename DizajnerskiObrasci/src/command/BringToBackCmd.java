package command;

import java.util.Collections;

import geometry.Shape;
import mvc.DrawingModel;

public class BringToBackCmd implements Command {

	private DrawingModel model;
	private Shape shape;
	private int index;
	private int tempIndex;
	
	public BringToBackCmd() {

	}

	public BringToBackCmd(DrawingModel model, Shape shape) {
		this.model = model;
		this.shape = shape;
	}

	
	
	@Override
	public void execute() {
		index = model.getShapes().indexOf(shape);
		tempIndex = index;
		
		while (index > 0) {
			Collections.swap(model.getShapes(), index, index - 1);
			index--;
			}
	}
	

	@Override
	public void unexecute() {
		int index = 0;
		
		while (index < tempIndex) {
			Collections.swap(model.getShapes(), index, index + 1);
			index++;
		}

	}

}
