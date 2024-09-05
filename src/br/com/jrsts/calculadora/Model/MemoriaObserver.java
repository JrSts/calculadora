package br.com.jrsts.calculadora.Model;

@FunctionalInterface
public interface MemoriaObserver {
	
	void valorAlterado(String novoValor);
	
}
