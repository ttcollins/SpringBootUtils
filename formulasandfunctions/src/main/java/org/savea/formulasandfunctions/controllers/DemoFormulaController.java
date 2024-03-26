package org.savea.formulasandfunctions.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.savea.formulasandfunctions.controllers.dtos.payloads.DemoFormulaBody;
import org.savea.formulasandfunctions.models.ModelAttributes;
import org.savea.formulasandfunctions.service.FormulaService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class DemoFormulaController {

    private final FormulaService formulaService;

    @PostMapping("/evaluate")
    public double evaluateFormula(@RequestBody DemoFormulaBody demoFormulaBody) {
        log.info("formula: {}", demoFormulaBody.formula());
        ModelAttributes attributes = new ModelAttributes(demoFormulaBody.x(), demoFormulaBody.y());
        return formulaService.evaluateFormula(demoFormulaBody.formula(), attributes);
    }
}
