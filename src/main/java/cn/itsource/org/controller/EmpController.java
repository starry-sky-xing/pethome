package cn.itsource.org.controller;

import cn.itsource.org.domain.Employee;
import cn.itsource.org.service.IEmployeeService;
import cn.itsource.basic.utiles.JsonResult;
import cn.itsource.basic.utiles.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmpController {
    @Autowired
    private IEmployeeService empservice;

    @GetMapping
    public List<Employee> loadEmpAll(){

        return empservice.findAll();
    }

}
