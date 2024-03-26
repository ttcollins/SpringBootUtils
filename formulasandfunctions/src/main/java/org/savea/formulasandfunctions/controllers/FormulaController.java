package org.savea.formulasandfunctions.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.savea.formulasandfunctions.models.ModelAttributes;
import org.savea.formulasandfunctions.service.FormulaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class FormulaController {

    private final FormulaService formulaService;

    @GetMapping("/evaluate")
    public double evaluateFormula(@RequestBody FormulaBody formulaBody) {
        log.info("formula: {}", formulaBody.formula());
        ModelAttributes attributes = new ModelAttributes(formulaBody.x(), formulaBody.y());
        return formulaService.evaluateFormula(formulaBody.formula(), attributes);
    }
}
