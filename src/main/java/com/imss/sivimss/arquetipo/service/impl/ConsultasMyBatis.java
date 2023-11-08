package com.imss.sivimss.arquetipo.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.imss.sivimss.arquetipo.configuration.mapper.Consultas;
import com.imss.sivimss.arquetipo.model.entity.PersonaEntityMyBatis;
import com.imss.sivimss.arquetipo.model.request.Persona;
import com.imss.sivimss.arquetipo.repository.PersonaRepository;
import com.imss.sivimss.arquetipo.utils.AppConstantes;
import com.imss.sivimss.arquetipo.utils.Response;

import com.imss.sivimss.arquetipo.configuration.MyBatisConnect;

@Service
public class ConsultasMyBatis {

	@Autowired
	private PersonaRepository personaRepository;
	
	protected static Consultas consultas;

	public Response<Object>  consultaPaginado( Integer pagina, Integer tamanio) {

		MyBatisConnect con = new MyBatisConnect();
		con.conectar();
		consultas = con.crearBeanDeConsultas();
		List<Map<String, Object>> resp = consultas.selectHashMap("SELECT * FROM SVC_PERSONA");
		
		List<PersonaEntityMyBatis> per =consultas.selectPersona();
		
		return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO, per);
	}

	public Response<Object>  insertaPersona( Persona persona, Integer idUsuarioAlta) {

		MyBatisConnect con = new MyBatisConnect();
		con.conectar();
		consultas = con.crearBeanDeConsultas();
		//List<Map<String, Object>> resp = consultas.selectHashMap("SELECT * FROM SVC_PERSONA");
		
		consultas.insertPersona( persona, idUsuarioAlta);
		
		return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO, null);
	}
	
}
