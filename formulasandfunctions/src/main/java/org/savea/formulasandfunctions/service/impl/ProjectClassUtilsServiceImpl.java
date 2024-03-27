package org.savea.formulasandfunctions.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.savea.formulasandfunctions.models.FinanceSettings;
import org.savea.formulasandfunctions.service.ProjectClassUtilsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@Slf4j
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

    @Override
    public <T> String getClassFieldValues(T model, String field) {
        try {
            Method getterMethod = model.getClass().getMethod(constructGetterMethodName(field));
            return String.valueOf(getterMethod.invoke(model));
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            return null;
        }
    }

    private String constructGetterMethodName(String field) {
        return "get" + Character.toUpperCase(field.charAt(0)) + field.substring(1);
    }

    @Override
    public <T> String getClassVarValues(T model, String variable) {
        try {
            Method getterMethod = model.getClass().getMethod(constructGetterMethodName(variable));
            return String.valueOf(getterMethod.invoke(model));
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            return null;
        }
    }

    @Override
    public <T, M> void setFieldValues(T model, String field, M value) {
        try {
            Method setterMethod = model.getClass().getMethod(constructSetterMethodName(field), BigDecimal.class);
            setterMethod.invoke(model, BigDecimal.valueOf((double) value).setScale(2, RoundingMode.HALF_UP));
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            log.error("Error setting field value", e);
        }
    }

    @Override
    public <T> void setTheFormulaUsed(T model, String field, String value) {
        try {
            Method setterMethod = model.getClass().getMethod(constructSetterMethodName(field), String.class);
            setterMethod.invoke(model, value);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            log.error("Error setting formula used", e);
        }

    }

    private String constructSetterMethodName(String field) {
        return "set" + Character.toUpperCase(field.charAt(0)) + field.substring(1);
    }
}
