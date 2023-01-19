/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cashier.app.helpers;

import javax.swing.JFrame;

/**
 *
 * @author adwin
 */
public class Popup {
    /**
        * Open the popup and do some stuff with it so we don't repeat ourself
        * @param <T> - The window type
        * @param popup - The instance of the window
        * @param title - The window title
        * @return popup
    */
    public static<T extends JFrame> T open(T popup, String title) {
        popup.setLocationRelativeTo(null);
        popup.setVisible(true);
        popup.setResizable(false);
        popup.setTitle(title);

        return popup;
    }
}
