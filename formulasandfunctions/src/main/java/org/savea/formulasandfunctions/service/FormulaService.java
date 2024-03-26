package org.savea.formulasandfunctions.service;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.savea.formulasandfunctions.models.ModelAttributes;
import org.springframework.stereotype.Service;

@Service
public class FormulaService {
    public double evaluateFormula(String formula, ModelAttributes attributes) {
        Expression expression = new ExpressionBuilder(formula)
                .variables("x", "y")
                .build()
                .setVariable("x", attributes.getX())
                .setVariable("y", attributes.getY());
        return expression.evaluate();
    }
}
