import models.User;
import models.expense.*;
import models.split.PercentSplit;
import models.split.Split;

import java.util.List;

public class ExpenseService {
    public static Expense createExpense(SplitType splitType, double amount, User paidBy, List<Split> splits) {
        switch (splitType) {
            case CUSTOM:
                return new CustomExpense(amount, paidBy, splits);
            case PERCENT:
                for (Split split : splits) {
                    PercentSplit percentSplit = (PercentSplit) split;
                    split.setAmount((amount*percentSplit.getPercent())/100.0);
                }
                return new PercentExpense(amount, paidBy, splits);
            case EQUAL:
                int totalSplits = splits.size();
                double splitAmount = ((double) Math.round(amount*100/totalSplits))/100.0;
                for (Split split : splits) {
                    split.setAmount(splitAmount);
                }
                splits.get(0).setAmount(splitAmount + (amount - splitAmount*totalSplits));
                return new EqualExpense(amount, paidBy, splits);
            default:
                return null;
        }
    }
}