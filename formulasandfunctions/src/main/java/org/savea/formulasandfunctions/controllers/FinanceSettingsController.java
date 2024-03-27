package org.savea.formulasandfunctions.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.savea.formulasandfunctions.controllers.dtos.payloads.FinanceSettingsDto;
import org.savea.formulasandfunctions.controllers.dtos.responses.FinanceSettingsResponse;
import org.savea.formulasandfunctions.service.FinanceSettingsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/finance-settings")
public class FinanceSettingsController {

    private final FinanceSettingsService financeSettingsService;

    @PostMapping
    public ResponseEntity<Object> storeRecord(@RequestBody FinanceSettingsDto dto) {
        log.info("Received Data: {}", dto.toString());
        return new ResponseEntity<>(
                financeSettingsService.saveSettings(dto.toModel()),
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<Object> getRecords() {
        return new ResponseEntity<>(
                new FinanceSettingsResponse(financeSettingsService.getActiveRecord()),
                HttpStatus.OK
        );
    }
}
