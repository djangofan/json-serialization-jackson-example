package com.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.LinkedHashMap;
import java.util.Map;

public class Main {
  private static final ObjectMapper MAPPER = new ObjectMapper();

  public static void main(String[] args) throws JsonProcessingException {
    final Map<String, Object> linkedHashMap = new LinkedHashMap<>();
    linkedHashMap.put("foo", "bar");
    linkedHashMap.put("bar", "foo");

    TestObjects.Bar bar = new TestObjects.Bar(1, false, "bar", linkedHashMap);
    TestObjects.Foo foo = new TestObjects.Foo(1, "foo", bar, false);

    // serialize to String
    String serialized = MAPPER.writeValueAsString(foo);

    // de-serialize
    TestObjects.Foo foo2 = MAPPER.readValue(serialized, TestObjects.Foo.class);
    System.out.println(MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(foo2));

  }

}