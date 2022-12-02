/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package observadores;

import interfaces.IObservadorEditarPublicacion;
import oyentes.OyenteEditarPublicacion;

/**
 * Clase que permite registrar y eliminar al observador a un evento, y escucha por actualizaciones.
 * @author Equipo Broker.
 */
public class ObservadorEditarPublicacion {
    /**
     * Atributo con la instancia estática de la clase.
     */
    private static ObservadorEditarPublicacion observadorEditarPublicacion;
    /**
     * Atributo con un objeto del tipo oyente para el evento del observador.
     */
    private OyenteEditarPublicacion oyenteEditarPublicacion;
    /**
     * Método constructor de la clase.
     */
    private ObservadorEditarPublicacion(){
        
    }
    /**
     * Método utilizado para obtener la instancia de la clase.
     * @return Instancia de la clase.
     */
    public static ObservadorEditarPublicacion getInstancia(){
        if(observadorEditarPublicacion==null){
            observadorEditarPublicacion= new ObservadorEditarPublicacion();
        }
        return observadorEditarPublicacion;
    }
    /**
     * Método utilizado para registrar al observador al evento.
     * @param observadorEditarPublicacion Observador a reigstrar.
     */
    public void suscribirse(IObservadorEditarPublicacion observadorEditarPublicacion){
        this.oyenteEditarPublicacion= new OyenteEditarPublicacion(observadorEditarPublicacion);
    }
    /**
     * Método utilizado para eliminar al observador del evento.
     * @param observadorEditarPublicacion Observador a eliminar.
     */
    public void desuscribirse(IObservadorEditarPublicacion observadorEditarPublicacion){
        this.oyenteEditarPublicacion.eliminarObservador();
        this.oyenteEditarPublicacion=null;
    }
}
