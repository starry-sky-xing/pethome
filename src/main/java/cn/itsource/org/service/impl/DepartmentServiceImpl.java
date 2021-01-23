package cn.itsource.org.service.impl;

import cn.itsource.basic.service.impl.BaseServiceImpl;
import cn.itsource.org.domain.Department;
import cn.itsource.org.service.IDepartmentService;
import cn.itsource.org.mapper.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
public class DepartmentServiceImpl extends BaseServiceImpl<Department> implements IDepartmentService {
    @Autowired
    private DepartmentMapper departmentMapper;

    //多级部门查询
    public void findMultistage(List<Department> departments, Long editId){
        System.out.println("=33333============="+editId);
        //如果有第三级
        if(departments.size()!=0){
            //遍历集合
            for(Department department:departments){
                //更具id查询下一级
                List<Department> children = departmentMapper.findChildByParentId(department.getId(),editId);
                //给下一级赋值
                department.setChildren(children.size()==0?null:children);
                //如果有下级 继续递归查询
                this.findMultistage(children, editId);

            }

        }
    }
    //获取一二级目录
    @Override
    public List<Department> findOneTwolevelDepartments(Long id) {
        System.out.println("一二级==========="+id);
        //一二级目录
        List<Department> onetwolevel = departmentMapper.findOneTwolevelDepartments(id);
        for(Department department:onetwolevel){
            //第二级目录
            List<Department> children = department.getChildren();
            //遍历第二级
            List<Department> newChildren = new ArrayList<>();
            for (Department child:children) {
                if(child.getId()!=id){
                    newChildren.add(child);
                }
            }
            department.setChildren(newChildren.size()==0?null:newChildren);
            //获取下一级目录
            this.findMultistage(newChildren,id);
        }
        return onetwolevel;
    }
}
