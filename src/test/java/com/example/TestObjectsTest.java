package com.example;


import com.example.TestObjects.Bar;
import com.example.TestObjects.Foo;
import com.example.TestObjects.NestedMap;
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

    Map<String, Object> deepLinkedHashMap = new LinkedHashMap<>();
    deepLinkedHashMap.put("foo1", "1");
    deepLinkedHashMap.put("foo2", "2");
    NestedMap deepMap = NestedMap.builder()
        .id(3435)
        .description("deep map")
        .nestedMap(deepLinkedHashMap)
        .build();

    Map<String, Object> fooLinkedHashMap = new LinkedHashMap<>();
    fooLinkedHashMap.put("foo1", "1");
    fooLinkedHashMap.put("foo2", "2");
    NestedMap fooNestedMap = NestedMap.builder()
        .id(3435)
        .description("foo map")
        .nestedMap(fooLinkedHashMap)
        .build();

    Map<String, Object> barLinkedHashMap = new LinkedHashMap<>();
    barLinkedHashMap.put("bar1", "3");
    barLinkedHashMap.put("bar2", "4");
    barLinkedHashMap.put("deepMap", deepMap);
    NestedMap barNestedMap = NestedMap.builder()
        .id(1465)
        .description("bar map")
        .nestedMap(barLinkedHashMap)
        .build();

    Bar bar = Bar.builder()
        .id(1)
        .description("bar description")
        .stringBoolean("false")
        .barMap(barNestedMap)
        .build();

    Foo foo = Foo.builder()
        .id(1)
        .description("foo description")
        .primitiveBoolean(false)
        .booleanObject(true)
        .fooMap(fooNestedMap)
        .barWithMap(bar)
        .build();

    String serialized = null;
    try {
      serialized = MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(foo);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
    System.out.println(serialized);
    assertTrue(serialized.contains("foo description"));
    assertTrue(serialized.contains("deep map"));
  }

  @Test
  void testDeserialization() {
    Map<String, Object> deepLinkedHashMap = new LinkedHashMap<>();
    deepLinkedHashMap.put("foo1", "11");
    deepLinkedHashMap.put("foo2", "12");
    NestedMap deepMap = NestedMap.builder()
        .id(235)
        .description("deep map")
        .nestedMap(deepLinkedHashMap)
        .build();

    Map<String, Object> fooLinkedHashMap = new LinkedHashMap<>();
    fooLinkedHashMap.put("foo1", "1");
    fooLinkedHashMap.put("foo2", "2");
    NestedMap fooNestedMap = NestedMap.builder()
        .id(3435)
        .description("foo map")
        .nestedMap(fooLinkedHashMap)
        .build();

    Map<String, Object> barLinkedHashMap = new LinkedHashMap<>();
    barLinkedHashMap.put("bar1", "3");
    barLinkedHashMap.put("bar2", "4");
    barLinkedHashMap.put("deepMap", deepMap);
    NestedMap barNestedMap = NestedMap.builder()
        .id(1465)
        .description("bar map")
        .nestedMap(barLinkedHashMap)
        .build();

    Bar bar = Bar.builder()
        .id(7)
        .description("bar description")
        .stringBoolean("false")
        .barMap(barNestedMap)
        .build();

    Foo foo = Foo.builder()
        .id(9)
        .description("foo description")
        .primitiveBoolean(false)
        .booleanObject(true)
        .fooMap(fooNestedMap)
        .barWithMap(bar)
        .build();

    String serialized = null;
    try {
      serialized = MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(foo);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
    System.out.println(serialized);
    assertTrue(serialized.contains("foo description"));
    assertTrue(serialized.contains("deep map"));
    try {
      Foo foo1 = MAPPER.readValue(serialized, Foo.class);
      Bar bar1 = foo1.getBarWithMap();
      NestedMap barMap1 = bar1.getBarMap();
      Map<String, Object> deep1 = barMap1.getNestedMap();

      assertEquals(bar1.getId(), 7);
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

      // verify deep map
      assertEquals("deep map", ((Map)deep1.get("deepMap")).get("description"));
      assertEquals("12", ((Map<?, ?>)((Map<?, ?>)deep1.get("deepMap")).get("nestedMap")).get("foo2"));
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }

  }

  @Test
  void testDeserialization_NestedMapsOnly() {

    Map<String, Object> level3Map = new LinkedHashMap<>();
    level3Map.put("foo1", "31");
    level3Map.put("foo2", "32");

    Map<String, Object> level2Map = new LinkedHashMap<>();
    level2Map.put("foo1", "21");
    level2Map.put("level3Map", level3Map);

    Map<String, Object> level1Map = new LinkedHashMap<>();
    level1Map.put("foo1", "11");
    level1Map.put("level2Map", level2Map);

    NestedMap level0map = NestedMap.builder()
        .nestedMap(level1Map)
        .build();

    String serialized = null;
    try {
      serialized = MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(level0map);

      NestedMap nm0 = MAPPER.readValue(serialized, NestedMap.class);
      Map<String, Object> nm1 = nm0.getNestedMap();
      Map<String, Object> nm2 = (Map<String, Object>) nm1.get("level2Map");
      Map<String, Object> nm3 = (Map<String, Object>) nm2.get("level3Map");
      System.out.println(nm1);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
    //System.out.println(serialized);
  }

}