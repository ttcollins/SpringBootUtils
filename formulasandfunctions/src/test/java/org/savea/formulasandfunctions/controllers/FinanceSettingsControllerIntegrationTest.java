package org.savea.formulasandfunctions.controllers;

import org.junit.jupiter.api.Test;
import org.savea.formulasandfunctions.FormulasAndFunctionsApplication;
import org.savea.formulasandfunctions.controllers.dtos.payloads.FinanceSettingsDto;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = FormulasAndFunctionsApplication.class)
@AutoConfigureMockMvc
@EnableAutoConfiguration(exclude = SecurityAutoConfiguration.class)
@AutoConfigureTestDatabase
class FinanceSettingsControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    FinanceSettingsDto financeSettingsDto = new FinanceSettingsDto(
            BigDecimal.valueOf(312.00),
            BigDecimal.valueOf(95.00),
            BigDecimal.valueOf(140.00),
            BigDecimal.valueOf(250.00),
            BigDecimal.valueOf(50.00),
            BigDecimal.valueOf(100.00));

    @Test
    void givenFinanceSettings_whenStoreFinanceSettings_thenRetrieveFinanceSettings() throws Exception {
        mvc.perform(post("/finance-settings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(financeSettingsDto)))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    void givenFinanceSettings_whenStoreNewFinanceSettings_thenNoNewRecordShouldBeCreated() throws Exception {
        //Store the first record
        mvc.perform(post("/finance-settings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(financeSettingsDto)))
                .andExpect(status().isCreated())
                .andDo(print());

        //Store the second record
        mvc.perform(post("/finance-settings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(financeSettingsDto)))
                .andExpect(status().isCreated())
                .andDo(print());

        //Fetch all the records and make sure that only one record is created
        mvc.perform(get("/finance-settings"))
                .andDo(print())
                .andExpect(jsonPath("$.id", is(1)));
    }
}
