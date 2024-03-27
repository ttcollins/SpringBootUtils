package org.savea.formulasandfunctions.controllers.dtos.responses;

import org.savea.formulasandfunctions.models.FinanceSettings;

import java.math.BigDecimal;
import java.util.List;

public record FinanceSettingsResponse(
        Long id,
        BigDecimal fiberDistanceCost,
        BigDecimal polesCost,
        BigDecimal equipmentCost,
        BigDecimal fiberStringingCost,
        BigDecimal polePlantingCost,
        BigDecimal equipmentServiceCost
) {

    public static List<FinanceSettingsResponse> toList(List<FinanceSettings> financeSettings) {
        return financeSettings.stream().map(FinanceSettingsResponse::new).toList();
    }

    public FinanceSettingsResponse(FinanceSettings financeSettings) {
        this(financeSettings.getId(),
                financeSettings.getFiberDistanceCost(),
                financeSettings.getPolesCost(),
                financeSettings.getEquipmentCost(),
                financeSettings.getFiberStringingCost(),
                financeSettings.getPolePlantingCost(),
                financeSettings.getEquipmentServiceCost());
    }
}
