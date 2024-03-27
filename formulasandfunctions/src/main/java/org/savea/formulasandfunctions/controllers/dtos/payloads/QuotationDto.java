package org.savea.formulasandfunctions.controllers.dtos.payloads;

import org.savea.formulasandfunctions.models.Quotation;

import java.math.BigDecimal;

public record QuotationDto(
        double fiberDistance,
        double poles,
        double equipment,
        BigDecimal nrc,
        boolean automate
) {

    /**
     * This method will convert the DTO into a model object.
     */
    public Quotation toModel() {
        return new Quotation(fiberDistance, poles, equipment, nrc);
    }

}
