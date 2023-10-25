package com.imss.sivimss.arquetipo.service.impl;

import java.io.IOException;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.imss.sivimss.arquetipo.model.dto.VelatorioDTO;
import com.imss.sivimss.arquetipo.model.entity.VelatorioEntity;
import com.imss.sivimss.arquetipo.model.request.UsuarioDto;
import com.imss.sivimss.arquetipo.service.PeticionesService;
import com.imss.sivimss.arquetipo.utils.ConsultasUtil;
import com.imss.sivimss.arquetipo.utils.DatosRequestUtil;
import com.imss.sivimss.arquetipo.utils.Response;


@Service
public class PeticionesServiceImpl implements PeticionesService {

	@Value("${endpoints.ms-principal-endpoint}")
	private String urlDominioPrincipal;

	@Autowired
	private DatosRequestUtil datosUtil;
	
	@Autowired
	private ConsultasUtil consultasUtil;
	

	@Override
	public Response<Object>  consultarById(Integer id,Authentication authentication) throws IOException {
		return consultasUtil.consultarById(id);
	}
	
	@Override
	public Response<Object>  consultarByIdPaginado(Map<String, Object> params, Integer id, Authentication authentication) throws IOException {
		Integer pagina =  Integer.parseInt( params.get("pagina").toString() );
		Integer tamanio =  Integer.parseInt( params.get("tamanio").toString() );
		return consultasUtil.consultarByIdPaginado(id, pagina, tamanio);
	}

	@Override
	public Response<Object> consultar(Authentication authentication) throws IOException {
		return consultasUtil.consultar();
	}
	
	@Override
	public Response<Object> consultarPaginado(Map<String, Object> params, Authentication authentication) throws Throwable {
		Integer pagina =  Integer.parseInt( params.get("pagina").toString() );
		Integer tamanio =  Integer.parseInt( params.get("tamanio").toString() );
		return consultasUtil.consultaPaginado( pagina, tamanio);
		
	}

	@Override
	public Response<Object> guardarDatos(String request, Authentication authentication) throws Throwable {
		UsuarioDto user = datosUtil.getUserData(authentication);
		Gson gson = new Gson();
		VelatorioEntity vel = gson.fromJson(request, VelatorioEntity.class);

		return consultasUtil.guardarDatos(vel, user.getIdUsuario());
		
	}	

	@Override
	public Response<Object> actualizaDatos(String request, Authentication authentication) throws Throwable {
		UsuarioDto user = datosUtil.getUserData(authentication);
		Gson gson = new Gson();
		VelatorioEntity vel = gson.fromJson(request, VelatorioEntity.class);
		return consultasUtil.actualizarDatos(vel, user.getIdUsuario());
		
	}

	@Override
	public Response<Object> borrarDatos(String request, Authentication authentication) throws Throwable {
		UsuarioDto user = datosUtil.getUserData(authentication);
		Gson gson = new Gson();
		VelatorioDTO vel = gson.fromJson(request, VelatorioDTO.class);

		return consultasUtil.borrarDatos(vel.getIdVelatorio(), user.getIdUsuario());
		
	}

}
