/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proxyclientebroker;

import com.fasterxml.jackson.databind.ObjectMapper;
import dominio.Comentario;
import dominio.Publicacion;
import dominio.Usuario;

/**
 *
 * @author Admin
 */
public class Proxy {
    
    public Proxy(){
        
    }
    
    public String serializarSolicitudRegistroUsuario(Usuario usuario){
        try{
            ObjectMapper mapper=new ObjectMapper();
            String solicitudSerializada= mapper.writeValueAsString(usuario);
            return solicitudSerializada;
        } catch(Exception e){
            e.printStackTrace();
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
