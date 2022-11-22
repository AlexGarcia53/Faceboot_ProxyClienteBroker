/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package observadores;

import interfaces.IObservadorRegistrarPublicacion;
import oyentes.OyenteRegistrarPublicacion;

/**
 *
 * @author Admin
 */
public class ObservadorRegistrarPublicacion {
    private static ObservadorRegistrarPublicacion observadorRegistrarPublicacion;
    private OyenteRegistrarPublicacion oyenteRegistrarPublicacion;
    
    private ObservadorRegistrarPublicacion(){
        
    }
    
    public static ObservadorRegistrarPublicacion getInstancia(){
        if(observadorRegistrarPublicacion==null){
            observadorRegistrarPublicacion= new ObservadorRegistrarPublicacion();
        }
        return observadorRegistrarPublicacion;
    }
    
    public void suscribirse(IObservadorRegistrarPublicacion observador){
        this.oyenteRegistrarPublicacion= new OyenteRegistrarPublicacion(observador);
    }
    
    public void desuscribirse(IObservadorRegistrarPublicacion observador){
        this.oyenteRegistrarPublicacion.eliminarObservador();
        this.oyenteRegistrarPublicacion= null;
    }
}
