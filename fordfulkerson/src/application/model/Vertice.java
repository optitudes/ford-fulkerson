package application.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;

import application.exception.ElementoNoEncontradoException;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;

public class Vertice {
	Integer flujoIn;
	Integer flujoOut;
	Label lblNodo;
	TextField nodo; 
	Character clase;
	Character tipo;
	ArrayList<Arista> listaAristasReceptoras;
	ArrayList<Arista> listaAristasEmisoras;
	public Vertice(char clase,Integer maxPermitido) {
		super();
		this.clase=clase;
		this.listaAristasEmisoras= new ArrayList<>();
		this.listaAristasReceptoras= new ArrayList<>();
		flujoIn=0;
		flujoOut=0;
		
	}
	
	public Vertice(char clase) {
		super();
		this.clase = clase;
		this.listaAristasEmisoras= new ArrayList<>();
		this.listaAristasReceptoras= new ArrayList<>();
		flujoIn=0;
		flujoOut=0;

	}

	
	
	
	public Integer getFlujoIn() {
		return flujoIn;
	}

	public void setFlujoIn(Integer flujoIn) {
		this.flujoIn = flujoIn;
	}

	public Integer getFlujoOut() {
		return flujoOut;
	}

	public void setFlujoOut(Integer flujoOut) {
		this.flujoOut = flujoOut;
	}

	public TextField getNodo() {
		return nodo;
	}

	public void setNodo(TextField nodo) {
		this.nodo = nodo;
	}

	public char getTipo() {
		return tipo;
	}

	public void setTipo(Character tipo) {
		this.tipo = tipo;
	}

	public Label getLblNodo() {
		return lblNodo;
	}

	public void setLblNodo(Label lblNodo) {
		this.lblNodo = lblNodo;
	}

	public Character getClase() {
		return clase;
	}
	public void setClase(char clase) {
		this.clase = clase;
	}
	



	public void agregarAristaEmisora(Arista a){
		listaAristasEmisoras.add(a);
	}
	public void agregarAristaReceptora(Arista a){
		listaAristasReceptoras.add(a);

		
	}
	public boolean validarTipo(char tipoRef) {
		if(this.tipo==tipoRef)
			return true;
		return false;
	}

	public void setVisible() {
		
		nodo.setVisible(true);
		nodo.setText(clase+"");
		for (Arista arista : listaAristasReceptoras) {
			arista.setVisible();
			
		}
	}

	@Override
	public String toString() {
		return "Vertice [flujoIn=" + flujoIn + ", flujoOut=" + flujoOut + ", lblNodo=" + lblNodo + ", nodo=" + nodo
				+ ", clase=" + clase + ", tipo=" + tipo + ", listaAristasReceptoras=" + listaAristasReceptoras
				+ ", listaAristasEmisoras=" + listaAristasEmisoras + "]";
	}

	public void limpiarflujo() {
		for (Arista arista : listaAristasEmisoras) {
			arista.setCantActual(0);;
			
		}
		
	}

	public boolean validarClase(char claseRef) {
		if(this.clase==claseRef)
			return true;
		return false;
	}

	public Vertice getVertice(char clase) {

		for (Arista arista : listaAristasEmisoras) {
			if(arista.validarVerticeEmisor(clase))
				return arista.getNodoEmisor();
		}
		for (Arista arista : listaAristasReceptoras) {
			if(arista.validarVerticeReceptor(clase))
				return arista.getNodoReceptor();
		}
		
		return null;
	}

	public ArrayList<Arista> getListaAristasReceptoras() {
		return listaAristasReceptoras;
	}

	public void setListaAristasReceptoras(ArrayList<Arista> listaAristasReceptoras) {
		this.listaAristasReceptoras = listaAristasReceptoras;
	}

	public ArrayList<Arista> getListaAristasEmisoras() {
		return listaAristasEmisoras;
	}

	public void setListaAristasEmisoras(ArrayList<Arista> listaAristasEmisoras) {
		this.listaAristasEmisoras = listaAristasEmisoras;
	}

	public ArrayList<Integer> obtenerVariacionPermitida(char claseRef) throws ElementoNoEncontradoException {
		ArrayList<Integer> listaValores=new ArrayList<>();
		for (Arista arista : listaAristasEmisoras) {
			if(arista.getNodoEmisor().getClase()==claseRef){listaValores.add(arista.getCantActual());}
		}
		for (Arista arista : listaAristasReceptoras) {
			if(arista.getNodoReceptor().getClase()==claseRef){listaValores.add(arista.obtenerVariacionPositivaMax());}
		}
		
		return listaValores;

	}
	public void modificarFlujo(char claseRef, int variacion) throws ElementoNoEncontradoException {
		boolean flujoModificado=false;
		for (Arista arista : listaAristasEmisoras) {
			if(arista.getNodoEmisor().getClase()==claseRef){arista.variarFlujo(variacion);flujoModificado=true;}
		}
		for (Arista arista : listaAristasReceptoras) {
			if(arista.getNodoReceptor().getClase()==claseRef){arista.variarFlujo(variacion);flujoModificado=true;}
		}
		if(!flujoModificado)
			throw new ElementoNoEncontradoException("No se ha podido encontrar el elemento de la clase["+claseRef+"]"
					+ " asociado a la clase "+getClase());
	}


	
	


}
