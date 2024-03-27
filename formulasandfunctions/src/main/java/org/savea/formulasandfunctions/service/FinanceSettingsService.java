package org.savea.formulasandfunctions.service;

import org.savea.formulasandfunctions.models.FinanceSettings;
import org.savea.formulasandfunctions.models.FormulaStoreRecord;
import org.savea.formulasandfunctions.models.ModelAttributes;

import java.util.List;

public interface FinanceSettingsService {

    /**
     * Save a {@link FinanceSettings} record
     * Only one record is meant to be in the database at a time
     * If a record already exists, it will be updated
     * @param settings, the {@link FinanceSettings} record to save
     * @return the saved {@link FinanceSettings} record
     */
    FinanceSettings saveSettings(FinanceSettings settings);

    /**
     * Get all {@link FinanceSettings} records
     * Ideally, only one record should be in the database at a time
     * So only one will be returned
     * @return an object of {@link FinanceSettings}
     */
    FinanceSettings getActiveRecord();
}
