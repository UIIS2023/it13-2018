package command;

import geometry.Circle;

public class ModifyCircleCmd implements Command {

	private Circle oldState;
	private Circle newState;
	private Circle original;
	
	public ModifyCircleCmd()
	{
		
	}
	
	public ModifyCircleCmd(Circle oldState,Circle newState)
	{
		this.oldState=oldState;
		this.newState = newState;
	}
	
	@Override
	public void execute() {
		
			original = oldState.clone();
		
			oldState.getCenter().setX(newState.getCenter().getX());
			oldState.getCenter().setY(newState.getCenter().getY());
			oldState.setRadius(newState.getRadius());
			oldState.setColor(newState.getColor());
			oldState.setInnerColor(newState.getInnerColor());
			
		
	}

	@Override
	public void unexecute() {
		
			
		oldState.setCenter(original.getCenter());
		oldState.setRadius(original.getRadius());
		oldState.setColor(original.getColor());
		oldState.setInnerColor(original.getInnerColor());
		
	}
}
