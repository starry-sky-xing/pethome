package cn.itsource.basic.query;

import lombok.Data;

@Data
public class BaseQuery {
    private Integer localPage=1; //当前页面
    private Integer pageSize=5;//显示条数

    public Integer getBegin() {
        return (localPage-1)*pageSize;
    }
}
