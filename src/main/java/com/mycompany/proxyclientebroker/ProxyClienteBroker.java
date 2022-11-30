/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.proxyclientebroker;

import dominio.Comentario;
import dominio.Operacion;
import dominio.Publicacion;
import dominio.Solicitud;
import dominio.Usuario;
import excepciones.ErrorBusquedaPublicacionesException;
import excepciones.ErrorBusquedaUsuarioException;
import excepciones.ErrorEditarComentarioException;
import excepciones.ErrorEditarUsuarioException;
import excepciones.ErrorEliminarComentarioException;
import excepciones.ErrorEliminarPublicacionException;
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
 *
 * @author Admin
 */
public class ProxyClienteBroker implements IProxy{
//    private SuscriptorRegistrarPublicacion suscriptorRegistrarPublicacion;
    private String HOST= "127.0.0.1";
    private int PORT= 5000;
//    private static ProxyClienteBroker proxyClienteBroker;
//    private Proxy proxy;
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
//    private String username;
    
    public ProxyClienteBroker(){
        try{
            this.socket=new Socket(HOST, PORT);
            this.bufferedReader= new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter= new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
//            this.proxy= new Proxy();
//            this.enviarMensaje(username);
//            this.escucharPorMensaje();
        } catch (IOException e){
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }
    
//    public static ProxyClienteBroker obtenerProxyClienteBroker(){
//        if(proxyClienteBroker==null){
//            proxyClienteBroker= new ProxyClienteBroker();
//        }
//        return proxyClienteBroker;
//    }
    
    public String enviarSolicitud(String solicitud){
        String respuesta = "";
        try{
            bufferedWriter.write(solicitud);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            
            respuesta= bufferedReader.readLine();
            System.out.println(respuesta);
        } catch (IOException e){
            closeEverything(socket, bufferedReader, bufferedWriter);
        } finally{
            return respuesta;
        }
    }
    
    public void enviarMensaje(String mensaje){
        try{
            
            bufferedWriter.write(mensaje);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            
        } catch (IOException e){
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }
    
//    public void escucharPorMensaje(){
//        new Thread(new Runnable(){
//            @Override
//            public void run(){
//                String mensajeBroker;
//                
//                while(socket.isConnected() && suscriptorRegistrarPublicacion!=null){
//                    try{
//                        mensajeBroker= bufferedReader.readLine();
//                        System.out.println(mensajeBroker);
//                        canalizarNotificacion(mensajeBroker);
//                    } catch (IOException e){
//                        closeEverything(socket, bufferedReader, bufferedWriter);
//                    }
//                }
//            }
//        }).start();
//    }
    
    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter){
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
    
//    public static void main(String[] args) throws IOException{
//        Scanner tec= new Scanner(System.in);
//        System.out.print("Enter your username for the group chat: ");
//        String username= tec.nextLine();
//        Socket socket= new Socket("192.168.1.70", 5000);
//        ProxyClienteBroker client= new ProxyClienteBroker(socket, username);
//        client.listenForMessage();
//        client.sendMessage();
//    }
    
//    public void canalizarNotificacion(String notificacion){
//        Solicitud notificacionServidor= Proxy.getInstancia().deserializarSolicitud(notificacion);
//        Operacion tipoNotificacion= notificacionServidor.getOperacion();
//        switch(tipoNotificacion){
//            case registrar_usuario:
//                this.notificarEventoRegistrarUsuario(notificacion); break;
//            case iniciar_sesion:
//                this.notificarEventoIniciarSesion(notificacion); break;
//            case registrar_publicacion:
//                this.notificarEventoRegistrarPublicacion(notificacionServidor.getRespuesta()); break;
//            default:
//                return;
//        }
//    }

    @Override
    public Usuario registrarUsuario(Usuario usuario) {
        String usuarioSerializado= Proxy.getInstancia().serializarUsuario(usuario);
        Solicitud solicitud= new Solicitud(Operacion.registrar_usuario, usuarioSerializado);
        String solicitudSerializada= Proxy.getInstancia().serializarSolicitud(solicitud);
        String respuestaServidor= this.enviarSolicitud(solicitudSerializada);
        Solicitud solicitudRespuesta= Proxy.getInstancia().deserializarSolicitud(respuestaServidor);
        Usuario respuesta= Proxy.getInstancia().deserealizarUsuario(solicitudRespuesta.getRespuesta());
        if(respuesta!=null){
            return respuesta;
        }else{
            throw new ErrorGuardarUsuarioException(solicitudRespuesta.getRespuesta());
        }
    }

    @Override
    public Usuario iniciarSesion(Usuario usuario) {
        String usuarioSerializado= Proxy.getInstancia().serializarUsuario(usuario);
        Solicitud solicitud= new Solicitud(Operacion.iniciar_sesion, usuarioSerializado);
        String solicitudSerializada= Proxy.getInstancia().serializarSolicitud(solicitud);
        String respuestaServidor= this.enviarSolicitud(solicitudSerializada);
        System.out.println(respuestaServidor);
        Solicitud solicitudRespuesta= Proxy.getInstancia().deserializarSolicitud(respuestaServidor);
        Usuario respuesta= Proxy.getInstancia().deserealizarUsuario(solicitudRespuesta.getRespuesta());
        if(respuesta!=null){
            return respuesta;
        }
        else{
            throw new ErrorBusquedaUsuarioException(solicitudRespuesta.getRespuesta());
        }
    }
    
    @Override
    public Usuario iniciarSesionFacebook(Usuario usuario) {
        String usuarioSerializado= Proxy.getInstancia().serializarUsuario(usuario);
        Solicitud solicitud= new Solicitud(Operacion.iniciar_sesion_facebook, usuarioSerializado);
        String solicitudSerializada= Proxy.getInstancia().serializarSolicitud(solicitud);
        String respuestaServidor= this.enviarSolicitud(solicitudSerializada);
        System.out.println(respuestaServidor);
        Solicitud solicitudRespuesta= Proxy.getInstancia().deserializarSolicitud(respuestaServidor);
        Usuario respuesta= Proxy.getInstancia().deserealizarUsuario(solicitudRespuesta.getRespuesta());
        if(respuesta!=null){
            return respuesta;
        }
        else{
            throw new ErrorBusquedaUsuarioException(solicitudRespuesta.getRespuesta());
        }
    }
  
    @Override
    public Publicacion registrarPublicacion(Publicacion publicacion) {
        String publicacionSerealizada = Proxy.getInstancia().serializarSolicitudRegistroPublicacion(publicacion);
        Solicitud solicitud = new Solicitud (Operacion.registrar_publicacion, publicacionSerealizada);
        String solicitudSerializada= Proxy.getInstancia().serializarSolicitud(solicitud);
        String respuestaServidor= this.enviarSolicitud(solicitudSerializada);
        Solicitud solicitudRespuesta= Proxy.getInstancia().deserializarSolicitud(respuestaServidor);
        Publicacion respuesta= Proxy.getInstancia().deserealizarPublicacion(solicitudRespuesta.getRespuesta());
        if(respuesta!=null){
            return respuesta;
        }else{
            throw new ErrorGuardarPublicacionException(solicitudRespuesta.getRespuesta());
        }

    }

    @Override
    public void suscribirseEventoRegistrarPublicacion(IObservadorRegistrarPublicacion suscriptor) {
        ObservadorRegistrarPublicacion.getInstancia().suscribirse(suscriptor);
    }

    @Override
    public void desuscribirseEventoRegistrarPublicacion(IObservadorRegistrarPublicacion suscriptor) {
        ObservadorRegistrarPublicacion.getInstancia().desuscribirse(suscriptor);
    }

    @Override
    public List<Publicacion> consultarPublicaciones() {
        Solicitud solicitud= new Solicitud(Operacion.consultar_publicaciones);
        String solicitudSerealizada= Proxy.getInstancia().serializarSolicitud(solicitud);
        String respuestaServidor= this.enviarSolicitud(solicitudSerealizada);
        Solicitud solicitudRespuesta= Proxy.getInstancia().deserializarSolicitud(respuestaServidor);
        List<Publicacion> publicaciones= Proxy.getInstancia().deserealizarLista(solicitudRespuesta.getRespuesta());
        if(publicaciones!=null){
            return publicaciones;
        } else{
            throw new ErrorBusquedaPublicacionesException(solicitudRespuesta.getRespuesta());
        }
    }

    @Override
    public void suscribirseEventoEditarPublicacion(IObservadorEditarPublicacion suscriptor) {
        ObservadorEditarPublicacion.getInstancia().suscribirse(suscriptor);
    }

    @Override
    public void desuscribirseEventoEditarPublicacion(IObservadorEditarPublicacion suscriptor) {
        ObservadorEditarPublicacion.getInstancia().desuscribirse(suscriptor);
    }

    @Override
    public Publicacion editarPublicacion(Publicacion publicacion) {
        String publicacionSerealizada = Proxy.getInstancia().serializarSolicitudRegistroPublicacion(publicacion);
        Solicitud solicitud = new Solicitud (Operacion.editar_publicacion, publicacionSerealizada);
        String solicitudSerializada= Proxy.getInstancia().serializarSolicitud(solicitud);
        String respuestaServidor= this.enviarSolicitud(solicitudSerializada);
        Solicitud solicitudRespuesta= Proxy.getInstancia().deserializarSolicitud(respuestaServidor);
        Publicacion respuesta= Proxy.getInstancia().deserealizarPublicacion(solicitudRespuesta.getRespuesta());
        if(respuesta!=null){
            return respuesta;
        }else{
            throw new ErrorGuardarPublicacionException(solicitudRespuesta.getRespuesta());
        }
    }

    @Override
    public Publicacion eliminarPublicacion(Publicacion publicacion) {
        String publicacionSerealizada = Proxy.getInstancia().serializarSolicitudRegistroPublicacion(publicacion);
        Solicitud solicitud = new Solicitud (Operacion.eliminar_publicacion, publicacionSerealizada);
        String solicitudSerializada= Proxy.getInstancia().serializarSolicitud(solicitud);
        String respuestaServidor= this.enviarSolicitud(solicitudSerializada);
        Solicitud solicitudRespuesta= Proxy.getInstancia().deserializarSolicitud(respuestaServidor);
        Publicacion respuesta= Proxy.getInstancia().deserealizarPublicacion(solicitudRespuesta.getRespuesta());
        if(respuesta!=null){
            return respuesta;
        }else{
            throw new ErrorEliminarPublicacionException(solicitudRespuesta.getRespuesta());
        }
    }

    @Override
    public void suscribirseEventoEliminarPublicacion(IObservadorEliminarPublicacion suscriptor) {
        ObservadorEliminarPublicacion.getInstancia().suscribirse(suscriptor);
    }

    @Override
    public void desuscribirseEventoEliminarPublicacion(IObservadorEliminarPublicacion suscriptor) {
        ObservadorEliminarPublicacion.getInstancia().desuscribirse(suscriptor);
    }

    @Override
    public Comentario registrarComentario(Comentario comentario) {
        String comentarioSerealizado = Proxy.getInstancia().serializarComentario(comentario);
        Solicitud solicitud = new Solicitud (Operacion.registrar_comentario, comentarioSerealizado);
        String solicitudSerializada= Proxy.getInstancia().serializarSolicitud(solicitud);
        String respuestaServidor= this.enviarSolicitud(solicitudSerializada);
        Solicitud solicitudRespuesta= Proxy.getInstancia().deserializarSolicitud(respuestaServidor);
        Comentario respuesta= Proxy.getInstancia().deserealizarComentario(solicitudRespuesta.getRespuesta());
        if(respuesta!=null){
            return respuesta;
        }else{
            throw new ErrorGuardarComentarioException(solicitudRespuesta.getRespuesta());
        }
    }

    @Override
    public Comentario editarComentario(Comentario comentario) {
        String comentarioSerealizado = Proxy.getInstancia().serializarComentario(comentario);
        Solicitud solicitud = new Solicitud (Operacion.editar_comentario, comentarioSerealizado);
        String solicitudSerializada= Proxy.getInstancia().serializarSolicitud(solicitud);
        String respuestaServidor= this.enviarSolicitud(solicitudSerializada);
        Solicitud solicitudRespuesta= Proxy.getInstancia().deserializarSolicitud(respuestaServidor);
        Comentario respuesta= Proxy.getInstancia().deserealizarComentario(solicitudRespuesta.getRespuesta());
        if(respuesta!=null){
            return respuesta;
        }else{
            throw new ErrorEditarComentarioException(solicitudRespuesta.getRespuesta());
        }
    }

    @Override
    public void suscribirseEventoRegistrarComentario(IObservadorRegistrarComentario suscriptor) {
        ObservadorRegistrarComentario.getInstancia().suscribirse(suscriptor);
    }

    @Override
    public void desuscribirseEventoRegistrarComentario(IObservadorRegistrarComentario suscriptor) {
        ObservadorRegistrarComentario.getInstancia().desuscribirse(suscriptor);
    }

    @Override
    public void suscribirseEventoEditarComentario(IObservadorEditarComentario suscriptor) {
        ObservadorEditarComentario.getInstancia().suscribirse(suscriptor);
    }

    @Override
    public void desuscribirseEventoEditarComentario(IObservadorEditarComentario suscriptor) {
        ObservadorEditarComentario.getInstancia().desuscribirse(suscriptor);
    }

    @Override
    public void suscribirseEventoEliminarComentario(IObservadorEliminarComentario suscriptor) {
        ObservadorEliminarComentario.getInstancia().suscribirse(suscriptor);
    }

    @Override
    public void desuscribirseEventoEliminarComentario(IObservadorEliminarComentario suscriptor) {
        ObservadorEliminarComentario.getInstancia().desuscribirse(suscriptor);
    }

    @Override
    public Comentario eliminarComentario(Comentario comentario) {
        String comentarioSerealizado = Proxy.getInstancia().serializarComentario(comentario);
        Solicitud solicitud = new Solicitud (Operacion.eliminar_comentario, comentarioSerealizado);
        String solicitudSerializada= Proxy.getInstancia().serializarSolicitud(solicitud);
        String respuestaServidor= this.enviarSolicitud(solicitudSerializada);
        Solicitud solicitudRespuesta= Proxy.getInstancia().deserializarSolicitud(respuestaServidor);
        Comentario respuesta= Proxy.getInstancia().deserealizarComentario(solicitudRespuesta.getRespuesta());
        if(respuesta!=null){
            return respuesta;
        }else{
            throw new ErrorEliminarComentarioException(solicitudRespuesta.getRespuesta());
        }
    }

    @Override
    public Usuario editarPerfilUsuario(Usuario usuario) {
        String usuarioSerializado= Proxy.getInstancia().serializarUsuario(usuario);
        Solicitud solicitud= new Solicitud(Operacion.editar_perfil, usuarioSerializado);
        String solicitudSerializada= Proxy.getInstancia().serializarSolicitud(solicitud);
        String respuestaServidor= this.enviarSolicitud(solicitudSerializada);
        Solicitud solicitudRespuesta= Proxy.getInstancia().deserializarSolicitud(respuestaServidor);
        Usuario respuesta= Proxy.getInstancia().deserealizarUsuario(solicitudRespuesta.getRespuesta());
        if(respuesta!=null){
            return respuesta;
        }else{
            throw new ErrorEditarUsuarioException(solicitudRespuesta.getRespuesta());
        }
    }


}
