package com.imss.sivimss.arquetipo.model.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class Persona {
	@NotBlank(message = "Nombre no puede ser vacío")
    private String nomPersona;
	
	@Min(value=18,message = "Edad Minima 18")
	private int edad;
	
	@Min(value=1,message = "Los velatorios deben estar dentro del rango")
	@Max(value=18,message = "Los velatorios deben estar dentro del rango")
	private int idVelatorio;
	
	@NotBlank
	@Pattern(regexp = "^([A-ZÑ\\x26]{3,4}([0-9]{2})(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1]))((-)?([A-Z\\d]{3}))?$",
			message = "Debe ser un RFC válido")
	private String cveRFC;
	
	@Email(message = "Debe ser un correo válido")
	private String correo;
	
	private int idPersona;
	
	private String cveCURP;
	private String cveNSS;
	@NotBlank(message = "Primer Apellido no puede ser vacío")
	private String primerApellido;
	@NotBlank(message = "Segundo Apellido no puede ser vacío")
	private String segundoApellido;
	private Integer numSexo;
	private String otroSexo;
	private String fecNac;
	private Integer idPais;
	private Integer idEstado;
	private String telefono;
	private String telefonoFijo;
	private String tipoPersona;
	private String numINE;
	
}
