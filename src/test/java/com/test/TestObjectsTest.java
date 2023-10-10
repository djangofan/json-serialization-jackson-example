package com.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestObjectsTest {

  @Test
  void addition() {
    final Map<String, Object> linkedHashMap = new LinkedHashMap<>();
    linkedHashMap.put("foo", "bar");
    linkedHashMap.put("bar", "foo");

    TestObjects.Bar bar = new TestObjects.Bar(1, false, "bar", linkedHashMap);
    TestObjects.Foo foo = new TestObjects.Foo(1, "foo", bar, false);

    Assertions.assertDoesNotThrow(() -> {
      String serialized = Main.MAPPER.writeValueAsString(foo);
      assertTrue(serialized.contains("test"));
    });
  }

}
