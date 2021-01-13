package cn.itsource.org.service;

import cn.itsource.basic.service.IBaseService;
import cn.itsource.org.domain.Department;

import java.util.List;

public interface IDepartmentService extends IBaseService<Department>{
//    void add(Department department);
//    void update(Department department);
//    void delete(Long id);
//    Department findById(Long id);
//    List<Department> findAll();
//
//    PageBean<Department> findPage(DepQuery depQuery);


    /**
     * 批量删除
     * @param department
     */
    void deleteMany(List<Department> department);

    /**
     * 查询yier级目录的方法
     * @return
     */
    List<Department> findOneTwolevelDepartments();
}
