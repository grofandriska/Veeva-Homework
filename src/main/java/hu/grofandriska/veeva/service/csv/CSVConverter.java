package hu.grofandriska.veeva.service.csv;

import hu.grofandriska.veeva.model.account.Lead;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.io.UncheckedIOException;

@Service
public class CSVConverter {
    public String convertLeadToCsv(Lead lead) {
        StringWriter sw = new StringWriter();

        try (CSVPrinter printer = new CSVPrinter(sw,
                CSVFormat.DEFAULT
                        .withHeader("email__c", "first_name__c", "last_name__c"))) {

            printer.printRecord(
                    lead.getEmailAddress(),
                    lead.getFirstName(),
                    lead.getLastName()
            );

        } catch (IOException e) {
            throw new UncheckedIOException("Failed to write CSV", e);
        }

        return sw.toString();
    }
}
