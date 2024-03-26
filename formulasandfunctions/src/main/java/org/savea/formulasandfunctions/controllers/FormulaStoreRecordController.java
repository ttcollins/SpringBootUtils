package org.savea.formulasandfunctions.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.savea.formulasandfunctions.controllers.dtos.payloads.FormulaStoreRecordDto;
import org.savea.formulasandfunctions.controllers.dtos.responses.FormulaStoreRecordResponse;
import org.savea.formulasandfunctions.service.FormulaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class FormulaStoreRecordController {

    private final FormulaService formulaService;

    @PostMapping("/store-formula")
    public ResponseEntity<Object> storeFormula(@RequestBody FormulaStoreRecordDto dto)
            throws NoSuchFieldException, ClassNotFoundException {
        log.info("Received Data: {}", dto.toString());
        return new ResponseEntity<>(formulaService.storeNewFormula(dto.toModel()), HttpStatus.CREATED);
    }

    @GetMapping("/get-formulas")
    public ResponseEntity<Object> getFormulas() {
        return new ResponseEntity<>(
                FormulaStoreRecordResponse.toList(formulaService.getAllFormulaStoreRecords()),
                HttpStatus.OK
        );
    }
}
