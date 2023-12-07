package com.imss.sivimss.arquetipo.configuration.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.imss.sivimss.arquetipo.model.entity.PersonaEntity;
import com.imss.sivimss.arquetipo.model.entity.PersonaEntityMyBatis;

public interface PersonaMigracionMapper {

	@Select("SELECT sp.ID_PERSONA AS idPersona, su.CVE_CURP AS cveCURP, su.NOM_USUARIO AS nomPersona "
			+ ", su.NOM_APELLIDO_PATERNO AS primerApellido, su.NOM_APELLIDO_MATERNO AS segundoApellido "
			+ ", su.FEC_NACIMIENTO AS fecNac, su.REF_CORREOE AS correo, su.ID_ESTADO_NACIMIENTO AS idEdoNac "
			+ " FROM SVT_USUARIOS su "
			+ " LEFT JOIN SVC_PERSONA sp ON sp.CVE_CURP = su.CVE_CURP "
			+ " WHERE  su.CVE_CURP IS NOT NULL ")
	public List<PersonaEntity> consultaUsuarios();

	@Update(value = "UPDATE SVT_USUARIOS SET ID_PERSONA = #{in.idPersona}  WHERE CVE_CURP = #{in.cveCURP}")
	public int inserIdPersona(@Param("in")PersonaEntity persona);
	
	@Update(value = "UPDATE SVC_PERSONA SET ID_ESTADO_NACIMIENTO = #{in.idEdoNac}  WHERE CVE_CURP = #{in.cveCURP}")
	public int inserIdEdoNacInPersona(@Param("in")PersonaEntity persona);

	@Insert(value = "INSERT INTO SVC_PERSONA(NOM_PERSONA, NOM_PRIMER_APELLIDO, NOM_SEGUNDO_APELLIDO, CVE_CURP, FEC_NAC, REF_CORREO, ID_USUARIO_ALTA, ID_ESTADO_NACIMIENTO) "
			+ "VALUES ( #{in.nomPersona},#{in.primerApellido},#{in.segundoApellido}, #{in.cveCURP}, #{in.fecNac},#{in.correo}, #{in.idUsuarioAlta}, #{idEdoNac} )")
	@Options(useGeneratedKeys = true,keyProperty = "in.idPersona", keyColumn="ID_PERSONA")
	public int insertarPersona(@Param("in")PersonaEntity persona);
	
}
