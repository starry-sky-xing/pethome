package cn.itsource.org.service;

import cn.itsource.basic.service.IBaseService;
import cn.itsource.org.domain.Department;

import java.util.List;

public interface IDepartmentService extends IBaseService<Department>{

    /**
     * 查询yier级目录的方法
     * @return
     * @param id 需要禁用不查 的editId
     */
    List<Department> findOneTwolevelDepartments(Long id);
}
