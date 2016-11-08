package com.jrsolutions.architecture.client;

import java.lang.reflect.Proxy;

public class PFactory {

	public <T> Object createObject(Class<T> clase) {

		// retornaria un objeto local java como siempre
		// return clase.newInstance();

		return Proxy.newProxyInstance(clase.getClassLoader(), clase.getInterfaces(),
				new ProxyClient<T>(clase.getName()));

	}

	public <T> Object createObject(Class<T> clase, Object... args) {

		// retornaria un objeto local java como siempre
		// return clase.newInstance();

		return Proxy.newProxyInstance(clase.getClassLoader(), clase.getInterfaces(),
				new ProxyClient<T>(clase.getName(), args));

	}
}
