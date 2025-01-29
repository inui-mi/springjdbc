package com.example.springjdbc.repository;

//import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.springjdbc.domain.Employee;

@Repository
public class EmployeeRepository {

    @Autowired
    private NamedParameterJdbcTemplate template;

    private static final RowMapper<Employee> EMPLOYEE_ROW_MAPPER = (rs, i) -> {
        Employee employees = new Employee();
        employees.setId(rs.getInt("id"));
        employees.setName(rs.getString("name"));
        employees.setAge(rs.getInt("age"));
        employees.setGender(rs.getString("gender"));
        employees.setDepartmentId(rs.getInt("department_id"));
        return employees;
    };

    public Employee load(Integer id){
        String sql = "SELECT id, name, age, gender, department_id FROM employees WHERE id = :id";
        SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
        Employee employees = template.queryForObject(sql, param, EMPLOYEE_ROW_MAPPER);
        return employees;
        // System.out.println("Repositoryのliad()が呼ばれました id = " + id);
        // return null;
    }

    public List<Employee> findAll(){
        String sql = "SELECT id, name, age, gender, department_id FROM employees ORDER BY age";
        List<Employee> employeesList = template.query(sql, EMPLOYEE_ROW_MAPPER);
        return employeesList;
        // System.out.println("RepositoryのfindAll()が呼ばれました");
        // return new ArrayList<Employee>();
    }

    public Employee save(Employee employees){
        SqlParameterSource param = new BeanPropertySqlParameterSource(employees);

        if(employees.getId() == null){
            String sql = """
                    insert into employees (
                        name
                        ,age
                        ,gender
                        ,department_id
                    ) values (
                        :name
                        ,:age
                        ,:gender
                        ,:departmentId
                    ) returning id        
                    """;
            Integer id = template.queryForObject(sql, param, Integer.class);
            employees.setId(id);
        }else{
            String updateSql = "UPDATE employees SET name=:name, age=:age, "
            + "gender=:gender, department_id=:departmentId" + "WHERE id=:id";
            template.update(updateSql, param);
        }
        return employees;
        // System.out.println("Repositoryのsave()が呼ばれました employee = " + employee);
        // return null;
    }

    public void deleteById(Integer id){
        String deleteSql = "DELETE FROM employees WHERE id=:id";

        SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);

        template.update(deleteSql, param);
        //System.out.println("RepositoryのdeleteById()が呼ばれました id = " + id);
    }
}
