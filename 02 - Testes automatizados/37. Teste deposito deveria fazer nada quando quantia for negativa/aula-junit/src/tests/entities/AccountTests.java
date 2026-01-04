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
    public void depositShouldDoNothingNegativeAmount(){
        //Padrão AAA
        //●	Arrange: instancie os objetos necessários
        double expectedValue = 100.0;
        Account acc = new Account(1L,expectedValue);
        double amount = -200.0;

        //●	Act: execute as ações necessárias
        acc.deposit(amount);

        //●	Assert: declare o que deveria acontecer (resultado esperado)
        Assertions.assertEquals(expectedValue, acc.getBalance());
    }


}
