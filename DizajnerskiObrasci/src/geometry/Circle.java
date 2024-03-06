package geometry;

import java.awt.Color;
import java.awt.Graphics;

public class Circle extends SurfaceShape{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Point center;
	private int radius;
	
	
	public Circle() {

	}
	
	public Circle(Point center, int radius) {
		this.center = center;
		this.radius = radius;
	}
	
	public Circle(Point center, int radius, boolean selected) {
		this(center, radius);
		setSelected(selected);
	}
	
	public Circle(Point center, int radius, boolean selected, Color color) {
		this(center, radius, selected);
		setColor(color);
	}
	
	public Circle(Point center, int radius, boolean selected, Color color, Color innerColor) {
		this(center, radius, selected, color);
		setInnerColor(innerColor);
	}
	
	@Override
	public int compareTo(Object o) {
		if (o instanceof Circle) {
			return (this.radius - ((Circle) o).radius);
		}
		return 0;
	}
	
	
	public void moveBy(int byX, int byY) {
		this.center.moveBy(byX, byY);
	}
	
	@Override
	public void fill(Graphics g) {
		g.setColor(getInnerColor());
		g.fillOval(this.center.getX() - radius	 + 1, this.center.getY() - radius + 1, radius*2 - 2, radius*2 - 2);
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(getColor());
		g.drawOval(this.center.getX() - radius	, this.center.getY() - radius, radius*2, radius*2); 
		this.fill(g);
		if (isSelected()) {
			g.setColor(Color.BLUE);
			g.drawRect(this.center.getX() - 3, this.center.getY() - 3, 6, 6);
			g.drawRect(this.center.getX() - radius - 3, this.center.getY() - 3, 6, 6);
			g.drawRect(this.center.getX() + radius - 3, this.center.getY() - 3, 6, 6);
			g.drawRect(this.center.getX() - 3, this.center.getY() - radius - 3, 6, 6);
			g.drawRect(this.center.getX() - 3, this.center.getY() + radius - 3, 6, 6);
		}
		
	}
	
	public double area() {
		return radius * radius * Math.PI;
	}
	
	public boolean equals(Object obj) {
		if (obj instanceof Circle) {
			Circle prosledjeni = (Circle) obj;
			if (this.center.equals(prosledjeni.getCenter()) &&
					this.radius == prosledjeni.getRadius()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	@Override
	public Circle clone () {
		Circle circle = new Circle(new Point(50,100),50);
		
		circle.getCenter().setX(this.getCenter().getX());
		circle.getCenter().setY(this.getCenter().getY());
		
		circle.setRadius(this.getRadius());
		
		circle.setColor(this.getColor());
		circle.setInnerColor(this.getInnerColor());
		
		return circle;
		
	}
	
	
	public boolean contains(int x, int y) {
		return center.distance(x, y) <= radius;
	}
	
	public boolean contains(Point p) {
		return center.distance(p.getX(), p.getY()) <= radius;
	}
	
	public Point getCenter() {
		return center;
	}

	public void setCenter(Point center) {
		this.center = center;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
			this.radius = radius;
		
	}

	public String toString() {
		return "Circle -> Center: ( " + center.getX() + " , " + center.getY() + " ) Radius: " + radius + " Outer Color: " + this.getColor().getRGB() + " Inner Color: " + this.getInnerColor().getRGB();
	}

}