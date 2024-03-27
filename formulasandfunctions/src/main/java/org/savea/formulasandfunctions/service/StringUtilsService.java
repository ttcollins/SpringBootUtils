package org.savea.formulasandfunctions.service;

import java.util.List;

public interface StringUtilsService {

    /**
     * Extract fields from a formula
     * @param formula, the formula to extract fields from
     * @return a list of fields extracted from the formula
     */
    List<String> extractFields(String formula);

    /**
     * Extract variables from a formula
     * @param formula, the formula to extract variables from
     * @return a list of variables extracted from the formula
     */
    List<String> extractVariables(String formula);

    /**
     * This method takes a formula, and replaces all the field('') text with the actual column's value
     *
     * @param formula,        the formula to format
     * @param modelAttributes, the model attributes to use in the formula
     * @return the formatted formula
     */
    <T> String formatFieldFormula(String formula, T modelAttributes);

    /**
     * This method takes a formula, and replaces all the var('') text with the actual variable's value
     * @param formula, the formula to format
     * @param variableModel, the model to use in the formula
     * @return the formatted formula
     * @param <T> the type of the model
     */
    <T> String formatVariableFormula(String formula, T variableModel);
}
