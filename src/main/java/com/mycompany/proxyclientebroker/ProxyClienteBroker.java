/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.proxyclientebroker;

import dominio.Operacion;
import dominio.Publicacion;
import dominio.Solicitud;
import dominio.Usuario;
import interfaces.IProxy;
import interfaces.ISuscriptorEventoRegistrarPublicacion;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class ProxyClienteBroker implements IProxy{
    private ISuscriptorEventoRegistrarPublicacion suscriptorEventoRegistrarPublicacion;
    private String HOST= "192.168.0.4";
    private int PORT= 5000;
//    private static ProxyClienteBroker proxyClienteBroker;
    private Proxy proxy;
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String username;
    
    public ProxyClienteBroker(){
        try{
            this.socket=new Socket(HOST, PORT);
            this.bufferedReader= new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter= new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.proxy= new Proxy();
//            this.enviarMensaje(username);
            this.escucharPorMensaje();
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
            
//            Scanner tec= new Scanner(System.in);
//            while(socket.isConnected()){
//                String messageToSend= tec.nextLine();
//                bufferedWriter.write(username+": "+messageToSend);
//                bufferedWriter.newLine();
//                bufferedWriter.flush();
//            }
        } catch (IOException e){
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }
    
    public void escucharPorMensaje(){
        new Thread(new Runnable(){
            @Override
            public void run(){
                String mensajeBroker;
                
                while(socket.isConnected()){
                    try{
                        mensajeBroker= bufferedReader.readLine();
                        canalizarNotificacion(mensajeBroker);
                    } catch (IOException e){
                        closeEverything(socket, bufferedReader, bufferedWriter);
                    }
                }
            }
        }).start();
    }
    
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
    
    public void canalizarNotificacion(String notificacion){
        Solicitud notificacionServidor= proxy.deserializarSolicitud(notificacion);
        Operacion tipoNotificacion= notificacionServidor.getOperacion();
        switch(tipoNotificacion){
            case registrar_usuario:
                this.notificarEventoRegistrarUsuario(notificacion); break;
            case iniciar_sesion:
                this.notificarEventoIniciarSesion(notificacion); break;
            case registrar_publicacion:
                this.notificarEventoRegistrarPublicacion(notificacionServidor.getRespuesta()); break;
            default:
                return;
        }
    }

    @Override
    public String registrarUsuario(Usuario usuario) {
        String usuarioSerializado= proxy.serializarUsuario(usuario);
        Solicitud solicitud= new Solicitud(Operacion.registrar_usuario, usuarioSerializado);
        String solicitudSerializada= proxy.serializarSolicitud(solicitud);
        String respuestaServidor= this.enviarSolicitud(solicitudSerializada);
        Solicitud solicitudRespuesta= proxy.deserializarSolicitud(respuestaServidor);
        Usuario respuesta= proxy.deserealizarUsuario(solicitudRespuesta.getRespuesta());
        if(respuesta==null){
            return solicitudRespuesta.getRespuesta();
        }else{
            return respuesta.toString();
        }
    }

    @Override
    public String iniciarSesion(Usuario usuario) {
        String usuarioSerializado= proxy.serializarUsuario(usuario);
        Solicitud solicitud= new Solicitud(Operacion.iniciar_sesion, usuarioSerializado);
        String solicitudSerializada= proxy.serializarSolicitud(solicitud);
        String respuestaServidor= this.enviarSolicitud(solicitudSerializada);
        System.out.println(respuestaServidor);
        Solicitud solicitudRespuesta= proxy.deserializarSolicitud(respuestaServidor);
//        if(solicitudRespuesta.getRespuesta().startsWith("Excepción: ")){
//            return solicitudRespuesta.getRespuesta();
//        }else{
//            Usuario respuesta= proxy.deserealizarUsuario(solicitudRespuesta.getRespuesta());
//            return respuesta.toString();
//        }
        Usuario respuesta= proxy.deserealizarUsuario(solicitudRespuesta.getRespuesta());
        if(respuesta==null){
            return solicitudRespuesta.getRespuesta();
        }
        else{
            return respuesta.toString();
        }
    }

    @Override
    public String registrarPublicacion(Publicacion publicacion) {
        String publicacionSerealizada = proxy.serializarSolicitudRegistroPublicacion(publicacion);
        Solicitud solicitud = new Solicitud (Operacion.registrar_publicacion, publicacionSerealizada);
        String solicitudSerializada= proxy.serializarSolicitud(solicitud);
        String respuestaServidor= this.enviarSolicitud(solicitudSerializada);
        Solicitud solicitudRespuesta= proxy.deserializarSolicitud(respuestaServidor);
        return solicitudRespuesta.getRespuesta();

    }

    @Override
    public void suscribirseEventoRegistrarPublicacion(ISuscriptorEventoRegistrarPublicacion suscriptor) {
//        Solicitud suscripcion= new Solicitud(Operacion.suscribir_observador_muro);
//        String suscripcionSerializada= proxy.serializarSolicitud(suscripcion);
//        String respuestaServidor= this.enviarSolicitud(suscripcionSerializada);
//        Solicitud solicitudRespuesta= proxy.deserializarSolicitud(respuestaServidor);
//        if(solicitudRespuesta.getRespuesta().equalsIgnoreCase("Éxito")){
//            System.out.println("XD");
//            this.suscriptor= suscriptor;
//            this.escucharPorMensaje();
//        }
        this.suscriptorEventoRegistrarPublicacion= suscriptor;
    }
    
    public void notificarEventoRegistrarPublicacion(String actualizacion){
        this.suscriptorEventoRegistrarPublicacion.notificarPublicacion(actualizacion);
    }
    
    public void notificarEventoRegistrarUsuario(String actualizacion){
        
    }
    
    public void notificarEventoIniciarSesion(String actualizacion){
        
    }

    @Override
    public void desuscribirseEventoRegistrarPublicacion() {
//        Solicitud suscripcion= new Solicitud(Operacion.desuscribir_observador_muro);
//        String suscripcionSerializada= proxy.serializarSolicitud(suscripcion);
//        String respuestaServidor= this.enviarSolicitud(suscripcionSerializada);
//        Solicitud solicitudRespuesta= proxy.deserializarSolicitud(respuestaServidor);
//        if(solicitudRespuesta.getRespuesta().equalsIgnoreCase("Éxito")){
//            this.suscriptor= null;
//        }
        this.suscriptorEventoRegistrarPublicacion=null;
    }
}
