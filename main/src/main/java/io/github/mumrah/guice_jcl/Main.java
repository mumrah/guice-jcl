package io.github.mumrah.guice_jcl;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.multibindings.Multibinder;
import org.xeustechnologies.jcl.JarClassLoader;

import java.io.*;
import java.net.URL;
import java.util.*;

public class Main {

  private static final String PREFIX = "META-INF/services/";

  public static Class<? extends Plugin> loadPluginClass(String pluginPath, Class clazz) throws IOException, ClassNotFoundException {
    // Load JAR files from the filesystem and load a class from them
    // Use SPI-style config file
    JarClassLoader jcl = new JarClassLoader();
    jcl.add(pluginPath);
    URL config = jcl.getResource(PREFIX + clazz.getName());
    BufferedReader reader = new BufferedReader(new InputStreamReader(config.openStream()));
    String line = reader.readLine();
    String className = line.trim().split(" ")[0];
    return (Class<? extends Plugin>)jcl.loadClass(className);
  }

  public static void main(String args[]) throws Exception {
    // Crawl a directory on the filesystem for plugins
    File pluginDir = new File(System.getProperty("plugins.dir", "build/plugins"));
    final Set<Class<? extends Plugin>> pluginClasses = new HashSet<>();
    for(File plugin : pluginDir.listFiles()) {
      pluginClasses.add(loadPluginClass(plugin.getPath(), Plugin.class));
    }

    Injector injector = Guice.createInjector(new AbstractModule() {
      @Override
      protected void configure() {
        Multibinder<Plugin> multibinder = Multibinder.newSetBinder(binder(), Plugin.class);
        for(Class<? extends Plugin> clazz : pluginClasses) {
          multibinder.addBinding().to(clazz);
        }
        bind(Config.class).toInstance(new Config() {
          @Override
          public String getSomeConfig() {
            return "some-config";
          }
        });
      }
    });

    // Test it out with a dependent class
    SomeDependentThing dependentThing = injector.getInstance(SomeDependentThing.class);
    dependentThing.run();
  }
}
