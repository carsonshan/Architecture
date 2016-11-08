package com.jrsolutions.architecture.client;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import es.indrasl.web.test.server.IntrospectorHelper;

public class ProxyClient<T> implements InvocationHandler {
	

	private IntrospectorHelper helper=new IntrospectorHelper();

	public T object;
	
	public  ProxyClient(String className,Object ... args){
		helper.createInstance(className, paramTypes(args), args);
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		
		return helper.execute(object, method.getName(),method.getParameterTypes(), args);

	}

	private Class<?>[] paramTypes(Object ... args){
		Class<?>[] ret=new Class<?>[args.length];
		for(int i=0;i<args.length;i++){
			ret[i]=args.getClass(); // TODO  handle null arg
		}
		return ret;
	}
}
