package Vista;
import Controlador.Controlador;
import javax.swing.*;
import java.awt.*;

public class MenuPrincipal extends JFrame {
    public MenuPrincipal(Controlador controlador) {
        setTitle("Laboratorio clínico");
        setSize(500, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        JButton registrar = new JButton("Registrar paciente");
        JButton ver = new JButton("Ver pacientes");
        registrar.addActionListener(e -> {
            new Vista(controlador).setVisible(true);
            dispose();
        });
        ver.addActionListener(e ->
            new VistaPacientes(controlador).setVisible(true)
        );
        setLayout(new FlowLayout());
        add(new JLabel("MENÚ PRINCIPAL"));
        add(registrar);
        add(ver);
    }
}

