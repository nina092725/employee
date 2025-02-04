package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Employee;
import com.example.repository.EmployeeRepository;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 従業員情報を操作するサービス.
 * 
 * @author igamasayuki
 *
 */
@Service
@Transactional
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate; // ★ JdbcTemplate をインジェクション


    /**
     * 従業員情報を全件取得します.
     * 
     * @return 従業員情報一覧
     */
    public List<Employee> showList() {
        List<Employee> employeeList = employeeRepository.findAll();
        return employeeList;
    }

    /**
     * 従業員情報を取得します.
     * 
     * @param id ID
     * @return 従業員情報
     * @throws org.springframework.dao.DataAccessException 検索されない場合は例外が発生します
     */
    public Employee showDetail(Integer id) {
        Employee employee = employeeRepository.load(id);
        return employee;
    }

    /**
     * 従業員情報を更新します.
     * 
     * @param employee 更新した従業員情報
     */
    // public void update(Employee employee) {
    //     employeeRepository.update(employee);
    // }
    public void update(Employee employee) {
    SqlParameterSource param = new BeanPropertySqlParameterSource(employee);
    String updateSql = """
        UPDATE employees SET 
        name=:name, 
        mail_address=:mailAddress, 
        address=:address, 
        telephone=:telephone, 
        salary=:salary, 
        dependents_count=:dependentsCount 
        WHERE id=:id
        """;
        jdbcTemplate.update(updateSql, param);
}


    /**
     * 名前検索を行います.
     * 
     * @param searchWord 検索ワード
     * @return 検索結果
     */
    public List<Employee> findByNameContaining(String name) {
        return employeeRepository.findByNameContaining(name);
    }
}