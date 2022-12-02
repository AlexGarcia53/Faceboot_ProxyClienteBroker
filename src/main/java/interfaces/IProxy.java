/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import dominio.Comentario;
import dominio.Hashtag;
import dominio.Mensaje;
import dominio.Publicacion;
import dominio.Solicitud;
import dominio.Usuario;
import java.util.List;

/**
 * Interfaz que contiene las operaciones que puede realizar el proxy.
 * @author Equipo Broker.
 */
public interface IProxy {
    /**
     * Método utilizado para registrar a un Usuario
     * @param usuario Usuario a registrar.
     * @return Usuario registrado.
     */
    public Usuario registrarUsuario(Usuario usuario);
    /**
     * Método utilizado para iniciar sesión de manera local.
     * @param usuario Usuario para iniciar sesión.
     * @return Usuario de la sesión.
     */
    public Usuario iniciarSesion(Usuario usuario);
    /**
     * Método utilizado para iniciar sesión con facebook.
     * @param usuario Usuario para iniciar sesión con facebook.
     * @return Usuario de la sesión.
     */
    public Usuario iniciarSesionFacebook(Usuario usuario);
    /**
     * Método utilizado para editar a un usuario registrado.
     * @param usuario Usuario con datos actualizados.
     * @return Usuario actualizado.
     */
    public Usuario editarPerfilUsuario(Usuario usuario);
    /**
     * Método utilizado para consultar a un usuario mediante el nombre, devuelve la primera 
     * coincidencia.
     * @param usuario Usuario a consultar.
     * @return Resultado de la busqueda.
     */
    public Usuario consultarUsuarioNombre(Usuario usuario);
    /**
     * Método utilizado para registrar una publicación nueva.
     * @param publicacion Publicación a registar.
     * @return Publicación registrada.
     */
    public Publicacion registrarPublicacion(Publicacion publicacion);
    /**
     * Método utilizado para editar una publicación existente.
     * @param publicacion Publicación con datos actualizados.
     * @return Publicación actualizada.
     */
    public Publicacion editarPublicacion(Publicacion publicacion);
    /**
     * Método utilizado para eliminar una publicación existente.
     * @param publicacion Publicación a eliminar.
     * @return Publicación eliminada.
     */
    public Publicacion eliminarPublicacion(Publicacion publicacion);
    /**
     * Método utilizado para consultar las publicaciones mediante un hashtag, devuelve todas las 
     * publicaciones que tengan asociado un hashtag con el mismo nombre.
     * @param hashtag Hashtag a buscar.
     * @return Resultado de la busqueda.
     */
    public List<Publicacion> consultarPublicaciones(Hashtag hashtag);
    /**
     * Método utilizado para reigstrar un comentario nuevo.
     * @param comentario Comentario a registrar.
     * @return Comentario registrado.
     */
    public Comentario registrarComentario(Comentario comentario);
    /**
     * Método utilizado para actualizar un comentario existente.
     * @param comentario Comentario con datos acutalizados.
     * @return Comentario actualizado.
     */
    public Comentario editarComentario(Comentario comentario);
    /**
     * Método utilizado para eliminar un comentario existente.
     * @param comentario Comentario a eliminar.
     * @return Comentario eliminado.
     */
    public Comentario eliminarComentario(Comentario comentario);
    /**
     * Método utilizado para enviar un mensaje a otro usuario mediante los método de notificación 
     * disponibles.
     * @param mensaje Mensaje a enviar.
     * @return Mensaje enviado.
     */
    public Mensaje enviarMensaje(Mensaje mensaje);
    /**
     * Método utilizado para consultar todas las publicaciones existentes.
     * @return Resultado de la busqueda.
     */
    public List<Publicacion> consultarPublicaciones();
    /**
     * Método utilizado para registrar un suscriptor al evento de registar publicación.
     * @param suscriptor Suscriptor a registrar.
     */
    public void suscribirseEventoRegistrarPublicacion(IObservadorRegistrarPublicacion suscriptor);
    /**
     * Método utilizado para eliminar a un suscriptor del evento de registrar publicación.
     * @param suscriptor Suscriptor a eliminar.
     */
    public void desuscribirseEventoRegistrarPublicacion(IObservadorRegistrarPublicacion suscriptor);
    /**
     * Método utilizado para registrar un suscriptor al evento de editar publicación.
     * @param suscriptor Suscriptor a registrar.
     */
    public void suscribirseEventoEditarPublicacion(IObservadorEditarPublicacion suscriptor);
    /**
     * Método utilizado para eliminar a un suscriptor del evento de editar publicación.
     * @param suscriptor Suscriptor a eliminar.
     */
    public void desuscribirseEventoEditarPublicacion(IObservadorEditarPublicacion suscriptor);
    /**
     * Método utilizado para registrar un suscriptor al evento de eliminar publicación.
     * @param suscriptor Suscriptor a registrar.
     */
    public void suscribirseEventoEliminarPublicacion(IObservadorEliminarPublicacion suscriptor);
    /**
     * Método utilizado para eliminar a un suscriptor del evento de eliminar publicación.
     * @param suscriptor Suscriptor a eliminar.
     */
    public void desuscribirseEventoEliminarPublicacion(IObservadorEliminarPublicacion suscriptor);
    /**
     * Método utilizado para registrar un suscriptor al evento de registar comentario.
     * @param suscriptor Suscriptor a registrar.
     */
    public void suscribirseEventoRegistrarComentario(IObservadorRegistrarComentario suscriptor);
    /**
     * Método utilizado para eliminar a un suscriptor del evento de registrar comentario.
     * @param suscriptor Suscriptor a eliminar.
     */
    public void desuscribirseEventoRegistrarComentario(IObservadorRegistrarComentario suscriptor);
    /**
     * Método utilizado para registrar un suscriptor al evento de editar comentario.
     * @param suscriptor Suscriptor a registrar.
     */
    public void suscribirseEventoEditarComentario(IObservadorEditarComentario suscriptor);
    /**
     * Método utilizado para eliminar a un suscriptor del evento de editar comentario.
     * @param suscriptor Suscriptor a eliminar.
     */
    public void desuscribirseEventoEditarComentario(IObservadorEditarComentario suscriptor);
    /**
     * Método utilizado para registrar un suscriptor al evento de eliminar comentario.
     * @param suscriptor Suscriptor a registrar.
     */
    public void suscribirseEventoEliminarComentario(IObservadorEliminarComentario suscriptor);
    /**
     * Método utilizado para eliminar a un suscriptor del evento de eliminar comentario.
     * @param suscriptor Suscriptor a eliminar.
     */
    public void desuscribirseEventoEliminarComentario(IObservadorEliminarComentario suscriptor);
    
}
