package org.savea.formulasandfunctions.service.impl;

import lombok.RequiredArgsConstructor;
import org.savea.formulasandfunctions.models.FinanceSettings;
import org.savea.formulasandfunctions.models.Quotation;
import org.savea.formulasandfunctions.repositories.QuotationRepository;
import org.savea.formulasandfunctions.service.QuotationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuotationServiceImpl implements QuotationService {

    private final QuotationRepository quotationRepository;


    @Override
    public Quotation saveRecord(Quotation quotation) {
        return quotationRepository.save(quotation);
    }

    @Override
    public Quotation processAndSaveRecord(Quotation quotation, boolean automate) {
        //TODO: Implement this method
        //Look for existing active formulas attached to this model
        //If none, then return an error, "No formulas exist for this model"
        //If there are formulas, then process them
        return saveRecord(quotation);
    }

    @Override
    public List<Quotation> getActiveRecords() {
        return quotationRepository.findAll();
    }
}
