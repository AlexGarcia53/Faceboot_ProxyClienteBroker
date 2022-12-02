/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package observadores;

import interfaces.IObservadorRegistrarComentario;
import oyentes.OyenteRegistrarComentario;

/**
 * Clase que permite registrar y eliminar al observador a un evento, y escucha por actualizaciones.
 * @author Equipo Broker.
 */
public class ObservadorRegistrarComentario {
    /**
     * Atributo con la instancia estática de la clase.
     */
    private static ObservadorRegistrarComentario observadorRegistrarComentario;
    /**
     * Atributo con un objeto del tipo oyente para el evento del observador.
     */
    private OyenteRegistrarComentario oyenteRegistrarComentario;
    /**
     * Método constructor de la clase.
     */
    private ObservadorRegistrarComentario(){
        
    }
    /**
     * Método utilizado para obtener la instancia de la clase.
     * @return Instancia de la clase.
     */
    public static ObservadorRegistrarComentario getInstancia(){
        if(observadorRegistrarComentario==null){
            observadorRegistrarComentario= new ObservadorRegistrarComentario();
        }
        return observadorRegistrarComentario;
    }
    /**
     * Método utilizado para registrar al observador al evento.
     * @param observador Observador a reigstrar.
     */
    public void suscribirse(IObservadorRegistrarComentario observador){
        this.oyenteRegistrarComentario= new OyenteRegistrarComentario(observador);
    }
    /**
     * Método utilizado para eliminar al observador del evento.
     * @param observador Observador a eliminar.
     */
    public void desuscribirse(IObservadorRegistrarComentario observador){
        this.oyenteRegistrarComentario.eliminarObservador();
        this.oyenteRegistrarComentario= null;
    }
}
