package cn.itsource.org.domain;

import cn.itsource.basic.domain.BaseDomain;
import lombok.Data;

import javax.sql.rowset.spi.SyncResolver;
import java.util.List;

@Data
public class Department extends BaseDomain{

    private String sn;
    private String name;
    private String dirPath;
    private Integer state;
    private Employee manager;
    private Department dept_parent;
    private List<Department> children;

    public String getValue(){
        return this.id.toString();
    }
    public String getLabel(){
        return this.name;
    }
}
