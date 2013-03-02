package paint;
import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;


public class Trazo {
	
	private ArrayList<Point2D> arrL;
	private Color colorDelTrazo;
	
	public Trazo(Color col){
		arrL = new ArrayList<Point2D>();
		setColor(col);
	}
	
	public void addPunto(Point2D p)
	{
		arrL.add(p);
	}
	
	public Point2D getPunto(int index)
	{
		return arrL.get(index);
		
	}
	public Color getColor()
	{
		return colorDelTrazo;
	}
	public void setColor(Color c)
	{
		colorDelTrazo = c; 
	}
	public int getLongitud()
	{
		return arrL.size();
	}
	
	public ArrayList<Point2D> getConjuntoDePuntos()
	{
		return arrL;
	}
	
	public void addAll(ArrayList<Point2D> arr)
	{
		arrL.addAll(arr);
	}
	
	public boolean contains(Point2D p)
	{
		for(Point2D pun : arrL)
		{
			if(pun.equals(p))
				return true;
		}
		return false;
	}
}
