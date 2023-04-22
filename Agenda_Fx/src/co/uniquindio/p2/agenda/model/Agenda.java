package co.uniquindio.p2.agenda.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import co.uniquindio.p2.agenda.exceptions.AgendaException;
import co.uniquindio.p2.agenda.exceptions.ContactoException;
import co.uniquindio.p2.agenda.exceptions.GrupoException;
import co.uniquindio.p2.agenda.exceptions.ReunionException;

public class Agenda implements Serializable {

	/**
	 *
	 */
	//private static final long serialVersionUID = 1L;

	private String nombre;
	private Contacto[] listaContactos;
	private Grupo[] listaGrupos;
	private Reunion[] listaReuniones;


	public Agenda(String nombre, int numeroContactos,int numeroGrupos,int numeroReuniones) {
		this.nombre = nombre;
		this.listaContactos = new Contacto[numeroContactos];
		this.listaGrupos = new Grupo[numeroGrupos];
		this.listaReuniones = new Reunion[numeroReuniones];

		//Inicializo valores para probarlos en la tabla
		Contacto contacto1 = new Contacto("Miguel", "Grone", "Cr 24A", "3124038907", "miguelF@gmail.com", 10, 10);
		Contacto contacto2 = new Contacto("Juan", "Bandido", "Cr 23 #101", "3214567889", "juanV@hotmail.com", 10, 10);
		listaContactos[0] = contacto1;
		listaContactos[1] = contacto2;

		//Inicalizo valores de grupos para probarlos en la tabla
		Grupo grupo1 = new Grupo("Familia", 10, Categoria.FAMILIA);
		Grupo grupo2 = new Grupo("Programacion", 10, Categoria.OFICINA);
		listaGrupos[0] = grupo1;
		listaGrupos[1] = grupo2;

		//Inicializo valores para las reuniones en la tabla
		Reunion reunion1 = new Reunion("Conversatorio", "12/05/2023", "15:00", 10);
		Reunion reunion2 = new Reunion("Salida colegas", "23/04/2023", "22:00", 10);
		listaReuniones[0] = reunion1;
		listaReuniones[1] = reunion2;

	}


