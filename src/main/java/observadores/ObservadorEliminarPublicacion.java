/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package observadores;

import interfaces.IObservadorEliminarPublicacion;
import oyentes.OyenteEliminarPublicacion;

/**
 *
 * @author Admin
 */
public class ObservadorEliminarPublicacion {
    private static ObservadorEliminarPublicacion observadorEliminarPublicacion;
    private OyenteEliminarPublicacion oyenteEliminarPublicacion;
    
    private ObservadorEliminarPublicacion(){
        
    }
    
    public static ObservadorEliminarPublicacion getInstancia(){
        if(observadorEliminarPublicacion==null){
            observadorEliminarPublicacion= new ObservadorEliminarPublicacion();
        }
        return observadorEliminarPublicacion;
    }
    
    public void suscribirse(IObservadorEliminarPublicacion observadorEditarPublicacion){
        this.oyenteEliminarPublicacion= new OyenteEliminarPublicacion(observadorEditarPublicacion);
    }
    
    public void desuscribirse(IObservadorEliminarPublicacion observadorEditarPublicacion){
        this.oyenteEliminarPublicacion.eliminarObservador();
        this.oyenteEliminarPublicacion=null;
    }
}
