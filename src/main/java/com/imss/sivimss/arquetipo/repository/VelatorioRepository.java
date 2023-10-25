package com.imss.sivimss.arquetipo.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.imss.sivimss.arquetipo.model.entity.VelatorioEntity;

public interface VelatorioRepository  extends JpaRepository<VelatorioEntity, Integer> {

	@Query(value = "SELECT sv.ID_VELATORIO AS idVelatorio, sv.DES_VELATORIO AS nombreVelatorio FROM SVC_VELATORIO sv ORDER BY sv.ID_VELATORIO ASC"
			, nativeQuery = true)
	List<Map<String, Object>>  buscarVelatorio();
	
	@Query(value = "SELECT sv.ID_VELATORIO AS idVelatorio, sv.DES_VELATORIO AS nombreVelatorio FROM SVC_VELATORIO sv "
			+ " WHERE sv.ID_DELEGACION = ?1 "
			+ " ORDER BY sv.ID_VELATORIO ASC"
			, nativeQuery = true)
	List<Map<String, Object>>  buscarVelatorioFiltro(Integer idDelegacion);

	@Query(value = "SELECT sv.ID_VELATORIO AS idVelatorio, sv.DES_VELATORIO AS nombreVelatorio FROM SVC_VELATORIO sv ORDER BY sv.ID_VELATORIO ASC ",
			countQuery = "SELECT count(*) FROM SVC_VELATORIO sv "
			, nativeQuery = true)
	List<Map<String, Object>>  buscarVelatorioPaginado(Pageable pageable);
	
	@Query(value = "SELECT COUNT(*) AS conteo FROM (SELECT sv.ID_VELATORIO AS idVelatorio, sv.DES_VELATORIO AS nombreVelatorio FROM SVC_VELATORIO sv ORDER BY sv.ID_VELATORIO ASC ) tem"
			, nativeQuery = true)
	List<Map<String, Object>>  buscarVelatorioPaginadoTotal();

	@Query(value = "SELECT sv.ID_VELATORIO AS idVelatorio, sv.DES_VELATORIO AS nombreVelatorio FROM SVC_VELATORIO sv WHERE sv.ID_DELEGACION = ?1 ORDER BY sv.ID_VELATORIO ASC",
			countQuery = "SELECT count(*) FROM SVC_VELATORIO sv WHERE sv.ID_DELEGACION = ?1"
			, nativeQuery = true)
	List<Map<String, Object>>  buscarVelatorioFiltroPag(Pageable pageable, Integer idDelegacion);
	
	@Query(value = "SELECT COUNT(*) AS conteo FROM (SELECT sv.ID_VELATORIO AS idVelatorio, sv.DES_VELATORIO AS nombreVelatorio FROM SVC_VELATORIO sv  WHERE sv.ID_DELEGACION = ?1 ) tem"
			, nativeQuery = true)
	List<Map<String, Object>>  buscarVelatorioFiltroPagTotal(Integer idDelegacion);
	
	@Modifying
	@Transactional
	@Query(
	  value = " INSERT INTO SVC_VELATORIO (DES_VELATORIO, ID_USUARIO_ADMIN, NOM_RESPO_SANITARIO, CVE_ASIGNACION, ID_DOMICILIO, NUM_TELEFONO, IND_ACTIVO, ID_USUARIO_ALTA, FEC_ALTA, ID_DELEGACION)"
	  		+" VALUES(:#{#velatorio.desVelatorio}, :#{#velatorio.idUsuarioAdmin}, :#{#velatorio.nomRepSanitario}, :#{#velatorio.cveAsignacion}, :#{#velatorio.idDomicilio}, :#{#velatorio.numTelefono},1, :idUsuarioAlta, CURDATE(), :#{#velatorio.idDelegacion})",
	  		nativeQuery = true)
	void guardarVelatorio(@Param("velatorio") VelatorioEntity velatorio,
			@Param("idUsuarioAlta") Integer idUsuarioAlta);
	

	@Modifying
	@Transactional
	@Query(
	  value = " UPDATE SVC_VELATORIO SET DES_VELATORIO = :#{#velatorio.desVelatorio}, ID_USUARIO_ADMIN = :#{#velatorio.idUsuarioAdmin}, NOM_RESPO_SANITARIO = :#{#velatorio.nomRepSanitario}"
	  		+ ", CVE_ASIGNACION = :#{#velatorio.cveAsignacion}, ID_DOMICILIO = :#{#velatorio.idDomicilio}, NUM_TELEFONO = :#{#velatorio.numTelefono}, ID_USUARIO_MODIFICA = :idUsuarioModifica"
	  		+ ", FEC_ACTUALIZACION = CURDATE(), ID_DELEGACION =  :#{#velatorio.idDelegacion} WHERE ID_VELATORIO = :#{#velatorio.idVelatorio}",
	  		nativeQuery = true)
	void actualizarVelatorio(@Param("velatorio") VelatorioEntity velatorio ,@Param("idUsuarioModifica") Integer idUsuarioModifica);
	

	@Modifying
	@Transactional
	@Query(
	  value = " UPDATE SVC_VELATORIO SET IND_ACTIVO = 0, FEC_BAJA = CURDATE(), ID_USUARIO_BAJA = :idUsuarioBaja WHERE ID_VELATORIO = :idVelatorio",
	  		nativeQuery = true)
	void borrarVelatorio(@Param("idVelatorio") Integer idVelatorio,@Param("idUsuarioBaja") Integer idUsuarioBaja);
}
