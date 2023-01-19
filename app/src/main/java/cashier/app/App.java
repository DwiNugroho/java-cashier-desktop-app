/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package cashier.app;

import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.intellijthemes.FlatArcOrangeIJTheme;

import cashier.app.helpers.Popup;
import cashier.app.views.Login;

public class App {
    public static void main(String[] args) {
        FlatLightLaf.install();
        FlatArcOrangeIJTheme.install();
        
        Popup.<Login>open(new Login(), "Authentication");
    }
}