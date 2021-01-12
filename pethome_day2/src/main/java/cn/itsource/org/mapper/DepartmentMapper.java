package cn.itsource.org.mapper;

import cn.itsource.basic.mapper.BaseMapper;
import cn.itsource.org.domain.Department;


import java.util.List;

public interface DepartmentMapper extends BaseMapper<Department>{

    //批量删除
    void deleteMany(List<Department> department);
    //查找一二级目录
    List<Department> findOneTwolevelDepartments();
    //根据parentId查询child
    List<Department> findChildByParentId(Long id);
}
