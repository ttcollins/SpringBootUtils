package org.savea.formulasandfunctions.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.savea.formulasandfunctions.controllers.dtos.payloads.FormulaStoreRecordDto;
import org.savea.formulasandfunctions.controllers.dtos.responses.FormulaStoreRecordResponse;
import org.savea.formulasandfunctions.service.FormulaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/formulas")
public class FormulaStoreRecordController {

    private final FormulaService formulaService;

    @PostMapping
    public ResponseEntity<Object> storeFormula(@RequestBody FormulaStoreRecordDto dto)
            throws NoSuchFieldException, ClassNotFoundException {
        log.info("Received Data: {}", dto.toString());
        return new ResponseEntity<>(formulaService.storeNewFormula(dto.toModel()), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Object> getFormulas() {
        return new ResponseEntity<>(
                FormulaStoreRecordResponse.toList(formulaService.getAllFormulaStoreRecords()),
                HttpStatus.OK
        );
    }
}