	public Agenda() {
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public Contacto[] getListaContactos() {
		return listaContactos;
	}


	public void setListaContactos(Contacto[] listaContactos) {
		this.listaContactos = listaContactos;
	}


	public Grupo[] getListaGrupos() {
		return listaGrupos;
	}


	public void setListaGrupos(Grupo[] listaGrupos) {
		this.listaGrupos = listaGrupos;
	}


	public Reunion[] getListaReuniones() {
		return listaReuniones;
	}


	public void setListaReuniones(Reunion[] listaReuniones) {
		this.listaReuniones = listaReuniones;
	}


	@Override
	public String toString() {
		return "Agenda \nnombre" + nombre ;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Agenda other = (Agenda) obj;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}

	/**
	 *Recibe un contacto como parametro
	 *Se verifica si este contacto ya existe si la varible contacto toma el valor de null, indica que no existe este contacto, por lo tanto se puede crear
	 *Luego se ve que posicion del arreglo está disponible, si se encuentra una posicion disponible se agrega el contacto al arreglo
	 *Si retorna -1 indica que no hay espacio disponibe, por lo tanto se lanza la excepcion
	 *Si este metodo retorna false es que el contacto no se ha podido agregar, si retorna true es que fue agregado con exito
	 * @param newContacto
	 * @throws ContactoException
	 */
	public boolean aniadirContacto(Contacto newContacto) {
		Contacto contacto = obtenerContacto(newContacto);
		int posDisponible = obtenerPosicionLibre();
		if (!existeContacto(newContacto)) {
			if(contacto == null && posDisponible != -1) {
				listaContactos[posDisponible] = newContacto;
				return true;
			}
		}else{
			return false;
		}
		return false;

	}

	public Contacto obtenerContacto(Contacto newContacto) {
		Contacto contactoEncontrado = null;
		for(Contacto contacto : listaContactos) {
			//Tengo que ponerle el que sea diferente a null porque o sino causo un nullPointerException
			if(contacto != null && contacto.getNombre().equals(newContacto.getNombre()) && contacto.getTelefono().equals(newContacto.getTelefono())) {
				contactoEncontrado = contacto;
			}
		}
		return contactoEncontrado;
	}

	private boolean existeContacto(Contacto newContacto) {
		boolean encontrado= false;
		List<Contacto> asList = Arrays.asList(this.listaContactos);
		for (Contacto contacto : asList) {
			if(contacto!= null && contacto.getNombre().equals(newContacto.getNombre()) && contacto.getTelefono().equals(newContacto.getTelefono()) ){
				encontrado=true;
				return encontrado;
			}
		}
		return encontrado;

	}
	/**
	 * Recibe el arreglo y se analiza cada posicion y si en la posicion i=null la variable pos toma este valor
	 * si no hay ningun sitio disponible retorna -1
	 * @param arreglo
	 * @return
	 */
	private int obtenerPosicionLibre() {
	    for (int i = 0; i < listaContactos.length; i++) {
	        if(listaContactos[i] == null) {
	            return i;
	        }
	    }
	    return -1;
	}

	/** METODO 2
	 *
	 * @param contactoEliminar
	 * Se evalua que el contacto si exista, si eciste se inicia el recorrido del arreglo hasta encontrarlo en el arreglo, al momento de encontrarlo se elimina
	 * Si el contacto no eciste lanza la excepcion
	 *
	 * @return false, si el contacto no se pudo eliminar
	 * @return true, si el contacto fue eliminado con exito
	 * @throws ContactoException
	 */
	public boolean eliminarContacto2(Contacto contactoEliminar) throws ContactoException{

		boolean eliminado= false;
		boolean existeContacto= existeContacto(contactoEliminar);

		if(!existeContacto){
			eliminado= false;
			throw new ContactoException("El contacto no existe, por lo tanto no se puede eliminar");
		}

		for (int i = 0; i < listaContactos.length; i++) {
			if(listaContactos[i] == contactoEliminar){
				listaContactos[i]= null;
				eliminado= true;
				break;
			}
		}

		return eliminado;
	}
	/**
	 *	Si en una posicion del arreglo hay un "null" significa que no tiene ningun contacto, por lo tanto la agenda no estaria llena
	 * @return
	 */
	public boolean agendaLlena(){
		boolean agendaLlena= true;;
		for (int i = 0; i < listaContactos.length; i++) {
			if(listaContactos[i] ==null){
				agendaLlena=false;
			}
		}
		return agendaLlena;
	}
	/**
	 * Si hay una posicion con valor null indica que hay un hueco libre, el cual se suma al contador
	 * @return la cantidad de espacios dispibles del arreglo
	 */
	public int huecosLibres(){
		int cantHuecos=0;
		for (int i = 0; i < listaContactos.length; i++) {
			if(listaContactos[i] ==null){
				cantHuecos++;
			}
		}

		return cantHuecos;
	}
//------------------------- la logica de los grupos-----------------------------

/**
 *
 * @param newGrupo
 * @return
 */
	public boolean crearGrupo(Grupo newGrupo){
		boolean creado= false;
		Grupo grupoNuevoGrupo= obtenerGrupo(newGrupo);
		int posDisponible = obtenerPosicionLibreGrupo();


		if(!existeGrupo(newGrupo)){
			if(grupoNuevoGrupo == null && posDisponible != -1) {
				listaGrupos[posDisponible] = newGrupo;
				return true;
			}
		}
		return creado;
	}


/**
 *
 * @param newGrupo
 * @return
 */
	public Grupo obtenerGrupo(Grupo newGrupo) {
		Grupo grupoEncontrado = null;
		for(Grupo grupoAux : listaGrupos) {
			//Tengo que ponerle el que sea diferente a null porque o sino causo un nullPointerException
			if(grupoAux != null && grupoAux.getNombre().equals(newGrupo.getNombre())) {
				grupoEncontrado = grupoAux;
			}
		}
		return grupoEncontrado;
	}

/**
 *
 * @param newGrupo
 * @return
 */
	private boolean existeGrupo(Grupo newGrupo) {
		boolean encontrado= false;
		List<Grupo> asList = Arrays.asList(this.listaGrupos);
		for (Grupo grupoAux : asList) {
			if(grupoAux != null && grupoAux.getNombre().equals(newGrupo.getNombre())){
				encontrado=true;
				return encontrado;
			}
		}
		return encontrado;

	}

	public boolean eliminarGrupo(Grupo grupoEliminar) throws GrupoException{

		boolean eliminado= false;
		boolean existeGrupo= existeGrupo(grupoEliminar);

		if(!existeGrupo){
			eliminado= false;
			throw new GrupoException("El grupo no existe, por lo tanto no se puede eliminar");
		}

		for (int i = 0; i < listaGrupos.length; i++) {
			if(listaGrupos[i] == grupoEliminar){
				listaGrupos[i]= null;
				eliminado= true;
				break;
			}
		}

	    if(eliminado) {
	    	eliminarGrupoContactos(grupoEliminar); //Es para eliminar este grupo de todos los contactos
	    }

		return eliminado;
	}

	/**
	 * Se elimina el grupo de cada uno de los contactos
	 * @param grupoEliminar
	 */
	private void eliminarGrupoContactos(Grupo grupoEliminar) {
		for(int i = 0; i < listaContactos.length; i++) {
			Contacto contacto = listaContactos[i];
			if(contacto != null) {
				for(int j = 0; j < contacto.getListaGrupos().length; j++) {
					Grupo grupo = contacto.getListaGrupos()[j];
					if(grupo != null && grupo.equals(grupoEliminar)) {
						contacto.getListaGrupos()[j] = null;
					}
				}
			}
		}
	}
/**
 * Se obtiene la copia de la lista de contactos
 * @param contactoSeleccion
 * @param grupoSeleccion
 * @return
 */

	public boolean aniadirContactoAGrupo(Contacto contactoSeleccion, Grupo grupoSeleccion) {
		boolean agregado= false;
		Contacto[] listaContactosGrupo= grupoSeleccion.getListaContactos();
		int posDisponible= obtenerPosicionLibreContactosGrupo(listaContactosGrupo);

		Grupo[] listaGruposContacto= contactoSeleccion.getListaGrupos();
		int posDisponibleGrupoContacto= obtenerPosicionLibreGrupoContactos(listaGruposContacto);


		if (!existeEnGrupo(contactoSeleccion, listaContactosGrupo)  && posDisponible!=-1 ) {
			if (!existeEnContacto(grupoSeleccion,listaGruposContacto) && posDisponibleGrupoContacto !=-1 ) {
				listaContactosGrupo[posDisponible]= contactoSeleccion;
				grupoSeleccion.setListaContactos(listaContactosGrupo);
				listaGruposContacto[posDisponibleGrupoContacto]= grupoSeleccion;
				contactoSeleccion.setListaGrupos(listaGruposContacto);
				agregado=true;
			}
		}


		return agregado;
	}


	public int obtenerPosicionLibreContactosGrupo(Contacto[] listaContactosGrupo){
	    for (int i = 0; i < listaContactosGrupo.length; i++) {
	        if(listaContactosGrupo[i] == null)
	            return i;
	    }
	    return -1;
	}


	public int obtenerPosicionLibreGrupoContactos(Grupo[] listaGruposContacto){
	    for (int i = 0; i < listaGruposContacto.length; i++) {
	        if(listaGruposContacto[i] == null)
	            return i;
	    }
	    return -1;
	}
	/**
	 * Como no se pueden repetir contactos en los grupos, es necesario verificar si el contacto ya esta en la lista de contactos del grupo
	 * @param contactoSeleccion
	 * @param listaAux
	 * @return
	 */
	public boolean existeEnGrupo(Contacto contactoSeleccion, Contacto[] listaAux){
		boolean existe= false;
		for (Contacto contactoAux : listaAux) {
			if(contactoAux!= null){
				if (contactoAux.equals(contactoSeleccion) || contactoAux == contactoSeleccion) {
					existe= true;
					return existe;
				}
			}
		}



		return existe;
	}

	/**
	 *
	 * @param grupoSeleccion
	 * @param grupoAux
	 * @return
	 * Como un grupo no puede estar doblemente asignado se hace la verificacion de esto
	 *
	 *
	 */
	public boolean existeEnContacto(Grupo grupoSeleccion, Grupo[] listaGrupoAux) {
		boolean existe= false;
		for (Grupo grupoAux : listaGrupoAux) {
			if(grupoAux!=null){
				if (grupoAux.equals(grupoSeleccion) || grupoAux == grupoSeleccion) {
					existe= true;
					return existe;
				}
			}
		}

		return existe;
	}

/**
 *
 * @return
 */
	private int obtenerPosicionLibreGrupo() {
	    for (int i = 0; i < listaGrupos.length; i++) {
	        if(listaGrupos[i] == null)
	            return i;
	    }
	    return -1;
	}
//------------------------------------la logica de las reuniones----------------------------------------------------------------
	/**
	 *
	 * @param newReunion
	 * @return
	 */
	public boolean crearReunion(Reunion newReunion){
		boolean creado= false;
		Reunion reunionNueva= obtenerReunion(newReunion);
		int posDisponible = obtenerPosicionLibreReunion();


		if(!existeReunion(newReunion)){
			if(reunionNueva == null && posDisponible != -1) {
				listaReuniones[posDisponible] = newReunion;
				return true;
			}
		}
		return creado;
	}

	/**
	 *
	 * @param newReunion
	 * @return
	 */
	public Reunion obtenerReunion(Reunion newReunion) {
		Reunion reunionEncontrada = null;
		for(Reunion reunionAux : listaReuniones) {
			//Tengo que ponerle el que sea diferente a null porque o sino causo un nullPointerException
			if(reunionAux != null && reunionAux.getDescripcion().equals(newReunion.getDescripcion())) {
				reunionEncontrada = reunionAux;
			}
		}
		return reunionEncontrada;
	}
	/**
	 *
	 * @param newReunion
	 * @return
	 */
	private boolean existeReunion(Reunion newReunion) {
		boolean encontrado= false;
		List<Reunion> asList = Arrays.asList(this.listaReuniones);
		for (Reunion reunionAux : asList) {
			if(reunionAux != null && reunionAux.getDescripcion().equals(newReunion.getDescripcion())){
				encontrado=true;
				return encontrado;
			}
		}
		return encontrado;

	}
	private int obtenerPosicionLibreReunion() {
	    for (int i = 0; i < listaGrupos.length; i++) {
	        if(listaGrupos[i] == null)
	            return i;
	    }
	    return -1;
	}

	public boolean eliminarReunion(Reunion reunionEliminar) throws ReunionException{

		boolean eliminado= false;
		boolean existeReunion= existeReunion(reunionEliminar);

		if(!existeReunion){
			eliminado= false;
			throw new ReunionException("La reunion no existe, por lo tanto no se puede eliminar");
		}

		for (int i = 0; i < listaReuniones.length; i++) {
			if(listaReuniones[i] == reunionEliminar){
				listaReuniones[i]= null;
				eliminado= true;
				break;
			}
		}

		return eliminado;
	}

	public boolean aniadirContactoAReunion(Contacto contactoSeleccion, Reunion reunionSeleccion) {
		boolean agregado= false;
		Contacto[] listaContactosReunion= reunionSeleccion.getListaContactos();
		int posDisponible= obtenerPosicionLibreContactosReunion(listaContactosReunion);

		Reunion[] listaReunionContacto= contactoSeleccion.getListaReuniones();
		int posDisponibleGrupoContacto= obtenerPosicionLibreReunionContactos(listaReunionContacto);


		if (!existeEnGrupo(contactoSeleccion, listaContactosReunion)  && posDisponible!=-1 ) {
			if (!reunionExisteEnContacto(reunionSeleccion,listaReunionContacto) && posDisponibleGrupoContacto !=-1 ) {
				listaContactosReunion[posDisponible]= contactoSeleccion;
				reunionSeleccion.setListaContactos(listaContactosReunion);
				listaReunionContacto[posDisponibleGrupoContacto]= reunionSeleccion;
				contactoSeleccion.setListaReuniones(listaReunionContacto);
				agregado=true;
			}
		}


		return agregado;
	}

	public int obtenerPosicionLibreContactosReunion(Contacto[] listaContactosGrupo){
	    for (int i = 0; i < listaContactosGrupo.length; i++) {
	        if(listaContactosGrupo[i] == null)
	            return i;
	    }
	    return -1;
	}


	public int obtenerPosicionLibreReunionContactos(Reunion[] listaReunionsContacto){
	    for (int i = 0; i < listaReunionsContacto.length; i++) {
	        if(listaReunionsContacto[i] == null)
	            return i;
	    }
	    return -1;
	}
	public boolean reunionExisteEnContacto(Reunion reunionSeleccion, Reunion[] listaReunionAux) {
		boolean existe= false;
		for (Reunion reunionAux : listaReunionAux) {
			if(reunionAux!=null){
				if (reunionAux.equals(reunionSeleccion) || reunionAux == reunionSeleccion) {
					existe= true;
					return existe;
				}
			}
		}

		return existe;
	}

}
