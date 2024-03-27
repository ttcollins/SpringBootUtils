package org.savea.formulasandfunctions.controllers.dtos.responses;

import org.savea.formulasandfunctions.models.Quotation;

import java.math.BigDecimal;
import java.util.List;

public record QuotationResponse(
        Long id,
        double fiberDistance,
        double poles,
        double equipment,
        BigDecimal nrc,
        FormulaStoreRecordResponse nrcFormulaUsed
) {

    public static List<QuotationResponse> toList(List<Quotation> quotations) {
        return quotations.stream().map(QuotationResponse::new).toList();
    }

    public QuotationResponse(Quotation quotation) {
        this(quotation.getId(),
                quotation.getFiberDistance(),
                quotation.getPoles(),
                quotation.getEquipment(),
                quotation.getNrc(),
                new FormulaStoreRecordResponse(quotation.getNrcFormulaUsed()));
    }
}
