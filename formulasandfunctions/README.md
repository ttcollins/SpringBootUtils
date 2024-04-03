# FormulasAndFunctions Project

The FormulasAndFunctions project is a Java-based application developed using Maven and Spring Boot.
The project is designed to handle and manipulate various formulas and functions, particularly in the context
of financial calculations.

It puts to use the concept of [Reflection](https://www.oracle.com/technical-resources/articles/java/javareflection.html#:~:text=Reflection%20is%20a%20feature%20in,its%20members%20and%20display%20them.) in Java.

The idea is that;

1. A user should be able to create a formula to be used to calculate a value.
2. When filling in a form with a specific value, a user should be able to have the value filled manually or automatically
   based on the formula.
3. If the user decides to have the specific value calculated automatically, the formula should be used to calculate the value.

## Key Components

### Models

The project includes the following major models;


| **Tables**            | **Columns/Attributes**                                                                                      |
| --------------------- | ----------------------------------------------------------------------------------------------------------- |
| 1. FormulaStoreRecord | tableName, columnName, formula, formulaStatus                                                               |
| 2. FinanceSettings    | fiberDistanceCost, poleCost, equipmentCost,<br />fiberStringingCost, polePlantingCost, equipmentServiceCost |
| 3. Quotation          | fiberDistance, poles, equipment, nrc, nrcFormulaUsed                                                        |

### Services

All the listed models are managed by their own service.

### Controllers

All the services attached to the models are managed by their own controller.

### Tests

The project includes integration tests for the controllers and services.

## Functionality

The application allows users to create and manipulate `Quotation` objects.
It also allows users to automate the calculation of `nrc` (Non-Recurring Cost) based on a formula.
The formula can be stored and retrieved for future use.

A formula stored should match a specific syntax which makes use of specific flags. The flags include;

1. `field('columnName')` which represents an attribute in the `financeSettings` model e.g. `field('fiberDistanceCost')`.
2. `var('columnName')` which represents an attribute in the model/table being targeted by the formula. If the formula is
   targeting the `Quotation` model, the `var` flag should be used to reference attributes in that model e.g. `var('fiberDistance')`.

### Formula Creation

![Formula Creation](https://github.com/ttcollins/SpringBootUtils/assets/51233620/97ce1d03-556d-4975-941d-93bf643fea5b)

```abc
An example of the formula that can be supplied:
((field('fiberDistanceCost')*var('fiberDistance'))+(field('polesCost')*var('poles'))+(field('equipmentCost')*var('equipment')))+((field('fiberStringingCost')*var('fiberDistance'))+(field('polePlantingCost')*var('poles'))+(field('equipmentServiceCost')*var('equipment')))
```

### Formula Usage

For the formula to work, the columns associated with `field('columnName')` in the financeSettings model must not be null.

![Formula Usage](https://github.com/ttcollins/SpringBootUtils/assets/51233620/0d1e34a5-f45c-401c-870e-6f64029c7760)

## Running Tests

Tests can be run using the standard Maven command: `mvn test`.

## Building and Running the Application

The application can be built using the standard Maven command: `mvn clean install`.
The resulting JAR file can be run using the command: `java -jar target/formulasandfunctions-0.0.1-SNAPSHOT.jar`.

Please note that the actual commands may vary depending on the specific configuration of the project.
