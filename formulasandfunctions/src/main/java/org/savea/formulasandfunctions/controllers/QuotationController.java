package org.savea.formulasandfunctions.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.savea.formulasandfunctions.controllers.dtos.payloads.QuotationDto;
import org.savea.formulasandfunctions.controllers.dtos.responses.QuotationResponse;
import org.savea.formulasandfunctions.service.QuotationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/quotations")
public class QuotationController {

    private final QuotationService quotationService;

    @PostMapping
    public ResponseEntity<Object> storeRecord(@RequestBody QuotationDto dto) {
        log.info("Received Data: {}", dto.toString());
        return new ResponseEntity<>(
                quotationService.processAndSaveRecord(dto.toModel(), dto.automate()),
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<Object> getRecords() {
        return new ResponseEntity<>(
                QuotationResponse.toList(quotationService.getActiveRecords()),
                HttpStatus.OK
        );
    }
}
