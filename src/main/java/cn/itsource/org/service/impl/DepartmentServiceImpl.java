package cn.itsource.org.service.impl;

import cn.itsource.basic.service.impl.BaseServiceImpl;
import cn.itsource.org.domain.Department;
import cn.itsource.org.query.DepQuery;
import cn.itsource.org.service.IDepartmentService;
import cn.itsource.org.mapper.DepartmentMapper;
import cn.itsource.basic.utiles.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
public class DepartmentServiceImpl extends BaseServiceImpl<Department> implements IDepartmentService {
    @Autowired
    private DepartmentMapper departmentMapper;

//    @Override
//    @Transactional
//    public void add(Department department) {
//        departmentMapper.add(department);
//    }
//
//    @Override
//    @Transactional
//    public void update(Department department) {
//        departmentMapper.update(department);
//    }
//
//    @Override
//    @Transactional
//    public void delete(Long id) {
//        departmentMapper.delete(id);
//    }
//
//    @Override
//    public Department findById(Long id) {
//        return departmentMapper.findById(id);
//    }
//
//    @Override
//    public List<Department> findAll() {
//
//        return departmentMapper.findAll();
//    }

//    @Override
//    public PageBean<Department> findPage(DepQuery depQuery) {
//        //查询所有条数
//        Long count = departmentMapper.count(depQuery);
//        //如果条数为空则省略查询数据库的操作
//        if(count==null || count<=0){
//            return new PageBean<>();
//
//        }
//        List<Department> list = departmentMapper.findPage(depQuery);
//        return new PageBean<Department>(count,list);
//    }

    @Override
    public void deleteMany(List<Department> department) {
        departmentMapper.deleteMany(department);
    }

    //多级部门查询
    public void findMultistage(List<Department> departments){
        //如果有第三级
        if(departments.size()!=0){
            //遍历集合
            for(Department department:departments){
                //更具id查询下一级
                List<Department> children = departmentMapper.findChildByParentId(department.getId());
                //给下一级赋值
                department.setChildren(children.size()==0?null:children);
                //如果有下级 继续递归查询
                this.findMultistage(children);

            }

        }
    }
    //获取一二级目录
    @Override
    public List<Department> findOneTwolevelDepartments() {
        //一二级目录
        List<Department> onetwolevel = departmentMapper.findOneTwolevelDepartments();
        for(Department department:onetwolevel){
            //第二级目录
            List<Department> children = department.getChildren();
            if(children.size()==0){
                department.setChildren(null);
            }
            //获取下一级目录
            this.findMultistage(children);
        }
        return onetwolevel;
    }
}
