/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import dominio.Publicacion;

/**
 * Interfaz que contiene el método para notificar a un observador la eliminación de una publicación.
 * @author Equipo Broker.
 */
public interface IObservadorEliminarPublicacion {
    /**
     * Método utilizado para notificar la eliminación de una publicación.
     * @param publicacion Actualización de dicho evento.
     */
    public void notificarEliminacionPublicacion(Publicacion publicacion);
}
