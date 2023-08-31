package me.bw;

import java.net.URL;
import java.net.URLClassLoader;

public class CustomClassLoader extends URLClassLoader {
    public CustomClassLoader(URL[] urls) {
        super(urls, null);
    }

    @Override
    public synchronized Class<?> loadClass(String name) throws ClassNotFoundException {
        return super.loadClass(name);
    }
}
