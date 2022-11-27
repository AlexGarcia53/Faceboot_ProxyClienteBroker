/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import dominio.Publicacion;
import dominio.Solicitud;
import dominio.Usuario;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface IProxy {
    public Usuario registrarUsuario(Usuario usuario);
    public Usuario iniciarSesion(Usuario usuario);
    public Usuario iniciarSesionFacebook(Usuario usuario);
    public Publicacion registrarPublicacion(Publicacion publicacion);
    public List<Publicacion> consultarPublicaciones();
    public void suscribirseEventoRegistrarPublicacion(IObservadorRegistrarPublicacion suscriptor);
    public void desuscribirseEventoRegistrarPublicacion(IObservadorRegistrarPublicacion suscriptor);
}
