package org.savea.formulasandfunctions.controllers.dtos.responses;

import org.savea.formulasandfunctions.models.FormulaStatus;
import org.savea.formulasandfunctions.models.FormulaStoreRecord;

import java.util.List;

public record FormulaStoreRecordResponse(
        Long id,
        String tableName,
        String columnName,
        String formula,
        FormulaStatus formulaStatus
) {

    public static List<FormulaStoreRecordResponse> toList(List<FormulaStoreRecord> formulaStoreRecords) {
        return formulaStoreRecords.stream().map(FormulaStoreRecordResponse::new).toList();
    }

    public FormulaStoreRecordResponse(FormulaStoreRecord formulaStoreRecord) {
        this(formulaStoreRecord.getId(),
                formulaStoreRecord.getTableName(),
                formulaStoreRecord.getColumnName(),
                formulaStoreRecord.getFormula(),
                formulaStoreRecord.getFormulaStatus());
    }
}
