package cn.itsource.pet.query;

import cn.itsource.basic.query.BaseQuery;
import lombok.Data;

@Data
public class SearchMasterMsgQuery extends BaseQuery{

    //0 待处理    1已处理
    private Integer state;

    //店铺id
    private Long shopId;

    //标题
    private String title;
}
