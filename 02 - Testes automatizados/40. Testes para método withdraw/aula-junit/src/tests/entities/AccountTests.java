package tests.entities;

import entities.Account;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tests.factory.AccountFactory;

public class AccountTests {

    @Test
    //Nomenclatura de um teste
    //●	<AÇÃO> should <EFEITO> [when <CENÁRIO>]
    public void depositShouldIncreaseBalanceWhenPositiveAmount(){

        //Padrão AAA
        //●	Arrange: instancie os objetos necessários
        double amount = 200.0;
        double expectedValue = 196.0;
        Account acc = AccountFactory.createEmptyAccount();

        //●	Act: execute as ações necessárias
        acc.deposit(amount);

        //●	Assert: declare o que deveria acontecer (resultado esperado)
        Assertions.assertEquals(expectedValue, acc.getBalance());
    }

    @Test
    //Nomenclatura de um teste
    //●	<AÇÃO> should <EFEITO> [when <CENÁRIO>]
    public void depositShouldDoNothingNegativeAmount(){
        //Padrão AAA
        //●	Arrange: instancie os objetos necessários
        double expectedValue = 100.0;
        Account acc = AccountFactory.createAccount(expectedValue);
        double amount = -200.0;

        //●	Act: execute as ações necessárias
        acc.deposit(amount);

        //●	Assert: declare o que deveria acontecer (resultado esperado)
        Assertions.assertEquals(expectedValue, acc.getBalance());
    }

    @Test
    public void fullWithdrawShouldClearBalanceAndReturnFullBalance(){
        //Padrão AAA
        //●	Arrange: instancie os objetos necessários
        double expectedValue = 0.0;
        double initialBalance = 800.0;
        Account acc = AccountFactory.createAccount(initialBalance);

        //●	Act: execute as ações necessárias
        double result = acc.fullWithdraw();

        //●	Assert: declare o que deveria acontecer (resultado esperado)
        Assertions.assertTrue(expectedValue == acc.getBalance());
        Assertions.assertTrue(result == initialBalance);
    }

    @Test
    public void withdrawShouldDecreaseBalanceWhenSufficientBalance(){
        //Padrão AAA
        //●	Arrange: instancie os objetos necessários
        Account acc = AccountFactory.createAccount(800.0);

        //●	Act: execute as ações necessárias
        acc.withdraw(500.0);

        //●	Assert: declare o que deveria acontecer (resultado esperado)
        Assertions.assertEquals(300.0, acc.getBalance());
    }

    @Test
    public void withdrawShouldThrowExceptionWhenInsufficientBalance(){
        
        //●	Assert: declare o que deveria acontecer (resultado esperado)
        Assertions.assertThrows(IllegalArgumentException.class, ()->{
            //Padrão AAA
            //●	Arrange: instancie os objetos necessários
            Account acc = AccountFactory.createAccount(800.0);

            //●	Act: execute as ações necessárias
            acc.withdraw(801.0);;
        });
    }
}
