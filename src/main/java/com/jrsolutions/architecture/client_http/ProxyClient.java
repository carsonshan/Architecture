package com.jrsolutions.architecture.client_http;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ProxyClient<T> implements InvocationHandler {
	

	
	public T object;
	
	public int objectId;
	
	public  ProxyClient(String className,Object ... args){
		try {
			String urlString="http://host:puerto/ctx/servlet?createObject"+"?className="+className+args;
			//helper.createInstance(className, paramTypes(args), args);	
			URL url=new URL(urlString);
			HttpURLConnection con=(HttpURLConnection)url.openConnection();
			int code=con.getResponseCode();
			if(code!=200){
				objectId=-1;
				return ;
			}
			ObjectInputStream is=new ObjectInputStream(con.getInputStream());
			this.objectId=is.readInt();	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public  ProxyClient(String className,Class<?>[]types,Object ... args){
		try {
			String urlString="http://host:puerto/ctx/servlet?createObject"+"?className="+className+args;
			//helper.createInstance(className, paramTypes(args), args);
			URL url;

			url = new URL(urlString);

			HttpURLConnection con=(HttpURLConnection)url.openConnection();
			int code=con.getResponseCode();
			if(code!=200){
				objectId=-1;
				return ;
			}
			ObjectInputStream is=new ObjectInputStream(con.getInputStream());
			this.objectId=is.readInt();	
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		String urlString="http://host:puerto/ctx/servlet?executeObject"+"?ObjectId="+objectId+args;
		//helper.createInstance(className, paramTypes(args), args);
		URL url=new URL(urlString);
		HttpURLConnection con=(HttpURLConnection)url.openConnection();
		ObjectOutputStream os=new ObjectOutputStream(con.getOutputStream());
		for(Object p:args){
			os.writeObject(p);
		}
		int code=con.getResponseCode();
		if(code!=200){
			return  null;
		}
		ObjectInputStream is=new ObjectInputStream(con.getInputStream());
		Object object=is.readObject();
		return object;	
	}


}
