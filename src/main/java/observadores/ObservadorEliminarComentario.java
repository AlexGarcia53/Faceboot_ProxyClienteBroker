/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package observadores;

import interfaces.IObservadorEliminarComentario;
import oyentes.OyenteEliminarComentario;

/**
 *
 * @author Admin
 */
public class ObservadorEliminarComentario {
    private static ObservadorEliminarComentario observadorEliminarComentario;
    private OyenteEliminarComentario oyenteEliminarComentario;
    
    private ObservadorEliminarComentario(){
        
    }
    
    public static ObservadorEliminarComentario getInstancia(){
        if(observadorEliminarComentario==null){
            observadorEliminarComentario= new ObservadorEliminarComentario();
        }
        return observadorEliminarComentario;
    }
    
    public void suscribirse(IObservadorEliminarComentario observador){
        this.oyenteEliminarComentario= new OyenteEliminarComentario(observador);
    }
    
    public void desuscribirse(IObservadorEliminarComentario observador){
        this.oyenteEliminarComentario.eliminarObservador();
        this.oyenteEliminarComentario= null;
    }
}
