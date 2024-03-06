package command;

import java.util.Collections;

import geometry.Shape;
import mvc.DrawingModel;

public class BringToFrontCmd implements Command {

	private DrawingModel model;
	private Shape shape;
	private int index;
	private int tempIndex;

	public BringToFrontCmd()
	{
		
	}
	
	public BringToFrontCmd(DrawingModel model, Shape shape)
	{
		this.model = model;
		this.shape = shape;
	}
	
	
	@Override
	public void execute() {
	
		index = model.getShapes().indexOf(shape);
		tempIndex = index;
		
		while (index<model.getShapes().size()-1) {
			Collections.swap(model.getShapes(), index, index + 1);
			index++;
		}
		
	}



	@Override
	public void unexecute() {
		
		int index=model.getShapes().size() - 1;
		
		while(index > tempIndex)
		{
			Collections.swap(model.getShapes(), index, index-1);
			index--;
		}

	}

}
