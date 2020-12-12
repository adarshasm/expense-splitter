import models.User;
import models.expense.SplitType;
import models.split.CustomSplit;
import models.split.EqualSplit;
import models.split.PercentSplit;
import models.split.Split;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Driver {
    public static void main(String[] args) {
        ExpenseManager expenseManager = new ExpenseManager();
        String confirm = "Y";

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter Name of the User separated by comma(,) and press ENTER to finish:");
        String userList = scanner.nextLine();
        String[] users = userList.split(",");
        int noOfUsers = users.length;
        for (String user : users) {
            expenseManager.addUser(new User(user));
        }
        while (confirm.equalsIgnoreCase("Y")) {
            List<Split> splits = new ArrayList<>();
            System.out.println("Enter Expense in this format: (INVALID Values are NOT Validated!!):");
            System.out.println("Name: <Expense name>\namount: <Amount>\npaid_by: <User>\nsplit_by:<EQUAL,PERCENT,CUSTOM>\n" +
                    "if CUSTOM/PERCENT <Value for user1, ... ,Value for userN> enter 0 against the user who is not participated:");
            System.out.println("Enter Expense:");
            String expenseType = scanner.nextLine();
            double amount = Double.parseDouble(scanner.nextLine());
            String paidBy = scanner.nextLine();
            String splitBy = scanner.nextLine();
            switch (splitBy) {
                case "EQUAL":
                    for (String user : users) {
                        splits.add(new EqualSplit(expenseManager.userMap.get(user)));
                    }
                    expenseManager.addExpense(SplitType.EQUAL, amount, paidBy, splits);
                    break;
                case "CUSTOM":
                    String[] amounts;
                    amounts = scanner.nextLine().split(",");
                    for (int i = 0; i < noOfUsers; i++) {
                        splits.add(new CustomSplit(expenseManager.userMap.get(users[i]), Double.parseDouble(amounts[i])));
                    }
                    expenseManager.addExpense(SplitType.CUSTOM, amount, paidBy, splits);
                    break;
                case "PERCENT":
                    String[] percents;
                    percents = scanner.nextLine().split(",");
                    for (int i = 0; i < noOfUsers; i++) {
                        splits.add(new PercentSplit(expenseManager.userMap.get(users[i]), Double.parseDouble(percents[i])));
                    }
                    expenseManager.addExpense(SplitType.PERCENT, amount, paidBy, splits);
                    break;
            }
            System.out.println("Press Y/y if you want to enter more expenses:");
            confirm = scanner.nextLine();
        }
        expenseManager.showBalances();
    }
}
