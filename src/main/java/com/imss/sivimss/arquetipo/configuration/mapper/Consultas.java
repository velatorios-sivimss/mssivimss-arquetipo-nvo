package com.imss.sivimss.arquetipo.configuration.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

import com.imss.sivimss.arquetipo.model.entity.PersonaEntityMyBatis;


@Repository
public interface Consultas {
	static class PureSqlProvider{
        public String sql(String sql) {
            return sql;
        }
 
        public String count(String from) {
            return "SELECT count(*) FROM " + from;
        }
    }
	@SelectProvider(type = PureSqlProvider.class, method = "sql")
	public List<Map<String, Object>> selectNativeQuery(String sql);

	@SelectProvider(type = PureSqlProvider.class, method = "sql")
	public ArrayList<PersonaEntityMyBatis> selectPersona(String sql);

}
