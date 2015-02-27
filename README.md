# Example of using Guice with JCL

This is a sample project to demonstrate how to use Google Guide dependency injection with JCL (JarClassLoader).

How it works:

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
