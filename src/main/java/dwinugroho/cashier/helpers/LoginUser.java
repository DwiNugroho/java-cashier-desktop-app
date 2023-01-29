package dwinugroho.cashier.helpers;

import dwinugroho.cashier.models.UserModel;

public class LoginUser {
    public static UserModel user;

    public static UserModel getData() {
        return user;
    }

    public static void setData(UserModel newUser) {
        user = newUser;
    }

    public static void removeData() {
        user = null;
    }
}
