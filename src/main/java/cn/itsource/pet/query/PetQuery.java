package cn.itsource.pet.query;

import cn.itsource.basic.query.BaseQuery;
import lombok.Data;

@Data
public class PetQuery extends BaseQuery{

    //0 下架 1 上架
    private Integer state;

    //店铺id
    private Long shopId;

    //宠物名
    private String name;
}
