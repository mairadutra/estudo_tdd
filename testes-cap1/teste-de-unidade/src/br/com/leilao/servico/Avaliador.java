package br.com.leilao.servico;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.management.RuntimeErrorException;

import br.com.leilao.dominio.Lance;
import br.com.leilao.dominio.Leilao;

public class Avaliador {
	
	private double mairorDeTodos = Double.NEGATIVE_INFINITY;
	private double menorDeTodos = Double.POSITIVE_INFINITY;
	private double mediaLance = Double.MIN_VALUE;
	private List<Lance> maiores;

	public void avalia(Leilao leilao){
		
		if (leilao.getLances().size() == 0){
			throw new RuntimeErrorException(null, "não é possível avaliar um leilao sem lances");
		}
		
		for (Lance lance: leilao.getLances()){
			
			if (lance.getValor() > mairorDeTodos)
				mairorDeTodos = lance.getValor();
			if (lance.getValor() < menorDeTodos )
				menorDeTodos = lance.getValor();
		}
		maiores = new ArrayList<Lance>(leilao.getLances());
		Collections.sort(maiores, new Comparator <Lance>() {
			
			public int compare (Lance o1, Lance o2){
				if (o1.getValor() < o2.getValor()) return 1;
				if (o1.getValor() > o2.getValor()) return -1;
					return 0;
			}
		});
		//começa no primeiro item da lista e  se tiver mais de 3 (?) pega 3, do contrario (:) pega o proprio tamanho da lista
		maiores = maiores.subList(0, maiores.size() > 3 ? 3 : maiores.size());
	}
	
	public void media(Leilao leilao){
		
		double numLances = 0;
		
		for (Lance lance: leilao.getLances()){
				
			mediaLance = mediaLance + lance.getValor();
			numLances = numLances + 1;
			
		}
		
		mediaLance = (mediaLance/numLances);
	
	}
	
	
	public List<Lance> getTresMaiores() {
		return maiores;
	}
	
	public double getMaiorLance() {
		return mairorDeTodos;
	}
	
	public double getMenorLance() {
		return menorDeTodos;
	}
	
	public double getMediaLance() {
		return mediaLance;
	}
	

}
