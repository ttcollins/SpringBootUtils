package org.savea.formulasandfunctions.service.impl;

import org.savea.formulasandfunctions.service.StringUtilsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class StringUtilsServiceImpl implements StringUtilsService {

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
}
