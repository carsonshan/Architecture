package com.jrsolutions.architecture.server;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarFile;

public class IntrospectorHelper {

	private List<ClassLoader> loaders=new ArrayList<ClassLoader>();
	
	public IntrospectorHelper(){
		loaders.add(this.getClass().getClassLoader());
		// Lee un fichero de configuracion e inicializa Jars que no estan
		//    en el classpath al arrancar la aplicacion
	}
	
	public void addJar(String urlString){		
		try {
			URL url;
			url = new URL(urlString);
			URL[] array=new URL[1];
			array[0]=url;
			URLClassLoader jcLoader= new URLClassLoader(array);
			// los nuevos los ponemos al final, es decir, no cambiamos la 
			//    la implementacion a usar (pero podríamos, add(0,jcLoader) o
			//    modificando findClass() para que busque en otro orden

			
			// la URL es local la podemos usar como si fuera un jarFile
			// JarFile jar=new JarFile(url.getFile());
			//   jar.getManifest();  buscar propiedades del manifest que nos inventemos nosotros
			//   jar.entries(); iterar sobre las clases para buscar las que implementen alguna
			//                   interfaz determinada o alguna notacion
			
			loaders.add(jcLoader);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	
	public Object createInstance(String className, Class<?>[] types,Object[] args) {
		Class<?> clase;
		try {
			clase = findClass(className);
			if(args !=null && args.length>0){
				Constructor<?> c=clase.getConstructor(types);
				Object obj=c.newInstance(args);
				return obj;	
			}else{
				Object obj=clase.newInstance();
				return obj;	
			}
			// not reached
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}

	public Object execute(Object obj, String method,Class<?>[]types, Object[] args) {
		
		try {
			Method m=obj.getClass().getMethod(method, types);
			Object r = m.invoke(obj, args);
			return r;
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	public Class<?> findClass(String name) throws ClassNotFoundException {
		for(ClassLoader loader:loaders){
			Class<?> clase=loader.loadClass(name);
			if(clase!=null){
				return clase;
			}
		}
		return null;
	}
	
	


}
