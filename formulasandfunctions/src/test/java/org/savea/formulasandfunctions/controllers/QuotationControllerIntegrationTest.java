package org.savea.formulasandfunctions.controllers;

import org.junit.jupiter.api.Test;
import org.savea.formulasandfunctions.FormulasAndFunctionsApplication;
import org.savea.formulasandfunctions.controllers.dtos.payloads.FinanceSettingsDto;
import org.savea.formulasandfunctions.controllers.dtos.payloads.FormulaStoreRecordDto;
import org.savea.formulasandfunctions.controllers.dtos.payloads.QuotationDto;
import org.savea.formulasandfunctions.service.FinanceSettingsService;
import org.savea.formulasandfunctions.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = FormulasAndFunctionsApplication.class)
@AutoConfigureMockMvc
@EnableAutoConfiguration(exclude = SecurityAutoConfiguration.class)
@AutoConfigureTestDatabase
class QuotationControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private FinanceSettingsService financeSettingsService;

    QuotationDto quotationDto = new QuotationDto(
            0.4,
            1,
            1,
            BigDecimal.valueOf(600.0),
            false
    );

    QuotationDto quotationDtoAuto = new QuotationDto(
            0.4,
            1,
            1,
            BigDecimal.valueOf(600.0),
            true
    );

    FinanceSettingsDto financeSettingsDto = new FinanceSettingsDto(
            BigDecimal.valueOf(312.00),
            BigDecimal.valueOf(95.00),
            BigDecimal.valueOf(140.00),
            BigDecimal.valueOf(250.00),
            BigDecimal.valueOf(50.00),
            BigDecimal.valueOf(100.00));

    FormulaStoreRecordDto formulaStoreRecord = new FormulaStoreRecordDto(
            "Quotation",
            "nrc",
            "((field('fiberDistanceCost')*var('fiberDistance'))+(field('polesCost')*var('poles'))+" +
                    "(field('equipmentCost')*var('equipment')))+((field('fiberStringingCost')*var('fiberDistance'))+" +
                    "(field('polePlantingCost')*var('poles'))+(field('equipmentServiceCost')*var('equipment')))");

    @Test
    void givenQuotationDetails_whenStoreQuotationDetails_thenRetrieveQuotationDetails() throws Exception {
        mvc.perform(post("/quotations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(quotationDto)))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    void givenQuotationDetails_whenStoreQuotationDetails_AndAutomateIsTrue_thenRetrieveQuotationDetails() throws Exception {
        //Create the formula
        mvc.perform(post("/formulas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(formulaStoreRecord)))
                .andExpect(status().isCreated())
                .andDo(print());

        //Create finance settings
        financeSettingsService.saveSettings(financeSettingsDto.toModel());

        //Create the quotation and make sure the nrcFormulaUsed is not null
        mvc.perform(post("/quotations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(quotationDtoAuto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nrcFormulaUsed", is(formulaStoreRecord.formula())))
                .andDo(print());
    }

}
