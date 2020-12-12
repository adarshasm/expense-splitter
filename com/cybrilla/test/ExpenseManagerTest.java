import models.User;
import models.expense.SplitType;
import models.split.CustomSplit;
import models.split.EqualSplit;
import models.split.PercentSplit;
import models.split.Split;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class ExpenseManagerTest {

    ExpenseManager expenseManager;
    List<Split> splits = new ArrayList<>();

    @Before
    public void init(){
        expenseManager = new ExpenseManager();
        expenseManager.addUser(new User("testUser1"));
        expenseManager.addUser(new User("testUser2"));
        expenseManager.addUser(new User("testUser3"));
        splits.clear();
    }

    @Test
    public void test_addExpense_equal(){
        splits.add(new EqualSplit(new User("testUser1")));
        splits.add(new EqualSplit(new User("testUser2")));
        splits.add(new EqualSplit(new User("testUser3")));
        expenseManager.addExpense(SplitType.EQUAL,1200,"testUser1", splits);
        Assert.assertEquals(400,expenseManager.balanceSheet.get("testUser1").get("testUser2"),0.0);
    }

    @Test
    public void test_addExpense_PERCENT(){
        splits.add(new PercentSplit(new User("testUser1"),10));
        splits.add(new PercentSplit(new User("testUser2"),90));
        splits.add(new PercentSplit(new User("testUser3"),0));
        expenseManager.addExpense(SplitType.PERCENT,1200,"testUser1", splits);
        Assert.assertEquals(1080,expenseManager.balanceSheet.get("testUser1").get("testUser2"),0.0);
    }

    @Test
    public void test_addExpense_CUSTOM(){
        splits.add(new CustomSplit(new User("testUser1"),1000));
        splits.add(new CustomSplit(new User("testUser2"),200));
        splits.add(new CustomSplit(new User("testUser3"),0));
        expenseManager.addExpense(SplitType.CUSTOM,1200,"testUser1", splits);
        Assert.assertEquals(200,expenseManager.balanceSheet.get("testUser1").get("testUser2"),0.0);
    }

    @Test
    public void test_addExpense_multiple_transactions(){
        splits.add(new EqualSplit(new User("testUser1")));
        splits.add(new EqualSplit(new User("testUser2")));
        splits.add(new EqualSplit(new User("testUser3")));
        expenseManager.addExpense(SplitType.EQUAL,1200,"testUser1", splits);
        splits.clear();
        splits.add(new CustomSplit(new User("testUser1"),800));
        splits.add(new CustomSplit(new User("testUser2"),200));
        splits.add(new CustomSplit(new User("testUser3"),200));
        expenseManager.addExpense(SplitType.CUSTOM,1200,"testUser2", splits);
        splits.clear();
        splits.add(new PercentSplit(new User("testUser1"),10));
        splits.add(new PercentSplit(new User("testUser2"),90));
        splits.add(new PercentSplit(new User("testUser3"),0));
        expenseManager.addExpense(SplitType.PERCENT,1200,"testUser3", splits);
        Assert.assertEquals(280,expenseManager.balanceSheet.get("testUser1").get("testUser3"),0.0);
        Assert.assertEquals(400,expenseManager.balanceSheet.get("testUser2").get("testUser1"),0.0);
        Assert.assertEquals(880,expenseManager.balanceSheet.get("testUser3").get("testUser2"),0.0);
    }
}
