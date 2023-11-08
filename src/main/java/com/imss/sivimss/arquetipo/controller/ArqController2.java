package com.imss.sivimss.arquetipo.controller;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import java.util.Map;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.imss.sivimss.arquetipo.model.request.Persona;
import com.imss.sivimss.arquetipo.model.request.PersonaNombres;
import com.imss.sivimss.arquetipo.model.request.RequestValidationForm;
import com.imss.sivimss.arquetipo.service.PeticionesArquetipo;
import com.imss.sivimss.arquetipo.service.PeticionesService;
import com.imss.sivimss.arquetipo.utils.AppConstantes;
import com.imss.sivimss.arquetipo.utils.DatosRequest;

import com.imss.sivimss.arquetipo.utils.LogUtil;
import com.imss.sivimss.arquetipo.utils.ProviderServiceRestTemplate;
import com.imss.sivimss.arquetipo.utils.Response;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.extern.java.Log;

@Log
@RestController
@RequestMapping("/arquetipo")
public class ArqController2 {
	@Autowired
	private PeticionesArquetipo arq;

	
	@GetMapping("/consulta/mappers")
	public CompletableFuture<Object> consultaUsandoMappers(Authentication authentication)	throws Throwable {
		Response<Object> response = arq.consultaUsandoMappers();
		return CompletableFuture.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));
	}
	
	@GetMapping("/consulta/querynativa")
	public CompletableFuture<Object> consultaUsandoQuerysNativas(Authentication authentication)	throws Throwable {
		Response<Object> response = arq.consultaUsandoQuerysNativas();
		return CompletableFuture.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));
	}
	
	@PostMapping("/insert/mappers")
	public CompletableFuture<Object> nuevoRegistroUsandoMappersParam(@RequestBody PersonaNombres persona, Authentication authentication)	throws Throwable {
		Response<Object> response = arq.nuevoRegistroUsandoMappersParam(persona);
		return CompletableFuture.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));
	}
	
	@PostMapping("/insert/mappers/obj")
	public CompletableFuture<Object> nuevoRegistroUsandoMappersObj(@RequestBody PersonaNombres persona, Authentication authentication)	throws Throwable {
		Response<Object> response = arq.nuevoRegistroUsandoMappersObj(persona);
		return CompletableFuture.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));
	}
	
	@PostMapping("/update/mappers/obj/{id}")
	public CompletableFuture<Object> actualizarRegistroUsandoMappersObj(@RequestBody PersonaNombres persona,
			@PathVariable int id, Authentication authentication)	throws Throwable {
		Response<Object> response = arq.actualizarRegistroUsandoMappersObj(persona,id);
		return CompletableFuture.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));
	}
	
	
	/*
	 * 
	 * FallBack
	 * 
	 */
	
	

}
