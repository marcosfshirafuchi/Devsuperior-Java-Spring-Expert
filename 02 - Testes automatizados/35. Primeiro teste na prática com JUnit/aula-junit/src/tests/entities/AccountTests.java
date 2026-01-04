package tests.entities;

import entities.Account;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AccountTests {

    @Test
    //Nomenclatura de um teste
    //●	<AÇÃO> should <EFEITO> [when <CENÁRIO>]
    public void depositShouldIncreaseBalanceWhenPositiveAmount(){

        //Padrão AAA
        //●	Arrange: instancie os objetos necessários
        double amount = 200.0;
        double expectedValue = 196.0;
        Account acc = new Account(1L,0.0);

        //●	Act: execute as ações necessárias
        acc.deposit(amount);

        //●	Assert: declare o que deveria acontecer (resultado esperado)
        Assertions.assertEquals(expectedValue, acc.getBalance());
    }

    @Test
    //Nomenclatura de um teste
    //●	<AÇÃO> should <EFEITO> [when <CENÁRIO>]
    public void withdrawShouldNotTheBalanceWhenTheAmountIsGreaterThanTheBalance(){
        //Padrão AAA
        //●	Arrange: instancie os objetos necessários
        double amount = 200.0;
        double balance = 100.0;
        Account acc = new Account(1L,balance);

        //●	Assert: declare o que deveria acontecer (resultado esperado)
        Assertions.assertThrows(IllegalArgumentException.class, () -> acc.withdraw(amount));
    }

    @Test
    //Nomenclatura de um teste
    //●	<AÇÃO> should <EFEITO> [when <CENÁRIO>]
    public void withdrawShouldTheBalanceWhenTheBalanceIsGreaterThanTheAmount(){
        //Padrão AAA
        //●	Arrange: instancie os objetos necessários
        double amount = 200.0;
        double balance = 1200.0;
        Account acc = new Account(1L,balance);

        //●	Act: execute as ações necessárias
        acc.withdraw(amount);

        //●	Assert: declare o que deveria acontecer (resultado esperado)
        Assertions.assertEquals(1000.0, acc.getBalance());
    }

    @Test
    //Nomenclatura de um teste
    //●	<AÇÃO> should <EFEITO> [when <CENÁRIO>]
    public void fullWithdrawShouldClearBalanceAndReturnFullBalance(){
        //Padrão AAA
        //●	Arrange: instancie os objetos necessários
        double balance = 1200.0;
        Account acc = new Account(1L,balance);

        //●	Act: execute as ações necessárias
        double result = acc.fullWithdraw();

        //●	Assert: declare o que deveria acontecer (resultado esperado)
        Assertions.assertEquals(balance, result);
        Assertions.assertEquals(0.0, acc.getBalance());

    }

    @Test
    //Nomenclatura de um teste
    //●	<AÇÃO> should <EFEITO> [when <CENÁRIO>]
    public void negativeValueShouldNotMakeDeposit(){
        //Padrão AAA
        //●	Arrange: instancie os objetos necessários
        double amount = -200.0;
        double balance = 100.0;
        Account acc = new Account(1L,balance);

        //●	Assert: declare o que deveria acontecer (resultado esperado)
        Assertions.assertThrows(IllegalArgumentException.class, () -> acc.deposit(amount));
    }
}
