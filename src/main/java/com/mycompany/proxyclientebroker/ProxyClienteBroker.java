/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.proxyclientebroker;

import dominio.Operacion;
import dominio.Solicitud;
import dominio.Usuario;
import interfaces.IProxy;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

    private Proxy proxy;
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private String username;
    
    public ProxyClienteBroker(String username){
        try{
            this.socket=new Socket("192.168.0.4", 5000);
            this.bufferedReader= new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter= new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.outputStream= new ObjectOutputStream(socket.getOutputStream());
            this.inputStream= new ObjectInputStream(socket.getInputStream());
            this.username= username;
            this.proxy= new Proxy();
            this.enviarMensaje(username);
            /**this.escucharPorMensaje();**/
        } catch (IOException e){
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }
    
    public String enviarSolicitud(String solicitud){
        String respuesta = "";
        try{
            bufferedWriter.write(solicitud);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            
            respuesta= bufferedReader.readLine();
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
    
    /** public void escucharPorMensaje(){
        new Thread(new Runnable(){
            @Override
            public void run(){
                Solicitud respuestaBroker;
                
                while(socket.isConnected()){
                    try{
                        respuestaBroker= (Solicitud)inputStream.readObject();
                        System.out.println(respuestaBroker);
                    } catch (IOException e){
                        closeEverything(socket, bufferedReader, bufferedWriter);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(ProxyClienteBroker.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }).start();
    } **/
    
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

    @Override
    public String registrarUsuario(Usuario usuario) {
        String usuarioSerializado= proxy.serializarUsuario(usuario);
        Solicitud solicitud= new Solicitud(Operacion.registrar_usuario, usuarioSerializado);
        String solicitudSerializada= proxy.serializarSolicitud(solicitud);
        String respuestaServidor= this.enviarSolicitud(solicitudSerializada);
        Solicitud solicitudRespuesta= proxy.deserializarSolicitud(respuestaServidor);
        Usuario respuesta= proxy.deserealizarUusuario(solicitudRespuesta.getRespuesta());
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
        Solicitud solicitudRespuesta= proxy.deserializarSolicitud(respuestaServidor);
        Usuario respuesta= proxy.deserealizarUusuario(solicitudRespuesta.getRespuesta());
        if(respuesta==null){
            return solicitudRespuesta.getRespuesta();
        }
        else{
            return respuesta.toString();
        }
    }
}
