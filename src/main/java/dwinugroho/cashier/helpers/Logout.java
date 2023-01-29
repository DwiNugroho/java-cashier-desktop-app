package dwinugroho.cashier.helpers;

import java.io.IOException;

public class Logout {

    public static void go() throws IOException {
        LoginUser.removeData();
        FXRouter.goTo("login");
    }
}
