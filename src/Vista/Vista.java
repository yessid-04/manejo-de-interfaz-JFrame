package Vista;
/**
 * @author SNEYDER YESSID RONDON INFANTE
 * DATE 1 : 28/10/2026
 */
import Controlador.Controlador;
import Modelo.Paciente;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
/** Formulario de escritorio para que el administrador registre pacientes. */
public class Vista extends JFrame {
    private final Controlador controlador;
    private final JTextField documento=new JTextField(), nombres=new JTextField(), apellidos=new JTextField();
    private final JTextField nacimiento=new JTextField(), fijo=new JTextField(), celular=new JTextField();
    private final JTextField correo=new JTextField(), direccion=new JTextField(), ocupacion=new JTextField();
    private final JTextArea observaciones=new JTextArea(3,20);
    private final JComboBox<String> tipoDoc=combo("Cédula de ciudadanía","Tarjeta de identidad","Cédula de extranjería","Pasaporte","Registro civil");
    private final JComboBox<String> genero=combo("Masculino","Femenino","Otro","Prefiere no decirlo");
    private final JComboBox<String> eps=combo("Nueva EPS","Sanitas","Sura","Salud Total","Famisanar","Compensar","Otra");
    private final JComboBox<String> regimen=combo("Contributivo","Subsidiado","Especial","No afiliado");
    private final JComboBox<String> estadoCivil=combo("Soltero(a)","Casado(a)","Unión libre","Separado(a)","Divorciado(a)","Viudo(a)");
    private final JComboBox<String> condicion=combo("No","Sí");
    private final JLabel resumenNombre=new JLabel("Sin paciente registrado"), resumenDoc=new JLabel("Documento: —"), resumenEdad=new JLabel("Edad: —"), total=new JLabel();

