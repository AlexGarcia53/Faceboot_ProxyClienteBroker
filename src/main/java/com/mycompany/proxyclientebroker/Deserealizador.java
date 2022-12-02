/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proxyclientebroker;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dominio.Comentario;
import dominio.Hashtag;
import dominio.Mensaje;
import dominio.Operacion;
import dominio.Publicacion;
import dominio.Solicitud;
import dominio.Usuario;
import java.util.List;

/**
 *
 * @author Admin
 */
public class Deserealizador {
    private static Deserealizador deserealizador;
    
    private Deserealizador(){
        
    }
    
    public static Deserealizador getInstancia(){
        if(deserealizador==null){
            deserealizador= new Deserealizador();
        }
        return deserealizador;
    }   
    
    public String serializarUsuario(Usuario usuario){
        try{
            ObjectMapper mapper=new ObjectMapper();
            String solicitudSerializada= mapper.writeValueAsString(usuario);
            return solicitudSerializada;
        } catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
    public String serializarSolicitud(Solicitud solicitud){
        try{
            ObjectMapper mapper=new ObjectMapper();
            String solicitudSerializada= mapper.writeValueAsString(solicitud);
            return solicitudSerializada;
        } catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
    public Solicitud deserializarSolicitud(String solicitud){
        try{
            ObjectMapper conversion= new ObjectMapper();
            return conversion.readValue(solicitud, Solicitud.class);
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    public Usuario deserealizarUsuario(String usuario){
        try{
            ObjectMapper conversion= new ObjectMapper();
            return conversion.readValue(usuario, Usuario.class);
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    public String serializarPublicacion(Publicacion publicacion){
        try{
            ObjectMapper mapper= new ObjectMapper();
            String solicitudSerializada= mapper.writeValueAsString(publicacion);
            return solicitudSerializada;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
    public Publicacion deserealizarPublicacion(String publicacion){
        try{
            ObjectMapper conversion= new ObjectMapper();
            return conversion.readValue(publicacion, Publicacion.class);
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    public String serializarComentario(Comentario comentario){
        try{
            ObjectMapper mapper=new ObjectMapper();
            String solicitudSerializada= mapper.writeValueAsString(comentario);
            return solicitudSerializada;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
    public List<Publicacion> deserealizarLista(String lista){
        try{
            ObjectMapper conversion= new ObjectMapper();
            return conversion.readValue(lista, new TypeReference<List<Publicacion>>(){});
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    public Comentario deserealizarComentario(String comentario){
        try{
            ObjectMapper conversion= new ObjectMapper();
            return conversion.readValue(comentario, Comentario.class);
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    public String serializarMensaje(Mensaje mensaje){
        try {
            ObjectMapper mapper = new ObjectMapper();
            String solicitudSerializada = mapper.writeValueAsString(mensaje);
            return solicitudSerializada;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public Mensaje deserealizarMensaje(String mensaje){
        try{
            ObjectMapper conversion= new ObjectMapper();
            return conversion.readValue(mensaje, Mensaje.class);
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    public String serializarHashtag(Hashtag hashtag){
        try {
            ObjectMapper mapper = new ObjectMapper();
            String solicitudSerializada = mapper.writeValueAsString(hashtag);
            return solicitudSerializada;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public Hashtag deserealizarHashtag(String hashtag){
        try{
            ObjectMapper conversion= new ObjectMapper();
            return conversion.readValue(hashtag, Hashtag.class);
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
