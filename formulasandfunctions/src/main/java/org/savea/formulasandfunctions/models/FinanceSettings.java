package org.savea.formulasandfunctions.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.Validate;

import java.math.BigDecimal;

@Entity
@Table(name = "finance_settings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FinanceSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "fiber_distance_cost", columnDefinition = "Decimal(20,2) default'0.00'")
    private BigDecimal fiberDistanceCost;

    @Column(name = "poles_cost", columnDefinition = "Decimal(20,2) default'0.00'")
    private BigDecimal polesCost;

    @Column(name = "equipment_cost", columnDefinition = "Decimal(20,2) default'0.00'")
    private BigDecimal equipmentCost;

    @Column(name = "fiber_stringing_cost", columnDefinition = "Decimal(20,2) default'0.00'")
    private BigDecimal fiberStringingCost;

    @Column(name = "pole_planting_cost", columnDefinition = "Decimal(20,2) default'0.00'")
    private BigDecimal polePlantingCost;

    @Column(name = "equipment_service_cost", columnDefinition = "Decimal(20,2) default'0.00'")
    private BigDecimal equipmentServiceCost;

    public FinanceSettings(BigDecimal fiberDistanceCost, BigDecimal polesCost, BigDecimal equipmentCost,
                           BigDecimal fiberStringingCost, BigDecimal polePlantingCost, BigDecimal equipmentServiceCost) {
        this.fiberDistanceCost = fiberDistanceCost;
        this.polesCost = polesCost;
        this.equipmentCost = equipmentCost;
        this.fiberStringingCost = fiberStringingCost;
        this.polePlantingCost = polePlantingCost;
        this.equipmentServiceCost = equipmentServiceCost;
    }

}
