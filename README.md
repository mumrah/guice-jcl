# Example of using Guice with JCL

This is a sample project to demonstrate how to use [Google Guice](https://github.com/google/guice) dependency injection with [JCL](https://github.com/kamranzafar/JCL) (JarClassLoader).

## How it works

Instead of using JCL's `JclObjectFactory` to instantiate objects, you bind the Classes returned by JCL in a Guice
module. Here is a simple example binding `SomeImpl` to `SomeInterface`. The impl class resides in "some.jar" which
is loaded at runtime by JCL.

```java
JarClassLoader jcl = new JarClassLoader();
jcl.add("some.jar");
Class<SomeInterface> clazz = (Class<SomeInterface>)jcl.loadClass("SomeImpl");
Injector injector = Guice.createInjector(new AbstractModule() {
    @Override
    protected void configure() {
      bind(SomeInterface.class).to(clazz);
    }
});

SomeInterface something = injector.getInstance(SomeInterface.class); // This returns SomeImpl
```

## What's in this repo

Two plugins (plugin-a and plugin-b) which both depend on _different_ versions of Jackson. JCL loads the plugins from a "plugins" directory at runtime and binds them to a Guice Multibinder. A dependent class then gets injected with both plugins and runs them.

To run the example: `./gradlew :main:run` 
