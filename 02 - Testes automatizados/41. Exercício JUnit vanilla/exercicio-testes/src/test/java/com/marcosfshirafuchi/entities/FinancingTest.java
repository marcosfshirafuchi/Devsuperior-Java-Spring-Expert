package com.marcosfshirafuchi.entities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FinancingTest {

    @Test
    //Nomenclatura de um teste
    //●	<AÇÃO> should <EFEITO> [when <CENÁRIO>]

    public void deveCriarOObjetoComOsDadosCorretosQuandoOsDadosForemValidos(){

        //Padrão AAA
        //●	Arrange: instancie os objetos necessários
        double totalAmount = 100000.00;
        double income = 2000.00;
        int months = 80;
        Financing fin = new Financing(totalAmount, income, months);

        //●	Act: execute as ações necessárias
        double quota = fin.quota();

        //●	Assert: declare o que deveria acontecer (resultado esperado)
        Assertions.assertTrue(quota <= fin.getIncome()/2.0);
    }

    @Test
    //Nomenclatura de um teste
    //●	<AÇÃO> should <EFEITO> [when <CENÁRIO>]
    public void deveLancarIllegalArgumentExceptionQuandoOsDadosNaoForemInvalidos(){
        //Padrão AAA

        //●	Assert: declare o que deveria acontecer (resultado esperado)
        Assertions.assertThrows(IllegalArgumentException.class, () ->{
            //●	Arrange: instancie os objetos necessários
            double totalAmount = 100000.00;
            double income = 2000.00;
            int months = 20;
            Financing fin = new Financing(totalAmount, income, months);

            //●	Act: execute as ações necessárias
            double quota = fin.quota();

            //●	Assert: declare o que deveria acontecer (resultado esperado)
            Assertions.assertTrue(quota > fin.getIncome()/2.0);
        });
    }

    @Test
    public void deveAtualizarOValorNoMetodoSetTotalAmountQuandoOsDadosForemValidos(){
        //Padrão AAA
        //●	Arrange: instancie os objetos necessários
        double totalAmount = 100000.00;
        double income = 2000.00;
        int months = 80;
        Financing fin = new Financing(totalAmount, income, months);

        //●	Act: execute as ações necessárias
        double quota = fin.quota();
        fin.setTotalAmount(totalAmount);

        //●	Assert: declare o que deveria acontecer (resultado esperado)
        Assertions.assertTrue(quota <= income/2.0);
        Assertions.assertEquals(totalAmount, fin.getTotalAmount());
    }

    @Test
    public void deveLancarIllegalArgumentExceptionNoMetodoSetTotalAmountQuandoOsDadosNaoForemValidos(){
        //●	Assert: declare o que deveria acontecer (resultado esperado)
        Assertions.assertThrows(IllegalArgumentException.class, () ->{
            //●	Arrange: instancie os objetos necessários
            double totalAmount = 100000.00;
            double income = 2000.00;
            int months = 20;
            Financing fin = new Financing(totalAmount, income, months);

            //●	Act: execute as ações necessárias
            double quota = fin.quota();

            //●	Assert: declare o que deveria acontecer (resultado esperado)
            Assertions.assertTrue(quota > fin.getIncome()/2.0);
        });
    }

    @Test
    public void deveAtualizarOValorNoMetodoSetIncomeQuandoOsDadosForemValidos(){
        //Padrão AAA
        //●	Arrange: instancie os objetos necessários
        double totalAmount = 100000.00;
        double income = 2000.00;
        int months = 80;
        Financing fin = new Financing(totalAmount, income, months);

        //●	Act: execute as ações necessárias
        double quota = fin.quota();
        fin.setIncome(income);

        //●	Assert: declare o que deveria acontecer (resultado esperado)
        Assertions.assertTrue(quota <= fin.getIncome()/2.0);
    }

    @Test
    public void deveLancarIllegalArgumentExceptionNoMetodoSetIncomeQuandoOsDadosNaoForemValidos(){
        //●	Assert: declare o que deveria acontecer (resultado esperado)
        Assertions.assertThrows(IllegalArgumentException.class, () ->{
            //●	Arrange: instancie os objetos necessários
            double totalAmount = 100000.00;
            double income = 2000.00;
            int months = 20;
            Financing fin = new Financing(totalAmount, income, months);

            //●	Act: execute as ações necessárias
            double quota = fin.quota();

            //●	Assert: declare o que deveria acontecer (resultado esperado)
            Assertions.assertTrue(quota > fin.getIncome()/2.0);
        });
    }

    @Test
    public void deveAtualizarOValorNoMetodoSetMonthsQuandoOsDadosForemValidos(){
        //Padrão AAA
        //●	Arrange: instancie os objetos necessários
        double totalAmount = 100000.00;
        double income = 2000.00;
        int months = 80;
        Financing fin = new Financing(totalAmount, income, months);

        //●	Act: execute as ações necessárias
        double quota = fin.quota();
        fin.setMonths(months);

        //●	Assert: declare o que deveria acontecer (resultado esperado)
        Assertions.assertTrue(quota <= fin.getIncome()/2.0);
        Assertions.assertEquals(months, fin.getMonths());

    }

    @Test
    public void deveLancarIllegalArgumentExceptionNoMetodoSetMonthsQuandoOsDadosNaoForemValidos(){
        //●	Assert: declare o que deveria acontecer (resultado esperado)
        Assertions.assertThrows(IllegalArgumentException.class,()->{
            //Padrão AAA
            //●	Arrange: instancie os objetos necessários
            double totalAmount = 100000.00;
            double income = 2000.00;
            int months = 20;
            Financing fin = new Financing(totalAmount, income, months);

            //●	Act: execute as ações necessárias
            double quota = fin.quota();

            //●	Assert: declare o que deveria acontecer (resultado esperado)
            Assertions.assertTrue(quota > fin.getIncome()/2.0);
        });
    }

    @Test
    public void deveCalcularCorretamenteNoMetodoEntryOValorDaEntrada(){
        //Padrão AAA
        //●	Arrange: instancie os objetos necessários
        double totalAmount = 100000.00;
        double income = 2000.00;
        int months = 80;
        Financing fin = new Financing(totalAmount, income, months);

        //●	Act: execute as ações necessárias
        double resultOfTheEntry = fin.entry();

        //●	Assert: declare o que deveria acontecer (resultado esperado)
        Assertions.assertEquals(20000.00, resultOfTheEntry);

    }

    @Test
    public void deveCalcularCorretamenteNoMetodoQuotaOValorDaPrestacao(){
        //Padrão AAA
        //●	Arrange: instancie os objetos necessários
        double totalAmount = 100000.00;
        double income = 2000.00;
        int months = 80;
        Financing fin = new Financing(totalAmount, income, months);

        //●	Act: execute as ações necessárias
        double resultOfTheQuota = fin.quota();
        
        //●	Assert: declare o que deveria acontecer (resultado esperado)
        Assertions.assertEquals(1000.0, resultOfTheQuota);
    }
}