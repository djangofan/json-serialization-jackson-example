package com.example;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestObjectsTest {

  private static final ObjectMapper MAPPER = new ObjectMapper();

  @Test
  void testSerialization() {
    Map<String, Object> linkedHashMap = new LinkedHashMap<>();
    linkedHashMap.put("foo", "bar");
    linkedHashMap.put("bar", "foo");

    TestObjects.Bar bar = TestObjects.Bar.builder()
        .id(1)
        .description("bar description")
        .stringBoolean("false")
        .nestedMap(linkedHashMap)
        .build();
    TestObjects.Foo foo = TestObjects.Foo.builder()
        .id(1)
        .description("foo description")
        .primitiveBoolean(false)
        .booleanObject(true)
        .bar(bar)
        .build();

    String serialized = null;
    try {
      serialized = MAPPER.writeValueAsString(foo);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
    assertTrue(serialized.contains("foo description"));
  }

  @Test
  void testDeserialization() {
    Map<String, Object> linkedHashMap = new LinkedHashMap<>();
    linkedHashMap.put("foo", "bar");
    linkedHashMap.put("bar", "foo");

    TestObjects.Bar bar = TestObjects.Bar.builder()
        .id(3)
        .description("bar description")
        .stringBoolean("false")
        .nestedMap(linkedHashMap)
        .build();
    TestObjects.Foo foo = TestObjects.Foo.builder()
        .id(1)
        .description("foo description")
        .primitiveBoolean(false)
        .booleanObject(true)
        .bar(bar)
        .build();

    String serialized = null;
    try {
      serialized = MAPPER.writeValueAsString(foo);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }

    assertTrue(serialized.contains("foo description"));
    try {
      TestObjects.Foo foo1 = MAPPER.readValue(serialized, TestObjects.Foo.class);
      TestObjects.Bar bar1 = foo1.getBar();

      assertEquals(bar1.id, 3);
      assertEquals(foo1.getDescription(), "foo description");

      // verify getPrimitiveBoolean
      assertEquals(foo1.isPrimitiveBoolean(), false);

      // verify getStringBoolean
      assertNotNull(bar1.getStringBoolean());
      assertEquals(bar1.getStringBoolean().getClass(), String.class);
      assertEquals(bar1.getStringBoolean(), "false");

      // verify getBooleanObject
      assertNotNull(foo1.getBooleanObject());
      assertEquals(foo1.getBooleanObject().getClass(), Boolean.class);
      assertEquals(foo1.getBooleanObject(), true);

    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }

  }

}