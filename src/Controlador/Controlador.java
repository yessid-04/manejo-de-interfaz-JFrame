package Controlador;
/**
 * @author SNEYDER YESSID RONDON INFANTE
 * DATE 1 : 28/10/2026
 */
import Modelo.Paciente;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
/** Coordina validaciones, lista en memoria y persistencia CSV. */
public class Controlador {
    private final ArrayList<Paciente> pacientes = new ArrayList<>();
    private final Path archivoCsv;
    private static final String CABECERA = "tipoDocumento,numeroDocumento,nombres,apellidos,fechaNacimiento,genero,telefonoFijo,telefonoCelular,"
                                         + "correo,direccion,eps,regimen,ocupacion,estadoCivil,condicionMedica,observaciones";
    private static final Pattern EMAIL = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private static final Pattern TELEFONO = Pattern.compile("^[0-9+() .-]{7,20}$");
    private static final Pattern DOCUMENTO = Pattern.compile("^[A-Za-z0-9.-]{4,20}$");

    public Controlador() throws IOException { 
        this(Path.of("data", "pacientes.csv")); 
    }
    public Controlador(Path archivoCsv) throws IOException { 
        this.archivoCsv=archivoCsv; cargar(); 
    }
    public List<Paciente> getPacientes() { 
        return List.copyOf(pacientes); 
    }
    public Path getArchivoCsv() {
        return archivoCsv;
    }
    public void registrar(Paciente p) throws IOException {
        if (p == null) throw new IllegalArgumentException("No se recibieron datos del paciente.");
        validar(p);
        for(Paciente existente: pacientes) {
            if(existente.getClaveUnica().equalsIgnoreCase(p.getClaveUnica()))
                throw new IllegalArgumentException("Ya existe un paciente con ese tipo y número de documento.");
        }
        pacientes.add(p);
        try { guardarCsv(); } catch(IOException ex) { pacientes.remove(p); throw ex; }
    }
    private void validar(Paciente p) {
        requerido(p.getTipoDocumento(), "Tipo de documento"); requerido(p.getNumeroDocumento(), "Número de documento");
        if(!DOCUMENTO.matcher(p.getNumeroDocumento()).matches()) throw new IllegalArgumentException("El documento debe tener entre 4 y 20 caracteres alfanuméricos (se permiten punto y guion).");
        requerido(p.getNombres(), "Nombres"); requerido(p.getApellidos(), "Apellidos");
        if(!p.getNombres().matches("[\\p{L}\\p{M} .'-]{2,80}") || !p.getApellidos().matches("[\\p{L}\\p{M} .'-]{2,80}"))
            throw new IllegalArgumentException("Nombres y apellidos deben contener letras y tener entre 2 y 80 caracteres.");
        if(p.getFechaNacimiento()==null) throw new IllegalArgumentException("Ingrese la fecha de nacimiento.");
        if(p.getFechaNacimiento().isAfter(LocalDate.now())) throw new IllegalArgumentException("La fecha de nacimiento no puede ser futura.");
        if(p.getEdad()>120) throw new IllegalArgumentException("Verifique la fecha de nacimiento: la edad no puede superar 120 años.");
        requerido(p.getGenero(), "Género"); requerido(p.getTelefonoCelular(), "Teléfono celular");
        if(!TELEFONO.matcher(p.getTelefonoCelular()).matches()) throw new IllegalArgumentException("El teléfono celular debe tener entre 7 y 20 caracteres numéricos o símbolos telefónicos.");
        if(!p.getTelefonoFijo().isBlank() && !TELEFONO.matcher(p.getTelefonoFijo()).matches()) throw new IllegalArgumentException("El teléfono fijo no tiene un formato válido.");
        if(!p.getCorreo().isBlank() && !EMAIL.matcher(p.getCorreo()).matches()) throw new IllegalArgumentException("El correo electrónico no tiene un formato válido.");
        requerido(p.getDireccion(), "Dirección de residencia"); requerido(p.getEps(), "EPS"); requerido(p.getRegimen(), "Régimen");
        requerido(p.getEstadoCivil(), "Estado civil");
        if(p.getDireccion().length()>180 || p.getObservaciones().length()>500 || p.getOcupacion().length()>100)
            throw new IllegalArgumentException("La dirección admite 180 caracteres, la ocupación 100 y las observaciones 500.");
    }
    private void requerido(String valor,String campo){ if(valor==null||valor.isBlank()) throw new IllegalArgumentException("El campo "+campo+" es obligatorio."); }
    private void cargar() throws IOException {
        if(!Files.exists(archivoCsv)) return;
        List<String> lineas=Files.readAllLines(archivoCsv, StandardCharsets.UTF_8);
        for(int i=1;i<lineas.size();i++) {
            if(lineas.get(i).isBlank()) continue;
            try {
                List<String> c=parseCsv(lineas.get(i)); if(c.size()!=16) throw new IllegalArgumentException("Columnas incorrectas");
                Paciente p=new Paciente(c.get(0),c.get(1),c.get(2),c.get(3),LocalDate.parse(c.get(4)),c.get(5),c.get(6),c.get(7),c.get(8),c.get(9),c.get(10),c.get(11),c.get(12),c.get(13),Boolean.parseBoolean(c.get(14)),c.get(15));
                boolean duplicado=pacientes.stream().anyMatch(x->x.getClaveUnica().equalsIgnoreCase(p.getClaveUnica()));
                if(!duplicado) pacientes.add(p);
            } catch(RuntimeException ex) { throw new IOException("El archivo de pacientes tiene datos inválidos en la línea "+(i+1)+".",ex); }
        }
    }
    private void guardarCsv() throws IOException {
        Path padre=archivoCsv.toAbsolutePath().getParent(); if(padre!=null) Files.createDirectories(padre);
        List<String> lineas=new ArrayList<>(); lineas.add(CABECERA);
        for(Paciente p:pacientes) {
            String[] c={p.getTipoDocumento(),p.getNumeroDocumento(),p.getNombres(),p.getApellidos(),p.getFechaNacimiento().toString(),p.getGenero(),p.getTelefonoFijo(),p.getTelefonoCelular(),p.getCorreo(),p.getDireccion(),p.getEps(),p.getRegimen(),p.getOcupacion(),p.getEstadoCivil(),Boolean.toString(p.tieneCondicionMedica()),p.getObservaciones()};
            for(int i=0;i<c.length;i++) c[i]=csv(c[i]);
            lineas.add(String.join(",",c));
        }
        Path temporal=archivoCsv.resolveSibling(archivoCsv.getFileName()+".tmp");
        Files.write(temporal,lineas,StandardCharsets.UTF_8);
        try { Files.move(temporal,archivoCsv,java.nio.file.StandardCopyOption.REPLACE_EXISTING,java.nio.file.StandardCopyOption.ATOMIC_MOVE); }
        catch(java.nio.file.AtomicMoveNotSupportedException ex){Files.move(temporal,archivoCsv,java.nio.file.StandardCopyOption.REPLACE_EXISTING);}
    }
    private static String csv(String s){return "\""+s.replace("\"","\"\"")+"\"";}
    private static List<String> parseCsv(String linea) {
        List<String> r=new ArrayList<>(); StringBuilder b=new StringBuilder(); boolean comillas=false;
        for(int i=0;i<linea.length();i++){char ch=linea.charAt(i); if(ch=='\"'){if(comillas&&i+1<linea.length()&&linea.charAt(i+1)=='\"'){b.append('\"');i++;}else comillas=!comillas;}else if(ch==','&&!comillas){r.add(b.toString());b.setLength(0);}else b.append(ch);}
        r.add(b.toString()); if(comillas) throw new IllegalArgumentException("Comillas CSV incompletas"); return r;
    }
}
