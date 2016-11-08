package com.jrsolutions.architecture.server;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class ObjectBroker {

	private final IntrospectorHelper intros=new IntrospectorHelper();
	
	private Map<Integer,Object> liveObjects=new HashMap<Integer,Object>();
	private AtomicInteger counter=new AtomicInteger(1);
	
	public int createObject(String className,String[] types,Object ... args){

		// habría que convertir los args a los tipos indicados (y con el mismo numero)
		return createObject( className,paramTypes(types),args);

	}
	
	public int createObject(String className,Class<?>[] types,Object ... args){
		Object obj=intros.createInstance(className,types,args); //new Object(); //todo
		int id=counter.getAndIncrement();
		liveObjects.put(id, obj);
		return id;
	}
	
	public void deleteObject(int objectId){
		liveObjects.remove(objectId);
	}
	
	public Object execute(int objectId,String method,String[] types,String[] args){
		Object obj=liveObjects.get(objectId);
		if(obj!=null){
			return intros.execute(obj,method,paramTypes(types),args);
		}else{
			// El objeto ya no existe
			return null;
		}
	}
	
	private Class<?>[] paramTypes(String ... types) {
		Class<?>[] ret=new Class<?>[types.length];
		for(int i=0;i<types.length;i++){
			try {
				ret[i]=intros.findClass(types[i]);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // TODO  handle null arg
		}
		return ret;
	}
	
}
