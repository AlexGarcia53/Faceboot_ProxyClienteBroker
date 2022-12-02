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
 *
 * @author Admin
 */
public interface IProxy {
    public Usuario registrarUsuario(Usuario usuario);
    public Usuario iniciarSesion(Usuario usuario);
    public Usuario iniciarSesionFacebook(Usuario usuario);
    public Usuario editarPerfilUsuario(Usuario usuario);
    public Usuario consultarUsuarioNombre(Usuario usuario);
    public Publicacion registrarPublicacion(Publicacion publicacion);
    public Publicacion editarPublicacion(Publicacion publicacion);
    public Publicacion eliminarPublicacion(Publicacion publicacion);
    public List<Publicacion> consultarPublicaciones(Hashtag hashtag);
    public Comentario registrarComentario(Comentario comentario);
    public Comentario editarComentario(Comentario comentario);
    public Comentario eliminarComentario(Comentario comentario);
    public Mensaje enviarMensaje(Mensaje mensaje);
    public List<Publicacion> consultarPublicaciones();
    public void suscribirseEventoRegistrarPublicacion(IObservadorRegistrarPublicacion suscriptor);
    public void desuscribirseEventoRegistrarPublicacion(IObservadorRegistrarPublicacion suscriptor);
    public void suscribirseEventoEditarPublicacion(IObservadorEditarPublicacion suscriptor);
    public void desuscribirseEventoEditarPublicacion(IObservadorEditarPublicacion suscriptor);
    public void suscribirseEventoEliminarPublicacion(IObservadorEliminarPublicacion suscriptor);
    public void desuscribirseEventoEliminarPublicacion(IObservadorEliminarPublicacion suscriptor);
    public void suscribirseEventoRegistrarComentario(IObservadorRegistrarComentario suscriptor);
    public void desuscribirseEventoRegistrarComentario(IObservadorRegistrarComentario suscriptor);
    public void suscribirseEventoEditarComentario(IObservadorEditarComentario suscriptor);
    public void desuscribirseEventoEditarComentario(IObservadorEditarComentario suscriptor);
    public void suscribirseEventoEliminarComentario(IObservadorEliminarComentario suscriptor);
    public void desuscribirseEventoEliminarComentario(IObservadorEliminarComentario suscriptor);
    
}
