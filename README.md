# hotswapagent-deadlock-demo

Run this demo with JVM option "-XX:+AllowEnhancedClassRedefinition -XX:HotswapAgent=external -javaagent:/your-hotswap-agent.jar-location=autoHotswap=true -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=localhost:8000", and it is possible to hit the deadlock below.

```shell
Found one Java-level deadlock:
=============================
"main":
  waiting to lock monitor 0x00007fc93000ff00 (object 0x000000061e6c1bc0, a org.hotswap.agent.config.PluginManager),
  which is held by "Thread-4"
"Thread-4":
  waiting to lock monitor 0x00007fc93000fe00 (object 0x000000061e604170, a me.bw.CustomClassLoader),
  which is held by "main"

Java stack information for the threads listed above:
===================================================
"main":
	at org.hotswap.agent.config.PluginManager.initClassLoader(PluginManager.java:181)
	- waiting to lock <0x000000061e6c1bc0> (a org.hotswap.agent.config.PluginManager)
	at org.hotswap.agent.annotation.handler.PluginClassFileTransformer.transform(PluginClassFileTransformer.java:173)
	at org.hotswap.agent.annotation.handler.PluginClassFileTransformer.transform(PluginClassFileTransformer.java:112)
	at org.hotswap.agent.util.HotswapTransformer.transform(HotswapTransformer.java:253)
	at java.lang.instrument.ClassFileTransformer.transform(java.instrument@11.0.15/ClassFileTransformer.java:246)
	at sun.instrument.TransformerManager.transform(java.instrument@11.0.15/TransformerManager.java:188)
	at sun.instrument.InstrumentationImpl.transform(java.instrument@11.0.15/InstrumentationImpl.java:563)
	at java.lang.ClassLoader.defineClass1(java.base@11.0.15/Native Method)
	at java.lang.ClassLoader.defineClass(java.base@11.0.15/ClassLoader.java:1017)
	at java.security.SecureClassLoader.defineClass(java.base@11.0.15/SecureClassLoader.java:174)
	at java.net.URLClassLoader.defineClass(java.base@11.0.15/URLClassLoader.java:555)
	at java.net.URLClassLoader$1.run(java.base@11.0.15/URLClassLoader.java:458)
	at java.net.URLClassLoader$1.run(java.base@11.0.15/URLClassLoader.java:452)
	at java.security.AccessController.doPrivileged(java.base@11.0.15/Native Method)
	at java.net.URLClassLoader.findClass(java.base@11.0.15/URLClassLoader.java:451)
	at java.lang.ClassLoader.loadClass(java.base@11.0.15/ClassLoader.java:589)
	- locked <0x000000061e604170> (a me.bw.CustomClassLoader)
	at java.lang.ClassLoader.loadClass(java.base@11.0.15/ClassLoader.java:522)
	at me.bw.CustomClassLoader.loadClass(CustomClassLoader.java:13)
	- locked <0x000000061e604170> (a me.bw.CustomClassLoader)
	at me.bw.App.test(App.java:22)
	at me.bw.App.main(App.java:12)
"Thread-4":
	at java.lang.ClassLoader.checkCerts(java.base@11.0.15/ClassLoader.java:1141)
	- waiting to lock <0x000000061e604170> (a me.bw.CustomClassLoader)
	at java.lang.ClassLoader.preDefineClass(java.base@11.0.15/ClassLoader.java:906)
	at java.lang.ClassLoader.defineClass(java.base@11.0.15/ClassLoader.java:1015)
	at jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(java.base@11.0.15/Native Method)
	at jdk.internal.reflect.NativeMethodAccessorImpl.invoke(java.base@11.0.15/NativeMethodAccessorImpl.java:62)
	at jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(java.base@11.0.15/DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(java.base@11.0.15/Method.java:566)
	at org.hotswap.agent.javassist.util.proxy.DefineClassHelper$JavaOther.defineClass(DefineClassHelper.java:214)
	at org.hotswap.agent.javassist.util.proxy.DefineClassHelper$Java11.defineClass(DefineClassHelper.java:52)
	at org.hotswap.agent.javassist.util.proxy.DefineClassHelper.toClass(DefineClassHelper.java:263)
	at org.hotswap.agent.javassist.ClassPool.toClass(ClassPool.java:1232)
	at org.hotswap.agent.javassist.CtClass.toClass(CtClass.java:1398)
	at org.hotswap.agent.util.classloader.ClassLoaderDefineClassPatcher.patch(ClassLoaderDefineClassPatcher.java:93)
	at org.hotswap.agent.config.PluginManager.initClassLoader(PluginManager.java:186)
	- locked <0x000000061e6c1bc0> (a org.hotswap.agent.config.PluginManager)
	at org.hotswap.agent.util.HotswapTransformer$1.executeCommand(HotswapTransformer.java:323)
	at org.hotswap.agent.command.impl.CommandExecutor.run(CommandExecutor.java:43)
```