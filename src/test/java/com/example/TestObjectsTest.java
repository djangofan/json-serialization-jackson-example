package com.example;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        .stringBoolean(false)
        .nestedMap(linkedHashMap)
        .build();
    TestObjects.Foo foo = TestObjects.Foo.builder()
        .id(1)
        .description("foo description")
        .primitiveBoolean(false)
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
        .stringBoolean(false)
        .nestedMap(linkedHashMap)
        .build();
    TestObjects.Foo foo = TestObjects.Foo.builder()
        .id(1)
        .description("foo description")
        .primitiveBoolean(false)
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
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }

  }

}