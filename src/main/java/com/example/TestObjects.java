package com.example;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Map;

public class TestObjects {

  @ToString
  @Getter
  @AllArgsConstructor
  @NoArgsConstructor
  @Builder
  static class Foo {

    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty("id")
    long id;

    @JsonProperty("description")
    String description;

    @JsonProperty("bar")
    Bar bar;

    @JsonProperty("primitiveBoolean")
    boolean primitiveBoolean;

    @JsonProperty("booleanObject")
    Boolean booleanObject;

  }

  @ToString
  @Getter
  @AllArgsConstructor
  @NoArgsConstructor
  @Builder
  static class Bar {

    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty("id")
    int id;

    @JsonProperty("stringBoolean")
    String stringBoolean;

    @JsonProperty("description")
    String description;

    @JsonSerialize(using = BarMapSerializer.class)
    @JsonProperty("bar")
    Map<String, Object> nestedMap;

  }

}