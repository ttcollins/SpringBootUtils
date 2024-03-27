package org.savea.formulasandfunctions.controllers.dtos.payloads;

import org.savea.formulasandfunctions.models.FinanceSettings;

import java.math.BigDecimal;

public record FinanceSettingsDto(
        BigDecimal fiberDistanceCost,
        BigDecimal polesCost,
        BigDecimal equipmentCost,
        BigDecimal fiberStringingCost,
        BigDecimal polePlantingCost,
        BigDecimal equipmentServiceCost
) {

    /**
     * This method will convert the DTO into a model object.
     */
    public FinanceSettings toModel() {
        return new FinanceSettings(fiberDistanceCost, polesCost, equipmentCost,
                fiberStringingCost, polePlantingCost, equipmentServiceCost);
    }

}
