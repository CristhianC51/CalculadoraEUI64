import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {

        PrincipalFrame frame1 = new PrincipalFrame();
        frame1.setContentPane(frame1.plane);
        frame1.setTitle("Calculadora EUI64");
        frame1.setSize(300, 260);
        frame1.setVisible(true);
        frame1.setResizable(false);
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (pantalla.width - frame1.getWidth()) / 2;
        int y = (pantalla.height - frame1.getHeight()) / 2;
        frame1.setLocation(x, y);

    }
}