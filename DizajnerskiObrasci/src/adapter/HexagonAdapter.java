package adapter;

import java.awt.Color;
import java.awt.Graphics;

import geometry.Point;
import geometry.Shape;
import hexagon.Hexagon;

public class HexagonAdapter extends Shape {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Hexagon hexagon = new Hexagon(0,0,0);
	
	public HexagonAdapter() {
		
	}
	
	public HexagonAdapter(Point p, int r)
	{
		
		hexagon.setX(p.getX());
		hexagon.setY(p.getY());
		hexagon.setR(r);

	}
	
	public HexagonAdapter(Point p, int r, boolean selected)
	{
		
		hexagon.setX(p.getX());
		hexagon.setY(p.getY());
		hexagon.setR(r);
		hexagon.setSelected(selected);
	}
	
	
	
	public HexagonAdapter(Point p, int r, Color borderColor, Color areaColor, boolean selected)
	{
		
		hexagon.setX(p.getX());
		hexagon.setY(p.getY());
		hexagon.setR(r);
		hexagon.setAreaColor(areaColor);
		hexagon.setBorderColor(borderColor);
		hexagon.setSelected(selected);
	}
	

	public HexagonAdapter clone() {
		
		HexagonAdapter hexagonAdapter = new HexagonAdapter();
		hexagonAdapter.hexagon.setX(this.hexagon.getX());
		hexagonAdapter.hexagon.setY(this.hexagon.getY());
		hexagonAdapter.hexagon.setR(this.hexagon.getR());
		
		hexagonAdapter.hexagon.setAreaColor(this.hexagon.getAreaColor());
		hexagonAdapter.hexagon.setBorderColor(this.hexagon.getBorderColor());
		
		return hexagonAdapter;			
	}
	

	@Override
	public int compareTo(Object o) {
		return 0;
	}

	@Override
	public boolean contains(int x, int y) {
		return hexagon.doesContain(x, y);
	}

	@Override
	public void draw(Graphics g) {
		hexagon.paint(g);
		
	}
	
	public String toString() {
	return "Hexagon -> Center: ( " + this.getHexagon().getX() + " , " + this.getHexagon().getY() +
		" ) Radius: " + this.getHexagon().getR() + " Outer Color: " + this.getHexagon().getBorderColor().getRGB() + 
			" Inner Color: " + this.getHexagon().getAreaColor().getRGB();
}
	
	public boolean equals(Object obj) {
		if (obj instanceof HexagonAdapter) {
			HexagonAdapter temp = (HexagonAdapter) obj;
			if (this.getHexagon().getR() == temp.getHexagon().getR() && this.getHexagon().getX() == temp.getHexagon().getX() && this.getHexagon().getY() == temp.getHexagon().getY()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	


	public Hexagon getHexagon() {
		return hexagon;
	}

	public void setHexagon(Hexagon hexagon) {
		this.hexagon = hexagon;
	}
	
	public void setSelected(boolean selected) {
		hexagon.setSelected(selected);
	}
	
	public boolean isSelected() {
		return hexagon.isSelected();
	}


}
