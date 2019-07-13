package me.joshua.Day_23;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
public class BookMapper {

    public static class BookSerializer extends JsonSerializer<Book> {

        @Override
        public void serialize(Book book, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeStringField("isbn", book.getIsbn());
            jsonGenerator.writeEndObject();
        }
    }
}
