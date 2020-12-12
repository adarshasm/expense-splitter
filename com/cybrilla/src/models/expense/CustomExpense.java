package models.expense;

import models.User;
import models.split.CustomSplit;
import models.split.Split;

import java.util.List;

public class CustomExpense extends Expense {
    public CustomExpense(double amount, User paidBy, List<Split> splits) {
        super(amount, paidBy, splits);
    }

    @Override
    public boolean validate() {
        for (Split split : getSplits()) {
            if (!(split instanceof CustomSplit)) {
                return false;
            }
        }

        double totalAmount = getAmount();
        double sumSplitAmount = 0;
        for (Split split : getSplits()) {
            CustomSplit exactSplit = (CustomSplit) split;
            sumSplitAmount += exactSplit.getAmount();
        }

        return totalAmount == sumSplitAmount;
    }
}
