package com.drgym.drgym.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;

public class TimestampFormatted extends JsonSerializer<Timestamp> {
    @Override
    public void serialize(Timestamp timestamp, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (timestamp == null) {
            gen.writeString("00:00:00");
        } else {
            String formattedTime = timestamp.toLocalDateTime().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            gen.writeString(formattedTime);
        }
    }
}
