/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unju.fi.services;

import org.springframework.beans.BeanInstantiationException;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Clase de Ejemplo Básico para obtener el contexto de Spring
 * 
 * @author José
 */
public class SpringUtil {
	private static ApplicationContext applicationContext;
	static {
		try {
			applicationContext = new ClassPathXmlApplicationContext(
					"ar/edu/unju/fi/services/applicationContext.xml");
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * Devuelve una instancia del bean solicitado
	 * 
	 * @param beanName
	 * @return
	 * @throws BeansException
	 * @throws BeanInstantiationException
	 */
	public static Object getBean(String beanName) throws BeansException,
			BeanInstantiationException {
		return applicationContext.getBean(beanName);
	}
}