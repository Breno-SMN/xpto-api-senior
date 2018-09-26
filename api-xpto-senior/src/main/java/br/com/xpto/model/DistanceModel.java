package br.com.xpto.model;

public class DistanceModel {
	
	private String cidadeInicial;
	
	private String cidadeFinal;
	
	private double distanciaKM;
	
	

	public DistanceModel(String cidadeInicial, String cidadeFinal, double distanciaKM) {
		super();
		this.cidadeInicial = cidadeInicial;
		this.cidadeFinal = cidadeFinal;
		this.distanciaKM = distanciaKM;
	}

	public String getCidadeInicial() {
		return cidadeInicial;
	}

	public void setCidadeInicial(String cidadeInicial) {
		this.cidadeInicial = cidadeInicial;
	}

	public String getCidadeFinal() {
		return cidadeFinal;
	}

	public void setCidadeFinal(String cidadeFinal) {
		this.cidadeFinal = cidadeFinal;
	}

	public double getDistanciaKM() {
		return distanciaKM;
	}

	public void setDistanciaKM(double distanciaKM) {
		this.distanciaKM = distanciaKM;
	}
	
	
	
	
}
