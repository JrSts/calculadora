package br.com.jrsts.calculadora.Model;

import java.util.ArrayList;
import java.util.List;


public class Memoria {
	
	private enum TipoComando {
		ZERAR, NUMERO, MULT, DIV, SOMA, SUB, VIRGULA, IGUAL, SINAL
	}

	private static Memoria instancia = new Memoria();
	private String textAtual = "";
	private String textoBuffer = "";
	private boolean substituir = false;
	private TipoComando ultimaOperacao = null;	
	private List<MemoriaObserver> observadores = new ArrayList<>();
	
	private Memoria() {}

	public String getTextAtual() {
		return textAtual.isEmpty() ? "0" : textAtual; 
	}
	
	public void adicionarObserver(MemoriaObserver o) {
		observadores.add(o);
	}

	public static Memoria getInstancia() {
		return instancia;
	}
	
	public void processarComando(String texto) {

		TipoComando tipoComando = detectarTipoComando(texto); 
		
		if (tipoComando == null) {
			return;
		} else if(tipoComando == TipoComando.ZERAR) {
			textAtual = "";
			textoBuffer = "";
			substituir = false;
			ultimaOperacao = null;
		} else if(tipoComando == TipoComando.SINAL && textAtual.contains("-")) {
			textAtual = textAtual.substring(1);
		} else if(tipoComando == TipoComando.SINAL && !textAtual.contains("-")) {
			textAtual = "-" + textAtual;
		}
		else if(tipoComando == TipoComando.NUMERO 
				|| tipoComando == TipoComando.VIRGULA) {
			textAtual = substituir ? texto : textAtual + texto;			
			substituir = false;
			
		} else {
			substituir = true;
			textAtual = obterResultadoOperacao();
			textoBuffer = textAtual;
			ultimaOperacao = tipoComando;
		}
		
		observadores.forEach(o -> o.valorAlterado(getTextAtual()));
	}

	private String obterResultadoOperacao() {
		if (ultimaOperacao == null || ultimaOperacao == TipoComando.IGUAL) {
			return textAtual;
		} 
		
		double resultado = 0;
		
		double numeroBuffer = Double.parseDouble(textoBuffer.replace(",", "."));
		double numeroAtual = Double.parseDouble(textAtual.replace(",", "."));
		
		if (ultimaOperacao == TipoComando.SOMA) {
			resultado = numeroBuffer + numeroAtual;
		} else if (ultimaOperacao == TipoComando.SUB) {
			resultado = numeroBuffer - numeroAtual;
		} else if (ultimaOperacao == TipoComando.MULT) {
			resultado = numeroBuffer * numeroAtual;
		} else if (ultimaOperacao == TipoComando.DIV) {
			resultado = numeroBuffer / numeroAtual;
		} 
		
		String resultadoString = Double.toString(resultado).replace(".", ",");
		
		boolean inteiro =  resultadoString.endsWith(",0");
		
		return inteiro ? resultadoString.replace(",0", "") : resultadoString;
	}

	private TipoComando detectarTipoComando(String texto) {
		if (texto.equals("0") && textAtual.isEmpty()) {
			return null;
		}

		try {
			Integer.parseInt(texto);
			return TipoComando.NUMERO;
		} catch (NumberFormatException e) {
			switch (texto) {
				case "AC":
					return TipoComando.ZERAR;
				case "*":
					return TipoComando.MULT;
				case "/":
					return TipoComando.DIV;
				case "-":
					return TipoComando.SUB;
				case "+":
					return TipoComando.SOMA;
				case "=":
					return TipoComando.IGUAL;
				case "±":
					return TipoComando.SINAL;
				case ",":
					if (!textAtual.contains(",")) {
						return TipoComando.VIRGULA;							
					}
			}	
			return null;
		}
	}
}