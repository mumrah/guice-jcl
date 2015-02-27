package io.github.mumrah.guice_jcl;

import com.google.inject.Inject;

import java.util.Collections;
import java.util.Set;

public class SomeDependentThing {
  private final Set<Plugin> plugins;

  @Inject
  public SomeDependentThing(Set<Plugin> plugins) {
    this.plugins = Collections.unmodifiableSet(plugins);
  }

  public void run() throws Exception {
    System.err.println("Before");
    for(Plugin plugin : plugins) {
      plugin.doSomething();
    }
    System.err.println("After");
  }
}
