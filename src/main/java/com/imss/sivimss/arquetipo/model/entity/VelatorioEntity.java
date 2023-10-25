package com.imss.sivimss.arquetipo.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "SVC_VELATORIO")
public class VelatorioEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_VELATORIO")
	private Integer idVelatorio;
	@Column(name = "DES_VELATORIO")
	private String desVelatorio;
	
	@Column(name = "NOM_VELATORIO")
	private String nomVelatorio;
	@Column(name = "ID_USUARIO_ADMIN")
	private Integer idUsuarioAdmin;
	@Column(name = "NOM_RESPO_SANITARIO")
	private String nomRepSanitario;
	@Column(name = "CVE_ASIGNACION")
	private String cveAsignacion;
	@Column(name = "ID_DOMICILIO")
	private Integer idDomicilio;
	@Column(name = "NUM_TELEFONO")
	private String numTelefono;
	@Column(name = "IND_ACTIVO")
	private Integer indActivo;
	@Column(name = "ID_USUARIO_ALTA")
	private Integer idUsuarioAlta;
	@Column(name = "FEC_ALTA")
	private String fecAlta;
	@Column(name = "FEC_ACTUALIZACION")
	private String fecActualizacion;
	@Column(name = "FEC_BAJA")
	private String fecBaja;
	@Column(name = "ID_USUARIO_MODIFICA")
	private Integer idUsuarioModifica;
	@Column(name = "ID_USUARIO_BAJA")
	private Integer idUsuarioBaja;
	@Column(name = "ID_DELEGACION")
	private Integer idDelegacion;
}
