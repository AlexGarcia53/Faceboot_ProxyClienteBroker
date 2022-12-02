/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package observadores;

import interfaces.IObservadorRegistrarPublicacion;
import oyentes.OyenteRegistrarPublicacion;

/**
 * Clase que permite registrar y eliminar al observador a un evento, y escucha por actualizaciones.
 * @author Equipo Broker.
 */
public class ObservadorRegistrarPublicacion {
    /**
     * Atributo con la instancia estática de la clase.
     */
    private static ObservadorRegistrarPublicacion observadorRegistrarPublicacion;
    /**
     * Atributo con un objeto del tipo oyente para el evento del observador.
     */
    private OyenteRegistrarPublicacion oyenteRegistrarPublicacion;
    /**
     * Método constructor de la clase.
     */
    private ObservadorRegistrarPublicacion(){
        
    }
    /**
     * Método utilizado para obtener la instancia de la clase.
     * @return Instancia de la clase.
     */
    public static ObservadorRegistrarPublicacion getInstancia(){
        if(observadorRegistrarPublicacion==null){
            observadorRegistrarPublicacion= new ObservadorRegistrarPublicacion();
        }
        return observadorRegistrarPublicacion;
    }
    /**
     * Método utilizado para registrar al observador al evento.
     * @param observador Observador a reigstrar.
     */
    public void suscribirse(IObservadorRegistrarPublicacion observador){
        this.oyenteRegistrarPublicacion= new OyenteRegistrarPublicacion(observador);
    }
    /**
     * Método utilizado para eliminar al observador del evento.
     * @param observador Observador a eliminar.
     */
    public void desuscribirse(IObservadorRegistrarPublicacion observador){
        this.oyenteRegistrarPublicacion.eliminarObservador();
        this.oyenteRegistrarPublicacion= null;
    }
}
