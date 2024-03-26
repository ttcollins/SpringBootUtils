package org.savea.formulasandfunctions.service;

import org.savea.formulasandfunctions.models.FormulaStoreRecord;
import org.savea.formulasandfunctions.models.ModelAttributes;

import java.util.List;

public interface FormulaService {

    /**
     * Evaluate the formula with the given attributes
     * @param formula, the formula to evaluate
     * @param attributes, the attributes to use in the formula
     * @return the result of the formula
     */
    double evaluateFormula(String formula, ModelAttributes attributes);

    /**
     * Save the formula store record
     * This method is used to validate if the formula should be stored, and if so, store it
     * @param formulaStoreRecord, the formula store record to save
     * @return the saved formula store record
     */
    FormulaStoreRecord storeNewFormula(FormulaStoreRecord formulaStoreRecord) throws ClassNotFoundException, NoSuchFieldException;

    /**
     * Save the formula store record
     * This method is used to simply store the formula
     * @param formulaStoreRecord, the formula store record to save
     * @return the saved formula store record
     */
    FormulaStoreRecord saveFormula(FormulaStoreRecord formulaStoreRecord);

    /**
     * Get all formula store records
     * @return a list of all formula store records
     */
    List<FormulaStoreRecord> getAllFormulaStoreRecords();
}
