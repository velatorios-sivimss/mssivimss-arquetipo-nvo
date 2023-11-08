package com.imss.sivimss.arquetipo.configuration;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import com.imss.sivimss.arquetipo.configuration.mapper.Consultas;


@Component
public class MyBatisConnect {
	private AnnotationConfigApplicationContext context = null;
	
	public AnnotationConfigApplicationContext conectar () {
		try {
			context = new AnnotationConfigApplicationContext(MyBatisConfig.class);
			return context;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Consultas crearBeanDeConsultas () {
		Consultas consultas = null;
		
		try {
			consultas = context.getBean(Consultas.class);
		} catch (Exception e) {
			e.printStackTrace();
			return consultas;
		}
		
		return consultas;
	}
	
	public void cerrar() {
		context.close();
	}
}
