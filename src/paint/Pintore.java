package paint;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

class View extends JFrame
{
	private JPanel panelBotones;
	private JPanel cajaColores;
	private DrawablePanel panelDibujo;
	private ButtonGroup opcionesColores;
	private ButtonGroup opcionesTrazado;
	private JRadioButton radioBRojo;
	private JRadioButton radioBNegro;
	private JRadioButton radioBAzul;
	private JRadioButton radioBVerde;
	private JRadioButton radioBRosa;
	private JRadioButton radioBNaranja;
	private JRadioButton radioBMagenta;
	private JRadioButton radioBCian;
	private JRadioButton radioBBlanco;
	private JRadioButton radioBAmarillo;
	private JRadioButton radioModoLinRecta;
	private JRadioButton radioModoCirculo;
	private JRadioButton radioModoLibre;
	private JRadioButton radioModoBezier;
	private JRadioButton radioModoPolygon;
	private JRadioButton radioModoRelleno;
	private Box cajita1, cajita2, cajita3, cajita4, cajita5;
	private JButton deshacerButton;
	private JButton borrarTodoButton;
	private JButton rellenaButton;
	private JButton rotarButton;
	private JButton aumTamButton;
	private JButton dismTamButton;
	private JButton tracePolygButton;
	//private Color currentColor;
	
