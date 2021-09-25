package recipes.utils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.List;


@Converter
public class StringListConverter implements AttributeConverter<List<String>, String> {

    @Override
    public String convertToDatabaseColumn(List<String> strs) {
        return String.join(";", strs);
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        return List.of(dbData.split(";"));
    }
}
