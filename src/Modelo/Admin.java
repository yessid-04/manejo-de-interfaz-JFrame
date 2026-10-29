package Modelo;
/**
 * @author SNEYDER YESSID RONDON INFANTE
 * DATE 1 : 28/10/2026
 */
/** Rol que realiza el registro administrativo de pacientes. */
public class Admin extends Usuario {
    public Admin() { super(0, ""); }
    public boolean puedeRegistrarPacientes() { return true; }
}
