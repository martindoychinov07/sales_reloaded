package com.reloaded.sales.repository;

import com.reloaded.sales.model.Translation;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TranslationRepository extends JpaRepository<Translation, Integer> {

  boolean existsTranslationByTranslationKey(String translationKey);
}
