package Vista;
import Controlador.Controlador;
import Modelo.Paciente;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VistaPacientes extends JFrame {
    public VistaPacientes(Controlador controlador) {
        setTitle("Pacientes registrados");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        String[] columnas = {
            "Documento", "Nombres", "Apellidos",
            "Teléfono", "Correo"
        };
        DefaultTableModel modelo =  new DefaultTableModel(columnas, 0);
        for (Paciente p : controlador.getPacientes()) {
            modelo.addRow(new Object[]{
                p.getNumeroDocumento(),
                p.getNombres(),
                p.getApellidos(),
                p.getTelefonoCelular(),
                p.getCorreo()
            });
        }
        add(new JScrollPane(new JTable(modelo)),
            BorderLayout.CENTER);
    }
}

