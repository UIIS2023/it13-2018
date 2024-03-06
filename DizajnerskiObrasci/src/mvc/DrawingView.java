package mvc;

import java.awt.Graphics;
import java.util.Iterator;
import geometry.Shape;
import javax.swing.JPanel;

public class DrawingView extends JPanel{

	private static final long serialVersionUID = 1L;	
	private DrawingModel model = new DrawingModel();

	public void setModel(DrawingModel model) {
		this.model = model;
	}
	 
	public void paint(Graphics g) {
		super.paint(g);
		Iterator<Shape> it = model.getShapes().iterator();		
		while (it.hasNext()) {
			it.next().draw(g);
		}
		
		//repaint(); jer se stalno poziva, zato mi treba da vodimo racuna o ovome
		
	}
}
