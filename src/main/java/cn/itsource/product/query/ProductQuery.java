package cn.itsource.product.query;

import cn.itsource.basic.query.BaseQuery;
import lombok.Data;

@Data
public class ProductQuery extends BaseQuery {
    //服务名称
    private String name;
    //状态
    private Integer state;
}
