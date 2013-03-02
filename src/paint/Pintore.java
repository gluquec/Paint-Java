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
import javax.swing.JSeparator;

class View extends JFrame
{
	private JPanel panelDeControl, panelBotonesBorrado, panelColores;
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
	private Box cajaColores1, cajaColores2, cajitaModos, cajitaAcciones,
		cajitaBotonesBorrado;
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
		
		cajaColores1 = Box.createHorizontalBox();
		cajaColores2 = Box.createHorizontalBox();
		cajitaModos = Box.createHorizontalBox();
		cajitaAcciones = Box.createHorizontalBox();
		cajitaBotonesBorrado = Box.createVerticalBox();
	
		panelDeControl = new JPanel();
		panelDeControl.setLayout(new BoxLayout(panelDeControl,
				BoxLayout.Y_AXIS));
		panelColores = new JPanel();
		panelColores.setLayout(new BoxLayout(panelColores, BoxLayout.Y_AXIS));
		panelBotonesBorrado = new JPanel(new BorderLayout(5,5));
		panelDibujo = new DrawablePanel();
		
		deshacerButton = new JButton("Borrar");
		borrarTodoButton = new JButton("Borrar todo!");
		rellenaButton = new JButton("Rellena");
		rotarButton = new JButton("Rotar");
		tracePolygButton = new JButton("Trazar Poligono");
		aumTamButton = new JButton("+");
		dismTamButton = new JButton("-");
		radioBNegro = new JRadioButton("Negro", true);
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
		opcionesColores = new ButtonGroup();
		opcionesTrazado = new ButtonGroup();
		
		cajitaModos.add(radioModoLinRecta);
		cajitaModos.add(Box.createHorizontalGlue());
		cajitaModos.add(radioModoCirculo);
		cajitaModos.add(Box.createHorizontalGlue());
		cajitaModos.add(radioModoLibre);
		cajitaModos.add(Box.createHorizontalGlue());
		cajitaModos.add(radioModoBezier);
		cajitaModos.add(Box.createHorizontalGlue());
		cajitaModos.add(radioModoPolygon);
		
		cajitaAcciones.add(aumTamButton);
		cajitaAcciones.add(Box.createHorizontalGlue());
		cajitaAcciones.add(dismTamButton);
		cajitaAcciones.add(Box.createHorizontalGlue());
		cajitaAcciones.add(rotarButton);
		cajitaAcciones.add(Box.createHorizontalGlue());
		cajitaAcciones.add(tracePolygButton);
		cajitaAcciones.add(Box.createHorizontalGlue());
		cajitaAcciones.add(rellenaButton);
		
		cajitaBotonesBorrado.add(deshacerButton);
		cajitaBotonesBorrado.add(Box.createVerticalGlue());
		cajitaBotonesBorrado.add(borrarTodoButton);
		cajitaBotonesBorrado.add(Box.createVerticalGlue());
		
		cajaColores1.add(radioBAmarillo);
		cajaColores1.add(Box.createHorizontalGlue());
		cajaColores1.add(radioBAzul);
		cajaColores1.add(Box.createHorizontalGlue());
		cajaColores1.add(radioBBlanco);
		cajaColores1.add(Box.createHorizontalGlue());
		cajaColores1.add(radioBBlanco);
		cajaColores1.add(Box.createHorizontalGlue());
		cajaColores1.add(radioBNegro);
		cajaColores1.add(Box.createHorizontalGlue());
		cajaColores1.add(radioBRojo);
		
		cajaColores2.add(radioBVerde);
		cajaColores2.add(Box.createHorizontalGlue());
		cajaColores2.add(radioBRosa);
		cajaColores2.add(Box.createHorizontalGlue());
		cajaColores2.add(radioBNaranja);
		cajaColores2.add(Box.createHorizontalGlue());
		cajaColores2.add(radioBMagenta);
		cajaColores2.add(Box.createHorizontalGlue());
		cajaColores2.add(radioBCian);
		
		panelColores.add(cajaColores1);
		panelColores.add(cajaColores2);
		panelColores.setBorder(BorderFactory.createTitledBorder(
				" Selecciona un color: "));
		
		panelBotonesBorrado.add(cajitaBotonesBorrado, BorderLayout.WEST);
		
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
		
		
		opcionesTrazado.add(radioModoLinRecta);
		opcionesTrazado.add(radioModoCirculo);
		opcionesTrazado.add(radioModoLibre);
		opcionesTrazado.add(radioModoBezier);
		opcionesTrazado.add(radioModoPolygon);
		opcionesTrazado.add(radioModoRelleno);
		
		panelDeControl.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		panelDeControl.add(Box.createVerticalStrut(5));
		panelDeControl.add(panelColores);
		panelDeControl.add(Box.createVerticalStrut(5));
		panelDeControl.add(cajitaModos);
		panelDeControl.add(Box.createVerticalStrut(5));
		panelDeControl.add(new JSeparator());
		panelDeControl.add(cajitaAcciones);
		panelDeControl.add(Box.createVerticalStrut(5));
		panelDeControl.add(new JSeparator());
		panelDeControl.add(panelBotonesBorrado);
		panelDeControl.add(Box.createVerticalStrut(5));
		
		panelDeControl.setBorder(
				BorderFactory.createTitledBorder(" Panel de Control: "));
		
		add(panelDibujo, BorderLayout.CENTER);
	    add(panelDeControl, BorderLayout.SOUTH);
	    
	    
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

	public static void main(String[] args) {
		
		View panel= new View();
		panel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel.setSize(650, 600);
		panel.setResizable(false);
		panel.setVisible(true);

	}
}
