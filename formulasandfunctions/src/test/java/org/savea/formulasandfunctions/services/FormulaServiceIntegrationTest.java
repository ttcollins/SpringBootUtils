package org.savea.formulasandfunctions.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.savea.formulasandfunctions.models.ModelAttributes;
import org.savea.formulasandfunctions.service.FormulaService;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
public class FormulaServiceIntegrationTest {

    @SpyBean
    private FormulaService formulaService;

    @Test
    public void givenFormulaWithParameters_whenCalculateFormula_thenResult() {
        // Given
        String formula = "x*2+y";
        ModelAttributes modelAttributes = new ModelAttributes(3, 4);

        // When
        double result = formulaService.evaluateFormula(formula, modelAttributes);
        System.out.println("result = " + result);

        // Then
        assertEquals(10, result, 0);
    }
}
