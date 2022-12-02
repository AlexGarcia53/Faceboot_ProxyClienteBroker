/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package observadores;

import interfaces.IObservadorEliminarComentario;
import oyentes.OyenteEliminarComentario;

/**
 * Clase que permite registrar y eliminar al observador a un evento, y escucha por actualizaciones.
 * @author Equipo Broker.
 */
public class ObservadorEliminarComentario {
    /**
     * Atributo con la instancia estática de la clase.
     */
    private static ObservadorEliminarComentario observadorEliminarComentario;
    /**
     * Atributo con un objeto del tipo oyente para el evento del observador.
     */
    private OyenteEliminarComentario oyenteEliminarComentario;
    /**
     * Método constructor de la clase.
     */
    private ObservadorEliminarComentario(){
        
    }
    /**
     * Método utilizado para obtener la instancia de la clase.
     * @return Instancia de la clase.
     */
    public static ObservadorEliminarComentario getInstancia(){
        if(observadorEliminarComentario==null){
            observadorEliminarComentario= new ObservadorEliminarComentario();
        }
        return observadorEliminarComentario;
    }
    /**
     * Método utilizado para registrar al observador al evento.
     * @param observador Observador a reigstrar.
     */
    public void suscribirse(IObservadorEliminarComentario observador){
        this.oyenteEliminarComentario= new OyenteEliminarComentario(observador);
    }
    /**
     * Método utilizado para eliminar al observador del evento.
     * @param observador Observador a eliminar.
     */
    public void desuscribirse(IObservadorEliminarComentario observador){
        this.oyenteEliminarComentario.eliminarObservador();
        this.oyenteEliminarComentario= null;
    }
}
