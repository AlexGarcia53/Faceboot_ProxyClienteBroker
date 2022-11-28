/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package observadores;

import interfaces.IObservadorEditarPublicacion;
import oyentes.OyenteEditarPublicacion;

/**
 *
 * @author Admin
 */
public class ObservadorEditarPublicacion {
    private static ObservadorEditarPublicacion observadorEditarPublicacion;
    private OyenteEditarPublicacion oyenteEditarPublicacion;
    
    private ObservadorEditarPublicacion(){
        
    }
    
    public static ObservadorEditarPublicacion getInstancia(){
        if(observadorEditarPublicacion==null){
            observadorEditarPublicacion= new ObservadorEditarPublicacion();
        }
        return observadorEditarPublicacion;
    }
    
    public void suscribirse(IObservadorEditarPublicacion observadorEditarPublicacion){
        this.oyenteEditarPublicacion= new OyenteEditarPublicacion(observadorEditarPublicacion);
    }
    
    public void desuscribirse(IObservadorEditarPublicacion observadorEditarPublicacion){
        this.oyenteEditarPublicacion.eliminarObservador();
        this.oyenteEditarPublicacion=null;
    }
}
