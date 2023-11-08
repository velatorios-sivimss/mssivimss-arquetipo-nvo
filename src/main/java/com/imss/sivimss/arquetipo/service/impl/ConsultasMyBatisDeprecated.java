package com.imss.sivimss.arquetipo.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.imss.sivimss.arquetipo.configuration.MyBatisConfig;
import com.imss.sivimss.arquetipo.configuration.mapper.Consultas;
import com.imss.sivimss.arquetipo.model.entity.PersonaEntityMyBatis;
import com.imss.sivimss.arquetipo.model.request.Persona;
import com.imss.sivimss.arquetipo.repository.PersonaRepository;
import com.imss.sivimss.arquetipo.utils.AppConstantes;
import com.imss.sivimss.arquetipo.utils.Response;

//import com.imss.sivimss.arquetipo.configuration.MyBatisConnect;

@Service
public class ConsultasMyBatisDeprecated {

	@Autowired
	private PersonaRepository personaRepository;
	
	private SqlSessionFactory sqlSessionFactory = new MyBatisConfig().buildqlSessionFactory();

	public Response<Object>  consultaPaginado( Integer pagina, Integer tamanio) {
		
		try(SqlSession session = sqlSessionFactory.openSession()) {
		    // do work
		}
		
		return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO, null);
	}

	public Response<Object>  insertaPersona( Persona persona, Integer idUsuarioAlta) {

//		MyBatisConnect con = new MyBatisConnect();
//		con.conectar();
//		consultas = con.crearBeanDeConsultas();
		//List<Map<String, Object>> resp = consultas.selectHashMap("SELECT * FROM SVC_PERSONA");
		
//		consultas.insertPersona( persona, idUsuarioAlta);
		
		return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO, null);
	}
	
}
