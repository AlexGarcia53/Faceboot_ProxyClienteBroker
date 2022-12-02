/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import dominio.Publicacion;

/**
 * Interfaz que contiene el método para notificar a un observador la edición de una publicación.
 * @author Equipo Broker.
 */
public interface IObservadorEditarPublicacion {
    /**
     * Método utilizado para notificar la edición de una publicación.
     * @param publicacion Actualización de dicho evento. 
     */
    public void notificarEdicionPublicacion(Publicacion publicacion);
}
