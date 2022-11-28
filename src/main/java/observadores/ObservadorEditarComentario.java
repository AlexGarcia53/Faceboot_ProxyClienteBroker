/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package observadores;

import interfaces.IObservadorEditarComentario;
import oyentes.OyenteEditarComentario;

/**
 *
 * @author Admin
 */
public class ObservadorEditarComentario {
    private static ObservadorEditarComentario observadorEditarComentario;
    private OyenteEditarComentario oyenteEditarComentario;
    
    private ObservadorEditarComentario(){
        
    }
    
    public static ObservadorEditarComentario getInstancia(){
        if(observadorEditarComentario==null){
            observadorEditarComentario= new ObservadorEditarComentario();
        }
        return observadorEditarComentario;
    }
    
    public void suscribirse(IObservadorEditarComentario observador){
        this.oyenteEditarComentario= new OyenteEditarComentario(observador);
    }
    
    public void desuscribirse(IObservadorEditarComentario observador){
        this.oyenteEditarComentario.eliminarObservador();
        this.oyenteEditarComentario= null;
    }
}
