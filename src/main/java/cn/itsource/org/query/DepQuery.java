package cn.itsource.org.query;

import cn.itsource.basic.query.BaseQuery;
import lombok.Data;

@Data
public class DepQuery extends BaseQuery {
    private String name;
    private Integer state;

}
