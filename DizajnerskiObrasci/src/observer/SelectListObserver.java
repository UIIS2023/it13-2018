package observer;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import mvc.DrawingFrame;


public class SelectListObserver implements PropertyChangeListener{
	
	private int listSize;
	private DrawingFrame frame;
	
	public SelectListObserver(DrawingFrame frame)
	{
		this.frame = frame;
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) { //
		
		
		if(evt.getPropertyName().equals("size"))
		{
		this.listSize = (int) evt.getNewValue();
		
		ShowButtons();
		}
		
	}
	
	public void ShowButtons()
	{
		if(listSize == 1)
		{
			frame.getBtnModify().setVisible(true);
			frame.getBtnBringToBack().setVisible(true);
			frame.getBtnBringToFront().setVisible(true);
			frame.getBtnToBack().setVisible(true);
			frame.getBtnToFront().setVisible(true);
			frame.getBtnDelete().setVisible(true);
		}
		
		else if (listSize > 1)
		{
			frame.getBtnModify().setVisible(false);
			frame.getBtnBringToBack().setVisible(false);
			frame.getBtnBringToFront().setVisible(false);
			frame.getBtnToBack().setVisible(false);
			frame.getBtnToFront().setVisible(false);
			
			frame.getBtnDelete().setVisible(true);
		}
		
		else 
		{
			frame.getBtnModify().setVisible(false);
			frame.getBtnBringToBack().setVisible(false);
			frame.getBtnBringToFront().setVisible(false);
			frame.getBtnToBack().setVisible(false);
			frame.getBtnToFront().setVisible(false);
			frame.getBtnDelete().setVisible(false);
		}
	}

}
