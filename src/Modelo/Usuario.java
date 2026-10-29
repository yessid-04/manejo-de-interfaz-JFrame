package Modelo;
/**
 * @author SNEYDER YESSID RONDON INFANTE
 * DATE 1 : 28/10/2026
 */
public abstract class Usuario {
    private final int documento;
    private final String clave;
    //constructuor
    protected Usuario(int documento, String clave) {
        this.documento=documento; this.clave=clave;
    }
    public int getDocumento() {
        return documento;
    }
    public String getClave() {
        return clave;
    }
}
