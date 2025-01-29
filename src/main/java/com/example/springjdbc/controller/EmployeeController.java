package com.example.springjdbc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.springjdbc.domain.Employee;
import com.example.springjdbc.service.EmployeeService;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService service;

    @RequestMapping("/execute")
    public String execute(Model model){
    // public String execute(){
        Employee employee = new Employee();
        employee.setName("山田 太郎");
        employee.setAge(20);
        employee.setGender("男");
        employee.setDepartmentId(1);
        service.save(employee); // employee = service.save(employee);

        Employee employee2 = service.load(26); //Employee employee2 = service.load(employee.getId());

        // // 更新処理追加
        // employee2.setName("山田花子");
        // employee2.setAge(50);
        // employee2.setGender("女");
        // employee2.setDepartmentId(4);
        // service.save(employee2);


        System.out.println(employee2);
        service.deleteById(26);

        service.findAll().forEach(System.out::println);
        //model.addAttribute("employees", service.findAll());
        return "emp-result";
    }
}
