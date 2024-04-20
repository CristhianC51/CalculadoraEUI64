import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.datatransfer.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PrincipalFrame extends JFrame {
    private JLabel txt_titulo;
    private JLabel txt_mac;
    protected JPanel plane;
    private JTextField txtFiel_MAC;
    private JLabel txt_resultado;
    private JButton btn_calcular;
    private JButton btn_copy;

    public PrincipalFrame () {

        btn_calcular.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (txtFiel_MAC.getText().isEmpty()) {
                    mensajeError("Aun no has ingresado ninguna Direccion MAC.");
                } else if (isValidMAC(txtFiel_MAC.getText()) == false) {
                    mensajeError("La direccion MAC es invalida.");
                }else {
                    Programa(txtFiel_MAC.getText());
                }
            }
        });

        btn_copy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (txt_resultado.getText() == "¡Aun no has calculado nada!") {
                    mensajeError("Aun no has calculado ninguna IPv6.");
                } else {
                    copiarPortapapeles(txt_resultado.getText());
                }
            }
        });

    }

    public void Programa(String MACEntry) {
        String MAC = MACEntry.toUpperCase();

        MAC = MAC.replaceAll("[\\.:\\-\\s]", "");
        int mitad = MAC.length() / 2;
        String MAC1 = MAC.substring(0, mitad);
        String MAC2 = MAC.substring(mitad);
        MAC = MAC1 + "FFFE" + MAC2;

        String MACPrimDig = MAC.substring(0, 2);
        String MACBinario = Integer.toBinaryString(Integer.parseInt(MACPrimDig, 16));

        while (MACBinario.length() < 8) {
            MACBinario = "0" + MACBinario;
        }

        char[] binarioArray = MACBinario.toCharArray();
        if (binarioArray[6] == '0') {
            binarioArray[6] = '1';
        } else {
            binarioArray[6] = '0';
        }

        String hexModificado = Integer.toHexString(Integer.parseInt(String.valueOf(binarioArray), 2));

        if (hexModificado.length() == 1) {
            hexModificado = "0" + hexModificado;
        }

        String textoFinal = hexModificado + MAC.substring(2);

        StringBuilder textoConSeparadores = new StringBuilder();
        for (int i = 0; i < textoFinal.length(); i++) {
            textoConSeparadores.append(textoFinal.charAt(i));
            if ((i + 1) % 4 == 0 && i != textoFinal.length() - 1) {
                textoConSeparadores.append(":");
            }
        }

        String IPv6 = textoConSeparadores.toString().toUpperCase();
        String regex = "(^|(?<=:))(0:){2,7}(:0|$)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(IPv6);
        IPv6 = matcher.replaceAll("::");

        txt_resultado.setText(IPv6);
    }

    public void copiarPortapapeles(String texto) {
        // Obtener el sistema del portapapeles
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

        // Crear una selección de texto
        StringSelection selection = new StringSelection(texto);

        // Copiar la selección al portapapeles
        clipboard.setContents(selection, null);
    }

    public void mensajeError(String mensaje) {
        JOptionPane.showMessageDialog(null, "¡ERROR! " + mensaje, "ERROR", JOptionPane.ERROR_MESSAGE);
    }

    public static boolean isValidMAC(String mac) {
        Pattern patron = Pattern.compile("^(([0-9A-Fa-f]{2}[:-]){5}[0-9A-Fa-f]{2}|([0-9A-Fa-f]{4}[.]){2}[0-9A-Fa-f]{4}|[0-9A-Fa-f]{12})$");
        Matcher matcher = patron.matcher(mac);
        return matcher.matches();
    }
}
