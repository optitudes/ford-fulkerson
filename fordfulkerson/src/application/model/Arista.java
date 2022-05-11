package application.model;


import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class Arista {
	Vertice nodoEmisor;
	Vertice nodoReceptor;
	String id;
	int orden;
	TextField cajaTexto;
	Separator arista;
	ImageView flecha1;
	ImageView flecha2;
	Integer cantActual;
	Integer cantMaxPermitida;


	public Arista(String id, TextField cajaTexto, Separator arista, ImageView flecha1, ImageView flecha2) {
		super();
		this.id = id;
		this.cajaTexto = cajaTexto;
		this.arista = arista;
		this.flecha1 = flecha1;
		this.flecha2 = flecha2;
		this.nodoReceptor=null;
		this.nodoEmisor=null;
		cantActual=0;
		cantMaxPermitida=0;
	}

	
	
	public Vertice getNodoEmisor() {
		return nodoEmisor;
	}



	public void setNodoEmisor(Vertice nodoEmisor) {
		this.nodoEmisor = nodoEmisor;
	}



	public void setOrden(int orden) {
		this.orden = orden;
	}



	public Vertice getNodoReceptor() {
		return nodoReceptor;
	}



	public void setNodoReceptor(Vertice nodoReceptor) {
		this.nodoReceptor = nodoReceptor;
	}



	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}

	public TextField getCajaTexto() {
		return cajaTexto;
	}
	public void setCajaTexto(TextField cajaTexto) {
		this.cajaTexto = cajaTexto;
	}
	public Separator getArista() {
		return arista;
	}
	public void setArista(Separator arista) {
		this.arista = arista;
	}
	public ImageView getFlecha1() {
		return flecha1;
	}
	public void setFlecha1(ImageView flecha1) {
		this.flecha1 = flecha1;
	}
	public ImageView getFlecha2() {
		return flecha2;
	}
	public void setFlecha2(ImageView flecha2) {
		this.flecha2 = flecha2;
	}


	public Integer getCantActual() {
		return cantActual;
	}



	public void setCantActual(Integer cantActual) {
		this.cantActual = cantActual;
	}



	public Integer getCantMaxPermitida() {
		return cantMaxPermitida;
	}



	public void setCantMaxPermitida(Integer cantMaxPermitida) {
		this.cantMaxPermitida = cantMaxPermitida;
	}



	@Override
	public String toString() {
		return "Arista [nodoReceptor=" + nodoReceptor + ", id=" + id + ", cajaTexto=" + cajaTexto + ", arista=" + arista
				+ ", flecha1=" + flecha1 + ", flecha2=" + flecha2 + "]";
	}



	public void setInvisible() {
	cajaTexto.setVisible(false);
	arista.setVisible(false);
	flecha1.setVisible(false);
	flecha2.setVisible(false);
	}



	public boolean validarClase(char claseSalida, char claseLLegada) {

		if(id.equalsIgnoreCase(""+claseSalida+claseLLegada) ||id.equalsIgnoreCase(""+claseLLegada+claseSalida))
			return true;
		return false;
	}



	public int getOrden() {
		return orden;
	}



	public void setOrden(Character a, Character b) {
		if(a.compareTo(b)>=0){this.orden=-1;}else{this.orden=1;}
		System.out.println("orden :"+orden+" clase"+id);
	}



	
	public void setVisible() {
		cajaTexto.setVisible(true);
		cajaTexto.setText("["+cantMaxPermitida+"] "+cantActual);
		arista.setVisible(true);
	if(this.orden>=0){
		flecha1.setVisible(true);
		flecha2.setVisible(false);

	}else{
			
		flecha1.setVisible(false);
		flecha2.setVisible(true);
	}
	}



	public boolean validarVerticeReceptor(char clase) {
		if(nodoReceptor.validarClase(clase))
			return true;
		return false;
	}



	public boolean validarVerticeEmisor(char clase) {
		if(nodoEmisor.validarClase(clase))
			return true;
		return false;
	}



	public boolean isFull() {
		if(cantActual==cantMaxPermitida)
			return true;
		return false;
	}



	public boolean isEmpty() {
		if(cantActual==0)
			return true;
		return false;
	}



	public Integer obtenerVariacionPositivaMax() {
		return cantMaxPermitida-cantActual;
	}



	public void variarFlujo(int variacion) {
		if(orden>0){cantActual+=variacion;}
		else{cantActual-=variacion;}
		
	}




}
