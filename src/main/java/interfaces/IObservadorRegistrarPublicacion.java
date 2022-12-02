/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import dominio.Publicacion;

/**
 * Interfaz que contiene el método para notificar a un observador el registro de una publicación.
 * @author Equipo Broker.
 */
public interface IObservadorRegistrarPublicacion {
    /**
     * Método utilizado para notificar el registro de una publicación.
     * @param actualizacion Actualización de dicho evento.
     */
    public void notificarRegistroPublicacion(Publicacion actualizacion);
}
