package br.com.jrsts.calculadora.View;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

import br.com.jrsts.calculadora.Model.Memoria;
import br.com.jrsts.calculadora.Model.MemoriaObserver;

@SuppressWarnings("serial")
public class Display extends JPanel implements MemoriaObserver {
	
	private final JLabel label;
	
	public Display() {
		Memoria.getInstancia().adicionarObserver(this);
		
		setBackground(new Color(46, 49, 50));
		label = new JLabel(Memoria.getInstancia().getTextAtual());
		label.setForeground(Color.WHITE);
		label.setFont(new Font("courier", Font.PLAIN, 30));
		
		
		setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 25));
		add(label);
		
	}

	@Override
	public void valorAlterado(String novoValor) {
		label.setText(novoValor);
		
	}
}
