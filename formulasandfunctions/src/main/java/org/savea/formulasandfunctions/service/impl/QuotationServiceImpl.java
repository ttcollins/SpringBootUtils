package org.savea.formulasandfunctions.service.impl;

import lombok.RequiredArgsConstructor;
import org.savea.formulasandfunctions.models.FinanceSettings;
import org.savea.formulasandfunctions.models.FormulaStoreRecord;
import org.savea.formulasandfunctions.models.Quotation;
import org.savea.formulasandfunctions.repositories.QuotationRepository;
import org.savea.formulasandfunctions.service.FinanceSettingsService;
import org.savea.formulasandfunctions.service.FormulaService;
import org.savea.formulasandfunctions.service.ProjectClassUtilsService;
import org.savea.formulasandfunctions.service.QuotationService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuotationServiceImpl implements QuotationService {

    private final QuotationRepository quotationRepository;
    private final FormulaService formulaService;
    private final FinanceSettingsService financeSettingsService;
    private final ProjectClassUtilsService projectClassUtilsService;

    @Override
    public Quotation saveRecord(Quotation quotation) {
        return quotationRepository.save(quotation);
    }

    @Override
    public Quotation processAndSaveRecord(Quotation quotation, boolean automate) {
        if (automate) {
            /*Look for existing active formulas attached to this model.
             * If none, then an error, "No formulas exist for this model", will be returned.
             * If more than one is found, an error will also be returned*/

            //Get the active finance settings
            FinanceSettings financeSettingsRecord = financeSettingsService.getActiveRecord();

            //Get all the attributes of this model that can be automated
            List<String> automationCandidates = getAutomationCandidates(quotation);

            //In case no automation candidates are found, throw an error
            if (automationCandidates.isEmpty()) throw new RuntimeException("There are no candidates for automation here");

            /*For each attribute, get the active formula.
            * Pass the formula for evaluation and set the result in the attribute*/
            for (String attribute : automationCandidates) {
                FormulaStoreRecord formulaStoreRecord = formulaService
                        .getActiveFormulaStoreRecordOfSpecificEntityAndAttribute(
                                quotation.getClass().getSimpleName(), attribute);
                double attributeValue = formulaService
                        .evaluateProjectFormulas(formulaStoreRecord.getFormula(), financeSettingsRecord, quotation);
                //Set the value of the attribute in the quotation object
                projectClassUtilsService.setFieldValues(quotation, attribute, attributeValue);
                //Set the FormulaUsed attribute in the quotation object
                projectClassUtilsService.setTheFormulaUsed(quotation, attribute + "FormulaUsed", formulaStoreRecord.getFormula());
            }

        }

        return saveRecord(quotation);
    }

    /**
     * Get all the attributes of the quotation model that can be automated
     * @param quotation, the quotation model to get the automation candidates from
     * @return a list of all the attributes that can be automated
     */
    private static List<String> getAutomationCandidates(Quotation quotation) {
        return Arrays.stream(quotation.getClass().getDeclaredFields())
                .filter(field -> field.getName().contains("FormulaUsed"))
                .map(field -> field.getName().replace("FormulaUsed", ""))
                .toList();
    }

    @Override
    public List<Quotation> getActiveRecords() {
        return quotationRepository.findAll();
    }
}
