package laboratorioclinico;
import Controlador.Controlador;
import Vista.MenuPrincipal;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
/**
 * @author SNEYDER YESSID RONDON INFANTE
 * DATE 1 : 28/10/2026
 */
public class LaboratorioClinico {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName()
                );
                Controlador controlador = new Controlador();
                MenuPrincipal menu = new MenuPrincipal(controlador);
                menu.setVisible(true);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                    null,
                    "No se pudo iniciar el sistema.\n"
                    + ex.getMessage(),
                    "Error de inicio",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        });
    }
}

