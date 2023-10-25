package com.imss.sivimss.arquetipo.service;

import java.io.IOException;
import java.util.Map;

import org.springframework.security.core.Authentication;

import com.imss.sivimss.arquetipo.utils.Response;


public interface PeticionesService {
	Response<Object>  consultarById(Integer id, Authentication authentication)throws IOException;
	Response<Object> consultar(Authentication authentication)throws IOException;
	Response<Object> consultarPaginado(Map<String, Object> params, Authentication authentication)throws Throwable;
	Response<Object> consultarByIdPaginado(Map<String, Object> params, Integer id, Authentication authentication)throws Throwable;
	Response<Object> guardarDatos(String request, Authentication authentication) throws Throwable;
	Response<Object> actualizaDatos(String request, Authentication authentication) throws Throwable;
	Response<Object> borrarDatos(String request, Authentication authentication) throws Throwable;
	
}
