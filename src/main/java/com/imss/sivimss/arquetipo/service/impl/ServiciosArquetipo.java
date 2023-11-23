package com.imss.sivimss.arquetipo.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.imss.sivimss.arquetipo.configuration.MyBatisConfig;
import com.imss.sivimss.arquetipo.configuration.mapper.Consultas;
import com.imss.sivimss.arquetipo.configuration.mapper.PersonaMapper;
import com.imss.sivimss.arquetipo.model.entity.PersonaEntityMyBatis;
import com.imss.sivimss.arquetipo.model.request.Paginado;
import com.imss.sivimss.arquetipo.model.request.PersonaNombres;
import com.imss.sivimss.arquetipo.service.PeticionesArquetipo;
import com.imss.sivimss.arquetipo.service.beans.ServiciosQuerysArquetipo;
import com.imss.sivimss.arquetipo.utils.AppConstantes;
import com.imss.sivimss.arquetipo.utils.Response;

import lombok.extern.java.Log;

@Log
@Service
public class ServiciosArquetipo implements PeticionesArquetipo {
	
	//private SqlSessionFactory sqlSessionFactory = MyBatisConfig.buildqlSessionFactory();

	@Autowired
	private ServiciosQuerysArquetipo query;

	@Autowired
	private MyBatisConfig myBatisConfig;
	
	private SqlSessionFactory sqlSessionFactory = myBatisConfig.buildqlSessionFactory();
	
	@Override
	public Response<Object>  consultaUsandoQuerysNativas() {
		
		List<Map<String, Object>> result = new ArrayList<>();
		try(SqlSession session = sqlSessionFactory.openSession()) {
			Consultas consultas = session.getMapper(Consultas.class);
			result = consultas.selectNativeQuery(query.queryGetArticulos());
		}
		
		return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO, result);
	}
	
	
	/* 
	 * Este es un ejemplo de un servicio para realizar un insert usando un objeto como parametro 
	 */
	@Override
	public Response<Object> nuevoRegistroUsandoMappersObj(PersonaNombres persona /* Nuestro request */ ) {
		/* 
		 * Creamos una instancia del objeto/representación del nuevo registro 
		 */
		PersonaEntityMyBatis per = new PersonaEntityMyBatis();
		
		/* Realizamos el mapeo de datos desde nuestro request  */
		per.setNomPersona(persona.getNomPersona());
		per.setPrimerApellido(persona.getPrimerApellido());
		per.setSegundoApellido(persona.getSegundoApellido());
		
		/* Creamos una instancia de nuestro objeto de respuesta */
		Response<Object> resp = new Response<>();

		/* 
		 * Se inicia un session Factory  
		 * 
		 * Usa los datos de MyBatisConfig y toma el datasource de ese mismo archivo
		 * 
		 * La trydeclaración -with-resources es una trydeclaración que declara uno 
		 * o más recursos. Un recurso es un objeto que debe cerrarse una vez finalizado el programa.
		 * */
		try (SqlSession session = sqlSessionFactory.openSession()) {
			
			/* 
			 * Debemos indicar cual o cuales Mapper vamos a utilizar
			 * (Asegurate de declararlo en tu archivo MyBatisConfig.class
			 * configuration.addMapper(NombreDeMiMapper.class);)
			 */
			PersonaMapper personaMapper = session.getMapper(PersonaMapper.class);

			
			try {
				/* 
				 * Para sentencias que actualizan datos o crean nuevos usaremos un try-catch
				 * 1._ accedemos al metodo de nuestro objeto mapper 
				 * 2._ Ejecutamos un commit para ver los cambios reflejados en BD
				 * 3._ Seteamos la data que vamos a devolver como respuesta
				 *  */
				
				personaMapper.nuevoRegistroObj(per);
				resp.setDatos(per);
			} catch (Exception e) {
				/*
				 * Para el escenario en que fallen las querys
				 * 
				 * 1._ Realizamos un roll back (regresamos los cambios)
				 * 2._ Cerramos la conexión.
				 * */
				
				session.rollback();
				session.close();
			}

			/* 
			 * Aunque Mybatis se encarga de cerrar las conexiones en automatico y 
			 * La trydeclaración -with-resources cierra los recursos en automático, 
			 * nunca esta de más cerrar manualmente la conexión 
			 */
			session.commit();
			session.close();
		}

		return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO, resp);
	}

	@Override
	public Response<Object> actualizarRegistroUsandoMappersObj(PersonaNombres persona, int id ) {
		PersonaEntityMyBatis per = new PersonaEntityMyBatis();
		
		per.setNomPersona(persona.getNomPersona());
		per.setPrimerApellido(persona.getPrimerApellido());
		per.setSegundoApellido(persona.getSegundoApellido());
		per.setIdPersona(id);
		
		Response<Object> resp = new Response<>();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			
			PersonaMapper personaMapper = session.getMapper(PersonaMapper.class);

			try {
				personaMapper.actualizarRegistroObj(per);
				resp.setDatos(per);
			} catch (Exception e) {	
				session.rollback();
				session.close();
			}

			session.commit();
			session.close();
		}

		return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO, resp);
	}


	@Override
	public Response<Object> paginadoGenerico(Paginado paginado) {
		Page<Map<String, Object>> objetoMapeado = null;
		String queryPage = paginado.getQuery() + " LIMIT " + (paginado.getPagina()*paginado.getTamanio()) + ", " + paginado.getTamanio();
		String query = "SELECT COUNT(*) AS conteo FROM (" + paginado.getQuery() + ") tem";
		List<Map<String, Object>> resp;
		List<Map<String, Object>> respTotal;
		Pageable pageable = PageRequest.of(paginado.getPagina(), paginado.getTamanio());
		
		
		try(SqlSession session = sqlSessionFactory.openSession()) {
			
			Consultas consultas = session.getMapper(Consultas.class);
			resp = consultas.selectNativeQuery(queryPage);
			respTotal = consultas.selectNativeQuery(query);
			
			Integer conteo =  Integer.parseInt( respTotal.get(0).get("conteo").toString() );
			objetoMapeado = new PageImpl<>(resp, pageable, conteo);
		}
		
		
		return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO, objetoMapeado);
	}

}
