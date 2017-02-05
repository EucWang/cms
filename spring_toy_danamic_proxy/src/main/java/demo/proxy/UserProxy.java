package demo.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.logging.Logger;

import demo.annotation.ProxyInfo;

public class UserProxy implements InvocationHandler{
	
	private static final Logger log = Logger.getLogger("UserProxy");
	
	private Object target;
	
	private UserProxy() {
	}
	
	public static Object getInstance(Object obj){
		UserProxy userProxy = new UserProxy();
		userProxy.target = obj;
		
		Object proxy = Proxy.newProxyInstance(userProxy.target.getClass().getClassLoader(), 
				userProxy.target.getClass().getInterfaces(), 
				userProxy);
		return proxy;
	}
	

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		
		log.info(" befor method invoke");
		
		if(method.isAnnotationPresent(ProxyInfo.class)){
			ProxyInfo annotation = method.getAnnotation(ProxyInfo.class);
			log.info(annotation.value());
		}
		
		Object result = method.invoke(target, args);
		
		
		return result;
	}

}
