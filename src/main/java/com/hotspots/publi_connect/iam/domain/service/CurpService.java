package com.hotspots.publi_connect.iam.domain.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.hotspots.publi_connect.iam.app.output.ValidateCurpResult;

@Service
public class CurpService {

    private static final Pattern CURP_PATTERN = Pattern.compile(
        "^[A-Z][AEIOUX][A-Z]{2}\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])"
        + "[HM](AS|BC|BS|CC|CL|CM|CS|CH|DF|DG|GT|GR|HG|JC|MC|MN|MS|NT|NL|OC|PL|QT|QR|SP|SL|SR|TC|TS|TL|VZ|YN|ZS|NE)"
        + "[B-DF-HJ-NP-TV-Z]{3}[A-Z0-9]\\d$"
    );

    private static final DateTimeFormatter BIRTH_DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/uuuu");

    private static final Map<String, String> STATE_BY_CODE = Map.ofEntries(
        Map.entry("AS", "AGUASCALIENTES"),
        Map.entry("BC", "BAJA CALIFORNIA"),
        Map.entry("BS", "BAJA CALIFORNIA SUR"),
        Map.entry("CC", "CAMPECHE"),
        Map.entry("CL", "COAHUILA"),
        Map.entry("CM", "COLIMA"),
        Map.entry("CS", "CHIAPAS"),
        Map.entry("CH", "CHIHUAHUA"),
        Map.entry("DF", "CIUDAD DE MEXICO"),
        Map.entry("DG", "DURANGO"),
        Map.entry("GT", "GUANAJUATO"),
        Map.entry("GR", "GUERRERO"),
        Map.entry("HG", "HIDALGO"),
        Map.entry("JC", "JALISCO"),
        Map.entry("MC", "MEXICO"),
        Map.entry("MN", "MICHOACAN"),
        Map.entry("MS", "MORELOS"),
        Map.entry("NT", "NAYARIT"),
        Map.entry("NL", "NUEVO LEON"),
        Map.entry("OC", "OAXACA"),
        Map.entry("PL", "PUEBLA"),
        Map.entry("QT", "QUERETARO"),
        Map.entry("QR", "QUINTANA ROO"),
        Map.entry("SP", "SAN LUIS POTOSI"),
        Map.entry("SL", "SINALOA"),
        Map.entry("SR", "SONORA"),
        Map.entry("TC", "TABASCO"),
        Map.entry("TS", "TAMAULIPAS"),
        Map.entry("TL", "TLAXCALA"),
        Map.entry("VZ", "VERACRUZ"),
        Map.entry("YN", "YUCATAN"),
        Map.entry("ZS", "ZACATECAS"),
        Map.entry("NE", "NACIDO EN EL EXTRANJERO")
    );

    public ValidateCurpResult validateAndExtract(String rawCurp) {
        String curp = rawCurp == null ? "" : rawCurp.trim().toUpperCase(Locale.ROOT);

        if (!CURP_PATTERN.matcher(curp).matches() || !hasValidCheckDigit(curp)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CURP invalida");
        }

        return new ValidateCurpResult(
            curp,
            curp.substring(3, 4),
            curp.substring(0, 2),
            curp.substring(2, 3),
            "H".equals(curp.substring(10, 11)) ? "HOMBRE" : "MUJER",
            parseBirthDate(curp).format(BIRTH_DATE_FORMAT),
            resolveState(curp.substring(11, 13))
        );
    }

    private LocalDate parseBirthDate(String curp) {
        String yy = curp.substring(4, 6);
        String mm = curp.substring(6, 8);
        String dd = curp.substring(8, 10);
        char homonymCode = curp.charAt(16);
        int century = Character.isDigit(homonymCode) ? 1900 : 2000;

        try {
            return LocalDate.parse(
                dd + "/" + mm + "/" + (century + Integer.parseInt(yy)),
                BIRTH_DATE_FORMAT
            );
        } catch (DateTimeParseException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CURP invalida");
        }
    }

    private String resolveState(String stateCode) {
        String state = STATE_BY_CODE.get(stateCode);
        if (state == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CURP invalida");
        }
        return state;
    }

    private boolean hasValidCheckDigit(String curp) {
        String dictionary = "0123456789ABCDEFGHIJKLMNÑOPQRSTUVWXYZ";
        int total = 0;

        for (int i = 0; i < 17; i++) {
            int value = dictionary.indexOf(curp.charAt(i));
            if (value < 0) {
                return false;
            }
            total += value * (18 - i);
        }

        int expectedDigit = 10 - (total % 10);
        expectedDigit = expectedDigit == 10 ? 0 : expectedDigit;
        int actualDigit = Character.getNumericValue(curp.charAt(17));
        return expectedDigit == actualDigit;
    }
}
