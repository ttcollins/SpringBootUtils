package org.savea.formulasandfunctions.service;

public interface ProjectClassUtilsService {

    /**
     * Check if a class exists in the project
     *
     * @param className, the name of the class to check
     * @return true if the class exists, false otherwise
     */
    boolean doesClassExist(String className);

    /**
     * Check if a class has an attribute
     *
     * @param className,     the name of the class to check
     * @param attributeName, the name of the attribute to check
     * @return true if the class has the attribute, false otherwise
     */
    boolean doesClassHaveAttribute(String className, String attributeName);

    /**
     * Check if a class has an attribute that allows calculations
     *
     * @param className,     the name of the class to check
     * @param attributeName, the name of the attribute to check
     * @return true if the class has the attribute and it allows calculations, false otherwise
     */
    boolean doesAttributeAllowCalculations(String className, String attributeName);

    /**
     * Check if a field belongs to the finance settings
     *
     * @param field, the field to check
     * @return true if the field belongs to the finance settings, false otherwise
     */
    boolean doesFieldBelongToFinanceSettings(String field);

    /**
     * Get the value of a class declared variable whose name is the field
     * If it is not found, return null
     *
     * @param model, the model to get the declared value from
     * @param field, the field to get the content from
     * @param <T>    the type of the model
     * @return the content of the declared value
     */
    <T> String getClassFieldValues(T model, String field);

    /**
     * Get the value of a class declared variable whose name is the variable
     * If it is not found, return null
     *
     * @param model,    the model to get the declared value from
     * @param variable, the variable to get the content from
     * @param <T>       the type of the model
     * @return the content of the declared value
     */
    <T> String getClassVarValues(T model, String variable);

    /**
     * Set the value of a field in a model
     *
     * @param model, the model to set the field value in
     * @param field, the field to set the value in
     * @param value, the value to set in the field
     * @param <T>    the type of the model
     * @param <M>    the type of the value
     */
    <T, M> void setFieldValues(T model, String field, M value);

    /**
     * Set the formula used in a field in a model
     * @param model, the model to set the formula used in
     * @param field, the field to set the formula used in
     * @param value, the formula used in the field
     * @param <T> the type of the model
     */
    <T> void setTheFormulaUsed(T model, String field, String value);
}
