package cn.itsource.org.service.impl;

import cn.itsource.basic.service.impl.BaseServiceImpl;
import cn.itsource.org.domain.Employee;
import cn.itsource.org.mapper.EmployeeMapper;
import cn.itsource.org.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EmployeeServiceImpl extends BaseServiceImpl<Employee> implements IEmployeeService {

}
