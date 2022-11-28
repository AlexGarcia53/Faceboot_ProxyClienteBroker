/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package observadores;

import interfaces.IObservadorRegistrarComentario;
import oyentes.OyenteRegistrarComentario;

/**
 *
 * @author Admin
 */
public class ObservadorRegistrarComentario {
    private static ObservadorRegistrarComentario observadorRegistrarComentario;
    private OyenteRegistrarComentario oyenteRegistrarComentario;
    
    private ObservadorRegistrarComentario(){
        
    }
    
    public static ObservadorRegistrarComentario getInstancia(){
        if(observadorRegistrarComentario==null){
            observadorRegistrarComentario= new ObservadorRegistrarComentario();
        }
        return observadorRegistrarComentario;
    }
    
    public void suscribirse(IObservadorRegistrarComentario observador){
        this.oyenteRegistrarComentario= new OyenteRegistrarComentario(observador);
    }
    
    public void desuscribirse(IObservadorRegistrarComentario observador){
        this.oyenteRegistrarComentario.eliminarObservador();
        this.oyenteRegistrarComentario= null;
    }
}
