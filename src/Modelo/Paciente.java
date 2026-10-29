package Modelo;
/**
 * @author SNEYDER YESSID RONDON INFANTE
 * DATE 1 : 28/10/2026
 */
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
/** Representa un paciente registrado en el laboratorio clínico. */
public class Paciente {
    private final String tipoDocumento, numeroDocumento, nombres, apellidos, genero;
    private final LocalDate fechaNacimiento;
    private final String telefonoFijo, telefonoCelular, correo, direccion;
    private final String eps, regimen, ocupacion, estadoCivil, observaciones;
    private final boolean condicionMedica;
    
    public Paciente(String tipoDocumento, String numeroDocumento, String nombres, String apellidos, LocalDate fechaNacimiento, String genero, String telefonoFijo, String telefonoCelular,
                    String correo, String direccion, String eps, String regimen, String ocupacion,String estadoCivil, boolean condicionMedica, String observaciones)
    {
        this.tipoDocumento=limpiar(tipoDocumento); this.numeroDocumento=limpiar(numeroDocumento);
        this.nombres=limpiar(nombres); this.apellidos=limpiar(apellidos); this.fechaNacimiento=fechaNacimiento;
        this.genero=limpiar(genero); this.telefonoFijo=limpiar(telefonoFijo);
        this.telefonoCelular=limpiar(telefonoCelular); this.correo=limpiar(correo);
        this.direccion=limpiar(direccion); this.eps=limpiar(eps); this.regimen=limpiar(regimen);
        this.ocupacion=limpiar(ocupacion); this.estadoCivil=limpiar(estadoCivil);
        this.condicionMedica=condicionMedica; this.observaciones=limpiar(observaciones);
    }
    private static String limpiar(String s) { return s == null ? "" : s.trim(); }
    
    //getters
    public String getTipoDocumento(){return tipoDocumento;}
    public String getNumeroDocumento(){return numeroDocumento;}
    public String getNombres(){return nombres;}
    public String getApellidos(){return apellidos;}
    public LocalDate getFechaNacimiento(){return fechaNacimiento;} 
    public int getEdad(){return Period.between(fechaNacimiento, LocalDate.now()).getYears();}
    public String getGenero(){return genero;} 
    public String getTelefonoFijo(){return telefonoFijo;}
    public String getTelefonoCelular(){return telefonoCelular;} 
    public String getCorreo(){return correo;}
    public String getDireccion(){return direccion;}
    public String getEps(){return eps;} public String getRegimen(){return regimen;}
    public String getOcupacion(){return ocupacion;}
    public String getEstadoCivil(){return estadoCivil;}
    public boolean tieneCondicionMedica(){return condicionMedica;}
    public String getObservaciones(){return observaciones;}
    public String getNombreCompleto(){return (nombres+" "+apellidos).trim();}
    public String getFechaFormateada(){return fechaNacimiento.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));}
    public String getClaveUnica(){return tipoDocumento.toLowerCase()+":"+numeroDocumento.toLowerCase();}
}
