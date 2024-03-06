package command;

import geometry.Donut;

public class ModifyDonutCmd implements Command {

	private Donut oldState;
	private Donut newState;
	private Donut original;
	
	public ModifyDonutCmd()
	{
		
	}
	
	public ModifyDonutCmd(Donut oldState,Donut newState)
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
		oldState.setInnerRadius(newState.getInnerRadius());
		oldState.setColor(newState.getColor());
		oldState.setInnerColor(newState.getInnerColor());
		
		
	}

	@Override
	public void unexecute() {
		
			
		oldState.setCenter(original.getCenter());
		oldState.setRadius(original.getRadius());
		oldState.setColor(original.getColor());
		oldState.setInnerColor(original.getInnerColor());
		oldState.setInnerRadius(original.getInnerRadius());
		
	}
}
