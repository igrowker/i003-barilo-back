package com.igrowker.miniproject.utils;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

@Component
public class BigDecimalValidator {
    /**
     * Convert a string to BigDecimal
     * 
     * @param value     The string that represents the number.
     * @param fieldName The name of the field for more clear error messages.
     * @return BigDecimal converted or null if the string is null or empty.
     * @throws IllegalArgumentException if the string is not a valid BigDecimal.
     */
    public BigDecimal validateAndParse(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        try {
            return new BigDecimal(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El campo '" + fieldName + "' no es un valor válido de BigDecimal.", e);
        }
    }
}