	public View()
	{
		super("A Paint by: GLC");
		
		cajita1 = Box.createHorizontalBox();
		cajita2 = Box.createHorizontalBox();
		cajita3 = Box.createHorizontalBox();
		cajita4 = Box.createHorizontalBox();
		cajita5 = Box.createHorizontalBox();
		cajaColores = new JPanel();
		deshacerButton = new JButton("Borrar");
		borrarTodoButton = new JButton("Borrar todo!");
		rellenaButton = new JButton("Rellena");
		rotarButton = new JButton("Rotar");
		tracePolygButton = new JButton("Trazar Poligono");
		aumTamButton = new JButton("+");
		dismTamButton = new JButton("-");
		radioBNegro = new JRadioButton("Negro", true);
		//currentColor = Color.black;
		radioBAmarillo = new JRadioButton("Amarillo", false);
		radioBAzul = new JRadioButton("Azul", false);
		radioBBlanco = new JRadioButton("Blanco", false);
		radioBRojo = new JRadioButton("Rojo", false);
		radioBVerde = new JRadioButton("Verde", false);
		radioBRosa= new JRadioButton("Rosa", false);
		radioBNaranja = new JRadioButton("Naranja", false);
		radioBMagenta= new JRadioButton("Magenta", false);
		radioBCian = new JRadioButton("Cian", false);
		radioModoLinRecta = new JRadioButton("LineaRecta", false);
		radioModoCirculo = new JRadioButton("Circulo", false);
		radioModoLibre = new JRadioButton("Libre", true);
		radioModoBezier = new JRadioButton("Curva",false);
		radioModoPolygon = new JRadioButton("Poligono",false);
		radioModoRelleno = new JRadioButton("Relleno",false);
		
		
		panelBotones = new JPanel();
		panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.Y_AXIS));
		panelDibujo = new DrawablePanel();
		opcionesColores = new ButtonGroup();
		
		cajita2.add(radioModoLinRecta);
		cajita2.add(Box.createHorizontalGlue());
		cajita2.add(radioModoCirculo);
		cajita2.add(Box.createHorizontalGlue());
		cajita2.add(radioModoLibre);
		cajita2.add(Box.createHorizontalGlue());
		cajita2.add(radioModoBezier);
		cajita2.add(Box.createHorizontalGlue());
		cajita2.add(radioModoPolygon);
		/*cajita2.add(Box.createHorizontalGlue());
		cajita2.add(radioModoRelleno);*/
		
		/*cajita3.add(rellenaButton);
		cajita3.add(Box.createHorizontalGlue());*/
		cajita3.add(aumTamButton);
		cajita3.add(Box.createHorizontalGlue());
		cajita3.add(dismTamButton);
		cajita3.add(Box.createHorizontalGlue());
		cajita3.add(rotarButton);
		cajita3.add(Box.createHorizontalGlue());
		cajita3.add(tracePolygButton);
		cajita3.add(Box.createHorizontalGlue());
		cajita3.add(rellenaButton);
		
		cajita4.add(deshacerButton);
		cajita4.add(Box.createHorizontalGlue());
		cajita4.add(borrarTodoButton);
		
		
		cajita1.add(radioBAmarillo);
		cajita1.add(Box.createHorizontalGlue());
		cajita1.add(radioBAzul);
		cajita1.add(Box.createHorizontalGlue());
		cajita1.add(radioBBlanco);
		cajita1.add(Box.createHorizontalGlue());
		cajita1.add(radioBBlanco);
		cajita1.add(Box.createHorizontalGlue());
		cajita1.add(radioBNegro);
		cajita1.add(Box.createHorizontalGlue());
		cajita1.add(radioBRojo);
		/*cajita1.add(Box.createHorizontalGlue());
		cajita1.add(radioBVerde);*/
		//cajita1.add(cajita1);
		
		cajita5.add(radioBVerde);
		cajita5.add(Box.createHorizontalGlue());
		cajita5.add(radioBRosa);
		cajita5.add(Box.createHorizontalGlue());
		cajita5.add(radioBNaranja);
		cajita5.add(Box.createHorizontalGlue());
		cajita5.add(radioBMagenta);
		cajita5.add(Box.createHorizontalGlue());
		cajita5.add(radioBCian);
		
		
		cajaColores.setLayout(new BoxLayout(cajaColores, BoxLayout.Y_AXIS));
		cajaColores.add(cajita1);
		cajaColores.add(cajita5);
		cajaColores.setBorder(BorderFactory.createTitledBorder(
				" Selecciona un color: "));
		
		opcionesColores.add(radioBAmarillo);
		opcionesColores.add(radioBAzul);
		opcionesColores.add(radioBBlanco);
		opcionesColores.add(radioBNegro);
		opcionesColores.add(radioBRojo);
		opcionesColores.add(radioBVerde);
		opcionesColores.add(radioBNaranja);
		opcionesColores.add(radioBRosa);
		opcionesColores.add(radioBMagenta);
		opcionesColores.add(radioBCian);
		
		opcionesTrazado = new ButtonGroup();
		opcionesTrazado.add(radioModoLinRecta);
		opcionesTrazado.add(radioModoCirculo);
		opcionesTrazado.add(radioModoLibre);
		opcionesTrazado.add(radioModoBezier);
		opcionesTrazado.add(radioModoPolygon);
		opcionesTrazado.add(radioModoRelleno);
		
		
		panelBotones.add(cajaColores);
		panelBotones.add(cajita2);
		panelBotones.add(cajita3);
		panelBotones.add(cajita4);
		
		
		panelBotones.setBorder(BorderFactory.createEtchedBorder());
		
		
		
		add(panelDibujo, BorderLayout.CENTER);
	    add(panelBotones, BorderLayout.SOUTH);
	    
	    /**Eventos**/
	    radioBAmarillo.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				
				panelDibujo.setCurrentColor(Color.yellow);
				
			}
		});
	    
	    radioBAzul.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				panelDibujo.setCurrentColor(Color.blue);				
			}
		});
	    
	    radioBBlanco.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				panelDibujo.setCurrentColor(Color.white);
				
			}
		});
	    
	    radioBNegro.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				panelDibujo.setCurrentColor(Color.black);
				
			}
		});
	    
	    radioBRojo.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				panelDibujo.setCurrentColor(Color.red);
				
			}
		});
	    
	    radioBVerde.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				panelDibujo.setCurrentColor(Color.green);
				
			}
		});
	    
	    radioBNaranja.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				panelDibujo.setCurrentColor(Color.orange);
				
			}
		});
	    
	    radioBRosa.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				panelDibujo.setCurrentColor(Color.pink);
				
			}
		});
	    
	    radioBMagenta.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				panelDibujo.setCurrentColor(Color.magenta);
				
			}
		});
	    
	    radioBCian.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				panelDibujo.setCurrentColor(Color.cyan);
				
			}
		});
	    
	    
	    
	    
	    
	    
	    radioModoLinRecta.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				if(radioModoLinRecta.isSelected())
				{
					panelDibujo.setModoLineaRecta(true);
					panelDibujo.setModoCirculo(false);
					panelDibujo.setModoLibre(false);
					panelDibujo.setModoBezier(false);
					panelDibujo.setModoPolygono(false);
					panelDibujo.setModoRelleno(false);
				}
				
			}
		});
	    
	    radioModoCirculo.addItemListener(new ItemListener() {
			
	    	@Override
			public void itemStateChanged(ItemEvent arg0) {
				if(radioModoCirculo.isSelected())
				{
					panelDibujo.setModoCirculo(true);
					panelDibujo.setModoLineaRecta(false);
					panelDibujo.setModoLibre(false);
					panelDibujo.setModoBezier(false);
					panelDibujo.setModoPolygono(false);
					panelDibujo.setModoRelleno(false);
				}
				
			}
		});
	    
	    radioModoLibre.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				if(radioModoLibre.isSelected())
				{
					panelDibujo.setModoLibre(true);
					panelDibujo.setModoLineaRecta(false);
					panelDibujo.setModoCirculo(false);
					panelDibujo.setModoBezier(false);
					panelDibujo.setModoPolygono(false);
					panelDibujo.setModoRelleno(false);
				}
			
			}
		});
	    
	    radioModoBezier.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				if(radioModoBezier.isSelected())
				{
					panelDibujo.setModoBezier(true);
					panelDibujo.setModoLineaRecta(false);
					panelDibujo.setModoCirculo(false);
					panelDibujo.setModoLibre(false);
					panelDibujo.setModoPolygono(false);
					panelDibujo.setModoRelleno(false);
				}
				
			}
		});
	    
	    radioModoPolygon.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				if(radioModoPolygon.isSelected())
				{
					panelDibujo.setModoPolygono(true);
					panelDibujo.setModoBezier(false);
					panelDibujo.setModoLineaRecta(false);
					panelDibujo.setModoCirculo(false);
					panelDibujo.setModoLibre(false);
					panelDibujo.setModoRelleno(false);
				}
			}
		});
	    
	    radioModoRelleno.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				if(radioModoRelleno.isSelected())
				{
					panelDibujo.setModoRelleno(true);
					panelDibujo.setModoPolygono(false);
					panelDibujo.setModoBezier(false);
					panelDibujo.setModoLineaRecta(false);
					panelDibujo.setModoCirculo(false);
					panelDibujo.setModoLibre(false);
				}
				
			}
		});
	    
	    deshacerButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				panelDibujo.del();
				
			}
		});
	    
	    rotarButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				panelDibujo.rotar();
				
			}
		});
	    
	    dismTamButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				panelDibujo.escalar(0.8);
				
			}
		});
	    
	    aumTamButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				panelDibujo.escalar(1.3);
				
			}
		});
	    
	    tracePolygButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(radioModoPolygon.isSelected())
					panelDibujo.tracePolygono();
				
			}
		});
	    
	    rellenaButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				panelDibujo.fill();
				
			}
		});
	    
	    borrarTodoButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int r = panelDibujo.clearTheCanvas();
				
				if(r==JOptionPane.OK_OPTION)
				{
					radioModoLibre.setSelected(true);
				}
				
			}
		});
	    
	    
	    
	    
	    
	}
}


public class Pintore {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		View panel= new View();
		panel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel.setSize(550, 500);
		panel.setVisible(true);

	}

}
