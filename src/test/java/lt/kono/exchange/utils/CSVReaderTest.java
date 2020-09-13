package lt.kono.exchange.utils;

import lt.kono.exchange.domain.Currency;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class CSVReaderTest {

    @Autowired
    CSVReader csvReader;

    @Value("${exchange.data.file}")
    private Resource dataResource;

    @Test
    @DisplayName("Should load Currency object from file")
    public void whenLoadingUsersFromCsvFile_thenLoaded() {
        List<Currency> users = csvReader.loadObjectList(Currency.class, dataResource.getFilename());
        assertFalse(users.isEmpty());
    }

}