package application.controller;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import application.Main;
import application.exception.CantidadNodosInvalidaException;
import application.exception.ElementoNoEncontradoException;
import application.exception.ErrorNodoAgregarException;
import application.exception.ErrorObtenerNodoException;
import application.exception.FuenteNoEncontrada;
import application.exception.SumideroNoEncontrado;
import application.model.Arista;
import application.model.Utilidades;
import application.model.Vertice;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class PrincipalViewController implements Initializable{
	Main main;
	char[] clases={'A','B','C','D','E'};
	ArrayList<TextField> listaNodos= new ArrayList<TextField>();
	ArrayList<Vertice> listaVertices=new ArrayList<Vertice>();
	ArrayList<Arista> listaAristas= new ArrayList<Arista>();

    @FXML
    private TextField txtAC;

    @FXML
    private TextField txtAD;

    @FXML
    private TextField txtAB;

    @FXML
    private TextField txtAE;

    @FXML
    private ImageView imgCB;

    @FXML
    private ImageView imgCA;

    @FXML
    private Separator separatorCE;

    @FXML
    private Separator separatorCD;

    @FXML
    private ImageView imgCD;

    @FXML
    private ImageView imgCE;

    @FXML
    private TextField txtBD;

    @FXML
    private TextField txtBE;

    @FXML
    private TextField txtBC;

    @FXML
    private ImageView imgBA;

    @FXML
    private TextField txxtCantNodos;

    @FXML
    private ImageView imgBE;

    @FXML
    private Separator separatorDE;
    

    @FXML
    private ImageView imgBC;

    @FXML
    private ImageView imgBD;

    @FXML
    private TextField txtNodoA;

    @FXML
    private TextField txtNodoC;

    @FXML
    private TextField txtNodoB;

    @FXML
    private TextField txtCE;

    @FXML
    private TextField txtCD;

    @FXML
    private Button btnGenerarGrafo;

    @FXML
    private TextField txtNodoE;

    @FXML
    private TextField txtNodoD;

    @FXML
    private Separator separatorAE;

    @FXML
    private Separator separatorAD;

    @FXML
    private VBox vBoxVertices;

    @FXML
    private ImageView imgED;

    @FXML
    private ImageView imgEB;

    @FXML
    private ImageView imgEC;

    @FXML
    private Separator separatorAC;

    @FXML
    private ImageView imgAD;

    @FXML
    private Separator separatorAB;

    @FXML
    private ImageView imgAE;

    @FXML
    private ImageView imgAB;

    @FXML
    private ImageView imgAC;

    @FXML
    private ImageView imgEA;

    @FXML
    private TextField txtDE;

    @FXML
    private Separator separatorBE;

    @FXML
    private Button btnGenerarNodos;

    @FXML
    private ImageView imgDC;

    @FXML
    private ImageView imgDA;

    @FXML
    private ImageView imgDB;

    @FXML
    private Separator separatorBD;

    @FXML
    private Separator separatorBC;

    @FXML
    private ImageView imgDE;
    @FXML
    void generarNodosAction(ActionEvent event) {
    	try {
			generarNodos();
		} catch (CantidadNodosInvalidaException |NumberFormatException | ErrorObtenerNodoException e) {
			Utilidades.mostrarMensaje("Error", "Error al crear nodos",e.getMessage(),AlertType.ERROR);
		}

    }

    

	@FXML
    void generarGrafoAction(ActionEvent event) {
		try {
			algoritmoFordFulkerson();
			imprimirNodos();
			
		} catch (Exception e) {
			Utilidades.mostrarMensaje("Error", "Error al solucionar", e.getMessage()+"\n"+e.getCause(), AlertType.ERROR);
		}

    }

	private void algoritmoFordFulkerson() throws FuenteNoEncontrada, SumideroNoEncontrado, ErrorObtenerNodoException, ElementoNoEncontradoException {
		char fuente=obtenerFuente();
		char sumidero=obtenerSumidero();
		flujoNulo();
		solucionarAlgoritmo(fuente,sumidero);

	}



	private void solucionarAlgoritmo(char fuente, char sumidero) throws ErrorObtenerNodoException, FuenteNoEncontrada, ElementoNoEncontradoException {
		ArrayList<String> recorridosEfectuados=new ArrayList<>();
		ArrayList<String> recorridos=new ArrayList<>();
		ArrayList<Character> secuenciaNodos= new ArrayList<>();
		Vertice verticeFuente=obtenerVerticeTipo(fuente);
		secuenciaNodos.add(obtenerClaseUsandoTipo(fuente));
		obtenerRecorridoAumentativoBacktracking(verticeFuente,sumidero,secuenciaNodos,recorridos);
		eliminarRecorridosEfectuados(recorridos,recorridosEfectuados);
		while(recorridos.size()>0)
		{
			System.out.println();
			String recorridoSeleccionado=recorridos.get(0);
			int incremento=obtenerVariacionFlujo(recorridoSeleccionado);
			modificarFlujos(recorridoSeleccionado,incremento);
			
			
			recorridosEfectuados.add(recorridos.get(0));
			recorridos.clear();
			obtenerRecorridoAumentativoBacktracking(verticeFuente,sumidero,secuenciaNodos,recorridos);
			eliminarRecorridosEfectuados(recorridos,recorridosEfectuados);
		}
	}



	private void eliminarRecorridosEfectuados(ArrayList<String> recorridos, ArrayList<String> recorridosEfectuados) {
		for (String recorridoEfectuado : recorridosEfectuados) {
			if(recorridos.contains(recorridoEfectuado))
				recorridos.remove(recorridoEfectuado);
		}
		
	}



	private void modificarFlujos(String recorridoSeleccionado, int variacion) throws ElementoNoEncontradoException, ErrorObtenerNodoException {
		Vertice verticeAux=obtenerVerticeClase(recorridoSeleccionado.charAt(0));
		char[] listaVertices= recorridoSeleccionado.toCharArray();
		for (int i = 1; i < listaVertices.length; i++) {
			verticeAux.modificarFlujo(recorridoSeleccionado.charAt(i),variacion);
			verticeAux=obtenerVerticeClase(recorridoSeleccionado.charAt(i));
		}
	}



	private int obtenerVariacionFlujo(String recorridoSeleccionado) throws ErrorObtenerNodoException, ElementoNoEncontradoException {
		System.out.println();
		ArrayList<Integer> variacionesPermitidas=new ArrayList<>();
		Vertice verticeAux=obtenerVerticeClase(recorridoSeleccionado.charAt(0));
		char[] listaVertices= recorridoSeleccionado.toCharArray();
		for (int i = 1; i < listaVertices.length; i++) {
			variacionesPermitidas.addAll(verticeAux.obtenerVariacionPermitida(recorridoSeleccionado.charAt(i)));
			verticeAux=obtenerVerticeClase(recorridoSeleccionado.charAt(i));
		}
		Collections.sort(variacionesPermitidas);
		for (Integer i: variacionesPermitidas) {
			System.out.print("["+i+"]");
		}
		System.out.println();
		System.out.println("Variacion permitida "+variacionesPermitidas.get(0));
		return variacionesPermitidas.get(0);
	}



	


	private void obtenerRecorridoAumentativoBacktracking(Vertice verticeActual, char sumidero, ArrayList<Character> secuenciaNodos, ArrayList<String> recorridos) {
		if(verticeActual.validarTipo(sumidero)){recorridos.add(generarRecorridos(secuenciaNodos));}
		else{
			ArrayList<Arista> conexionesVerticeActualEmisoras=verticeActual.getListaAristasEmisoras();
			ArrayList<Arista> conexionesVerticeActualReceptoras=verticeActual.getListaAristasReceptoras();

			for (Arista arista : conexionesVerticeActualEmisoras) {
				if(validarNodoRecorrido(arista.getNodoEmisor().getClase(),secuenciaNodos) && !arista.isEmpty())
				{
				secuenciaNodos.add(arista.getNodoEmisor().getClase());
				obtenerRecorridoAumentativoBacktracking(arista.getNodoEmisor(), sumidero, secuenciaNodos, recorridos);
				secuenciaNodos.remove(arista.getNodoEmisor().getClase());
				}
			}

			for (Arista arista : conexionesVerticeActualReceptoras) {
				if(validarNodoRecorrido(arista.getNodoReceptor().getClase(),secuenciaNodos) && !arista.isFull())
				{
				secuenciaNodos.add(arista.getNodoReceptor().getClase());
				obtenerRecorridoAumentativoBacktracking(arista.getNodoReceptor(), sumidero, secuenciaNodos, recorridos);
				secuenciaNodos.remove(arista.getNodoReceptor().getClase());
				}
			}
		}



	}



	private boolean validarNodoRecorrido(Character character2, ArrayList<Character> secuenciaNodos) {
		for (Character character : secuenciaNodos) {
			if(character2==character)
				return false;
		}
		return true;
	}



	private String generarRecorridos(ArrayList<Character> secuenciaNodos) {
		String secuenciaNodosString="";
		for (Character character : secuenciaNodos) {
			secuenciaNodosString+=character;
		}
		System.out.println("ruta :"+secuenciaNodosString);
		return secuenciaNodosString;
	}



	private Vertice obtenerVerticeTipo(char tipo) throws ErrorObtenerNodoException {
		for (Vertice  v: listaVertices) {
			if(v.validarTipo(tipo))
				return v;
		}
		throw new ErrorObtenerNodoException("ocurrió un error al obtener el nodo  {method :obtenerVerticeTipo}");
	}

	private Vertice obtenerVerticeClase(char clase) throws ErrorObtenerNodoException {
		for (Vertice  v: listaVertices) {
			if(v.validarClase(clase))
				return v;
		}
		throw new ErrorObtenerNodoException("ocurrió un error al obtener el nodo  {method :obtenerVerticeClase}");
	}


	private char obtenerSumidero() throws SumideroNoEncontrado {
		for (Vertice v : listaVertices) {
			if(v.validarTipo('S'))
				return 'S';
		}
		throw new SumideroNoEncontrado("No se ha podido hallar un nodo sumidero");
	}



	private char obtenerFuente() throws FuenteNoEncontrada {
		for (Vertice v : listaVertices) {
			if(v.validarTipo('F'))
				return 'F';
		}
		throw new FuenteNoEncontrada("No se ha podido hallar un nodo fuente");
	}
	private char obtenerClaseUsandoTipo(char tipo) throws FuenteNoEncontrada {
		for (Vertice v : listaVertices) {
			if(v.validarTipo(tipo))
				return v.getClase();
		}
		throw new FuenteNoEncontrada("No se ha podido hallar el nodo de tipo:"+tipo);
	}


	private void flujoNulo() {
		for (Vertice v : listaVertices) {
			v.limpiarflujo();
			
		}
		
	}



	private void imprimirNodos() {
		for (Vertice v : listaVertices) {
			v.setVisible();
		}
		
	}



	public void initialize(URL arg0, ResourceBundle arg1) {
		configGrafo();
		configNodods();
		limpiarGrafo();
		
	}

	private void configNodods() {
		listaNodos.clear();
		txtNodoA.setText("A");
		listaNodos.add(txtNodoA);
		txtNodoB.setText("B");
		listaNodos.add(txtNodoB);
		txtNodoC.setText("C");
		listaNodos.add(txtNodoC);
		txtNodoD.setText("D");
		listaNodos.add(txtNodoD);
		txtNodoE.setText("E");
		listaNodos.add(txtNodoE);
		
	}



	private void configGrafo() {
		listaAristas.clear();
		
		listaAristas.add(new Arista("AB",txtAB, separatorAB, imgAB, imgBA));
		listaAristas.add(new Arista("AE",txtAE, separatorAE, imgAE, imgEA));
		listaAristas.add(new Arista("BE",txtBE, separatorBE, imgBE, imgEB));
		listaAristas.add(new Arista("AD",txtAD, separatorAD, imgAD, imgDA));
		listaAristas.add(new Arista("AC",txtAC, separatorAC, imgAC, imgCA));
		listaAristas.add(new Arista("BD",txtBD, separatorBD, imgBD, imgDB));
		listaAristas.add(new Arista("CE",txtCE, separatorCE, imgCE, imgEC));
		listaAristas.add(new Arista("BC",txtBC, separatorBC, imgBC, imgCB));
		listaAristas.add(new Arista("DE",txtDE, separatorDE, imgDE, imgED));
		listaAristas.add(new Arista("CD",txtCD, separatorCD, imgCD, imgDC));
		
	}



	private void limpiarGrafo() {
		
		for (Arista a: listaAristas) {
			a.setInvisible();
		}
		for (TextField n : listaNodos) {
			n.setVisible(false);
			
		}
		
	}



	public void setMain(Main main) {
		this.main=main;
		this.listaVertices=new ArrayList<Vertice>();
		
	}
	private void generarNodos() throws CantidadNodosInvalidaException, ErrorObtenerNodoException {

		vBoxVertices.getChildren().clear();
		int cantidad=obtenerCantidad();

		for (int i = 0; i < cantidad; i++) {
			Vertice vertice=new Vertice(clases[i]);
			Label   lbl=new Label();
			lbl.setText(clases[i]+"");

			lbl.setOnMouseClicked(e->{
				try {
					char tipo;
					char[] nodosAdyacentes;
					tipo=obtenerTipo();
					setDatosVertice(lbl.getText().charAt(0),tipo);
					nodosAdyacentes=obtenerNodosAdyacentes(lbl.getText().charAt(0));
					generarAristas(lbl.getText().charAt(0),nodosAdyacentes);
					
				} catch (Exception e2) {
					Utilidades.mostrarMensaje("Error", "Error al configurar el nodo",e2.getCause()+" mse:"+e2.getMessage(), AlertType.ERROR);
				}
			});		
			vertice.setLblNodo(lbl);
			vertice.setNodo(getNodoTxt(clases[i]));
			listaVertices.add(vertice);
			vBoxVertices.getChildren().add(lbl);

		}
		

	}



	private TextField getNodoTxt(char c) throws ErrorObtenerNodoException {
		for (TextField n : listaNodos) {
			if(n.getText().charAt(0)==c)
				return n;
		}
		throw new ErrorObtenerNodoException("Error al asignar el nodo txt al vertice");
	}



	private char obtenerTipo() {
		char tipo=JOptionPane.showInputDialog("Ingrese el tipo: fuente(F) intermedio(I) sumidero(S)").toUpperCase().charAt(0);
		return tipo ;
	}



	private void generarAristas(char claseRef, char[] nodosAdyacentes) throws ErrorNodoAgregarException, ElementoNoEncontradoException {


			for (Vertice v : listaVertices) {
				if(v.validarClase(claseRef))
				{
					for (int j = 0; j < nodosAdyacentes.length; j++) {

						for (Vertice v2 : listaVertices) {
							if(v2.validarClase(nodosAdyacentes[j])){
								v.agregarAristaReceptora((obtenerArista(claseRef,nodosAdyacentes[j],v2,v)));

								break;
							}

						}

					}
					break;
				}

			}

	}



	private Arista obtenerArista(char claseSalida, char claseLLegada, Vertice v2, Vertice v) throws ElementoNoEncontradoException, NumberFormatException{
		for (Arista a : listaAristas) {
			if(a.validarClase(claseSalida,claseLLegada)){
				a.setOrden(claseSalida, claseLLegada);
				a.setNodoReceptor(v2);
				a.setNodoEmisor(v);
				a.setCantMaxPermitida(Integer.parseInt(JOptionPane.showInputDialog("Ingrese la cantidad máxima para la arista ["+claseSalida+" | "+claseLLegada+"] ")));
				v2.agregarAristaEmisora(a);
				return a;
			}
			
		}
		throw new ElementoNoEncontradoException("Ocurrió un error al obtener la arista");
	}



	private char[] obtenerNodosAdyacentes(char c) {
		char[] clasesNodos;
		char[] clases={'A','B','C','D','E'};

		String nodosDisponibles="";
		for (int i = 0; i < clases.length; i++) {
			if(clases[i]!=c)
				nodosDisponibles+=clases[i]+"";
			
		}
		char[] nodos=JOptionPane.showInputDialog("Nodos disponibles :("+nodosDisponibles+")\nElija los nodos donde ["+c+" ]vierte sin espacios ni comas").toUpperCase().toCharArray();
		clasesNodos=nodos;
		return clasesNodos;

	}



	private void setDatosVertice(char clase, char tipo) {
		for (Vertice vertice : listaVertices) {
			if(vertice.validarClase(clase)){vertice.setClase(clase);vertice.setTipo(tipo);break;}
		}
	}

	private int obtenerCantidad() throws CantidadNodosInvalidaException {
		
		int cantidad=Integer.parseInt(txxtCantNodos.getText());
		if(cantidad<1 || cantidad>5)
			throw new CantidadNodosInvalidaException("La cantidad de nodos debe ser mayor o igual a 1 y menor o igual a 5");
		return cantidad;
	}



}
