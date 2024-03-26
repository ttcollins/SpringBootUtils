package org.savea.formulasandfunctions.repositories;

import org.savea.formulasandfunctions.models.FormulaStatus;
import org.savea.formulasandfunctions.models.FormulaStoreRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FormulaStoreRecordRepository extends JpaRepository<FormulaStoreRecord, Long> {

    // Find all formula store records by table name, column name and formula status
    List<FormulaStoreRecord> findByTableNameAndColumnNameAndFormulaStatus(String tableName, String columnName, FormulaStatus formulaStatus);
}
