package org.savea.formulasandfunctions.services;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.savea.formulasandfunctions.controllers.dtos.payloads.FinanceSettingsDto;
import org.savea.formulasandfunctions.controllers.dtos.payloads.QuotationDto;
import org.savea.formulasandfunctions.models.FinanceSettings;
import org.savea.formulasandfunctions.models.Quotation;
import org.savea.formulasandfunctions.service.FinanceSettingsService;
import org.savea.formulasandfunctions.service.QuotationService;
import org.savea.formulasandfunctions.service.StringUtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
class StringUtilServiceImplIntegrationTest {

    @Autowired
    private StringUtilsService stringUtilsService;

    @Autowired
    private FinanceSettingsService financeSettingsService;

    @Autowired
    private QuotationService quotationService;

    FinanceSettingsDto financeSettingsDto = new FinanceSettingsDto(
            BigDecimal.valueOf(312.00),
            BigDecimal.valueOf(95.00),
            BigDecimal.valueOf(140.00),
            BigDecimal.valueOf(250.00),
            BigDecimal.valueOf(50.00),
            BigDecimal.valueOf(100.00));

    QuotationDto quotationDto = new QuotationDto(
            0.4,
            1,
            1,
            BigDecimal.valueOf(600.0),
            false
    );

    @Test
    void givenStringWithFields_whenFormatFieldFormula_thenReturnFormattedStringFormula() {
        // Given
        String formula = "((field('fiberDistanceCost')*var('fiberDistance'))+(field('polesCost')*var('poles'))+" +
                "(field('equipmentCost')*var('equipment')))+((field('fiberStringingCost')*var('fiberDistance'))+" +
                "(field('polePlantingCost')*var('poles'))+(field('equipmentServiceCost')*var('equipment')))";

        // When
        FinanceSettings financeSettings = financeSettingsService.saveSettings(financeSettingsDto.toModel());
        System.out.println(financeSettings.toString());
        String formattedFormula = stringUtilsService.formatFieldFormula(formula, financeSettings);
        System.out.println(formattedFormula);

        // Then
        String expectedFormula = "((312.0*var('fiberDistance'))+(95.0*var('poles'))+" +
                "(140.0*var('equipment')))+((250.0*var('fiberDistance'))+" +
                "(50.0*var('poles'))+(100.0*var('equipment')))";
        int same = StringUtils.compare(expectedFormula, formattedFormula);
        System.out.println(expectedFormula);
        Assertions.assertEquals(0, same);
    }

    @Test
    void givenStringWithVariables_whenFormatVariableFormula_thenReturnFormattedStringFormula() {
        // Given
        String formula = "((field('fiberDistanceCost')*var('fiberDistance'))+(field('polesCost')*var('poles'))+" +
                "(field('equipmentCost')*var('equipment')))+((field('fiberStringingCost')*var('fiberDistance'))+" +
                "(field('polePlantingCost')*var('poles'))+(field('equipmentServiceCost')*var('equipment')))";

        // When
        Quotation quotation = quotationService.processAndSaveRecord(quotationDto.toModel(), false);
        System.out.println(quotation.toString());
        String formattedFormula = stringUtilsService.formatVariableFormula(formula, quotation);
        System.out.println(formattedFormula);

        // Then
        String expectedFormula = "((field('fiberDistanceCost')*0.4)+(field('polesCost')*1.0)+" +
                "(field('equipmentCost')*1.0))+((field('fiberStringingCost')*0.4)+" +
                "(field('polePlantingCost')*1.0)+(field('equipmentServiceCost')*1.0))";
        int same = StringUtils.compare(expectedFormula, formattedFormula);
        System.out.println(expectedFormula);
        Assertions.assertEquals(0, same);
    }
}
