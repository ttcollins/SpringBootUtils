package org.savea.formulasandfunctions.controllers.dtos.payloads;

import org.savea.formulasandfunctions.models.FormulaStoreRecord;

public record FormulaStoreRecordDto(
        String tableName,
        String columnName,
        String formula
) {

    /**
     * This method will convert the DTO into a model object.
     */
    public FormulaStoreRecord toModel() {
        return new FormulaStoreRecord(tableName, columnName, formula);
    }

    @Override
    public String toString() {
        return "FormulaStoreRecordDto{" +
                "tableName='" + tableName + '\'' +
                ", columnName='" + columnName + '\'' +
                ", formula='" + formula + '\'' +
                '}';
    }
}
