package com.test;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Map;

public class TestObjects {


  @ToString
  @Getter
  @AllArgsConstructor
  @NoArgsConstructor
  static class Foo {
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty("id")
    long id;
    @JsonProperty("description")
    String description;
    @JsonProperty("bar")
    Bar bar;
  }

  @ToString
  @Getter
  @AllArgsConstructor
  @NoArgsConstructor
  static class Bar {
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty("id")
    long id;
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty("enabled")
    boolean enabled;
    @JsonProperty("description")
    String description;
    @JsonSerialize(using = BarMapSerializer.class)
    @JsonProperty("bar")
    Map<String, Object> nestedMap;
  }
}