package cn.itsource.org.service;

import cn.itsource.BaseTest;
import cn.itsource.org.domain.Department;
import cn.itsource.org.query.DepQuery;
import cn.itsource.basic.utiles.PageBean;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class IDepartmentServiceTest extends BaseTest {

    @Autowired
    private IDepartmentService departmentService;
    @Test
    public void findById() {
        Department department = departmentService.findById(3L);
        System.out.println(department);


    }

    @Test
    public void findAll() {
        departmentService.findAll().stream().forEach(System.out::println);
    }

    @Test
    public void findPage() {
        PageBean<Department> page = departmentService.findPage(new DepQuery());
        System.out.println(page);
    }
}