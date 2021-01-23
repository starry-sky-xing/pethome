package cn.itsource.org.controller;

import cn.itsource.basic.domain.BaseDomain;
import cn.itsource.org.domain.Department;
import cn.itsource.org.query.DepQuery;
import cn.itsource.org.service.IDepartmentService;
import cn.itsource.basic.utiles.JsonResult;
import cn.itsource.basic.utiles.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dept")
public class DepController {
    @Autowired
    private IDepartmentService departmentService;

    @PutMapping
    public JsonResult save(@RequestBody Department department){
        try {
            if(department.getId()==null){
                departmentService.add(department);
            }else {
                departmentService.update(department);
            }
            return new JsonResult();
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.me(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public JsonResult delete(@PathVariable("id") Long id){
        try {
            departmentService.delete(id);
            return new JsonResult();
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.me(e.getMessage());
        }
    }
    //批量删除
    @PostMapping("/batchRemove")
    public JsonResult delete(@RequestBody List<Department> department){
        //System.out.println(department);
        try {
            departmentService.BatchRemove(department);
            return new JsonResult();
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.me(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public Department findById(@PathVariable("id") Long id){
            return departmentService.findById(id);
    }
    @GetMapping
    public List<Department> findAll(){
            return departmentService.findAll();
    }

    @PostMapping
    public PageBean<Department> findPage(@RequestBody DepQuery depQuery){
            return departmentService.findPage(depQuery);

    }

    /**
     * 多级部门查询
     * @return
     */
    @PostMapping("/getDepartments")
    public List<Department> findPage(@RequestBody BaseDomain baseDomain){
        System.out.println(baseDomain.getId());
        return departmentService.findOneTwolevelDepartments(baseDomain.getId());

    }
}
