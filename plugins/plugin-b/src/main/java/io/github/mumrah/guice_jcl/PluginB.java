package io.github.mumrah.guice_jcl;

import com.google.inject.Inject;
import org.codehaus.jackson.map.ObjectMapper;

public class PluginB implements Plugin {

  private final Config config;

  @Inject
  public PluginB(Config config) {
    this.config = config;
  }

  @Override
  public void doSomething() throws Exception {
    Parent parent = new Parent();
    parent.age = 20;
    Name name = new Name();
    name.first = "John";
    name.last = "Doe";
    parent.name = name;

    ObjectMapper mapper = new ObjectMapper();
    System.err.println("Parent: " + mapper.writeValueAsString(parent) + ", config=" + config.getSomeConfig());
  }
}
