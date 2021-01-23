package cn.itsource.org.mapper;

import cn.itsource.basic.mapper.BaseMapper;
import cn.itsource.org.domain.Department;
import org.apache.ibatis.annotations.Param;


import java.util.List;

public interface DepartmentMapper extends BaseMapper<Department>{

    //查找一二级目录
    List<Department> findOneTwolevelDepartments(@Param("id") Long id);
    //根据parentId查询child
    List<Department> findChildByParentId(@Param("id") Long id,@Param("editId")Long editId );
}
