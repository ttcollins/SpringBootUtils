package org.savea.formulasandfunctions.repositories;

import org.savea.formulasandfunctions.models.Quotation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuotationRepository extends JpaRepository<Quotation, Long> {
}
