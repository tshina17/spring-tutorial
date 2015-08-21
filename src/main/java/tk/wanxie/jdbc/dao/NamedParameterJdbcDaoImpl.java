package tk.wanxie.jdbc.dao;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

public class NamedParameterJdbcDaoImpl extends NamedParameterJdbcDaoSupport {

    public int insertCircle(int circleId, String name) {
        String sql = "INSERT INTO circle (circle_id, name) VALUES (:circle_id, :name)";
        SqlParameterSource namedParameters = new MapSqlParameterSource("circle_id", circleId).addValue("name", name);
        return this.getNamedParameterJdbcTemplate().update(sql, namedParameters);
    }

}
