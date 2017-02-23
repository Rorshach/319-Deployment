package com.coastcapitalsavings.util.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * Serializes Timestamps into a more readable format instead of milliseconds.
 */
@Component
public class JsonTimeStampSerializer extends JsonSerializer<Timestamp> {

    private static final SimpleDateFormat dateTimeFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");

    @Override
    public void serialize(Timestamp value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        String formattedDate = dateTimeFormat.format(value);
        gen.writeString(formattedDate);
    }
}

