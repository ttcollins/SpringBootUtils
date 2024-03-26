package org.savea.formulasandfunctions.service;

public interface ProjectClassUtilsService {

    /**
     * Check if a class exists in the project
     * @param className, the name of the class to check
     * @return true if the class exists, false otherwise
     */
    boolean doesClassExist(String className);

    /**
     * Check if a class has an attribute
     * @param className, the name of the class to check
     * @param attributeName, the name of the attribute to check
     * @return true if the class has the attribute, false otherwise
     */
    boolean doesClassHaveAttribute(String className, String attributeName);

    /**
     * Check if a class has an attribute that allows calculations
     * @param className, the name of the class to check
     * @param attributeName, the name of the attribute to check
     * @return true if the class has the attribute and it allows calculations, false otherwise
     */
    boolean doesAttributeAllowCalculations(String className, String attributeName);

    /**
     * Check if a field belongs to the finance settings
     * @param field, the field to check
     * @return true if the field belongs to the finance settings, false otherwise
     */
    boolean doesFieldBelongToFinanceSettings(String field);
}
