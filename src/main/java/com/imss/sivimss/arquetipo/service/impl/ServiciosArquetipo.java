package com.imss.sivimss.arquetipo.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.imss.sivimss.arquetipo.configuration.MyBatisConfig;
import com.imss.sivimss.arquetipo.configuration.mapper.Consultas;
import com.imss.sivimss.arquetipo.configuration.mapper.OrdenesServicioMapper;
import com.imss.sivimss.arquetipo.configuration.mapper.PersonaMapper;
import com.imss.sivimss.arquetipo.configuration.mapper.PersonaMigracionMapper;
import com.imss.sivimss.arquetipo.model.entity.PersonaEntity;
import com.imss.sivimss.arquetipo.model.entity.PersonaEntityMyBatis;
import com.imss.sivimss.arquetipo.model.request.PersonaNombres;
import com.imss.sivimss.arquetipo.model.request.UsuarioDto;
import com.imss.sivimss.arquetipo.service.PeticionesArquetipo;
import com.imss.sivimss.arquetipo.utils.AppConstantes;
import com.imss.sivimss.arquetipo.utils.Response;


@Service
public class ServiciosArquetipo implements PeticionesArquetipo {

	@Autowired
	private ServiciosQuerysArquetipo query;
	
	public Response<Object>  consultaUsandoMappers( ) {
		SqlSessionFactory sqlSessionFactory = MyBatisConfig.buildqlSessionFactory();
		
		List<Map<String, Object>> result = new ArrayList<>();
		try(SqlSession session = sqlSessionFactory.openSession()) {
			OrdenesServicioMapper ods = session.getMapper(OrdenesServicioMapper.class);
			result = ods.consultaArticulos();	
		}catch(Exception ex) {
			ex.getMessage();
		}
		
		return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO, result);
	}

	
	public Response<Object>  consultaUsandoQuerysNativas() {

		SqlSessionFactory sqlSessionFactory = MyBatisConfig.buildqlSessionFactory();
		List<Map<String, Object>> result = new ArrayList<>();
		try(SqlSession session = sqlSessionFactory.openSession()) {
			Consultas consultas = session.getMapper(Consultas.class);
			result = consultas.selectNativeQuery(query.queryGetArticulos());
		} catch (Exception e) {
			e.printStackTrace();		
		}
		return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO, result);
	}

	public Response<Object>  nuevoRegistroUsandoQuerysNativas( PersonaNombres persona, Authentication authentication) {

		SqlSessionFactory sqlSessionFactory = MyBatisConfig.buildqlSessionFactory();
		Gson gson = new Gson();
		UsuarioDto usuarioDto = gson.fromJson((String) authentication.getPrincipal(), UsuarioDto.class);
		
		try(SqlSession session = sqlSessionFactory.openSession()) {
			Consultas aa = session.getMapper(Consultas.class);
			
			try {
				aa.selectNativeQuery(query.queryInsert(persona.getNomPersona(), persona.getPrimerApellido(), persona.getSegundoApellido(), "" + usuarioDto.getIdUsuario()));
				session.commit();
			} catch (Exception e) {
				session.rollback();
			}
			
		}
		
		return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO, null);
	}
	
	/* 
	 * Este es un ejemplo de un servicio para realizar un insert usando varios parametros 
	 */
	public Response<Object> nuevoRegistroUsandoMappersParam(PersonaNombres persona, Authentication authentication) {
		SqlSessionFactory sqlSessionFactory = MyBatisConfig.buildqlSessionFactory();
		Gson gson = new Gson();
		UsuarioDto usuarioDto = gson.fromJson((String) authentication.getPrincipal(), UsuarioDto.class);
		/* 
		 * Creamos una instancia del objeto/representación del nuevo registro 
		 */
		PersonaEntityMyBatis nuevoRegistro = new PersonaEntityMyBatis();
		
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
			PersonaMapper aa = session.getMapper(PersonaMapper.class);

			try {
				/* 
				 * Para sentencias que actualizan datos o crean nuevos usaremos un try-catch
				 * 1._ accedemos al metodo de nuestro objeto mapper 
				 * 2._ Ejecutamos un commit para ver los cambios reflejados en BD
				 * 3._ Seteamos la data que vamos a devolver como respuesta
				 *  */
				
				aa.nuevoRegistroParam(persona.getNomPersona(), persona.getPrimerApellido(),
						persona.getSegundoApellido(), usuarioDto.getIdUsuario() , nuevoRegistro);

				
				resp.setDatos(nuevoRegistro.getIdPersona());
				session.commit();
			} catch (Exception e) {
				/*
				 * Para el escenario en que fallen las querys
				 * 
				 * 1._ Realizamos un roll back (regresamos los cambios)
				 * 2._ Cerramos la conexión.
				 * */
				session.rollback();
			}

		}

		return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO, resp);
	}
	
	/* 
	 * Este es un ejemplo de un servicio para realizar un insert usando un objeto como parametro 
	 */
	public Response<Object> nuevoRegistroUsandoMappersObj(PersonaNombres persona, Authentication authentication) {
		SqlSessionFactory sqlSessionFactory = MyBatisConfig.buildqlSessionFactory();
		Gson gson = new Gson();
		UsuarioDto usuarioDto = gson.fromJson((String) authentication.getPrincipal(), UsuarioDto.class);
		/* 
		 * Creamos una instancia del objeto/representación del nuevo registro 
		 */
		PersonaEntityMyBatis per = new PersonaEntityMyBatis();
		
		/* Realizamos el mapeo de datos desde nuestro request  */
		per.setNomPersona(persona.getNomPersona());
		per.setPrimerApellido(persona.getPrimerApellido());
		per.setSegundoApellido(persona.getSegundoApellido());
		per.setIdUsuarioAlta(usuarioDto.getIdUsuario());
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
				session.commit();
			} catch (Exception e) {
				/*
				 * Para el escenario en que fallen las querys
				 * 
				 * 1._ Realizamos un roll back (regresamos los cambios)
				 * 2._ Cerramos la conexión.
				 * */
				
				session.rollback();
			}

		}

		return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO, resp);
	}

	@Override
	public Response<Object> actualizarRegistroUsandoMappersObj(PersonaNombres persona, Authentication authentication ) {
		SqlSessionFactory sqlSessionFactory = MyBatisConfig.buildqlSessionFactory();
		Gson gson = new Gson();
		UsuarioDto usuarioDto = gson.fromJson((String) authentication.getPrincipal(), UsuarioDto.class);
		PersonaEntityMyBatis per = new PersonaEntityMyBatis();
		
		per.setNomPersona(persona.getNomPersona());
		per.setPrimerApellido(persona.getPrimerApellido());
		per.setSegundoApellido(persona.getSegundoApellido());
		per.setIdPersona(persona.getIdPersona());
		per.setIdUsuarioModifica(usuarioDto.getIdUsuario());
		Response<Object> resp = new Response<>();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			
			PersonaMapper personaMapper = session.getMapper(PersonaMapper.class);

			try {
				personaMapper.actualizarRegistroObj(per);
				resp.setDatos(per);
				session.commit();
			} catch (Exception e) {	
				session.rollback();
			}
		}

		return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO, resp);
	}

	@Override
	public Response<Object> crearMigracion(PersonaNombres persona, Authentication authentication ) {
		List <PersonaEntity>usuarios = obtenerUsuarios();
		return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO, AppConstantes.EXITO);
	}

	private List <PersonaEntity> obtenerUsuarios() {
		SqlSessionFactory sqlSessionFactory = MyBatisConfig.buildqlSessionFactory();
		List <PersonaEntity>usuarios = null;
		try (SqlSession session = sqlSessionFactory.openSession()) {
			
			PersonaMigracionMapper personaMapper = session.getMapper(PersonaMigracionMapper.class);
			int id = 0;
			try {
				usuarios = personaMapper.consultaUsuarios();
				for(PersonaEntity pe : usuarios) {
					if(pe.getIdPersona() != null ) {
						personaMapper.inserIdPersona(pe);
						personaMapper.inserIdEdoNacInPersona(pe);
					}else {
						personaMapper.insertarPersona(pe);
						personaMapper.inserIdPersona(pe);
					}
				}
				session.commit();
			} catch (Exception e) {	
				session.rollback();
				e.getStackTrace();
			}
		}
		return usuarios;
	}
	
}
