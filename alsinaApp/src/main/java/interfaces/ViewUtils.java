package interfaces;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class ViewUtils {

    public static void setIconToButton(JButton button, String iconFile, int width, int height) {
        URL url = ViewUtils.class.getResource(iconFile);
        if (url != null) {
            ImageIcon icon = new ImageIcon(url);
            Image image = icon.getImage();
            Image resizedImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            ImageIcon resizedIcon = new ImageIcon(resizedImage);
            button.setIcon(resizedIcon);
        } else {
            System.err.println("No se pudo encontrar la imagen en la ruta especificada");
        }
        button.setHorizontalTextPosition(AbstractButton.LEFT);
        //button.setHorizontalAlignment(AbstractButton.CENTER);
    }
    
    public static void setIconToLabel(JLabel label, String iconFile, int width, int height) {
        URL url = ViewUtils.class.getResource(iconFile);
        if (url != null) {
            ImageIcon icon = new ImageIcon(url);
            Image image = icon.getImage();
            Image resizedImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            ImageIcon resizedIcon = new ImageIcon(resizedImage);
            label.setIcon(resizedIcon);
        } else {
            System.err.println("No se pudo encontrar la imagen en la ruta especificada");
        }
        //label.setHorizontalTextPosition(AbstractButton.LEFT);
        label.setHorizontalAlignment(AbstractButton.CENTER);
    }
}
