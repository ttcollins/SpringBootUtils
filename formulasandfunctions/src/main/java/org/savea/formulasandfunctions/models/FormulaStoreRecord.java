package org.savea.formulasandfunctions.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.Validate;

@Entity
@Table(name = "formula_store_records")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FormulaStoreRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "table_name")
    private String tableName;

    @Column(name = "column_name")
    private String columnName;

    @Column(name = "formula", columnDefinition = "TEXT")
    private String formula;

    @Column(name = "formula_status")
    @Enumerated(EnumType.STRING)
    private FormulaStatus formulaStatus = FormulaStatus.ACTIVE;

    public void validate() {
        Validate.notNull(this.getTableName(), "Table name is required");
        Validate.notNull(this.getColumnName(), "Column name is required");
        Validate.notNull(this.getFormula(), "Formula is required");
        Validate.notNull(this.getFormulaStatus(), "Formula status is required");
    }

    public FormulaStoreRecord(String tableName, String columnName, String formula) {
        this.tableName = tableName;
        this.columnName = columnName;
        this.formula = formula;
    }
}
