package spring.bank.calculation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class Iban {

    static String ibanCountryCode;

    static String ibanCheckDigits;

    static String ibanBankCode;

    @Value("${iban.countrycode}")
    public void setIbanCountryCode(String ibanCountryCode) {
        Iban.ibanCountryCode = ibanCountryCode;
    }

    @Value("${iban.checkdigits}")
    public void setIbanCheckDigits(String ibanCheckDigits) {
        Iban.ibanCheckDigits = ibanCheckDigits;
    }

    @Value("${iban.bankcode}")
    public void setIbanBankCode(String ibanBankCode) {
        Iban.ibanBankCode = ibanBankCode;
    }

    public static String build(Integer accountNumber) {

        String formattedAccountNumber = String.format("%06d", accountNumber);

        return ibanCountryCode
                + ibanCheckDigits
                + ibanBankCode
                + formattedAccountNumber;
    }

    public static boolean validate(String destinationIban) {
        return true; //Implement algorithm from https://en.wikipedia.org/wiki/International_Bank_Account_Number;
    }

    public static boolean isSourceBankEqualsDestinationBank(String destinationIban) {

        char[] destinationBankChar = new char[12];
        destinationIban.getChars(0,12, destinationBankChar,0);

        String sourceBank = ibanCountryCode+ibanCheckDigits+ibanBankCode;
        String destinationBank = String.valueOf(destinationBankChar);

        return Objects.equals(destinationBank, sourceBank);
    }
}
