/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package observadores;

import interfaces.IObservadorEliminarPublicacion;
import oyentes.OyenteEliminarPublicacion;

/**
 * Clase que permite registrar y eliminar al observador a un evento, y escucha por actualizaciones.
 * @author Equipo Broker.
 */
public class ObservadorEliminarPublicacion {
    /**
     * Atributo con la instancia estática de la clase.
     */
    private static ObservadorEliminarPublicacion observadorEliminarPublicacion;
    /**
     * Atributo con un objeto del tipo oyente para el evento del observador.
     */
    private OyenteEliminarPublicacion oyenteEliminarPublicacion;
    /**
     * Método constructor de la clase.
     */
    private ObservadorEliminarPublicacion(){
        
    }
    /**
     * Método utilizado para obtener la instancia de la clase.
     * @return Instancia de la clase.
     */
    public static ObservadorEliminarPublicacion getInstancia(){
        if(observadorEliminarPublicacion==null){
            observadorEliminarPublicacion= new ObservadorEliminarPublicacion();
        }
        return observadorEliminarPublicacion;
    }
    /**
     * Método utilizado para registrar al observador al evento.
     * @param observadorEditarPublicacion Observador a reigstrar.
     */
    public void suscribirse(IObservadorEliminarPublicacion observador){
        this.oyenteEliminarPublicacion= new OyenteEliminarPublicacion(observador);
    }
    /**
     * Método utilizado para eliminar al observador del evento.
     * @param observadorEditarPublicacion Observador a eliminar.
     */
    public void desuscribirse(IObservadorEliminarPublicacion observador){
        this.oyenteEliminarPublicacion.eliminarObservador();
        this.oyenteEliminarPublicacion=null;
    }
}
