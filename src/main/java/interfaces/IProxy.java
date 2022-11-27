/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import dominio.Mensaje;
import dominio.Publicacion;
import dominio.Solicitud;
import dominio.Usuario;

/**
 *
 * @author Admin
 */
public interface IProxy {
    public String registrarUsuario(Usuario usuario);
    public String iniciarSesion(Usuario usuario);
    public String iniciarSesionFacebook(Usuario usuario);
    public String registrarPublicacion(Publicacion publicacion);
    public String Notificar(Mensaje mensaje);
    public void suscribirseEventoRegistrarPublicacion(IObservadorRegistrarPublicacion suscriptor);
    public void desuscribirseEventoRegistrarPublicacion(IObservadorRegistrarPublicacion suscriptor);
}
