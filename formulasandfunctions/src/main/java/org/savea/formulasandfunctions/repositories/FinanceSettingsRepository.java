package org.savea.formulasandfunctions.repositories;

import org.savea.formulasandfunctions.models.FinanceSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FinanceSettingsRepository extends JpaRepository<FinanceSettings, Long> {
}
