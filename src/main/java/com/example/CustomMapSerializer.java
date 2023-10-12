package com.example;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomMapSerializer extends StdSerializer<Map<String, Object>> {
  public CustomMapSerializer() {
    this(null);
  }

  public CustomMapSerializer(Class<Map<String, Object>> t) {
    super(t);
  }

  @Override
  public void serialize(Map<String, Object> value, JsonGenerator jgen, SerializerProvider serializerProvider) throws IOException {
    jgen.writeStartObject();
    for (Map.Entry<String, Object> entry : value.entrySet()) {
      String key = entry.getKey();
      Pattern pattern = Pattern.compile("true|false", Pattern.CASE_INSENSITIVE);
      Matcher matcher = pattern.matcher(key);
      Object val = entry.getValue();
      if (!matcher.matches()) {
        if (val instanceof Boolean) {
          val = Boolean.toString((Boolean) val);
        }
      }
      jgen.writeObjectField(key, val);
    }
    jgen.writeEndObject();
  }
}