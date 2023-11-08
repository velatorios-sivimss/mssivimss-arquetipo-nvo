package com.imss.sivimss.arquetipo.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.imss.sivimss.arquetipo.configuration.MyBatisConfig;
import com.imss.sivimss.arquetipo.configuration.mapper.Consultas;
import com.imss.sivimss.arquetipo.configuration.mapper.OrdenesServicioMapper;
import com.imss.sivimss.arquetipo.configuration.mapper.PersonaMapper;
import com.imss.sivimss.arquetipo.model.entity.PersonaEntityMyBatis;
import com.imss.sivimss.arquetipo.model.request.PersonaNombres;
import com.imss.sivimss.arquetipo.repository.PersonaRepository;
import com.imss.sivimss.arquetipo.service.PeticionesArquetipo;
import com.imss.sivimss.arquetipo.utils.AppConstantes;
import com.imss.sivimss.arquetipo.utils.Response;

import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;

@Log
@Service
public class ServiciosArquetipo implements PeticionesArquetipo {
	
	private SqlSessionFactory sqlSessionFactory = MyBatisConfig.buildqlSessionFactory();

	public Response<Object>  consultaUsandoMappers( ) {
		
		List<Map<String, Object>> result = new ArrayList<>();
		try(SqlSession session = sqlSessionFactory.openSession()) {
			OrdenesServicioMapper ods = session.getMapper(OrdenesServicioMapper.class);
			result = ods.consultaArticulos();	
			session.commit();
			session.close();
		}
		
		return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO, result);
	}

	
	public Response<Object>  consultaUsandoQuerysNativas() {
		
		String ordenesServ = "SELECT DISTINCT "
				+ "    SA.ID_ARTICULO AS idArticulo, "
				+ "    SA.REF_ARTICULO AS nombreArticulo "
				+ "FROM "
				+ "    SVT_ARTICULO SA "
				+ "INNER JOIN SVT_INVENTARIO_ARTICULO STI ON "
				+ "    SA.ID_ARTICULO = STI.ID_ARTICULO AND STI.IND_ESTATUS NOT IN(2, 3) AND STI.ID_INVE_ARTICULO NOT IN( "
				+ "    SELECT "
				+ "        IFNULL(STP.ID_INVE_ARTICULO, 0) "
				+ "    FROM "
				+ "        SVC_DETALLE_CARAC_PRESUP_TEMP STP "
				+ "    WHERE "
				+ "        STP.IND_ACTIVO = 1 AND DATE_FORMAT(STP.TIM_ALTA, 'YY-MM-DD') = DATE_FORMAT(CURRENT_DATE(), 'YY-MM-DD') AND TIMESTAMPDIFF( "
				+ "            MINUTE, "
				+ "            DATE_ADD(STP.TIM_ALTA, INTERVAL 4 HOUR), "
				+ "            CURRENT_TIMESTAMP()) <= 0 "
				+ "        ) AND STI.ID_INVE_ARTICULO NOT IN( "
				+ "        SELECT "
				+ "            IFNULL(SDCP.ID_INVE_ARTICULO, 0) "
				+ "        FROM "
				+ "            SVC_DETALLE_CARAC_PRESUP SDCP "
				+ "        WHERE "
				+ "            SDCP.IND_ACTIVO = 1 "
				+ "    ) "
				+ "INNER JOIN SVT_ORDEN_ENTRADA SOE2 ON "
				+ "    SOE2.ID_ODE = STI.ID_ODE "
				+ "INNER JOIN SVT_CONTRATO SC ON "
				+ "    SC.ID_CONTRATO = SOE2.ID_CONTRATO "
				+ "INNER JOIN SVT_CONTRATO_ARTICULOS SCA ON "
				+ "    SCA.ID_CONTRATO = SC.ID_CONTRATO AND STI.ID_ARTICULO = SCA.ID_ARTICULO "
				+ "WHERE "
				+ "    STI.ID_TIPO_ASIGNACION_ART = 1 AND SA.ID_CATEGORIA_ARTICULO = 1 AND STI.ID_VELATORIO = 1 "
				+ "UNION "
				+ "SELECT DISTINCT "
				+ "    SA.ID_ARTICULO AS idArticulo, "
				+ "    SA.REF_ARTICULO AS nombreArticulo "
				+ "FROM "
				+ "    SVT_ARTICULO SA "
				+ "INNER JOIN SVT_INVENTARIO_ARTICULO STI ON "
				+ "    SA.ID_ARTICULO = STI.ID_ARTICULO AND STI.IND_ESTATUS NOT IN(2, 3) AND STI.ID_INVE_ARTICULO NOT IN( "
				+ "    SELECT "
				+ "        IFNULL(STP.ID_INVE_ARTICULO, 0) "
				+ "    FROM "
				+ "        SVC_DETALLE_CARAC_PRESUP_TEMP STP "
				+ "    WHERE "
				+ "        STP.IND_ACTIVO = 1 AND DATE_FORMAT(STP.TIM_ALTA, 'YY-MM-DD') = DATE_FORMAT(CURRENT_DATE(), 'YY-MM-DD') AND TIMESTAMPDIFF( "
				+ "            MINUTE, "
				+ "            DATE_ADD(STP.TIM_ALTA, INTERVAL 4 HOUR), "
				+ "            CURRENT_TIMESTAMP()) <= 0 "
				+ "        ) AND STI.ID_INVE_ARTICULO IN( "
				+ "        SELECT "
				+ "            IFNULL(SDCP.ID_INVE_ARTICULO, 0) "
				+ "        FROM "
				+ "            SVC_DETALLE_CARAC_PRESUP SDCP "
				+ "        INNER JOIN SVC_ATAUDES_DONADOS STA ON "
				+ "            STA.ID_INVE_ARTICULO = SDCP.ID_INVE_ARTICULO "
				+ "        INNER JOIN SVT_INVENTARIO_ARTICULO STAI ON "
				+ "            STAI.ID_INVE_ARTICULO = SDCP.ID_INVE_ARTICULO "
				+ "        WHERE "
				+ "            SDCP.IND_ACTIVO = 1 AND STAI.ID_TIPO_ASIGNACION_ART = 1 "
				+ "    ) "
				+ "INNER JOIN SVT_ORDEN_ENTRADA SOE2 ON "
				+ "    SOE2.ID_ODE = STI.ID_ODE "
				+ "INNER JOIN SVT_CONTRATO SC ON "
				+ "    SC.ID_CONTRATO = SOE2.ID_CONTRATO "
				+ "INNER JOIN SVT_CONTRATO_ARTICULOS SCA ON "
				+ "    SCA.ID_CONTRATO = SC.ID_CONTRATO AND STI.ID_ARTICULO = SCA.ID_ARTICULO "
				+ "WHERE "
				+ "    SA.ID_CATEGORIA_ARTICULO = 1 AND STI.ID_VELATORIO = 1";
		
		List<Map<String, Object>> result = new ArrayList<>();
		try(SqlSession session = sqlSessionFactory.openSession()) {
			Consultas consultas = session.getMapper(Consultas.class);
			result = consultas.selectNativeQuery(ordenesServ);		
			session.commit();
			session.close();
		}
		
		return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO, result);
	}

	public Response<Object>  nuevoRegistroUsandoQuerysNativas( String nombre, String apePaterno, String apeMaterno) {
		
		String insert = ""
				+ "INSERT INTO `SVC_PERSONA`(`NOM_PERSONA`, `NOM_PRIMER_APELLIDO`, `NOM_SEGUNDO_APELLIDO`) "
				+ "VALUES ( #{nombre},#{apePaterno},#{apeMaterno} )".replace("#{nombre}", nombre)
				.replace("#{apePaterno}", apePaterno).replace("#{apeMaterno}", apeMaterno);;
		
		try(SqlSession session = sqlSessionFactory.openSession()) {
			Consultas aa = session.getMapper(Consultas.class);
			
			try {
				aa.selectNativeQuery(insert);
			} catch (Exception e) {
				session.rollback();
			}
			
			session.commit();
			session.close();
		}
		
		return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO, null);
	}
	
	/* 
	 * Este es un ejemplo de un servicio para realizar un insert usando varios parametros 
	 */
	public Response<Object> nuevoRegistroUsandoMappersParam(PersonaNombres persona) {
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
						persona.getSegundoApellido(), nuevoRegistro);

				session.commit();
				resp.setDatos(nuevoRegistro.getIdPersona());
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
			session.close();
		}

		return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO, resp);
	}
	
	/* 
	 * Este es un ejemplo de un servicio para realizar un insert usando un objeto como parametro 
	 */
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
				session.commit();
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
			session.close();
		}

		return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO, resp);
	}
	
}
