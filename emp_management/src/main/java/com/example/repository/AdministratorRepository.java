package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Administrator;

/**
 * administratorsテーブルを操作するリポジトリ.
 */
@Repository
public class AdministratorRepository {

    /**
     * Administratorオブジェクトを生成するローマッパー.
     */
    private static final RowMapper<Administrator> ADMINISTRATOR_ROW_MAPPER = (rs, i) -> {
        Administrator administrator = new Administrator();
        administrator.setId(rs.getInt("id"));
        administrator.setName(rs.getString("name"));
        administrator.setMailAddress(rs.getString("mail_address"));
        administrator.setPassword(rs.getString("password"));
        return administrator;
    };

    @Autowired
    private NamedParameterJdbcTemplate template;

    /**
     * 主キーから管理者情報を取得します.
     */
    public Administrator load(Integer id) {
        String sql = "SELECT id, name, mail_address, password FROM administrators WHERE id = :id";
        SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
        return template.queryForObject(sql, param, ADMINISTRATOR_ROW_MAPPER);
    }

    /**
     * メールアドレスとパスワードから管理者情報を取得します.
     */
    public Administrator findByMailAddressAndPassword(String mailAddress, String password) {
        String sql = "SELECT id, name, mail_address, password FROM administrators WHERE mail_address = :mailAddress AND password = :password";
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("mailAddress", mailAddress)
                .addValue("password", password);
        List<Administrator> administratorList = template.query(sql, param, ADMINISTRATOR_ROW_MAPPER);
        return administratorList.isEmpty() ? null : administratorList.get(0);
    }

    /**
     * 管理者情報を挿入します.
     */
    public void insert(Administrator administrator) {
        SqlParameterSource param = new BeanPropertySqlParameterSource(administrator);
        String sql = "INSERT INTO administrators (name, mail_address, password) VALUES (:name, :mailAddress, :password)";
        template.update(sql, param);
    }

    /**
     * メールアドレスから管理者情報を取得します.
     */
    public Administrator findByMailAddress(String mailAddress) {
        String sql = "SELECT id, name, mail_address, password FROM administrators WHERE mail_address = :mailAddress";
        SqlParameterSource param = new MapSqlParameterSource().addValue("mailAddress", mailAddress);
        List<Administrator> administratorList = template.query(sql, param, ADMINISTRATOR_ROW_MAPPER);
        return administratorList.isEmpty() ? null : administratorList.get(0);
    }

    /**
     * 指定したメールアドレスが既に存在するかチェックします.
     */
    public boolean existsByMailAddress(String mailAddress) {
        String sql = "SELECT COUNT(*) FROM administrators WHERE mail_address = :mailAddress";
        SqlParameterSource param = new MapSqlParameterSource().addValue("mailAddress", mailAddress);
        Integer count = template.queryForObject(sql, param, Integer.class);
        return count != null && count > 0;
    }
}
