package com.portfolio.scott.infrastructure.utils;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

@Slf4j
public class NumericUtil {

    public static BigDecimal parseToBigDecimal(String value) {
        if (value != null && !value.isEmpty()) {
            try {
                return parseToBigDecimalWithLocale(value, Locale.KOREA);
            } catch (NumberFormatException | ParseException e) {
                log.error("Error parsing value to BigDecimal: {}", value, e);
            }
            return BigDecimal.ZERO;
        }
        return BigDecimal.ZERO;
    }

    public static BigDecimal parseToBigDecimalWithLocale(String input, Locale locale) throws ParseException {
        NumberFormat format = NumberFormat.getInstance(locale);
        Number number = format.parse(input);
        return new BigDecimal(number.toString());
    }
}
