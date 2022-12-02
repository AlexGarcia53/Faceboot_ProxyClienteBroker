/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import dominio.Comentario;

/**
 * Interfaz que contiene el método para notificar a un observador la eliminación de un comentario.
 * @author Equipo Broker.
 */
public interface IObservadorEliminarComentario {
    /**
     * Método utilizado para notificar la eliminación de un comentario.
     * @param comentario Actualización de dicho evento.
     */
    public void notificarEliminacionComentario(Comentario comentario);
}
