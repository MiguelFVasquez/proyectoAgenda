package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import application.Aplicacion;
import co.uniquindio.p2.agenda.exceptions.AgendaException;
import co.uniquindio.p2.agenda.exceptions.ContactoException;
import co.uniquindio.p2.agenda.exceptions.GrupoException;
import co.uniquindio.p2.agenda.exceptions.ReunionException;
import co.uniquindio.p2.agenda.model.Agenda;
import co.uniquindio.p2.agenda.model.Categoria;
import co.uniquindio.p2.agenda.model.Contacto;
import co.uniquindio.p2.agenda.model.Grupo;
import co.uniquindio.p2.agenda.model.Reunion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ContactoController implements Initializable{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
//Tabla de contactos
    @FXML
    private TableView<Contacto> tableViewContactos;

    @FXML
    private TableColumn<Contacto, String> columNombre;

    @FXML
    private TableColumn<Contacto, String> columTelefono;

    @FXML
    private TableColumn<Contacto, String> columAlias;

    @FXML
    private TableColumn<Contacto, String> columEmail;

    @FXML
    private TableColumn<Contacto, String> columDireccion;
//Tabla de grupos

    @FXML
    private TableView<Grupo> tableViewGrupos;

    @FXML
    private TableColumn<Grupo, String> columNombreGrupo;

    @FXML
    private TableColumn<Grupo, Categoria> columCategoria;

//Tabla de reuniones
    @FXML
    private TableView<Reunion> tableViewReuniones;
    @FXML
    private TableColumn<Reunion, String> columDescripcion;
    @FXML
    private TableColumn<Reunion, String> columFecha;
    @FXML
    private TableColumn<Reunion, String> columHora;

    @FXML
    private TextField txtDescripcion;

    @FXML
    private TextField txtFecha;

    @FXML
    private TextField txtHora;

    @FXML
    private Button btnEliminarReunion;
    @FXML
    private Button btnLimpiar2;

    @FXML
    private Button btnCrear;

    @FXML
    private Button aniadirGrupo;

    @FXML
    private Button btnVolver;

    @FXML
    private TextField txtNombreGrupo;


    @FXML
    private TextField txtTelefono;

    @FXML
    private TextField txtNombreContacto;

    @FXML
    private TextField txtAliasContacto;

    @FXML
    private TextField txtDireccion;

    @FXML
    private Button btnLimpiar;

    @FXML
    private Button btnEliminarContacto;

    @FXML
    private TextField txtEmail;

    @FXML
    private Button btnAgregarContacto;

    @FXML
    private Button aniadirReunion;

    @FXML
    private ComboBox<Categoria> comboBoxCategoria;

    @FXML
    private Button btnLimpiar1;


    @FXML
    private Button btnEliminar;


    @FXML
    private Button btnNuevoGrupo;

	private Agenda agenda;

	private Aplicacion aplicacion;

	private Stage stage;

	private Contacto contactoSeleccion;

	private Grupo grupoSeleccion;

	private Reunion reunionSeleccion;
//Tablas y listas-----------------------
	ObservableList<Contacto> listaContactos= FXCollections.observableArrayList();
    ObservableList<Grupo> listaGrupos= FXCollections.observableArrayList();
    ObservableList<Reunion> listaReuniones= FXCollections.observableArrayList();

    private ObservableList<Contacto> getListaContactos() {
    	listaContactos.addAll(agenda.getListaContactos());
		return listaContactos;
	}

    private ObservableList<Grupo> getDocumentosGrupos() {
    	listaGrupos.addAll(agenda.getListaGrupos());
		return listaGrupos;
	}

    private ObservableList<Reunion> getDocumentosReunion() {
    	listaReuniones.addAll(agenda.getListaReuniones());
		return listaReuniones;
	}
//------------------------------------------------------------------------

//---------------------------Funciones para validar los datos y mostrar las alertas-----------------------------------
	/**
	 * Valida los datos para las reuniones
	 *
	 * @param descripcion
	 * @param fecha
	 * @param hora
	 * @return
	 */
	private boolean validarDatos(String descripcion, String fecha, String hora) {
		String notificacion = "";

		if (descripcion == null || descripcion.equals(""))
			notificacion += "Nombre invalido\n";


		/*Se valida que el saldo ingresado no sea null ni sea cadena vacía,
		además se valida que sea numérico para su correcta conversión */
		if (fecha == null || fecha.equals(""))
			notificacion += "Alias inválido\n";


		if(hora == null || hora.equals(""))
			notificacion+= "Email invalido\n";


		if (!notificacion.equals("")) {
			mostrarMensaje("Notificación", "Contacto no creado", notificacion, AlertType.WARNING);
			return false;
		}

		return true;
	}


	/**
	 *
	 *Valida los datos para el grupo
	 * @param nombreGrupo
	 * @param categoriaGrupo
	 * @return
	 */
	private boolean validarDatos(String nombreGrupo, Categoria categoriaGrupo) {
			String notificacion = "";

			if (nombreGrupo == null || nombreGrupo.equals(""))
				notificacion += "Nombre invalido\n";

			if(categoriaGrupo==null){
				notificacion+= "Ninguna categoria ha sido seleccionada";
			}

			if (!notificacion.equals("")) {
				mostrarMensaje("Notificación", "Grupo no creado", notificacion, AlertType.WARNING);
				return false;
			}

			return true;
		}


	/**
	 * Valida los datos para los contactos
	 * @param nombre
	 * @param alias
	 * @param telefono
	 * @param email
	 * @param direccion
	 * @return
	 */
	private boolean validarDatos(String nombre, String alias, String telefono, String email, String direccion) {
		String notificacion = "";

		if (nombre == null || nombre.equals(""))
			notificacion += "Nombre invalido\n";

		if (alias == null || alias.equals(""))
			notificacion += "Alias inválido\n";

		if (telefono == null || telefono.equals("") || telefono.length()<10 ) {
			notificacion += "Número de telefono invalido, debe de tener 10 caracteres\n";
		} else if (!validarTelefono(telefono)) {
			notificacion += "El telefono debe de ser un valor numrico\n";
		}

		if(email == null || email.equals("") || !email.contains("@") )
			notificacion+= "Email invalido, asegurese de que este ingresando un correo valido, que contenga @\n";


		if(direccion== null || direccion.equals(""))
			notificacion+= "Direccion inavlida\n";


		if (!notificacion.equals("")) {
			mostrarMensaje("Notificación", "Contacto no creado", notificacion, AlertType.WARNING);
			return false;
		}

		return true;
	}
	/**
	 * Metodo que sive para validar que el telefono contenga un valor numerico, y evitar registar un contacto con numero de telefono "hola"
	 * @param telefono
	 * @return true si el telefono esta ingresado correctamente
	 */

	private boolean validarTelefono(String telefono){

	    boolean valido = true;
	    for (int i = 0; i < telefono.length(); i++) {
	        if (!Character.isDigit(telefono.charAt(i))) {
	            valido = false;
	            break;
	        }
	    }
	    return valido;
	}
	/**
	 *
	 * @param titulo
	 * @param header
	 * @param contenido
	 * @param alertype
	 */

	public void mostrarMensaje(String titulo, String header, String contenido, AlertType alertype) {
		Alert alert = new Alert(alertype);
		alert.setTitle(titulo);
		alert.setHeaderText(header);
		alert.setContentText(contenido);
		alert.showAndWait();
	}
//-----------Contacto---------------------------------------------------------------------------------------------------------------------------------------
    @FXML
    void aniadirContacto(ActionEvent event) throws ContactoException, AgendaException {
    	String nombre= txtNombreContacto.getText();
    	String alias= txtAliasContacto.getText();
    	String telefono= txtTelefono.getText();
    	String direccion= txtDireccion.getText();
    	String email= txtEmail.getText();

    	if(validarDatos(nombre, alias, telefono, email, direccion)){

    		crearContacto(nombre, alias, telefono, email, direccion);

    		txtNombreContacto.setText("");
    		txtAliasContacto.setText("");
    		txtTelefono.setText("");
    		txtEmail.setText("");
    		txtDireccion.setText("");

    	}

    }

    /**
     *
     * @param nombre
     * @param alias
     * @param telefono
     * @param email
     * @param direccion
     * @throws ContactoException
     * @throws AgendaException
     */
    private void crearContacto(String nombre, String alias, String telefono, String email, String direccion) throws ContactoException, AgendaException{
    	if(aplicacion.crearContacto(nombre,alias,telefono,email,direccion)){
    		tableViewContactos.getItems().clear();
    		tableViewContactos.setItems(getListaContactos());
    		mostrarMensaje("Notificación Contacto", "Contacto creado", "Contacto agregado con exito", AlertType.INFORMATION);
		}else{
			mostrarMensaje("Notificación Contacto", "Contacto no creado", "El contacto no pudo ser creado", AlertType.WARNING);
		}

    }



    @FXML
    void eliminarContacto(ActionEvent event) throws ContactoException {

    	if(contactoSeleccion!=null){
    		int confirmacion= JOptionPane.showConfirmDialog(null, "Seguro que desea eliminar este contacto?");
    		if(confirmacion==0){
	    		if(aplicacion.eliminarContacto(contactoSeleccion)){
	    			listaContactos.remove(contactoSeleccion);
	    			mostrarMensaje("Contacto eliminado", "Eliminacion de contacto", "Se ha eliminado el contacto con exito", AlertType.INFORMATION);
	    		}else{
	    			mostrarMensaje("Contacto eliminado", "Eliminacion de contacto", "No se ha podido eliminar el contacto", AlertType.WARNING);
	    		}
    		}

    	}else
    		mostrarMensaje("Contacto seleccion", "Contacto Seleccion", "No se ha seleccionado ningun contacto", AlertType.WARNING);
    }


    @FXML
    void limpiarCamposTxt(ActionEvent event) {
    	txtNombreContacto.setText("");
		txtAliasContacto.setText("");
		txtTelefono.setText("");
		txtEmail.setText("");
		txtDireccion.setText("");

    	txtNombreContacto.setDisable(false);
		txtTelefono.setDisable(false);
    }

	private void mostrarInformacionContacto() {
		if(contactoSeleccion != null) {
			txtNombreContacto.setText(contactoSeleccion.getNombre());
			txtAliasContacto.setText(contactoSeleccion.getAlias());
			txtDireccion.setText(contactoSeleccion.getDireccion());
			txtTelefono.setText(contactoSeleccion.getTelefono());
			txtEmail.setText(contactoSeleccion.getEmail());
			//Deshabilito los textFields necesarios
			txtNombreContacto.setDisable(true);
			txtTelefono.setDisable(true);
		}
	}

//-------------------------------------------------------------------------------------------------------------
//--------------------------------GRUPOS------------------------------------------------------------------------
//---------------------------------------Grupo------------------------------------------------------
    @FXML
    void crearGrupo(ActionEvent event) {
    	String nombreGrupo = txtNombreGrupo.getText();
    	Categoria categoriaGrupo= comboBoxCategoria.getValue();
    	if(validarDatos(nombreGrupo,categoriaGrupo)){
    		nuevoGrupo(nombreGrupo, categoriaGrupo);
    		txtNombreGrupo.setText("");
    		comboBoxCategoria.setValue(null);
    	}
    }

	private void nuevoGrupo(String nombreGrupo, Categoria categoria){
		if(aplicacion.crearGrupo(nombreGrupo, categoria)){
			mostrarMensaje("Notificacion grupo", "Grupo creado", "El grupo "+ nombreGrupo+ " Ha sido creado con exito", AlertType.INFORMATION);
			tableViewGrupos.getItems().clear();
			tableViewGrupos.setItems(getDocumentosGrupos());

		}else {
			mostrarMensaje("Notificacion grupo", "Grupo no creado","El grupo no se ha podido crear" , AlertType.WARNING);
		}
	}
    @FXML
    void limpiarCampos(ActionEvent event) {

    }
    /**
     * Se hace una confirmacion con el showConfirmDialog, este retorna 0 si seleccionan "Si", 1 si seleccionan "No o cancelar" y si no seleccionan retorna .1
     * @param event
     * @throws GrupoException
     */
    @FXML
    void eliminarGrupo(ActionEvent event) throws GrupoException {
    	if(grupoSeleccion !=null){
    		int confirmacion= JOptionPane.showConfirmDialog(null, "Seguro que desea eliminar este grupo?");
    		if(confirmacion==0){
    			if (aplicacion.eliminarGrupo(grupoSeleccion)) {
    				listaGrupos.remove(grupoSeleccion);
    				mostrarMensaje("Grupo eliminado", "Eliminacion de grupo", "Se ha elimindao el grupo correctamente", AlertType.INFORMATION);
				}else
					mostrarMensaje("Grupo eliminado", "Eliminacion de grupo", "No se ha podido eliminar el grupo", AlertType.WARNING);
    		}
    	}else
    		mostrarMensaje("Grupo seleccion", "Grupo seleccion", "No se ha seleccionado ningun grupo", AlertType.WARNING);
    }
    /**
     * El proceso de añadir un contacto a un grupo es el siguiente:
     * Se selecciona un contactp  y un grupo de sus respectivas tablas, luego se utiliza el boton de añadir contacto a grupo
     * Y añade el contacto seleccionado al grupo seleccionado, se hace asi porque despues de intentar, diferentes cosas como combo box
     * con el nombre de los grupos, y que estas no dieran resultado, se vuelve esta la mejor opcion
     * @param event
     */
    @FXML
    void aniadirAgrupo(ActionEvent event) {
      	if(contactoSeleccion != null && grupoSeleccion != null) {
    		if(aplicacion.aniadirContactoAGrupo(contactoSeleccion, grupoSeleccion)) {
    			tableViewContactos.getItems().clear();
    			tableViewContactos.setItems(getListaContactos());
    			tableViewGrupos.getItems().clear(); //Limpio la lista
    			tableViewGrupos.setItems(getDocumentosGrupos()); //Agrego nuevos datos a la lista
    			mostrarMensaje("Notificación grupo", "Notificación grupo", "Se ha añadido el contacto " +
    					contactoSeleccion.getNombre() + " al grupo " + grupoSeleccion.getNombre(), AlertType.INFORMATION);
    		} else {
    			mostrarMensaje("Notificación grupo", "Notificación grupo", "No se ha podido añadir el contacto \n "
    					+ "Esto se debe a que el grupo ya esta lleno o el contacto ya existe en este grupo", AlertType.WARNING);
    		}
    	} else {
    		mostrarMensaje("Notificación grupo", "Notificación grupo-Notificación contacto", "Por favor verifique que haya seleccionado"
    				+ " el contacto y el grupo", AlertType.WARNING);
    	}
    }

	private void mostrarInfoGrupo() {
		if(grupoSeleccion != null) {
			txtNombreGrupo.setText(grupoSeleccion.getNombre());
			comboBoxCategoria.setValue(null);
		}
	}

//---------------------------------REUNIONES-------------------------------------------------------------------------
//----------------------------------Reuniones-------------------------------------------

    @FXML
    void crearReunion(ActionEvent event) {
    	String descripcion = txtDescripcion.getText();
    	String fecha = txtFecha.getText();
    	String hora= txtHora.getText();
    	if (validarDatos(descripcion, fecha, hora)) {
    		nuevaReunion(descripcion, fecha, hora);
    		txtDescripcion.setText("");
    		txtFecha.setText("");
    		txtHora.setText("");
		}
    }

    private void nuevaReunion(String descripcion, String fecha,String hora){
    	if (aplicacion.crearReunion(descripcion, fecha, hora)) {
    		mostrarMensaje("Notificacion reunion", "Reunion creada", "La reunion fue creada existosamente", AlertType.INFORMATION);
    		tableViewReuniones.getItems().clear();
    		tableViewReuniones.setItems(getDocumentosReunion());
		}else {
			mostrarMensaje("Notificacion reunion", "Reunion no creada", "La reunion no se ha podido crear", AlertType.INFORMATION);
		}
    }

    @FXML
    void limpiarReunion(ActionEvent event) {

    }

    @FXML
    void eliminarReunion(ActionEvent event) throws ReunionException {
    	if (reunionSeleccion!=null) {
    		int confirmacion=  JOptionPane.showConfirmDialog(null, "Seguro que desea eliminar este contacto");
    		if(confirmacion==0){
    			if (aplicacion.eliminarReunion(reunionSeleccion)) {
    				listaReuniones.remove(reunionSeleccion);
    				mostrarMensaje("Reunion eliminada", "Eliminacion de reunion", "Se ha eliminado la reunion correctamente", AlertType.INFORMATION);
				}else {
					mostrarMensaje("Reunion eliminada", "Eliminacion de reunion", "No se ha podido eliminar la reunion", AlertType.WARNING);
				}
    		}
		}else {
			mostrarMensaje("Reunion seleccion", "Reunion seleccion", "No se ha seleccionado ninguna reunion", AlertType.WARNING);
		}
    }

    @FXML
    void aniadirAReunion(ActionEvent event) {
      	if(contactoSeleccion != null && reunionSeleccion != null) {
    		if(aplicacion.aniadirContactoAReunion(contactoSeleccion, reunionSeleccion)) {
    			tableViewContactos.getItems().clear();
    			tableViewContactos.setItems(getListaContactos());
    			tableViewReuniones.getItems().clear(); //Limpio la lista
    			tableViewReuniones.setItems(getDocumentosReunion()); //Agrego nuevos datos a la lista
    			mostrarMensaje("Notificación Reunion", "Notificación Reunion", "Se ha añadido el contacto " +
    					contactoSeleccion.getNombre() + " a la reunion con descripcion: " + reunionSeleccion.getDescripcion(), AlertType.INFORMATION);
    		} else {
    			mostrarMensaje("Notificación Reunion", "Notificación Reunion", "No se ha podido añadir el contacto \n "
    					+ "Esto se debe a que la reunion ya cuenta con todos los participantes o el contacto ya existe en esta reunion", AlertType.WARNING);
    		}
    	} else {
    		mostrarMensaje("Notificación grupo", "Notificación grupo-Notificación contacto", "Por favor verifique que haya seleccionado"
    				+ " el contacto y el grupo", AlertType.WARNING);
    	}
    }


//Inicializacion de la aplicacion---------------------------------------------------------------------

	public void setAplicacion(Aplicacion aplicacion){
		this.aplicacion = aplicacion;
	}

    @FXML
    void initialize() {
    	txtNombreGrupo.setText("");
		comboBoxCategoria.setValue(null);
    }


	public void setStage(Stage primaryStage) {
		stage = primaryStage;

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		/*Se le asigna los valores a la tabla de contactos*/
		this.columNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		this.columAlias.setCellValueFactory(new PropertyValueFactory<>("alias"));
		this.columTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
		this.columDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
		this.columEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

		tableViewContactos.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if(newSelection != null){
				contactoSeleccion= newSelection;
				mostrarInformacionContacto();
			}
		});
		/*Se le asigna los valores a la tabla de grupos y al comboBox categoria*/
		comboBoxCategoria.getItems().addAll(Categoria.AMIGOS, Categoria.FAMILIA, Categoria.FIESTA, Categoria.OFICINA);
		this.columNombreGrupo.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		this.columCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));



		tableViewGrupos.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if(newSelection != null){
				grupoSeleccion= newSelection;
				mostrarInfoGrupo();
			}
		});
		/*Se le asigna los valores a la tabla de reuniones*/

		this.columDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
		this.columFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
		this.columHora.setCellValueFactory(new PropertyValueFactory<>("hora"));

		tableViewReuniones.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if(newSelection != null){
				reunionSeleccion= newSelection;
				mostrarInformacionContacto();
			}
		});

	}
    @FXML
    void showVentanaPrincipal(ActionEvent event) {
		stage.close();
    }

}
