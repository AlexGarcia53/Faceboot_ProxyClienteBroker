/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.proxyclientebroker;

import dominio.Comentario;
import dominio.Hashtag;
import dominio.Mensaje;
import dominio.Operacion;
import dominio.Publicacion;
import dominio.Solicitud;
import dominio.Usuario;
import excepciones.ErrorBusquedaPublicacionesException;
import excepciones.ErrorBusquedaUsuarioException;
import excepciones.ErrorConsultarPublicacionException;
import excepciones.ErrorEditarComentarioException;
import excepciones.ErrorEditarUsuarioException;
import excepciones.ErrorEliminarComentarioException;
import excepciones.ErrorEliminarPublicacionException;
import excepciones.ErrorEnviarMensajeException;
import excepciones.ErrorGuardarComentarioException;
import excepciones.ErrorGuardarPublicacionException;
import excepciones.ErrorGuardarUsuarioException;
import interfaces.IObservadorEditarComentario;
import interfaces.IObservadorEditarPublicacion;
import interfaces.IObservadorEliminarComentario;
import interfaces.IObservadorEliminarPublicacion;
import interfaces.IObservadorRegistrarComentario;
import interfaces.IProxy;
import interfaces.IObservadorRegistrarPublicacion;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import observadores.ObservadorEditarComentario;
import observadores.ObservadorEditarPublicacion;
import observadores.ObservadorEliminarComentario;
import observadores.ObservadorEliminarPublicacion;
import observadores.ObservadorRegistrarComentario;
import observadores.ObservadorRegistrarPublicacion;

/**
 * Clase que representa al proxy del lado del cliente.
 * @author Equipo Broker.
 */
