package com.imss.sivimss.arquetipo.utils;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.imss.sivimss.arquetipo.model.entity.VelatorioEntity;
import com.imss.sivimss.arquetipo.repository.VelatorioRepository;


@Component
public class ConsultasUtil {

	@Autowired
	private VelatorioRepository velatorioRepository;
	
	@Autowired
	private DatosRequestUtil datosRequestUtil;

	public Response<Object> consultar() {
		List<Map<String, Object>> velatorios = velatorioRepository.buscarVelatorio();
		return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO,velatorios);
	}
	public Response<Object>  consultaPaginado( Integer pagina, Integer tamanio) {

		Pageable paginado = PageRequest.of(pagina, tamanio);
		List<Map<String, Object>> resp = velatorioRepository.buscarVelatorioPaginado(paginado);
		List<Map<String, Object>> respTotal = velatorioRepository.buscarVelatorioPaginadoTotal();
		Page<Map<String, Object>> objetoMapeado;
		Integer conteo =  Integer.parseInt( respTotal.get(0).get("conteo").toString() );
		objetoMapeado = new PageImpl<>(resp, paginado, conteo);


		return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO, objetoMapeado);
	}
	public Response<Object> consultarById (Integer id) {
		List<Map<String, Object>> velatorios = velatorioRepository.buscarVelatorioFiltro(id);
		return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO,velatorios);
	}

	public Response<Object> consultarByIdPaginado (Integer id, Integer pagina, Integer tamanio) {
		Pageable paginado = PageRequest.of(pagina, tamanio);
		List<Map<String, Object>> resp = velatorioRepository.buscarVelatorioFiltroPag(paginado, id);
		List<Map<String, Object>> respTotal = velatorioRepository.buscarVelatorioFiltroPagTotal(id);
		Page<Map<String, Object>> objetoMapeado;
		Integer conteo =  Integer.parseInt( respTotal.get(0).get("conteo").toString() );
		objetoMapeado = new PageImpl<>(resp, paginado, conteo);
		
		return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO,objetoMapeado);
	}

	public Response<Object> guardarDatos (VelatorioEntity velatorio, Integer idUsuarioAlta){
		velatorioRepository.guardarVelatorio(velatorio, idUsuarioAlta);		
		return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO,null);
	}

	public Response<Object> actualizarDatos (VelatorioEntity velatorio, Integer idUsuarioModifica){
		velatorioRepository.actualizarVelatorio(velatorio, idUsuarioModifica);	
		return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO,null);
	}

	public Response<Object> borrarDatos (Integer idVelatorio, Integer idUsuarioBaja){
		
		velatorioRepository.borrarVelatorio(idVelatorio,idUsuarioBaja);
		
		return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO,null);
	}
}
