package org.savea.formulasandfunctions.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "quotations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Quotation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "fiber_distance")
    private String fiberDistance;

    @Column(name = "poles")
    private String poles;

    @Column(name = "equipment")
    private String equipment;

    @Column(name = "nrc", columnDefinition = "Decimal(20,2) default'0.00'")
    private BigDecimal nrc;

    @ManyToOne
    @JoinColumn(name = "nrc_formula_used")
    private FormulaStoreRecord nrcFormulaUsed;

}
