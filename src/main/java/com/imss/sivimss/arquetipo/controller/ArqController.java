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
import com.imss.sivimss.arquetipo.model.request.RequestValidationForm;
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

@RestController
@RequestMapping("/sivimss")
public class ArqController {
	@Autowired
	private PeticionesService peticionesService;

	@Autowired
	private ProviderServiceRestTemplate providerRestTemplate;

	private static final String PAGINA = "pagina";

	private static final String TAMANIO = "tamanio";

	@Autowired
	private LogUtil logUtil;

	private static final String CONSULTA = "consulta";

	private static final Object Persona = null;

	@PostMapping("/ejem")
	@CircuitBreaker(name = "msflujo", fallbackMethod = "fallbackPersona")
	@Retry(name = "msflujo", fallbackMethod = "fallbackPersona")
	@TimeLimiter(name = "msflujo")
	public ResponseEntity<Persona> addUser(@Valid @RequestBody Persona request) {
		Response<Object> response = new Response();
		
        return ResponseEntity.ok(request);
    }
	
	
	
	
	@GetMapping("/velatorios/{id}")
	@CircuitBreaker(name = "msflujo", fallbackMethod = "fallbackconsultaDetalle")
	@Retry(name = "msflujo", fallbackMethod = "fallbackconsultaDetalle")
	@TimeLimiter(name = "msflujo")
	public CompletableFuture<Object> consultarById(@PathVariable @Min(1) @Max(18) Integer id, Authentication authentication)	throws IOException {
		Response<Object> response = peticionesService.consultarById(id, authentication);
		return CompletableFuture.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));
	}

	@GetMapping("/velatorios/paginado/{id}")
	@CircuitBreaker(name = "msflujo", fallbackMethod = "fallbackconsultaPaginadoById")
	@Retry(name = "msflujo", fallbackMethod = "fallbackconsultaPaginadoById")
	@TimeLimiter(name = "msflujo")
	public CompletableFuture<Object> consultarByIdPaginado(
			@RequestParam(defaultValue = AppConstantes.NUMERO_DE_PAGINA) Integer pagina,
			@RequestParam(defaultValue = AppConstantes.TAMANIO_PAGINA) Integer tamanio,
			@PathVariable Integer id, Authentication authentication)	throws Throwable {
		Map<String, Object> params = new HashMap<>();
		params.put(PAGINA, pagina);
		params.put(TAMANIO, tamanio);
		Response<Object> response = peticionesService.consultarByIdPaginado(params, id, authentication);
		return CompletableFuture.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));
	}
	@GetMapping("/velatorios")
	@CircuitBreaker(name = "msflujo", fallbackMethod = "fallbackconsulta")
	@Retry(name = "msflujo", fallbackMethod = "fallbackconsulta")
	@TimeLimiter(name = "msflujo")
	public CompletableFuture<Object> consultarAll( Authentication authentication)	throws IOException {
		Response<Object> response = peticionesService.consultar(authentication);
		return CompletableFuture.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));
	}

	@GetMapping("/velatorios/paginado")
	@CircuitBreaker(name = "msflujo", fallbackMethod = "fallbackconsultaPaginado")
	@Retry(name = "msflujo", fallbackMethod = "fallbackconsultaPaginado")
	@TimeLimiter(name = "msflujo")
	public CompletableFuture<Object> consultarPaginado(
			@RequestParam(defaultValue = AppConstantes.NUMERO_DE_PAGINA) Integer pagina,
			@RequestParam(defaultValue = AppConstantes.TAMANIO_PAGINA) Integer tamanio,
			Authentication authentication) throws Throwable {
		Map<String, Object> params = new HashMap<>();
		params.put(PAGINA, pagina);
		params.put(TAMANIO, tamanio);
		Response<Object> response = peticionesService.consultarPaginado(params,authentication);
		return CompletableFuture.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));
	}

	/*
	 * Guardar
	 * 
	 */
	@PostMapping("/guardar/velatorio")
	@CircuitBreaker(name = "msflujo", fallbackMethod = "fallbackGuardar")
	@Retry(name = "msflujo", fallbackMethod = "fallbackGuardar")
	@TimeLimiter(name = "msflujo")
	public CompletableFuture<Object> guardarDatos(@RequestBody String request,
			Authentication authentication) throws Throwable {
		Response<Object> response = peticionesService.guardarDatos(request,authentication);
		return CompletableFuture.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));
	}
	

	/*
	 * Actualizar
	 * 
	 */
	@PostMapping("/actualizar/velatorio")
	@CircuitBreaker(name = "msflujo", fallbackMethod = "fallbackGuardar")
	@Retry(name = "msflujo", fallbackMethod = "fallbackGuardar")
	@TimeLimiter(name = "msflujo")
	public CompletableFuture<Object> actualizarDatos(@RequestBody String request,
			Authentication authentication) throws Throwable {
		Response<Object> response = peticionesService.actualizaDatos(request,authentication);
		return CompletableFuture.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));
	}


	/*
	 * Actualizar
	 * 
	 */
	@PostMapping("/eliminar/velatorio")
	@CircuitBreaker(name = "msflujo", fallbackMethod = "fallbackGuardar")
	@Retry(name = "msflujo", fallbackMethod = "fallbackGuardar")
	@TimeLimiter(name = "msflujo")
	public CompletableFuture<Object> bajaDatos(@RequestBody String request,
			Authentication authentication) throws Throwable {
		Response<Object> response = peticionesService.borrarDatos(request,authentication);
		return CompletableFuture.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));
	}
	
	
	/**
	 * fallbacks detalle
	 * 
	 * @return respuestas
	 * @throws IOException 
	 */

	@SuppressWarnings("unused")
	private CompletableFuture<Object> fallbackconsultaPaginadoById(
			@RequestParam(defaultValue = AppConstantes.NUMERO_DE_PAGINA) Integer pagina,
			@RequestParam(defaultValue = AppConstantes.TAMANIO_PAGINA) Integer tamanio,
			@PathVariable Integer id, Authentication authentication,
			CallNotPermittedException e) throws Exception  {
		Response<?> response = providerRestTemplate.respuestaProvider(e.getMessage());
		 logUtil.crearArchivoLog(Level.INFO.toString(),this.getClass().getSimpleName(),this.getClass().getPackage().toString(),e.getMessage(),CONSULTA,authentication);

		return CompletableFuture
				.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));
	}

	@SuppressWarnings("unused")
	private CompletableFuture<Object> fallbackconsultaPaginado(
			@RequestParam(defaultValue = AppConstantes.NUMERO_DE_PAGINA) Integer pagina,
			@RequestParam(defaultValue = AppConstantes.TAMANIO_PAGINA) Integer tamanio, Authentication authentication,
			CallNotPermittedException e) throws Throwable {
		Response<?> response = providerRestTemplate.respuestaProvider(e.getMessage());
		 logUtil.crearArchivoLog(Level.INFO.toString(),this.getClass().getSimpleName(),this.getClass().getPackage().toString(),e.getMessage(),CONSULTA,authentication);

		return CompletableFuture
				.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));
	}
	
	@SuppressWarnings("unused")
	private CompletableFuture<Object> fallbackGuardar(@RequestBody String request, Authentication authentication,
			CallNotPermittedException e) throws Throwable {
		Response<?> response = providerRestTemplate.respuestaProvider(e.getMessage());
		 logUtil.crearArchivoLog(Level.INFO.toString(),this.getClass().getSimpleName(),this.getClass().getPackage().toString(),e.getMessage(),CONSULTA,authentication);

		return CompletableFuture
				.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));
	}

	/**
	 * fallbacks detalle
	 * 
	 * @return respuestas
	 * @throws IOException 
	 */

	@SuppressWarnings("unused")
	private CompletableFuture<Object> fallbackconsulta( Authentication authentication,
			CallNotPermittedException e) throws IOException {
		Response<?> response = providerRestTemplate.respuestaProvider(e.getMessage());
		 logUtil.crearArchivoLog(Level.INFO.toString(),this.getClass().getSimpleName(),this.getClass().getPackage().toString(),e.getMessage(),CONSULTA,authentication);

		return CompletableFuture
				.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));
	}
	
	/**
	 * fallbacks detalle
	 * 
	 * @return respuestas
	 * @throws IOException 
	 */
	@SuppressWarnings("unused")
	private CompletableFuture<Object> fallbackconsultaDetalle(@PathVariable Integer id, Authentication authentication,
			CallNotPermittedException e) throws IOException {
		Response<?> response = providerRestTemplate.respuestaProvider(e.getMessage());
		 logUtil.crearArchivoLog(Level.INFO.toString(),this.getClass().getSimpleName(),this.getClass().getPackage().toString(),e.getMessage(),CONSULTA,authentication);

		return CompletableFuture
				.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));
	}

	@SuppressWarnings("unused")
	private CompletableFuture<Object> fallbackconsultaDetalle(@PathVariable Integer id, Authentication authentication,
			RuntimeException e) throws IOException {
		Response<?> response = providerRestTemplate.respuestaProvider(e.getMessage());
		 logUtil.crearArchivoLog(Level.INFO.toString(),this.getClass().getSimpleName(),this.getClass().getPackage().toString(),e.getMessage(),CONSULTA,authentication);

		return CompletableFuture
				.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));
	}

	@SuppressWarnings("unused")
	private CompletableFuture<Object> fallbackconsultaDetalle(@PathVariable Integer id, Authentication authentication,
			NumberFormatException e) throws IOException {
		Response<?> response = providerRestTemplate.respuestaProvider(e.getMessage());
		 logUtil.crearArchivoLog(Level.INFO.toString(),this.getClass().getSimpleName(),this.getClass().getPackage().toString(),e.getMessage(),CONSULTA,authentication);

		return CompletableFuture
				.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));
	}


	
	
	
	
	
	private ResponseEntity<Persona> fallbackPersona(@RequestBody Persona request, Authentication authentication,
			CallNotPermittedException e) throws IOException {
		Response<?> response = providerRestTemplate.respuestaProvider(e.getMessage());
	    logUtil.crearArchivoLog(Level.INFO.toString(), this.getClass().getSimpleName(), this.getClass().getPackage().toString(), "Resiliencia", AppConstantes.CONSULTA, authentication);

	    return ResponseEntity.ok(request);
	}
 
	private ResponseEntity<Persona> fallbackPersona(@RequestBody Persona request, Authentication authentication,
			RuntimeException e) throws IOException {
		Response<?> response = providerRestTemplate.respuestaProvider(e.getMessage());
	    logUtil.crearArchivoLog(Level.INFO.toString(), this.getClass().getSimpleName(), this.getClass().getPackage().toString(), "Resiliencia", AppConstantes.CONSULTA, authentication);

	    return ResponseEntity.ok(request);
	}
 
	private ResponseEntity<Persona> fallbackPersona(@RequestBody Persona request, Authentication authentication,
			NumberFormatException e) throws IOException {
		Response<?> response = providerRestTemplate.respuestaProvider(e.getMessage());
 
	    logUtil.crearArchivoLog(Level.INFO.toString(), this.getClass().getSimpleName(), this.getClass().getPackage().toString(), "Resiliencia", AppConstantes.CONSULTA, authentication);
	    
	    return ResponseEntity.ok(request);
}
}