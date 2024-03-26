package org.savea.formulasandfunctions.controllers;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.savea.formulasandfunctions.FormulasAndFunctionsApplication;
import org.savea.formulasandfunctions.controllers.dtos.payloads.FormulaStoreRecordDto;
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

import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = FormulasAndFunctionsApplication.class)
@AutoConfigureMockMvc
@EnableAutoConfiguration(exclude = SecurityAutoConfiguration.class)
@AutoConfigureTestDatabase
class FormulaStoreRecordControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    FormulaStoreRecordDto formulaStoreRecord = new FormulaStoreRecordDto(
            "Quotation",
            "nrc",
            "((field('fiberDistanceCost')*var('fiberDistance'))+(field('polesCost')*var('poles'))+" +
                    "(field('equipmentCost')*var('equipment')))+((field('fiberStringingCost')*var('fiberDistance'))+" +
                    "(field('polePlantingCost')*var('poles'))+(field('equipmentServiceCost')*var('equipment')))");

    @Test
    void givenFormula_whenStoreFormula_thenRetrieveFormula() throws Exception {
        mvc.perform(post("/store-formula")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(formulaStoreRecord)))
                .andExpect(status().isCreated())
                .andDo(result ->
                        System.out.println(JsonPath.parse(result.getResponse().getContentAsString()).jsonString()));
    }

    @Test
    void givenFormula_whenStoreNewFormula_thenOldFormulaInactive() throws Exception {
        //Store the first record
        mvc.perform(post("/store-formula")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(formulaStoreRecord)))
                .andExpect(status().isCreated());

        //Store the second record
        mvc.perform(post("/store-formula")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(formulaStoreRecord)))
                .andExpect(status().isCreated());

        //Fetch all the records and make sure that only one is ACTIVE
        mvc.perform(get("/get-formulas"))
                .andDo(print())
                .andDo(result -> {
                    List<Map<String, Object>> formulas = JsonPath.parse(result.getResponse().getContentAsString()).read("$");
                    int numberOfActiveFormulas = 0;
                    for (Map<String, Object> formula : formulas) {
                        if (formula.get("formulaStatus").equals("ACTIVE")) numberOfActiveFormulas++;
                    }
                    System.out.println("Number of active formulas: " + numberOfActiveFormulas);
                    Assertions.assertEquals(1, numberOfActiveFormulas);
                });
    }
}
