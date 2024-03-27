package org.savea.formulasandfunctions.service.impl;

import lombok.RequiredArgsConstructor;
import org.savea.formulasandfunctions.models.FinanceSettings;
import org.savea.formulasandfunctions.repositories.FinanceSettingsRepository;
import org.savea.formulasandfunctions.service.FinanceSettingsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FinanceSettingsServiceImpl implements FinanceSettingsService {

    private final FinanceSettingsRepository financeSettingsRepository;

    @Override
    public FinanceSettings saveSettings(FinanceSettings settings) {
        FinanceSettings activeRecord = getActiveRecord();

        if (activeRecord != null) {
            if (settings.getFiberDistanceCost() != null)
                activeRecord.setFiberDistanceCost(settings.getFiberDistanceCost());

            if (settings.getPolesCost() != null) activeRecord.setPolesCost(settings.getPolesCost());

            if (settings.getEquipmentCost() != null) activeRecord.setEquipmentCost(settings.getEquipmentCost());

            if (settings.getFiberStringingCost() != null)
                activeRecord.setFiberStringingCost(settings.getFiberStringingCost());

            if (settings.getPolePlantingCost() != null)
                activeRecord.setPolePlantingCost(settings.getPolePlantingCost());

            if (settings.getEquipmentServiceCost() != null)
                activeRecord.setEquipmentServiceCost(settings.getEquipmentServiceCost());

            return financeSettingsRepository.save(activeRecord);
        }

        return financeSettingsRepository.save(settings);
    }

    @Override
    public FinanceSettings getActiveRecord() {
        List<FinanceSettings> financeSettings = financeSettingsRepository.findAll();
        return !financeSettings.isEmpty() ? financeSettings.get(0) : null;
    }
}
