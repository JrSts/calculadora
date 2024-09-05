package br.com.jrsts.calculadora.View;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Calculadora extends JFrame {
	
	public Calculadora() {
		
		organizarLayout();
		
		setSize(232, 322);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		
	}
	
	private void organizarLayout() {
		setLayout(new BorderLayout());
		
		Display display = new Display();
		add(display, BorderLayout.NORTH);
		display.setPreferredSize(new Dimension(233, 60));

		
		Teclado teclado = new Teclado();
		add(teclado);
		add(teclado, BorderLayout.CENTER);
		
		
	}

	public static void main(String[] args) {
		new Calculadora();
	}
}
