package com.depromeet.archive.domain.archive.entity;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Collections;
import java.util.List;

@Converter
public class CompanionsConverter implements AttributeConverter<List<String>, String> {

    private static final String COMPANION_DELIMITER = ",";

    @Override
    public String convertToDatabaseColumn(List<String> companions) {
        if (companions == null || companions.isEmpty()) return null;
        return String.join(COMPANION_DELIMITER, companions);
    }

    @Override
    public List<String> convertToEntityAttribute(String dbCompanions) {
        if (dbCompanions == null) return Collections.emptyList();
        return List.of(dbCompanions.split(COMPANION_DELIMITER));
    }

}
