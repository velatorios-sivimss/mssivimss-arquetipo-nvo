package com.imss.sivimss.arquetipo.service;

import org.springframework.security.core.Authentication;

import com.imss.sivimss.arquetipo.model.request.PersonaNombres;
import com.imss.sivimss.arquetipo.utils.Response;


public interface PeticionesArquetipo {
	public Response<Object> consultaUsandoMappers() ;
	public Response<Object>  consultaUsandoQuerysNativas() ;
	public Response<Object> nuevoRegistroUsandoMappersParam( PersonaNombres persona, Authentication authentication) ;
	public Response<Object>  nuevoRegistroUsandoMappersObj( PersonaNombres persona, Authentication authentication);
	public Response<Object> actualizarRegistroUsandoMappersObj(PersonaNombres persona, Authentication authentication);
	public Response<Object> nuevoRegistroUsandoQuerysNativas(PersonaNombres persona, Authentication authentication);
	public Response<Object> crearMigracion(PersonaNombres persona, Authentication authentication);
}
