package com.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.LinkedHashMap;
import java.util.Map;

public class Main {
  public static void main(String[] args) throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    final Map<String, Object> linkedHashMap = new LinkedHashMap<>();
    linkedHashMap.put("foo", "bar");
    linkedHashMap.put("bar", "foo");
    TestObjects.Bar bar = new TestObjects.Bar(1, false, "bar", linkedHashMap);
    TestObjects.Foo foo = new TestObjects.Foo(1, "foo", bar);
    String serialized = mapper.writeValueAsString(foo);
    TestObjects.Foo foo2 = mapper.readValue(serialized, TestObjects.Foo.class);
    System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(foo2));
  }
}