package com.imss.sivimss.arquetipo.configuration.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.imss.sivimss.arquetipo.model.entity.PersonaEntityMyBatis;
import com.imss.sivimss.arquetipo.model.request.Persona;

public interface PersonaInterface {


	@Select(value = "SELECT CVE_CURP AS cveCURP FROM SVC_PERSONA")
	public List<PersonaEntityMyBatis> selectPersona();
	//@Insert("insert into table3 (id, name) values(#{nameId}, #{name})")
	//@SelectKey(statement="call next value for TestSequence", keyProperty="nameId", before=true, resultType=int.class)int insertTable3(Name name);
	
	
	@Insert(value = " INSERT INTO SVC_PERSONA "
	  		+ " (CVE_RFC, CVE_CURP, CVE_NSS, NOM_PERSONA, NOM_PRIMER_APELLIDO, NOM_SEGUNDO_APELLIDO, NUM_SEXO, REF_OTRO_SEXO, FEC_NAC, ID_PAIS, ID_ESTADO, REF_TELEFONO, REF_TELEFONO_FIJO, REF_CORREO, TIP_PERSONA, ID_USUARIO_ALTA, FEC_ALTA, NUM_INE) "
	  		+ " VALUES(:#{#cveRFC},:#{#cveCURP},:#{#cveNSS},:#{#nomPersona},:#{#primerApellido},:#{#segundoApellido},:#{#numSexo},:#{#otroSexo},:#{#fecNac},:#{#idPais},:#{#idEstado},:#{#telefono},:#{#telefonoFijo},:#{#correo},:#{#tipoPersona}"
	  		+ ", :idUsuarioAlta, CURDATE(),:#{#numINE})")
	public void insertPersona(Persona persona,
			@Param("idUsuarioAlta") Integer idUsuarioAlta);
	
	@Select(value = "SELECT CVE_CURP AS cveCURP FROM SVC_PERSONA")
	public List<PersonaEntityMyBatis> updatePersona();
}
