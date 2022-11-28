/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oyentes;

import com.mycompany.proxyclientebroker.Proxy;
import dominio.Operacion;
import dominio.Solicitud;
import interfaces.IObservadorEditarPublicacion;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 *
 * @author Admin
 */
public class OyenteEditarPublicacion {
    private String HOST= "192.168.0.4";
    private int PORT= 5000;
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private IObservadorEditarPublicacion observador;
    
    public OyenteEditarPublicacion(IObservadorEditarPublicacion observador){
        try{
            this.socket= new Socket(HOST, PORT);
            this.bufferedReader= new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter= new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.observador= observador;
            this.registrarObservador();
            this.escucharPorMensaje();
        } catch(IOException e){
            cerrarTodo(socket, bufferedReader, bufferedWriter);
        }
    }
    
    private void registrarObservador(){
        Solicitud solicitud= new Solicitud(Operacion.suscribir_observador_editarPublicacion);
        String mensaje= Proxy.getInstancia().serializarSolicitud(solicitud);
        this.enviarMensaje(mensaje);
    }
    
    public void eliminarObservador(){
        Solicitud solicitud= new Solicitud(Operacion.desuscribir_observador_editarPublicacion);
        String mensaje= Proxy.getInstancia().serializarSolicitud(solicitud);
        this.enviarMensaje(mensaje);
    }
    
    public void enviarMensaje(String mensaje){
        try{
            
            bufferedWriter.write(mensaje);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            
        } catch (IOException e){
            cerrarTodo(socket, bufferedReader, bufferedWriter);
        }
    }
    
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

    public void escucharPorMensaje(){
        new Thread(new Runnable(){
            @Override
            public void run(){
                String mensajeOyenteBroker;
                
                while(socket.isConnected()){
                    try{
                        mensajeOyenteBroker= bufferedReader.readLine();
                        System.out.println(mensajeOyenteBroker);
                        observador.notificarEdicionPublicacion(Proxy.getInstancia().deserealizarPublicacion(mensajeOyenteBroker));
                    } catch (IOException e){
                        cerrarTodo(socket, bufferedReader, bufferedWriter);
                    }
                }
            }
        }).start();
    }
}
