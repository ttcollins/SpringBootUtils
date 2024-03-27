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
     * This method will take in a formula of type String, and a model of type T
     * It will evaluate the formula and produce a response of type M
     *
     * @param formula,        the formula to evaluate
     * @param <T>             the type of the model attributes
     * @param <M>             the type of the variable model
     * @param fieldsModel,    the model with field values to use in the formula
     * @param variablesModel, the model with variable values to use in the formula
     * @return the result of the formula
     */
    <T, M> double evaluateProjectFormulas(String formula, T fieldsModel, M variablesModel);

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

    /**
     * Get the ACTIVE {@link FormulaStoreRecord} of a specific entity and attribute
     * If none is found, an exception will be thrown
     * If more than one is found, an exception will be thrown
     * @param entityName, the name of the entity
     * @param attributeName, the name of the attribute
     * @return the active formula store record
     */
    FormulaStoreRecord getActiveFormulaStoreRecordOfSpecificEntityAndAttribute(String entityName, String attributeName);
}
