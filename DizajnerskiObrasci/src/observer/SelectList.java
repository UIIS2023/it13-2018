package observer;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class SelectList  {
	
	
	private int listSize;
	private PropertyChangeSupport propertyChangeSupport;
	
	public SelectList()
	{
		propertyChangeSupport = new PropertyChangeSupport(this);
		}
	
	

	public void setListSize(int listSize) {	
		propertyChangeSupport.firePropertyChange("size", this.listSize, listSize); //properti koji se menja, staro stanje, novo stanje
		
		this.listSize = listSize;
	}


	public int getListSize() {
		return listSize;
	}
	
	public void addPropertyChangeListener(PropertyChangeListener pcl)
	{
		propertyChangeSupport.addPropertyChangeListener(pcl);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener pcl)
	{
		propertyChangeSupport.removePropertyChangeListener(pcl);
	}

	
	

}
