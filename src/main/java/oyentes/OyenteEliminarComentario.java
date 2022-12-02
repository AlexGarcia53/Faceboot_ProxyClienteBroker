/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oyentes;

import com.mycompany.proxyclientebroker.Deserealizador;
import dominio.Operacion;
import dominio.Solicitud;
import interfaces.IObservadorEliminarComentario;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * Clase que representa a un oyente que escucha a uno de los eventos específicos.
 * @author Equipo Broker.
 */
public class OyenteEliminarComentario {
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
     * Atributo que contiene al observador del evento.
     */
    private IObservadorEliminarComentario observador;
    /**
     * Método constructor de la clase.
     * @param observador Observador del evento.
     */
    public OyenteEliminarComentario(IObservadorEliminarComentario observador){
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
    /**
     * Método utilizado para registrar al observador al evento.
     */
    private void registrarObservador(){
        Solicitud solicitud= new Solicitud(Operacion.suscribir_observador_eliminarComentario);
        String mensaje= Deserealizador.getInstancia().serializarSolicitud(solicitud);
        this.enviarMensaje(mensaje);
    }
    /**
     * Método utilizado para eliminar al observador del evento.
     */
    public void eliminarObservador(){
        Solicitud solicitud= new Solicitud(Operacion.desuscribir_observador_eliminarComentario);
        String mensaje= Deserealizador.getInstancia().serializarSolicitud(solicitud);
        this.enviarMensaje(mensaje);
    }
    /**
     * Método utilizado para enviar un mensaje a través del socket.
     * @param mensaje Mensaje a enviar.
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
     * Método utilizado para cerrar la conexión.
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
     * Método utilizado para escuchar por actualizaciones del evento y notificar al observador.
     */
    public void escucharPorMensaje(){
        new Thread(new Runnable(){
            @Override
            public void run(){
                String mensajeOyenteBroker;
                
                while(socket.isConnected()){
                    try{
                        mensajeOyenteBroker= bufferedReader.readLine();
                        System.out.println(mensajeOyenteBroker);
                        observador.notificarEliminacionComentario(Deserealizador.getInstancia().deserealizarComentario(mensajeOyenteBroker));
                    } catch (IOException e){
                        cerrarTodo(socket, bufferedReader, bufferedWriter);
                    }
                }
            }
        }).start();
    }
}
