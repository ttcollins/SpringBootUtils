package org.savea.formulasandfunctions.service.impl;

import lombok.RequiredArgsConstructor;
import org.savea.formulasandfunctions.service.ProjectClassUtilsService;
import org.savea.formulasandfunctions.service.StringUtilsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class StringUtilsServiceImpl implements StringUtilsService {

    private final ProjectClassUtilsService projectClassUtilsService;

    @Override
    public List<String> extractFields(String formula) {
        List<String> fields = new ArrayList<>();
        Pattern pattern = Pattern.compile("field\\('([^']*)'\\)");
        Matcher matcher = pattern.matcher(formula);
        while (matcher.find()) {
            fields.add(matcher.group(1));
        }
        return fields;
    }

    @Override
    public List<String> extractVariables(String formula) {
        List<String> variables = new ArrayList<>();
        Pattern pattern = Pattern.compile("var\\('([^']*)'\\)");
        Matcher matcher = pattern.matcher(formula);
        while (matcher.find()) {
            variables.add(matcher.group(1));
        }
        return variables;
    }

    @Override
    public <T> String formatFieldFormula(String formula, T modelAttributes) {
        //Find all the fields in the formula
        List<String> fields = extractFields(formula);

        //For every field found, find its value in the model attributes and replace it in the formula
        for (String field : fields) {
            String fieldValue = projectClassUtilsService.getClassFieldValues(modelAttributes, field);
            formula = formula.replace("field('" + field + "')", fieldValue != null ? fieldValue : "0");
        }

        return formula;
    }

    @Override
    public <T> String formatVariableFormula(String formula, T variableModel) {
        //Find all the variables in the formula
        List<String> variables = extractVariables(formula);

        //For every variable found, find its value in the variable model and replace it in the formula
        for (String variable : variables) {
            String variableValue = projectClassUtilsService.getClassVarValues(variableModel, variable);
            formula = formula.replace("var('" + variable + "')", variableValue != null ? variableValue : "0");
        }

        return formula;
    }
}
