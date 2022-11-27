/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.proxyclientebroker;

import dominio.Operacion;
import dominio.Publicacion;
import dominio.Solicitud;
import dominio.Usuario;
import excepciones.ErrorBusquedaPublicacionesException;
import excepciones.ErrorBusquedaUsuarioException;
import excepciones.ErrorGuardarPublicacionException;
import excepciones.ErrorGuardarUsuarioException;
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
import observadores.ObservadorRegistrarPublicacion;

/**
 *
 * @author Admin
 */
public class ProxyClienteBroker implements IProxy{
//    private SuscriptorRegistrarPublicacion suscriptorRegistrarPublicacion;
    private String HOST= "192.168.0.4";
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


}