    public Vista(Controlador controlador) {
        this.controlador=controlador;
        setTitle("Laboratorio clínico | Registro de pacientes"); setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(850,700)); setSize(1000,790); setLocationRelativeTo(null);
        construir(); actualizarResumen();
    }
    private void construir(){
        Color azul=new Color(35,108,197), fondo=new Color(246,249,253), borde=new Color(218,229,242);
        JPanel raiz=new JPanel(new BorderLayout(12,12)); raiz.setBackground(fondo); raiz.setBorder(new EmptyBorder(16,16,16,16)); setContentPane(raiz);
        JPanel encabezado=new JPanel(new BorderLayout(12,4)); encabezado.setBackground(Color.WHITE); encabezado.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(borde),new EmptyBorder(14,16,14,16)));
        JLabel icono=new JLabel("●"); icono.setForeground(azul); icono.setFont(new Font("SansSerif",Font.BOLD,28)); encabezado.add(icono,BorderLayout.WEST);
        JPanel titulos=new JPanel(new GridLayout(2,1)); titulos.setOpaque(false); JLabel titulo=new JLabel("Registrar paciente"); titulo.setFont(new Font("SansSerif",Font.BOLD,20)); titulo.setForeground(new Color(25,52,89));
        JLabel subtitulo=new JLabel("Complete la información del paciente para continuar con la atención."); subtitulo.setForeground(new Color(95,117,145)); titulos.add(titulo); titulos.add(subtitulo); encabezado.add(titulos,BorderLayout.CENTER); raiz.add(encabezado,BorderLayout.NORTH);
        JPanel contenido=new JPanel(new BorderLayout(12,0)); contenido.setOpaque(false); raiz.add(contenido,BorderLayout.CENTER);
        JPanel formulario=new JPanel(new GridBagLayout()); formulario.setBackground(Color.WHITE); formulario.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(borde),new EmptyBorder(15,15,15,15)));
        GridBagConstraints g=new GridBagConstraints();g.insets=new Insets(5,5,5,5);g.fill=GridBagConstraints.HORIZONTAL;g.weightx=1;
        int y=0;
        y=seccion(formulario,g,y,"Datos personales",azul);
        y=campo(formulario,g,y,"Tipo de documento *",tipoDoc,0); y=campo(formulario,g,y,"Número de documento *",documento,1);
        y=campo(formulario,g,y,"Nombres *",nombres,0); y=campo(formulario,g,y,"Apellidos *",apellidos,1);
        y=campo(formulario,g,y,"Fecha de nacimiento * (AAAA-MM-DD)",nacimiento,0); y=campo(formulario,g,y,"Género *",genero,1);
        y=seccion(formulario,g,y,"Información de contacto",azul);
        y=campo(formulario,g,y,"Teléfono fijo",fijo,0); y=campo(formulario,g,y,"Teléfono celular *",celular,1);
        y=campo(formulario,g,y,"Correo electrónico",correo,0); y=campo(formulario,g,y,"Dirección de residencia *",direccion,1);
        y=seccion(formulario,g,y,"Información adicional",azul);
        y=campo(formulario,g,y,"EPS *",eps,0); y=campo(formulario,g,y,"Régimen *",regimen,1);
        y=campo(formulario,g,y,"Ocupación",ocupacion,0); y=campo(formulario,g,y,"Estado civil *",estadoCivil,1);
        y=campo(formulario,g,y,"¿Condición médica relevante?",condicion,0);
        JLabel obsLabel=new JLabel("Observaciones (máx. 500 caracteres)"); g.gridx=0;g.gridy=y;g.gridwidth=2;formulario.add(obsLabel,g);
        observaciones.setLineWrap(true);observaciones.setWrapStyleWord(true);observaciones.setBorder(BorderFactory.createLineBorder(borde));
        g.gridy=++y;g.fill=GridBagConstraints.BOTH;g.weighty=1;formulario.add(new JScrollPane(observaciones),g);g.weighty=0;g.fill=GridBagConstraints.HORIZONTAL;g.gridwidth=1;
        JPanel acciones=new JPanel(new FlowLayout(FlowLayout.RIGHT)); acciones.setOpaque(false);
        JButton limpiar=new JButton("Limpiar"); JButton guardar=new JButton("Guardar paciente"); guardar.setBackground(azul);guardar.setForeground(Color.WHITE);guardar.setFocusPainted(false);
        limpiar.addActionListener(e->limpiar());guardar.addActionListener(e->guardar());acciones.add(limpiar);acciones.add(guardar);
        g.gridx=0;g.gridy=++y;g.gridwidth=2;formulario.add(acciones,g);
        JScrollPane scroll=new JScrollPane(formulario);scroll.setBorder(null);scroll.getVerticalScrollBar().setUnitIncrement(16);contenido.add(scroll,BorderLayout.CENTER);
        JPanel lateral=new JPanel();lateral.setPreferredSize(new Dimension(245,200));lateral.setLayout(new BoxLayout(lateral,BoxLayout.Y_AXIS));lateral.setOpaque(false);
        JPanel resumen=tarjeta("Resumen del registro",borde); resumen.setLayout(new BoxLayout(resumen,BoxLayout.Y_AXIS));resumen.add(resumenNombre);resumen.add(Box.createVerticalStrut(8));resumen.add(resumenDoc);resumen.add(resumenEdad);resumen.add(Box.createVerticalStrut(12));resumen.add(total);lateral.add(resumen);
        lateral.add(Box.createVerticalStrut(12));JPanel nota=tarjeta("Tenga en cuenta",borde);nota.setLayout(new BorderLayout(4,6));JLabel texto=new JLabel("<html>El documento debe ser único. Los campos con * son obligatorios. Los registros se guardan en memoria y en data/pacientes.csv.</html>");texto.setForeground(new Color(77,99,128));nota.add(texto,BorderLayout.CENTER);lateral.add(nota);contenido.add(lateral,BorderLayout.EAST);
        documento.getDocument().addDocumentListener(new SimpleDocumentListener(this::actualizarResumen));nombres.getDocument().addDocumentListener(new SimpleDocumentListener(this::actualizarResumen));apellidos.getDocument().addDocumentListener(new SimpleDocumentListener(this::actualizarResumen));nacimiento.getDocument().addDocumentListener(new SimpleDocumentListener(this::actualizarResumen));
    }
    private JPanel tarjeta(String titulo,Color borde){JPanel p=new JPanel();p.setBackground(Color.WHITE);p.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(borde),new EmptyBorder(14,12,14,12)));p.setMaximumSize(new Dimension(280,220));p.setAlignmentX(Component.LEFT_ALIGNMENT);p.add(new JLabel(titulo));return p;}
    private int seccion(JPanel p,GridBagConstraints g,int y,String texto,Color color){JLabel l=new JLabel("▌  "+texto);l.setForeground(color);l.setFont(new Font("SansSerif",Font.BOLD,14));g.gridx=0;g.gridy=y++;g.gridwidth=2;g.insets=new Insets(13,5,6,5);p.add(l,g);g.gridwidth=1;g.insets=new Insets(5,5,5,5);return y;}
    private int campo(JPanel p,GridBagConstraints g,int y,String etiqueta,JComponent c,int col){g.gridx=col;g.gridy=y;JPanel bloque=new JPanel(new BorderLayout(0,5));bloque.setOpaque(false);JLabel l=new JLabel(etiqueta);l.setFont(new Font("SansSerif",Font.PLAIN,12));bloque.add(l,BorderLayout.NORTH);c.setPreferredSize(new Dimension(180,29));if(c instanceof JTextField)((JTextField)c).setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(207,220,237)),new EmptyBorder(4,7,4,7)));bloque.add(c,BorderLayout.CENTER);p.add(bloque,g);return col==1?y+1:y;}
    private static JComboBox<String> combo(String... opciones){JComboBox<String> c=new JComboBox<>(opciones);c.setBackground(Color.WHITE);return c;}
    private void guardar(){
        try{
            LocalDate fecha;try{fecha=LocalDate.parse(nacimiento.getText().trim());}catch(DateTimeParseException ex){throw new IllegalArgumentException("La fecha debe tener el formato AAAA-MM-DD, por ejemplo 1990-05-21.");}
            Paciente p=new Paciente((String)tipoDoc.getSelectedItem(),documento.getText(),nombres.getText(),apellidos.getText(),fecha,(String)genero.getSelectedItem(),fijo.getText(),celular.getText(),correo.getText(),direccion.getText(),(String)eps.getSelectedItem(),(String)regimen.getSelectedItem(),ocupacion.getText(),(String)estadoCivil.getSelectedItem(),"Sí".equals(condicion.getSelectedItem()),observaciones.getText());
            controlador.registrar(p); actualizarResumen();JOptionPane.showMessageDialog(this,"Paciente registrado correctamente.\nTotal de pacientes: "+controlador.getPacientes().size(),"Registro exitoso",JOptionPane.INFORMATION_MESSAGE);limpiar();
        }catch(IllegalArgumentException ex){JOptionPane.showMessageDialog(this,ex.getMessage(),"Revise los datos",JOptionPane.WARNING_MESSAGE);}catch(IOException ex){JOptionPane.showMessageDialog(this,"No fue posible guardar el CSV. Verifique los permisos y vuelva a intentar.\nDetalle: "+ex.getMessage(),"Error de almacenamiento",JOptionPane.ERROR_MESSAGE);}
    }
    private void limpiar(){documento.setText("");nombres.setText("");apellidos.setText("");nacimiento.setText("");fijo.setText("");celular.setText("");correo.setText("");direccion.setText("");ocupacion.setText("");observaciones.setText("");tipoDoc.setSelectedIndex(0);genero.setSelectedIndex(0);eps.setSelectedIndex(0);regimen.setSelectedIndex(0);estadoCivil.setSelectedIndex(0);condicion.setSelectedIndex(0);actualizarResumen();}
    private void actualizarResumen(){resumenNombre.setText(nombres.getText().trim().isEmpty()&&apellidos.getText().trim().isEmpty()?"Nuevo paciente":(nombres.getText()+" "+apellidos.getText()).trim());resumenDoc.setText("Documento: "+(documento.getText().isBlank()?"—":documento.getText().trim()));String edad="—";try{edad=String.valueOf(java.time.Period.between(LocalDate.parse(nacimiento.getText().trim()),LocalDate.now()).getYears())+" años";}catch(Exception ignored){}resumenEdad.setText("Edad: "+edad);total.setText("Pacientes registrados: "+controlador.getPacientes().size());}
    private static class SimpleDocumentListener implements javax.swing.event.DocumentListener {private final Runnable r;SimpleDocumentListener(Runnable r){this.r=r;}public void insertUpdate(javax.swing.event.DocumentEvent e){r.run();}public void removeUpdate(javax.swing.event.DocumentEvent e){r.run();}public void changedUpdate(javax.swing.event.DocumentEvent e){r.run();}}
}
