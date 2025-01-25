package utils;

import java.awt.Container;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public interface GetTabbedPane {

	default public JTabbedPane getTabbedPane(JPanel panel) {
        Container parent = panel.getParent();
        while (parent != null) {
            if (parent instanceof JTabbedPane) {
                return (JTabbedPane) parent;
            }
            parent = parent.getParent();
        }
        return null; // Return null if not found
    }
}
