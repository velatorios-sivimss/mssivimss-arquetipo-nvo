package com.imss.sivimss.arquetipo.configuration.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.imss.sivimss.arquetipo.model.entity.PersonaEntity;
import com.imss.sivimss.arquetipo.model.entity.PersonaEntityMyBatis;

/*
 * Este es un ejemplo de cómo se pueden implementar querys a través de interfaces con MyBatis.
 * */


public interface PersonaMapper {

	/*
	 * Este es un ejemplo para realizar un insert con parametros, usualmente 
	 * se implementa con tipos primitivos (int, float, etc)
	 * 
	 * El nombre de la anotación es autoexplicativo, pero básicamente hace referencia 
	 * a un método planificado de MyBatis para ejecutar sentencias de tipo INSERT
	 * @Insert
	 * 
	 * Este es el objeto que obtenemos como salida (basicamente el id que se generó)
	 * @Param("out") PersonaEntityMyBatis out
	 * 
	 * Esta expresión se utiliza para especificar el atributo del objeto que almacenará 
	 * el identificador del nuevo registro. @Options tien mas aplicaciones, pero en este ejemplo
	 * se limita solo a obtener el id generado
	 * @Options(useGeneratedKeys = true,keyProperty = "out.idPersona", keyColumn="id")
	 * 
	 * Estos son comodines que usa mybatis para pasar parametros a una query
	 * @Param("nombre") -> El nombre del comodin debe hacer match con el parametro que estamos pasando
	 * */
	@Insert("INSERT INTO SVC_PERSONA(NOM_PERSONA, NOM_PRIMER_APELLIDO, NOM_SEGUNDO_APELLIDO, ID_USUARIO_ALTA) "
			+ "VALUES ( #{nombre},#{apePaterno},#{apeMaterno}, #{idUsuarioAlta} ) ;")
	@Options(useGeneratedKeys = true,keyProperty = "out.idPersona", keyColumn="id")
	public int nuevoRegistroParam(@Param("nombre") String nombre,
			@Param("apePaterno") String apePaterno,@Param("apeMaterno") String apeMaterno, @Param("idUsuarioAlta") int idUsuarioAlta , 
			@Param("out") PersonaEntityMyBatis out);
	
	
	/*
	 * Este es un ejemplo para realizar un insert con un objeto como parámetro
	 * Esta debería ser la forma estandar para insertar nuevos registros
	 * 
	 * Se debe pasar un objeto con la anotacion @Param("out") para despues sacar los
	 * valores del objeto usando los comodines ejemplo
	 * 
	 * Esta expresión se utiliza para especificar el atributo del objeto que almacenará 
	 * el identificador del nuevo registro. @Options tiene mas aplicaciones, pero en este ejemplo
	 * se limita solo a obtener el id generado
	 * @Options(useGeneratedKeys = true,keyProperty = "out.idPersona", keyColumn="id")
	 * 
	 * */
	@Insert(value = "INSERT INTO SVC_PERSONA(NOM_PERSONA, NOM_PRIMER_APELLIDO, NOM_SEGUNDO_APELLIDO, ID_USUARIO_ALTA) "
			+ "VALUES ( #{out.nomPersona},#{out.primerApellido},#{out.segundoApellido}, #{out.idUsuarioAlta} )")
	@Options(useGeneratedKeys = true,keyProperty = "out.idPersona", keyColumn="id")
	public int nuevoRegistroObj(@Param("out")PersonaEntityMyBatis persona);
	
	@Update(value = ""
			+ "UPDATE SVC_PERSONA  "
			+ "SET  "
			+ "	NOM_PERSONA=#{in.nomPersona}, "
			+ "	NOM_PRIMER_APELLIDO=#{in.primerApellido}, "
			+ "	NOM_SEGUNDO_APELLIDO=#{in.segundoApellido}, "
			+ " ID_USUARIO_MODIFICA = #{in.idUsuarioModifica} "
			+ " WHERE ID_PERSONA=#{in.idPersona}")
	public int actualizarRegistroObj(@Param("in")PersonaEntityMyBatis persona);
	
}
