/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proxyclientebroker;

import com.fasterxml.jackson.databind.ObjectMapper;
import dominio.Comentario;
import dominio.Operacion;
import dominio.Publicacion;
import dominio.Solicitud;
import dominio.Usuario;

/**
 *
 * @author Admin
 */
public class Proxy {
    
    public Proxy(){
        
    }
    
//    public String serializarObjetoOperacion(Operacion operacion){
//        try{
//            ObjectMapper mapper=new ObjectMapper();
//            String operacionSerializada= mapper.writeValueAsString(operacion);
//            return operacionSerializada;
//        } catch(Exception e){
//            e.printStackTrace();
//        }
//        return null;
//    }
    
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
    
    public String serializarSolicitudRegistroPublicacion(Publicacion publicacion){
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
    
    public String serializarSolicitudRegistroComentario(Comentario comentario){
        try{
            ObjectMapper mapper=new ObjectMapper();
            String solicitudSerializada= mapper.writeValueAsString(comentario);
            return solicitudSerializada;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
