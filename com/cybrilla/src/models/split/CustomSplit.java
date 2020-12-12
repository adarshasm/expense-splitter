package models.split;

import models.User;

public class CustomSplit extends Split {

    public CustomSplit(User user, double amount) {
        super(user);
        this.amount = amount;
    }
}
