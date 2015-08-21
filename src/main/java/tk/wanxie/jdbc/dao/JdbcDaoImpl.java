package tk.wanxie.jdbc.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;
import tk.wanxie.jdbc.model.Circle;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class JdbcDaoImpl {

    private DataSource dataSource;          // @Resource setter method if extra action is needed

    private JdbcTemplate jdbcTemplate;      // this only support ? placeholder for sql string

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;      // this support named parameters

    @Resource
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    // Everything within this method can be done with JdbcTemplate like in getCircleCount()
    public Circle getCircle(int circleId) {

        Circle circle = null;
        String query = "SELECT * FROM circle WHERE circle_id = ?";

        try (
            Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
        ){
            stmt.setInt(1, circleId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                circle = new Circle(circleId, rs.getString("name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return circle;
    }

    public int getCircleCount() {
        String sql = "SELECT COUNT(*) FROM circle";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public String getCircleName(int circleId) {
        String sql = "SELECT name FROM circle WHERE circle_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[] {circleId}, String.class);
    }

    // handle single row
    public Circle getCircleWhole(int circleId) {
        String sql = "SELECT * FROM circle WHERE circle_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{circleId}, new CircleMapper());
    }

    // handle multiple rows
    public List<Circle> getAllCircle() {
        String sql = "SELECT * FROM circle";
        return jdbcTemplate.query(sql, new CircleMapper());
    }

    public int insertCircle(Circle circle) {
        String sql = "INSERT INTO circle (circle_id, name) VALUES (?, ?)";
        return jdbcTemplate.update(sql, circle.getId(), circle.getName());
    }

    public int insertCircle(int circleId, String name) {
        String sql = "INSERT INTO circle (circle_id, name) VALUES (:circle_id, :name)";
        SqlParameterSource namedParameters = new MapSqlParameterSource("circle_id", circleId).addValue("name", name);
        return namedParameterJdbcTemplate.update(sql, namedParameters);
    }

    public void createTriangleTable() {
        String sql = "CREATE TABLE triangle ( triangle_id int(11) unsigned NOT NULL AUTO_INCREMENT, name varchar(60) NOT NULL, PRIMARY KEY (triangle_id) )";
        jdbcTemplate.execute(sql);
    }

    private static final class CircleMapper implements RowMapper<Circle> {
        @Override
        public Circle mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            return new Circle(resultSet.getInt("circle_id"), resultSet.getString("name"));
        }
    }

}
