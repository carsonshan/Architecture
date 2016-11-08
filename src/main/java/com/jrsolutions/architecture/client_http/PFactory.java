package com.jrsolutions.architecture.client_http;

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
	public <T> Object createObject(Class<T> clase,Class<?>[] types, Object... args) {

		// retornaria un objeto local java como siempre
		// return clase.newInstance();

		return Proxy.newProxyInstance(clase.getClassLoader(), clase.getInterfaces(),
				new ProxyClient<T>(clase.getName(),types, args));

	}
}