public class ProxyClienteBroker implements IProxy{
    /**
     * Atributo que contiene la ip a la que se conectará.
     */
    private String HOST= "127.0.0.1";
    /**
     * Atributo que contiene el puerto al que se conectará.
     */
    private int PORT= 5000;
    /**
     * Atributo que contiene el socket de la conexión.
     */
    private Socket socket;
    /**
     * Atributo que contiene el buffered reader de la conexión.
     */
    private BufferedReader bufferedReader;
    /**
     * Atriuto que contiene el buffered writer de la conexión.
     */
    private BufferedWriter bufferedWriter;
    /**
     * Método constructor de la clase.
     */
    public ProxyClienteBroker(){
        try{
            this.socket=new Socket(HOST, PORT);
            this.bufferedReader= new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter= new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e){
            cerrarTodo(socket, bufferedReader, bufferedWriter);
        }
    }
    /**
     * Método utilizado para enviar una solicitud en formato JSON.
     * @param solicitud Solicitud en formato JSON.
     * @return Respuesta del servidor en formato JSON.
     */
    public String enviarSolicitud(String solicitud){
        String respuesta = "";
        try{
            bufferedWriter.write(solicitud);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            
            respuesta= bufferedReader.readLine();
            System.out.println(respuesta);
        } catch (IOException e){
            cerrarTodo(socket, bufferedReader, bufferedWriter);
        } finally{
            return respuesta;
        }
    }
    /**
     * Método utilizado para enviar un mensaje en formato JSON.
     * @param mensaje Mensaje en formato JSON.
     */
    public void enviarMensaje(String mensaje){
        try{
            
            bufferedWriter.write(mensaje);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            
        } catch (IOException e){
            cerrarTodo(socket, bufferedReader, bufferedWriter);
        }
    }
    /**
     * Método utilizado para cerrar la conexión del socket.
     * @param socket Socket de la conexión.
     * @param bufferedReader Buffered reader de la conexión.
     * @param bufferedWriter Buffered writer de la conexión.
     */
    public void cerrarTodo(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter){
        try{
            if(bufferedReader != null){
                bufferedReader.close();
            }
            if(bufferedWriter != null){
                bufferedWriter.close();
            }
            if(socket != null){
                socket.close();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    /**
     * Método utilizado para registrar a un Usuario
     * @param usuario Usuario a registrar.
     * @return Usuario registrado.
     */
    @Override
    public Usuario registrarUsuario(Usuario usuario) {
        String usuarioSerializado= Deserealizador.getInstancia().serializarUsuario(usuario);
        Solicitud solicitud= new Solicitud(Operacion.registrar_usuario, usuarioSerializado);
        String solicitudSerializada= Deserealizador.getInstancia().serializarSolicitud(solicitud);
        String respuestaServidor= this.enviarSolicitud(solicitudSerializada);
        Solicitud solicitudRespuesta= Deserealizador.getInstancia().deserializarSolicitud(respuestaServidor);
        Usuario respuesta= Deserealizador.getInstancia().deserealizarUsuario(solicitudRespuesta.getRespuesta());
        if(respuesta!=null){
            return respuesta;
        }else{
            throw new ErrorGuardarUsuarioException(solicitudRespuesta.getRespuesta());
        }
    }
    /**
     * Método utilizado para iniciar sesión de manera local.
     * @param usuario Usuario para iniciar sesión.
     * @return Usuario de la sesión.
     */
    @Override
    public Usuario iniciarSesion(Usuario usuario) {
        String usuarioSerializado= Deserealizador.getInstancia().serializarUsuario(usuario);
        Solicitud solicitud= new Solicitud(Operacion.iniciar_sesion, usuarioSerializado);
        String solicitudSerializada= Deserealizador.getInstancia().serializarSolicitud(solicitud);
        String respuestaServidor= this.enviarSolicitud(solicitudSerializada);
        System.out.println(respuestaServidor);
        Solicitud solicitudRespuesta= Deserealizador.getInstancia().deserializarSolicitud(respuestaServidor);
        Usuario respuesta= Deserealizador.getInstancia().deserealizarUsuario(solicitudRespuesta.getRespuesta());
        if(respuesta!=null){
            return respuesta;
        }
        else{
            throw new ErrorBusquedaUsuarioException(solicitudRespuesta.getRespuesta());
        }
    }
    /**
     * Método utilizado para iniciar sesión con facebook.
     * @param usuario Usuario para iniciar sesión con facebook.
     * @return Usuario de la sesión.
     */
    @Override
    public Usuario iniciarSesionFacebook(Usuario usuario) {
        String usuarioSerializado= Deserealizador.getInstancia().serializarUsuario(usuario);
        Solicitud solicitud= new Solicitud(Operacion.iniciar_sesion_facebook, usuarioSerializado);
        String solicitudSerializada= Deserealizador.getInstancia().serializarSolicitud(solicitud);
        String respuestaServidor= this.enviarSolicitud(solicitudSerializada);
        System.out.println(respuestaServidor);
        Solicitud solicitudRespuesta= Deserealizador.getInstancia().deserializarSolicitud(respuestaServidor);
        Usuario respuesta= Deserealizador.getInstancia().deserealizarUsuario(solicitudRespuesta.getRespuesta());
        if(respuesta!=null){
            return respuesta;
        }
        else{
            throw new ErrorBusquedaUsuarioException(solicitudRespuesta.getRespuesta());
        }
    }
    /**
     * Método utilizado para registrar una publicación nueva.
     * @param publicacion Publicación a registar.
     * @return Publicación registrada.
     */
    @Override
    public Publicacion registrarPublicacion(Publicacion publicacion) {
        String publicacionSerealizada = Deserealizador.getInstancia().serializarPublicacion(publicacion);
        Solicitud solicitud = new Solicitud (Operacion.registrar_publicacion, publicacionSerealizada);
        String solicitudSerializada= Deserealizador.getInstancia().serializarSolicitud(solicitud);
        String respuestaServidor= this.enviarSolicitud(solicitudSerializada);
        Solicitud solicitudRespuesta= Deserealizador.getInstancia().deserializarSolicitud(respuestaServidor);
        Publicacion respuesta= Deserealizador.getInstancia().deserealizarPublicacion(solicitudRespuesta.getRespuesta());
        if(respuesta!=null){
            return respuesta;
        }else{
            throw new ErrorGuardarPublicacionException(solicitudRespuesta.getRespuesta());
        }

    }
    /**
     * Método utilizado para registrar un suscriptor al evento de registar publicación.
     * @param suscriptor Suscriptor a registrar.
     */
    @Override
    public void suscribirseEventoRegistrarPublicacion(IObservadorRegistrarPublicacion suscriptor) {
        ObservadorRegistrarPublicacion.getInstancia().suscribirse(suscriptor);
    }
    /**
     * Método utilizado para eliminar a un suscriptor del evento de registrar publicación.
     * @param suscriptor Suscriptor a eliminar.
     */
    @Override
    public void desuscribirseEventoRegistrarPublicacion(IObservadorRegistrarPublicacion suscriptor) {
        ObservadorRegistrarPublicacion.getInstancia().desuscribirse(suscriptor);
    }
    /**
     * Método utilizado para consultar todas las publicaciones existentes.
     * @return Resultado de la busqueda.
     */
    @Override
    public List<Publicacion> consultarPublicaciones() {
        Solicitud solicitud= new Solicitud(Operacion.consultar_publicaciones);
        String solicitudSerealizada= Deserealizador.getInstancia().serializarSolicitud(solicitud);
        String respuestaServidor= this.enviarSolicitud(solicitudSerealizada);
        Solicitud solicitudRespuesta= Deserealizador.getInstancia().deserializarSolicitud(respuestaServidor);
        List<Publicacion> publicaciones= Deserealizador.getInstancia().deserealizarLista(solicitudRespuesta.getRespuesta());
        if(publicaciones!=null){
            return publicaciones;
        } else{
            throw new ErrorBusquedaPublicacionesException(solicitudRespuesta.getRespuesta());
        }
    }
    /**
     * Método utilizado para registrar un suscriptor al evento de editar publicación.
     * @param suscriptor Suscriptor a registrar.
     */
    @Override
    public void suscribirseEventoEditarPublicacion(IObservadorEditarPublicacion suscriptor) {
        ObservadorEditarPublicacion.getInstancia().suscribirse(suscriptor);
    }
    /**
     * Método utilizado para eliminar a un suscriptor del evento de editar publicación.
     * @param suscriptor Suscriptor a eliminar.
     */
    @Override
    public void desuscribirseEventoEditarPublicacion(IObservadorEditarPublicacion suscriptor) {
        ObservadorEditarPublicacion.getInstancia().desuscribirse(suscriptor);
    }
    /**
     * Método utilizado para editar una publicación existente.
     * @param publicacion Publicación con datos actualizados.
     * @return Publicación actualizada.
     */
    @Override
    public Publicacion editarPublicacion(Publicacion publicacion) {
        String publicacionSerealizada = Deserealizador.getInstancia().serializarPublicacion(publicacion);
        Solicitud solicitud = new Solicitud (Operacion.editar_publicacion, publicacionSerealizada);
        String solicitudSerializada= Deserealizador.getInstancia().serializarSolicitud(solicitud);
        String respuestaServidor= this.enviarSolicitud(solicitudSerializada);
        Solicitud solicitudRespuesta= Deserealizador.getInstancia().deserializarSolicitud(respuestaServidor);
        Publicacion respuesta= Deserealizador.getInstancia().deserealizarPublicacion(solicitudRespuesta.getRespuesta());
        if(respuesta!=null){
            return respuesta;
        }else{
            throw new ErrorGuardarPublicacionException(solicitudRespuesta.getRespuesta());
        }
    }
    /**
     * Método utilizado para eliminar una publicación existente.
     * @param publicacion Publicación a eliminar.
     * @return Publicación eliminada.
     */
    @Override
    public Publicacion eliminarPublicacion(Publicacion publicacion) {
        String publicacionSerealizada = Deserealizador.getInstancia().serializarPublicacion(publicacion);
        Solicitud solicitud = new Solicitud (Operacion.eliminar_publicacion, publicacionSerealizada);
        String solicitudSerializada= Deserealizador.getInstancia().serializarSolicitud(solicitud);
        String respuestaServidor= this.enviarSolicitud(solicitudSerializada);
        Solicitud solicitudRespuesta= Deserealizador.getInstancia().deserializarSolicitud(respuestaServidor);
        Publicacion respuesta= Deserealizador.getInstancia().deserealizarPublicacion(solicitudRespuesta.getRespuesta());
        if(respuesta!=null){
            return respuesta;
        }else{
            throw new ErrorEliminarPublicacionException(solicitudRespuesta.getRespuesta());
        }
    }
    /**
     * Método utilizado para registrar un suscriptor al evento de eliminar publicación.
     * @param suscriptor Suscriptor a registrar.
     */
    @Override
    public void suscribirseEventoEliminarPublicacion(IObservadorEliminarPublicacion suscriptor) {
        ObservadorEliminarPublicacion.getInstancia().suscribirse(suscriptor);
    }
    /**
     * Método utilizado para eliminar a un suscriptor del evento de eliminar publicación.
     * @param suscriptor Suscriptor a eliminar.
     */
    @Override
    public void desuscribirseEventoEliminarPublicacion(IObservadorEliminarPublicacion suscriptor) {
        ObservadorEliminarPublicacion.getInstancia().desuscribirse(suscriptor);
    }
    /**
     * Método utilizado para reigstrar un comentario nuevo.
     * @param comentario Comentario a registrar.
     * @return Comentario registrado.
     */
    @Override
    public Comentario registrarComentario(Comentario comentario) {
        String comentarioSerealizado = Deserealizador.getInstancia().serializarComentario(comentario);
        Solicitud solicitud = new Solicitud (Operacion.registrar_comentario, comentarioSerealizado);
        String solicitudSerializada= Deserealizador.getInstancia().serializarSolicitud(solicitud);
        String respuestaServidor= this.enviarSolicitud(solicitudSerializada);
        Solicitud solicitudRespuesta= Deserealizador.getInstancia().deserializarSolicitud(respuestaServidor);
        Comentario respuesta= Deserealizador.getInstancia().deserealizarComentario(solicitudRespuesta.getRespuesta());
        if(respuesta!=null){
            return respuesta;
        }else{
            throw new ErrorGuardarComentarioException(solicitudRespuesta.getRespuesta());
        }
    }
    /**
     * Método utilizado para actualizar un comentario existente.
     * @param comentario Comentario con datos acutalizados.
     * @return Comentario actualizado.
     */
    @Override
    public Comentario editarComentario(Comentario comentario) {
        String comentarioSerealizado = Deserealizador.getInstancia().serializarComentario(comentario);
        Solicitud solicitud = new Solicitud (Operacion.editar_comentario, comentarioSerealizado);
        String solicitudSerializada= Deserealizador.getInstancia().serializarSolicitud(solicitud);
        String respuestaServidor= this.enviarSolicitud(solicitudSerializada);
        Solicitud solicitudRespuesta= Deserealizador.getInstancia().deserializarSolicitud(respuestaServidor);
        Comentario respuesta= Deserealizador.getInstancia().deserealizarComentario(solicitudRespuesta.getRespuesta());
        if(respuesta!=null){
            return respuesta;
        }else{
            throw new ErrorEditarComentarioException(solicitudRespuesta.getRespuesta());
        }
    }
    /**
     * Método utilizado para registrar un suscriptor al evento de registar comentario.
     * @param suscriptor Suscriptor a registrar.
     */
    @Override
    public void suscribirseEventoRegistrarComentario(IObservadorRegistrarComentario suscriptor) {
        ObservadorRegistrarComentario.getInstancia().suscribirse(suscriptor);
    }
    /**
     * Método utilizado para eliminar a un suscriptor del evento de registrar comentario.
     * @param suscriptor Suscriptor a eliminar.
     */
    @Override
    public void desuscribirseEventoRegistrarComentario(IObservadorRegistrarComentario suscriptor) {
        ObservadorRegistrarComentario.getInstancia().desuscribirse(suscriptor);
    }
    /**
     * Método utilizado para registrar un suscriptor al evento de editar comentario.
     * @param suscriptor Suscriptor a registrar.
     */
    @Override
    public void suscribirseEventoEditarComentario(IObservadorEditarComentario suscriptor) {
        ObservadorEditarComentario.getInstancia().suscribirse(suscriptor);
    }
    /**
     * Método utilizado para eliminar a un suscriptor del evento de editar comentario.
     * @param suscriptor Suscriptor a eliminar.
     */
    @Override
    public void desuscribirseEventoEditarComentario(IObservadorEditarComentario suscriptor) {
        ObservadorEditarComentario.getInstancia().desuscribirse(suscriptor);
    }
    /**
     * Método utilizado para registrar un suscriptor al evento de eliminar comentario.
     * @param suscriptor Suscriptor a registrar.
     */
    @Override
    public void suscribirseEventoEliminarComentario(IObservadorEliminarComentario suscriptor) {
        ObservadorEliminarComentario.getInstancia().suscribirse(suscriptor);
    }
    /**
     * Método utilizado para eliminar a un suscriptor del evento de eliminar comentario.
     * @param suscriptor Suscriptor a eliminar.
     */
    @Override
    public void desuscribirseEventoEliminarComentario(IObservadorEliminarComentario suscriptor) {
        ObservadorEliminarComentario.getInstancia().desuscribirse(suscriptor);
    }
    /**
     * Método utilizado para eliminar un comentario existente.
     * @param comentario Comentario a eliminar.
     * @return Comentario eliminado.
     */
    @Override
    public Comentario eliminarComentario(Comentario comentario) {
        String comentarioSerealizado = Deserealizador.getInstancia().serializarComentario(comentario);
        Solicitud solicitud = new Solicitud (Operacion.eliminar_comentario, comentarioSerealizado);
        String solicitudSerializada= Deserealizador.getInstancia().serializarSolicitud(solicitud);
        String respuestaServidor= this.enviarSolicitud(solicitudSerializada);
        Solicitud solicitudRespuesta= Deserealizador.getInstancia().deserializarSolicitud(respuestaServidor);
        Comentario respuesta= Deserealizador.getInstancia().deserealizarComentario(solicitudRespuesta.getRespuesta());
        if(respuesta!=null){
            return respuesta;
        }else{
            throw new ErrorEliminarComentarioException(solicitudRespuesta.getRespuesta());
        }
    }
    /**
     * Método utilizado para editar a un usuario registrado.
     * @param usuario Usuario con datos actualizados.
     * @return Usuario actualizado.
     */
    @Override
    public Usuario editarPerfilUsuario(Usuario usuario) {
        String usuarioSerializado= Deserealizador.getInstancia().serializarUsuario(usuario);
        Solicitud solicitud= new Solicitud(Operacion.editar_perfil, usuarioSerializado);
        String solicitudSerializada= Deserealizador.getInstancia().serializarSolicitud(solicitud);
        String respuestaServidor= this.enviarSolicitud(solicitudSerializada);
        Solicitud solicitudRespuesta= Deserealizador.getInstancia().deserializarSolicitud(respuestaServidor);
        Usuario respuesta= Deserealizador.getInstancia().deserealizarUsuario(solicitudRespuesta.getRespuesta());
        if(respuesta!=null){
            return respuesta;
        }else{
            throw new ErrorEditarUsuarioException(solicitudRespuesta.getRespuesta());
        }
    }
    /**
     * Método utilizado para consultar a un usuario mediante el nombre, devuelve la primera 
     * coincidencia.
     * @param usuario Usuario a consultar.
     * @return Resultado de la busqueda.
     */
    @Override
    public Usuario consultarUsuarioNombre(Usuario usuario) {
        String usuarioSerializado= Deserealizador.getInstancia().serializarUsuario(usuario);
        Solicitud solicitud= new Solicitud(Operacion.consultar_usuarioNombre, usuarioSerializado);
        String solicitudSerializada= Deserealizador.getInstancia().serializarSolicitud(solicitud);
        String respuestaServidor= this.enviarSolicitud(solicitudSerializada);
        Solicitud solicitudRespuesta= Deserealizador.getInstancia().deserializarSolicitud(respuestaServidor);
        Usuario respuesta= Deserealizador.getInstancia().deserealizarUsuario(solicitudRespuesta.getRespuesta());
        if(respuesta!=null){
            return respuesta;
        }else{
            throw new ErrorBusquedaUsuarioException(solicitudRespuesta.getRespuesta());
        }
    }
    /**
     * Método utilizado para enviar un mensaje a otro usuario mediante los método de notificación 
     * disponibles.
     * @param mensaje Mensaje a enviar.
     * @return Mensaje enviado.
     */
    @Override
    public Mensaje enviarMensaje(Mensaje mensaje) {
        String mensajeSerializado= Deserealizador.getInstancia().serializarMensaje(mensaje);
        Solicitud solicitud= new Solicitud(Operacion.registrar_mensaje, mensajeSerializado);
        String solicitudSerializada= Deserealizador.getInstancia().serializarSolicitud(solicitud);
        String respuestaServidor= this.enviarSolicitud(solicitudSerializada);
        Solicitud solicitudRespuesta= Deserealizador.getInstancia().deserializarSolicitud(respuestaServidor);
        Mensaje respuesta= Deserealizador.getInstancia().deserealizarMensaje(solicitudRespuesta.getRespuesta());
        if(respuesta!=null){
            return respuesta;
        }else{
            throw new ErrorEnviarMensajeException(solicitudRespuesta.getRespuesta());
        }
    }
    /**
     * Método utilizado para consultar las publicaciones mediante un hashtag, devuelve todas las 
     * publicaciones que tengan asociado un hashtag con el mismo nombre.
     * @param hashtag Hashtag a buscar.
     * @return Resultado de la busqueda.
     */
    @Override
    public List<Publicacion> consultarPublicaciones(Hashtag hashtag) {
        String hashtagSerializado= Deserealizador.getInstancia().serializarHashtag(hashtag);
        Solicitud solicitud= new Solicitud(Operacion.consultar_publicacionesHashtag, hashtagSerializado);
        String solicitudSerealizada= Deserealizador.getInstancia().serializarSolicitud(solicitud);
        String respuestaServidor= this.enviarSolicitud(solicitudSerealizada);
        Solicitud solicitudRespuesta= Deserealizador.getInstancia().deserializarSolicitud(respuestaServidor);
        List<Publicacion> publicaciones= Deserealizador.getInstancia().deserealizarLista(solicitudRespuesta.getRespuesta());
        if(publicaciones!=null){
            return publicaciones;
        } else{
            throw new ErrorConsultarPublicacionException(solicitudRespuesta.getRespuesta());
        }
    }


}
