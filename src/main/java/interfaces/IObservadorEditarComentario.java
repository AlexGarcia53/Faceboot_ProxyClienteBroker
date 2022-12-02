/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import dominio.Comentario;

/**
 * Interfaz que contiene el método para notificar a un observador la edición de un comentario.
 * @author Equipo Broker.
 */
public interface IObservadorEditarComentario {
    /**
     * Método utilizado para notificar la edición de un comentario.
     * @param comentario Actualización de dicho evento.
     */
    public void notificarEdicionComentario(Comentario comentario);
}
