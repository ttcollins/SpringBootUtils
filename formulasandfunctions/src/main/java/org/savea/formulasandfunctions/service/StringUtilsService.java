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
}
