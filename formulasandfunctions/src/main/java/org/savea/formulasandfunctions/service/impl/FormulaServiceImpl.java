package org.savea.formulasandfunctions.service.impl;

import lombok.RequiredArgsConstructor;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.savea.formulasandfunctions.models.FormulaStatus;
import org.savea.formulasandfunctions.models.FormulaStoreRecord;
import org.savea.formulasandfunctions.models.ModelAttributes;
import org.savea.formulasandfunctions.repositories.FormulaStoreRecordRepository;
import org.savea.formulasandfunctions.service.FormulaService;
import org.savea.formulasandfunctions.service.ProjectClassUtilsService;
import org.savea.formulasandfunctions.service.StringUtilsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FormulaServiceImpl implements FormulaService {

    private final FormulaStoreRecordRepository formulaStoreRecordRepository;
    private final ProjectClassUtilsService projectClassUtilsService;
    private final StringUtilsService stringUtilsService;

    @Override
    public double evaluateFormula(String formula, ModelAttributes attributes) {
        Expression expression = new ExpressionBuilder(formula)
                .variables("x", "y")
                .build()
                .setVariable("x", attributes.getX())
                .setVariable("y", attributes.getY());
        return expression.evaluate();
    }

    @Override
    public <T, M> double evaluateProjectFormulas(String formula, T fieldsModel, M variablesModel) {
        String formattedFieldFormula = stringUtilsService.formatFieldFormula(formula, fieldsModel);
        String formattedVariableFormula = stringUtilsService.formatVariableFormula(formattedFieldFormula, variablesModel);
        Expression expression = new ExpressionBuilder(formattedVariableFormula).build();
        return expression.evaluate();
    }

    @Override
    public FormulaStoreRecord storeNewFormula(FormulaStoreRecord formulaStoreRecord)
            throws ClassNotFoundException, NoSuchFieldException {
        validateModel(formulaStoreRecord);
        validateFields(formulaStoreRecord);
        validateVariables(formulaStoreRecord);
        deactivateExistingFormulas(formulaStoreRecord);
        return saveFormula(formulaStoreRecord);
    }

    /**
     * Validate that the model exists, the column exists, and the column allows calculations
     * @param formulaStoreRecord, the formula store record to validate the model for
     * @throws ClassNotFoundException, if the model does not exist
     * @throws NoSuchFieldException, if the column does not exist or does not allow calculations
     */
    private void validateModel(FormulaStoreRecord formulaStoreRecord) throws ClassNotFoundException, NoSuchFieldException {
        String tableName = formulaStoreRecord.getTableName();
        String columnName = formulaStoreRecord.getColumnName();

        if (!projectClassUtilsService.doesClassExist(tableName))
            throw new ClassNotFoundException("Model with name " + tableName + " does not exist");

        if (!projectClassUtilsService.doesClassHaveAttribute(tableName, columnName))
            throw new NoSuchFieldException("Column with name " + columnName + " is not part of the model with name " + tableName);

        if (!projectClassUtilsService.doesAttributeAllowCalculations(tableName, columnName))
            throw new NoSuchFieldException("Column with name " + columnName + " does not allow calculations");
    }

    /**
     * Validate that all fields in the formula are part of the FinanceSettings model
     * @param formulaStoreRecord, the formula store record to validate fields for
     * @throws NoSuchFieldException, if a field is not part of the FinanceSettings model
     */
    private void validateFields(FormulaStoreRecord formulaStoreRecord) throws NoSuchFieldException {
        List<String> fields = stringUtilsService.extractFields(formulaStoreRecord.getFormula());
        for (String field : fields) {
            if (!projectClassUtilsService.doesFieldBelongToFinanceSettings(field))
                throw new NoSuchFieldException("Field with name " + field + " is not part of the FinanceSettings model");
        }
    }

    /**
     * Validate that all variables in the formula are part of the model.
     * This is the model that has a field with a calculation being implemented on it.
     * @param formulaStoreRecord, the formula store record to validate variables for
     * @throws NoSuchFieldException, if a variable is not part of the model
     */
    private void validateVariables(FormulaStoreRecord formulaStoreRecord) throws NoSuchFieldException {
        List<String> variables = stringUtilsService.extractVariables(formulaStoreRecord.getFormula());
        for (String variable : variables) {
            if (!projectClassUtilsService.doesClassHaveAttribute(formulaStoreRecord.getTableName(), variable))
                throw new NoSuchFieldException("Variable with name " + variable + " is not part of the model with name "
                        + formulaStoreRecord.getTableName());
        }
    }

    /**
     * Deactivate existing formulas with the same table name, column name and formula status
     * @param formulaStoreRecord, the formula store record to deactivate existing formulas for
     */
    private void deactivateExistingFormulas(FormulaStoreRecord formulaStoreRecord) {
        List<FormulaStoreRecord> formulaStoreRecords = formulaStoreRecordRepository
                .findByTableNameAndColumnNameAndFormulaStatus(
                        formulaStoreRecord.getTableName(),
                        formulaStoreRecord.getColumnName(),
                        formulaStoreRecord.getFormulaStatus());

        formulaStoreRecords.forEach(storeRecord -> {
            storeRecord.setFormulaStatus(FormulaStatus.INACTIVE);
            formulaStoreRecordRepository.save(storeRecord);
        });
    }

    @Override
    public FormulaStoreRecord saveFormula(FormulaStoreRecord formulaStoreRecord) {
        formulaStoreRecord.validate();
        return formulaStoreRecordRepository.save(formulaStoreRecord);
    }

    @Override
    public List<FormulaStoreRecord> getAllFormulaStoreRecords() {
        return formulaStoreRecordRepository.findAll();
    }

    @Override
    public FormulaStoreRecord getActiveFormulaStoreRecordOfSpecificEntityAndAttribute(String entityName,
                                                                                      String attributeName) {
        /*Get the active FormulaStoreRecords for the provided entity and attribute.
        * If more than one exists, throw an exception informing the user that more than one active
        * formula exists and only one should exist.*/
        List<FormulaStoreRecord> formulaStoreRecords = formulaStoreRecordRepository
                .findByTableNameAndColumnNameAndFormulaStatus(entityName, attributeName, FormulaStatus.ACTIVE);

        if (formulaStoreRecords == null || formulaStoreRecords.isEmpty())
            throw new RuntimeException("No active formula exists for the entity " + entityName + " and attribute " + attributeName);

        if (formulaStoreRecords.size() > 1)
            throw new RuntimeException("More than one active formula exists for the entity " + entityName + " and attribute " + attributeName);

        return formulaStoreRecords.get(0);
    }
}
