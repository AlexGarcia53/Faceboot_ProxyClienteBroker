/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package observadores;

import interfaces.IObservadorEditarComentario;
import oyentes.OyenteEditarComentario;

/**
 * Clase que permite registrar y eliminar al observador a un evento, y escucha por actualizaciones.
 * @author Equipo Broker.
 */
public class ObservadorEditarComentario {
    /**
     * Atributo con la instancia estática de la clase.
     */
    private static ObservadorEditarComentario observadorEditarComentario;
    /**
     * Atributo con un objeto del tipo oyente para el evento del observador.
     */
    private OyenteEditarComentario oyenteEditarComentario;
    /**
     * Método constructor de la clase.
     */
    private ObservadorEditarComentario(){
        
    }
    /**
     * Método utilizado para obtener la instancia de la clase.
     * @return Instancia de la clase.
     */
    public static ObservadorEditarComentario getInstancia(){
        if(observadorEditarComentario==null){
            observadorEditarComentario= new ObservadorEditarComentario();
        }
        return observadorEditarComentario;
    }
    /**
     * Método utilizado para registrar al observador al evento.
     * @param observador Observador a reigstrar.
     */
    public void suscribirse(IObservadorEditarComentario observador){
        this.oyenteEditarComentario= new OyenteEditarComentario(observador);
    }
    /**
     * Método utilizado para eliminar al observador del evento.
     * @param observador Observador a eliminar.
     */
    public void desuscribirse(IObservadorEditarComentario observador){
        this.oyenteEditarComentario.eliminarObservador();
        this.oyenteEditarComentario= null;
    }
}
