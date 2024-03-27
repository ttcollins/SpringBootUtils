package org.savea.formulasandfunctions.service;

import org.savea.formulasandfunctions.models.FinanceSettings;
import org.savea.formulasandfunctions.models.Quotation;

import java.util.List;

public interface QuotationService {

    /**
     * Save a {@link Quotation} record
     * @param quotation, the {@link Quotation} record to save
     * @return the saved {@link Quotation} record
     */
    Quotation saveRecord(Quotation quotation);

    /**
     * Process and save a {@link Quotation} record
     * During the processing, if automate is true, then fields with formulas will be automatically filled
     * @param quotation, the {@link Quotation} record to process
     * @param automate, a boolean to determine if the fields with formulas should be automatically filled
     * @return the saved {@link Quotation} record
     */
    Quotation processAndSaveRecord(Quotation quotation, boolean automate);

    /**
     * Get all {@link Quotation} records
     * @return a list of {@link Quotation} records
     */
    List<Quotation> getActiveRecords();
}
