/**
 * Author: GLC
 */
package paint;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
//import java.awt.Robot;
//import java.awt.Shape;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JOptionPane;


public class DrawablePanel extends Canvas{
	
	private final int WIDTH=640, HEIGHT=590;
	private int xini, yini, xfin, yfin;// contadoooorr;
	private int x, y, dx, dy, p, incE, incNE, stepx, stepy;
	private int x0,y0,x1,y1, radius, numPtosControl, cuentaPuntosParaPolygs;
	private double xc, yc;
	private Trazo trazo, trazoActual, trazoTemporalBezier, trazoFronterizo;
	private ArrayList<Point> puntos;
	private ArrayList<Trazo> buffer; //un buffer es un arreglo de trazos.
	private Color currentColor; //borderCol;
	private boolean modoLR, mouseWasDragged, modoCirculo, modoLibre, 
					modoBezier, modoPolygono, modoRelleno;
	private Point2D cp0, cp1, cp2, cp3; //ptoInteriorRelleno;
	//private Robot rob;
	//private BufferedImage bIm;
	//private Graphics g;
	
	public DrawablePanel()
	{
		
		super.setSize(WIDTH, HEIGHT);
		currentColor = Color.black;
		//bIm = new BufferedImage(550, 500, BufferedImage.TYPE_INT_ARGB);
		puntos = new ArrayList<Point>();
		buffer = new ArrayList<Trazo>();
		modoLibre=true;
		addMouseMotionListener(new MouseMotionAdapter() {
			//almacena las coordenadas de arrastre y repinta
			
			public void mouseDragged(MouseEvent me)
			{
					puntos.add(me.getPoint());
					mouseWasDragged = true;
				//cuentaPtos++;
				//repaint();
				//System.out.println(cuentaPtos);
				
			}
		});
		
		addMouseListener(new MouseAdapter() {
			
			public void mouseReleased(MouseEvent me)
			{
				
				if(mouseWasDragged && !modoPolygono && !modoRelleno)
				{
					if(modoLR)
					{
						//trazoTemporalBezier = new Trazo(currentColor);
						xc = puntos.get(0).getX();
						yc = puntos.get(0).getY();
						setXiniYini();
						doBresenham();
						buffer.add(trazo);
						
						trazoTemporalBezier = trazo;
						
						actualizaDatosParaBezier();
						
					}
					else if(modoCirculo)
					{
						xc = puntos.get(0).x;
						yc = puntos.get(0).y;
						
						setCircleParams();
						traceCircle();
						buffer.add(trazo);
					}
					/**/
					
					else if(modoLibre)
					{
						xc = puntos.get(0).x;
						yc = puntos.get(0).y;
						trazo = new Trazo(currentColor);
						for(Point p : puntos)
							trazo.addPunto(p);
					
						buffer.add(trazo);
						System.out.println("trazo agrgad0");
					}
					
					mouseWasDragged = false;
					puntos.clear(); //limpia lista de puntos escuchados
					repaint();
				}
				
			}
			
			public void mousePressed(MouseEvent me)
			{
				int x = me.getPoint().x;
				int y = me.getPoint().y;
				//imp("("+x+","+y+")");
				trazoActual = clickSobreTrazo(x, y);
				
				if(trazoActual!=null)
				{
					System.out.println("Elemento seleccionado!");
					repaint();
				}
				if(modoBezier)
				{
					//imp("Bezier!!!!");
					
					numPtosControl++;
					//imp("numptoscontrol: "+numPtosControl);
					switch(numPtosControl)
					{
						case 1:
							cp1 = new Point(me.getPoint());
							break;
						case 2:
							cp2 = new Point( me.getPoint());
							doBezier(0.85);
							repaint();
							break;
						default:
							break;
								
					}
				}
				else if(modoPolygono)
				{
					//va agregando puntos para el bresenham recursivo
					cuentaPuntosParaPolygs++;
					//imp("puntos para poligs: "+cuentaPuntosParaPolygs);
					puntos.add(me.getPoint());
					
				}
				
				
			}
		});
		
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent ke)
			{
				 int keyCode = ke.getKeyCode();
				    switch( keyCode ) { 
				   
				        case KeyEvent.VK_UP:
				            moveUp();
				            repaint();
				            break;
				        case KeyEvent.VK_DOWN:
				        	moveDown();
				        	repaint();
				            break;
				        case KeyEvent.VK_LEFT:
				        	moveLeft();
				        	repaint();
				            break;
				        case KeyEvent.VK_RIGHT:
				        	moveRight();
				        	repaint();
				            break;
				        case KeyEvent.VK_B:
				        	
				        	del();
				        	break;
				    }
			}
		});
	}
	
	//se llama a s’ mismo autom‡ticamente
	public void paint(Graphics g)
	{
	
		super.paint(g);      
		Graphics2D g2 = (Graphics2D)g; 
		
		System.out.println("buf size: "+buffer.size());
		for(Trazo este : buffer)
		{
			g.setColor(este.getColor());
			for(Point2D pto : este.getConjuntoDePuntos())
			{
				Rectangle2D line = new Rectangle2D.Double(pto.getX(), pto.getY(),
						3.0, 3.0);
				g2.fill(line);
			}			
		}
	}
	
	
	public void setCurrentColor(Color col)
	{
		currentColor = col;
	}
	
	public void del()
	{
		if(trazoActual != null)
		{
			cuentaPuntosParaPolygs=0;
			puntos.clear();
			System.out.println("Borrado!");
			System.out.println(buffer.remove(trazoActual));
			trazoActual = null; //bug fix!*******
			repaint(); //vuelve a pintar, pero con el buffer actualizado.
		}
	}
	
	public void rotar()
	{
		//imp("trazo actual ymodo circ"+trazoActual+" "+modoCirculo);
		if(trazoActual!=null)
		{
			cuentaPuntosParaPolygs=0;
			puntos.clear();
			xc = trazoActual.getPunto(0).getX();
			yc = trazoActual.getPunto(0).getY();
			buffer.remove(trazoActual);
			trazo = new Trazo(currentColor);
			
			//Object arrePoint[]= trazoActual.getConjuntoDePuntos().toArray();
			
			
			for(Point2D p : trazoActual.getConjuntoDePuntos())
			{
				/*double nuevax= p.getX()*Math.cos((5*Math.PI)/180) - 
						p.getY()*Math.sin((5*Math.PI)/180);
				double nuevay = p.getX()*Math.sin((5*Math.PI)/180) + 
						p.getY()*Math.cos((5*Math.PI)/180);*/
				double xp= xc + (p.getX()-xc)*Math.cos(0.17) -
						(p.getY()-yc)*Math.sin(0.17);
				double yp= (yc + (p.getX()-xc)*Math.sin(0.17)) +
						(p.getY()-yc)*Math.cos(0.17);
				trazo.addPunto(new Point2D.Double(xp, yp));
				
			}
			trazoActual = trazo;
			if(modoLR)
			{
				trazoTemporalBezier = trazoActual;
				actualizaDatosParaBezier();
			}
			buffer.add(trazoActual);
			repaint();
		}
		
	}
	
	public Trazo clickSobreTrazo(int x, int y)
	{
		//int i=0;
		for(Trazo este : buffer)
		{
			
			for(Point2D pto : este.getConjuntoDePuntos())
			{
				//System.out.printf("(%d,%d)vs(%d,%d)\n", pto.x, pto.y, x, y);
				if(Math.abs(pto.getX() - x)<= 7 && Math.abs(pto.getY() - y)<= 7)
				{
					este.setColor(currentColor);
					if(modoLR)
					{
						trazoTemporalBezier = este;
						actualizaDatosParaBezier();
					}
					return este;
				}
				else
					continue;
			}
			
		}
		
		return null;
	}
	
	public void setModoLineaRecta(boolean valor)
	{
		modoLR = valor;
	}
	
	public void setModoCirculo(boolean v)
	{
		modoCirculo = v;
	}
	
	public void setModoLibre(boolean v)
	{
		modoLibre = v;
	}
	
	public void setModoBezier(boolean v)
	{
		if(v)
			numPtosControl = 0;
		modoBezier = v;
	}
	
	public void setModoPolygono(boolean v)
	{
		modoPolygono = v;
	}
	
	public void setModoRelleno(boolean v)
	{
		modoRelleno = v;
	}
	
	public void setXiniYini()
	{
		int ultimo = puntos.size()-1;
		if(puntos.size()>0)
		{
			xini = puntos.get(0).x;
			yini= puntos.get(0).y;
			xfin = puntos.get(ultimo).x;
			yfin = puntos.get(ultimo).y;
		}
	}
	
	public void setCircleParams()
	{
		int radCompara=0;
		int ultimo = puntos.size()-1;
		xini = puntos.get(0).x;
		yini= puntos.get(0).y;
		xfin = puntos.get(ultimo).x;
		yfin = puntos.get(ultimo).y;
		radius = Math.abs(xfin-xini);
		radCompara = Math.abs(yfin-yini);
		radius = (radCompara>radius) ? radCompara : radius;
		//imp("radius="+radius);
	}
	
	public void moveRight()
	{
		
		if(trazoActual!=null)
		{
			cuentaPuntosParaPolygs=0;
			puntos.clear();
			buffer.remove(trazoActual);
			trazo = new Trazo(currentColor);
			
			//Object arrePoint[]= trazoActual.getConjuntoDePuntos().toArray();
			
			
			for(Point2D p : trazoActual.getConjuntoDePuntos())
			{
				trazo.addPunto(new Point2D.Double(p.getX()+5, p.getY()));
			}
			trazoActual = trazo;
			if(modoLR)
			{
				trazoTemporalBezier = trazoActual;
				actualizaDatosParaBezier();
			}
			buffer.add(trazoActual);
		}
		
	}
	
	public void moveLeft()
	{
		if(trazoActual!=null)
		{
			cuentaPuntosParaPolygs=0;
			puntos.clear();
			buffer.remove(trazoActual);
			trazo = new Trazo(currentColor);
			
			//Object arrePoint[]= trazoActual.getConjuntoDePuntos().toArray();
			
			
			for(Point2D p : trazoActual.getConjuntoDePuntos())
			{
				trazo.addPunto(new Point2D.Double(p.getX()-5, p.getY()));
			}
			trazoActual = trazo;
			if(modoLR)
			{
				trazoTemporalBezier = trazoActual;
				actualizaDatosParaBezier();
			}
			buffer.add(trazoActual);
		}
		
	}
	
	public void moveUp()
	{
		if(trazoActual!=null)
		{
			cuentaPuntosParaPolygs=0;
			puntos.clear();
			buffer.remove(trazoActual);
			trazo = new Trazo(currentColor);
			
			//Object arrePoint[]= trazoActual.getConjuntoDePuntos().toArray();
			
			
			for(Point2D p : trazoActual.getConjuntoDePuntos())
			{
				trazo.addPunto(new Point2D.Double(p.getX(), p.getY()-5));
			}
			trazoActual = trazo;
			if(modoLR)
			{
				trazoTemporalBezier = trazoActual;
				actualizaDatosParaBezier();
			}
			buffer.add(trazoActual);
		}
		
	}
	
	public void moveDown()
	{
		if(trazoActual!=null)
		{
			cuentaPuntosParaPolygs=0;
			puntos.clear();
			buffer.remove(trazoActual);
			trazo = new Trazo(currentColor);
			
			//Object arrePoint[]= trazoActual.getConjuntoDePuntos().toArray();
			
			
			for(Point2D p : trazoActual.getConjuntoDePuntos())
			{
				trazo.addPunto(new Point2D.Double(p.getX(), p.getY()+5));
			}
			trazoActual = trazo;
			if(modoLR)
			{
				trazoTemporalBezier = trazoActual;
				actualizaDatosParaBezier();
			}
			buffer.add(trazoActual);
		}
		
	}
	
	 public void doBresenham()
	 {
		 
		    trazo = new Trazo(currentColor);
	        x0 = xini;
	        y0 = yini;
	        x1 = xfin;
	        y1 = yfin;
	          
	                //grph.drawLine(x0, y0, x1, y1);
	          dx = (x1 - x0);
	          dy = (y1 - y0);
	         /* determinar que punto usar para empezar, cual para terminar */
	          if (dy < 0) { 
	            dy = -dy; stepy = -1; 
	          } 
	          else
	            stepy = 1;
	          if (dx < 0) {  
	            dx = -dx; stepx = -1; 
	          } 
	          else 
	            stepx = 1;
	          x = x0;
	          y = y0;
	         // g.setColor(colorActual);
	          trazo.addPunto(new Point(x0,y0));
	          //g.drawLine( x0, y0, x0, y0);
	          
	          
	         /* se cicla hasta llegar al extremo de la l’nea */
	          if(dx>dy){
	            p = 2*dy - dx;
	            incE = 2*dy;
	            incNE = 2*(dy-dx);
	            while (x != x1){
	              x = x + stepx;
	              if (p < 0){
	                p = p + incE;
	              }
	              else {
	                y = y + stepy;
	                p = p + incNE;
	              }
	             // g.setColor(colorActual);
	              trazo.addPunto(new Point(x,y));
	             // g.drawLine( x, y, x, y);
	              
	            }
	          }
	          else{
	            p = 2*dx - dy;
	            incE = 2*dx;
	            incNE = 2*(dx-dy);
	            while (y != y1){
	              y = y + stepy;
	              if (p < 0){
	                p = p + incE;
	              }
	              else {
	                x = x + stepx;
	                p = p + incNE;
	              }
	              trazo.addPunto(new Point(x,y));
	              //g.drawLine( x, y, x, y);
	              
	            }
	          }	
	    }
	 
	 public void traceCircle()
		{
		 
		 trazo = new Trazo(currentColor);
		 	x0 = xini;
	        y0 = yini;
	        
			int f = 1 - radius;
			int ddF_x = 1;
			int ddF_y = -2 * radius;
			int x = 0;
			int y = radius;
			//g.drawLine(x0, y0 + radius, x0, y0 + radius);
	        trazo.addPunto(new Point(x0, y0 + radius));        
	                
			//g.drawLine(x0, y0 - radius,x0, y0 - radius);
			trazo.addPunto(new Point(x0, y0 - radius));
	                
			//g.drawLine(x0 + radius, y0,x0 + radius, y0);
			trazo.addPunto(new Point(x0 + radius, y0));
	                
			//g.drawLine(x0 - radius, y0,x0 - radius, y0);
			trazo.addPunto(new Point(x0 - radius, y0));
		  
			while(x < y){
				
				if(f >= 0)
				{
					y--;
					ddF_y += 2;
					f += ddF_y;
				}
				x++;
				ddF_x += 2;
				f += ddF_x;
		  
				//g.drawLine(x0 + x, y0 + y,x0 + x, y0 + y);
				trazo.addPunto(new Point(x0 + x, y0 + y));
				
				//g.drawLine(x0 - x, y0 + y,x0 - x, y0 + y);
				trazo.addPunto(new Point(x0 - x, y0 + y));

				//g.drawLine(x0 + x, y0 - y,x0 + x, y0 - y);
				trazo.addPunto(new Point(x0 + x, y0 - y));
				
				//g.drawLine(x0 - x, y0 - y,x0 - x, y0 - y);
				trazo.addPunto(new Point(x0 - x, y0 - y));

				//g.drawLine(x0 + y, y0 + x,x0 + y, y0 + x);
				trazo.addPunto(new Point(x0 + y, y0 + x));

				//g.drawLine(x0 - y, y0 + x,x0 - y, y0 + x);
				trazo.addPunto(new Point(x0 - y, y0 + x));

				//g.drawLine(x0 + y, y0 - x,x0 + y, y0 - x);
				trazo.addPunto(new Point(x0 + y, y0 - x));

				//g.drawLine(x0 - y, y0 - x,x0 - y, y0 - x);
				trazo.addPunto(new Point(x0 - y, y0 - x));

	                        
			}
	                
		}
	 
	 public Point2D calcBezierPoint(double t)
	 {
		    double   ax, bx, cx;
		    double  ay, by, cy;
		    double  tSquared, tCubed;
		   // Point result = ;
		 
		    /* c‡lculo de los coeficientes polinomiales */
		 
		    cx = 3.0 * (cp1.getX() - cp0.getX());
		    bx = 3.0 * (cp2.getX() - cp1.getX()) - cx;
		    ax = cp3.getX() - cp0.getX() - cx - bx;
		 
		    cy = 3.0 * (cp1.getY() - cp0.getY());
		    by = 3.0 * (cp2.getY() - cp1.getY()) - cy;
		    ay = cp3.getY() - cp0.getY() - cy - by;
		 
		    /* calculate the curve point at parameter value t */
		 
		    tSquared = t * t;
		    tCubed = tSquared * t;
		 
		    double x = ((ax * tCubed) + (bx * tSquared) + (cx * t) + cp0.getX());
		    double y = ((ay * tCubed) + (by * tSquared) + (cy * t) + cp0.getY());
		 
		    return new Point2D.Double(x, y);
	 }
	 
	 public void doBezier(double t)
	 {
		 trazoActual = trazoTemporalBezier;
		 trazoTemporalBezier = null;
		 if(trazoActual!=null)
		 {		
			 buffer.remove(trazoActual);
			trazo = new Trazo(currentColor);
			//int en =0;
			double   dt;
		    int   i, numberOfPoints=trazoActual.getLongitud();
		 
		    dt = 1.0 / ( numberOfPoints - 1 );
		 
		    for( i = 0; i < numberOfPoints; i++)
		    {
		    	Point2D nuevP = calcBezierPoint(i*dt);
		    	trazo.addPunto(new Point2D.Double(nuevP.getX(),
						nuevP.getY()));
		    }
			trazoActual = trazo;
			buffer.add(trazoActual);
	 	}
		 /*nuevoTrazo.addPunto(new Point2D.Double(calcBezierPoint(t).getX(),
						calcBezierPoint(t).getY()));*/
	 }
	 
	 public void actualizaDatosParaBezier()
	 {
		 int ult = trazoTemporalBezier.getLongitud() - 1;
			
			cp0 = new Point2D.Double(
					trazoTemporalBezier.getPunto(0).getX(),
					trazoTemporalBezier.getPunto(0).getY());
			cp3 = new Point2D.Double(trazoTemporalBezier.getPunto(ult).getX(),
					trazoTemporalBezier.getPunto(ult).getY());
			//imp("cp0,cp3:"+cp0+","+cp3);
	 }
	 
	 public void escalar(double esc)
	 {
		 if(trazoActual!=null)
		 {	
			 cuentaPuntosParaPolygs=0;
			 puntos.clear();
			 buffer.remove(trazoActual);
			 trazo = new Trazo(currentColor);
		 
			
			for(Point2D p : trazoActual.getConjuntoDePuntos())
			{
				trazo.addPunto(new Point2D.Double(p.getX()*esc, 
						p.getY()*esc));
			}
			trazoActual = trazo;
			buffer.add(trazoActual);
			repaint();
	 	}
	 }
	 
	 public void tracePolygono()
	 {
		 if(cuentaPuntosParaPolygs<3)
			 JOptionPane.showMessageDialog(this, 
					 "Toca al menos 3 puntos en pantalla", "Error",
					 JOptionPane.ERROR_MESSAGE);
		 else
		 {
			 Point temp = null; //para inicializar
			 Trazo trazoPoli = new Trazo(currentColor);
			 cuentaPuntosParaPolygs =0;
			 
			 for(int i=0; i< puntos.size(); i++)
			 {
				 switch(i)
				 {
					 case 0:
						 xini = puntos.get(i).x;
						 yini = puntos.get(i).y;
						 break;
					 case 1:
						 temp = puntos.get(i);
						 xfin = temp.x;
						 yfin = temp.y;
						 doBresenham();
						 trazoPoli.addAll(trazo.getConjuntoDePuntos());
						 break;
					 case 2: //a partir de aqui!!!
						 if(i==puntos.size()-1)
						 {
							 xini = temp.x;
							 yini = temp.y;
							 xfin = puntos.get(i).x;
							 yfin = puntos.get(i).y;
							 doBresenham();
							 trazoPoli.addAll(trazo.getConjuntoDePuntos());
							 xini = xfin;
							 yini = yfin;
							 xfin = puntos.get(0).x;
							 yfin = puntos.get(0).y;
							 doBresenham();
							 trazoPoli.addAll(trazo.getConjuntoDePuntos());
							 break; //!!!!
						 }
						 else
						 { 
							 /*xini y yini son el ultimo del anterior*/
							 xini = temp.x;
							 yini = temp.y;
							 xfin = puntos.get(i).x;
							 yfin = puntos.get(i).y;
							 doBresenham();
							 trazoPoli.addAll(trazo.getConjuntoDePuntos());
							 temp = puntos.get(i); //actualiza el ultimo punto!!
							 break;
						 }
					 case 3: //4to punto
						 if(i==puntos.size()-1)
						 {
							 xini = temp.x;
							 yini = temp.y;
							 xfin = puntos.get(i).x;
							 yfin = puntos.get(i).y;
							 doBresenham();
							 trazoPoli.addAll(trazo.getConjuntoDePuntos());
							 xini = xfin;
							 yini = yfin;
							 xfin = puntos.get(0).x;
							 yfin = puntos.get(0).y;
							 doBresenham();
							 trazoPoli.addAll(trazo.getConjuntoDePuntos());
							 break; //!!!!
						 }
						 else
						 { 
							 /*xini y yini son el ultimo del anterior*/
							 xini = temp.x;
							 yini = temp.y;
							 xfin = puntos.get(i).x;
							 yfin = puntos.get(i).y;
							 doBresenham();
							 trazoPoli.addAll(trazo.getConjuntoDePuntos());
							 temp = puntos.get(i); //actualiza el ultimo punto!!
							 break;
						 }
						 
					 case 4:
						 if(i==puntos.size()-1)
						 {
							 xini = temp.x;
							 yini = temp.y;
							 xfin = puntos.get(i).x;
							 yfin = puntos.get(i).y;
							 doBresenham();
							 trazoPoli.addAll(trazo.getConjuntoDePuntos());
							 xini = xfin;
							 yini = yfin;
							 xfin = puntos.get(0).x;
							 yfin = puntos.get(0).y;
							 doBresenham();
							 trazoPoli.addAll(trazo.getConjuntoDePuntos());
							 break; //!!!!
						 }
						 else
						 { 
							 /*xini y yini son el ultimo del anterior*/
							 xini = temp.x;
							 yini = temp.y;
							 xfin = puntos.get(i).x;
							 yfin = puntos.get(i).y;
							 doBresenham();
							 trazoPoli.addAll(trazo.getConjuntoDePuntos());
							 temp = puntos.get(i); //actualiza el ultimo punto!!
							 break;
						 }
					 case 5:
						 if(i==puntos.size()-1)
						 {
							 xini = temp.x;
							 yini = temp.y;
							 xfin = puntos.get(i).x;
							 yfin = puntos.get(i).y;
							 doBresenham();
							 trazoPoli.addAll(trazo.getConjuntoDePuntos());
							 xini = xfin;
							 yini = yfin;
							 xfin = puntos.get(0).x;
							 yfin = puntos.get(0).y;
							 doBresenham();
							 trazoPoli.addAll(trazo.getConjuntoDePuntos());
							 break; //!!!!
						 }
						 else
						 { 
							 /*xini y yini son el ultimo del anterior*/
							 xini = temp.x;
							 yini = temp.y;
							 xfin = puntos.get(i).x;
							 yfin = puntos.get(i).y;
							 doBresenham();
							 trazoPoli.addAll(trazo.getConjuntoDePuntos());
							 temp = puntos.get(i); //actualiza el ultimo punto!!
							 break;
						 }
					 case 6:
						 if(i==puntos.size()-1)
						 {
							 xini = temp.x;
							 yini = temp.y;
							 xfin = puntos.get(i).x;
							 yfin = puntos.get(i).y;
							 doBresenham();
							 trazoPoli.addAll(trazo.getConjuntoDePuntos());
							 xini = xfin;
							 yini = yfin;
							 xfin = puntos.get(0).x;
							 yfin = puntos.get(0).y;
							 doBresenham();
							 trazoPoli.addAll(trazo.getConjuntoDePuntos());
							 break; //!!!!
						 }
						 else
						 { 
							 /*xini y yini son el ultimo del anterior*/
							 xini = temp.x;
							 yini = temp.y;
							 xfin = puntos.get(i).x;
							 yfin = puntos.get(i).y;
							 doBresenham();
							 trazoPoli.addAll(trazo.getConjuntoDePuntos());
							 temp = puntos.get(i); //actualiza el ultimo punto!!
							 break;
						 }
					 
						 default:
							 xini = temp.x;
							 yini = temp.y;
							 xfin = puntos.get(i).x;
							 yfin = puntos.get(i).y;
							 doBresenham();
							 trazoPoli.addAll(trazo.getConjuntoDePuntos());
							 xini = xfin;
							 yini = yfin;
							 xfin = puntos.get(0).x;
							 yfin = puntos.get(0).y;
							 doBresenham();
							 trazoPoli.addAll(trazo.getConjuntoDePuntos());	
							 break;
				}//fin switch
				 
			 }//fin for
			 trazoActual = trazoPoli;
			 trazoPoli = null;
			 buffer.add(trazoActual);
			 puntos.clear();
			 repaint();
		 }
	 }
	 
	 public int clearTheCanvas()
	 {
		 int resp = 
				 JOptionPane.showConfirmDialog(this,
						 "Estas seguro de borrar todos\n" +
						 "los elementos en pantalla? ...");
		 if(resp == JOptionPane.OK_OPTION)
		 {
			 buffer.clear();
			 puntos.clear();
			 trazoActual = null;
			 trazo = null;
			 repaint();
			 
		 }
		 return resp;
	 }
	 
	 /*public void imp(String t)
	 {
		 System.out.println(t);
	 }*/
	 
	 public void fill()
	 {
		 cuentaPuntosParaPolygs=0;
		 puntos.clear();
		 
		 int numIntr=0;
		 ArrayList<Point2D> puntosTemporales = new ArrayList<Point2D>();
		 trazo = new Trazo(currentColor);
		 trazoFronterizo = trazoActual;
		 trazo = trazoActual;
		 
		 if(trazoActual!=null)
		 { 
			 //recorrer pantalla:
			 buffer.remove(trazoActual);
			 
			 for(int j = 0; j<HEIGHT; j++)
			 {
				 numIntr=0; puntosTemporales.clear();
				 
				 for(int i=0; i<WIDTH; i++)
				 {
					 if(intersectaFrontera(new Point2D.Double(i,j)))
						 numIntr++;
					 
					 switch(numIntr)
					 {
						 case 1:
							 puntosTemporales.add(new Point2D.Double(i,j));
							 break;
						 case 2:
							 numIntr=0;
							 trazo.addAll(puntosTemporales);
							 puntosTemporales.clear();
							 break;
					 }
				 }
			 }//fin recorrido pantalla
			 /*
			 for(int j = 0; j<WIDTH; j++)
			 {
				 numIntr=0; puntosTemporales.clear();
				 
				 for(int i=0; i<HEIGHT; i++)
				 {
					 if(intersectaFrontera(new Point2D.Double(i,j)))
						 numIntr++;
					 
					 switch(numIntr)
					 {
						 case 1:
							 puntosTemporales.add(new Point2D.Double(i,j));
							 break;
						 case 2:
							 numIntr=0;
							 trzRell.addAll(puntosTemporales);
							 puntosTemporales.clear();
							 break;
					 }
				 }
			 }*/
			 
			 trazoActual = trazo;
			 buffer.add(trazoActual);
			 puntosTemporales = null; 
			 trazo = null;
			 trazoFronterizo = null;
			 trazoActual=null;
			 repaint();
		 }
			 
	 }
	 
	 public boolean intersectaFrontera(Point2D pu)
	 {
		 for(Point2D p : trazoFronterizo.getConjuntoDePuntos())
		 {
			 if((int)p.getX() == (int)pu.getX() && (int)p.getY() == (int)pu.getY()) /****!!!****/
				 return true;
		 }
		 
		 return false;
		 
	 }
	 
	 public void mensErrorRelleno()
	 {
		 JOptionPane.showMessageDialog(this, "toca el interior de la figura",
		 		"Error", JOptionPane.ERROR_MESSAGE);
	 }
	 
	 public void mensErrorRelleno(String m)
	 {
		 JOptionPane.showMessageDialog(this, m,
		 		"Error", JOptionPane.ERROR_MESSAGE);
	 }
	 
}
