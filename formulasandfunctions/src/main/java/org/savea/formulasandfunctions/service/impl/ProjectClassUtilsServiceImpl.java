package org.savea.formulasandfunctions.service.impl;

import org.savea.formulasandfunctions.models.FinanceSettings;
import org.savea.formulasandfunctions.service.ProjectClassUtilsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ProjectClassUtilsServiceImpl implements ProjectClassUtilsService {

    @Value("${project.model.package}")
    private String modelPackage;

    @Override
    public boolean doesClassExist(String className) {
        try {
            Class.forName(modelPackage.concat(".").concat(className));
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    @Override
    public boolean doesClassHaveAttribute(String className, String attributeName) {
        try {
            Class<?> clazz = Class.forName(modelPackage.concat(".").concat(className));
            clazz.getDeclaredField(attributeName);
            return true;
        } catch (ClassNotFoundException | NoSuchFieldException e) {
            return false;
        }
    }

    @Override
    public boolean doesAttributeAllowCalculations(String className, String attributeName) {
        try {
            Class<?> clazz = Class.forName(modelPackage.concat(".").concat(className));
            clazz.getDeclaredField(attributeName + "FormulaUsed");
            return true;
        } catch (ClassNotFoundException | NoSuchFieldException e) {
            return false;
        }
    }

    @Override
    public boolean doesFieldBelongToFinanceSettings(String field) {
        try {
            FinanceSettings.class.getDeclaredField(field);
            return true;
        } catch (NoSuchFieldException e) {
            return false;
        }
    }
}
