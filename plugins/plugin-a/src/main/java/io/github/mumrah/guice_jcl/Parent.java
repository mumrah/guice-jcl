package io.github.mumrah.guice_jcl;

import org.codehaus.jackson.annotate.JsonUnwrapped;

public class Parent {
  public int age;
  @JsonUnwrapped
  public Name name;
}