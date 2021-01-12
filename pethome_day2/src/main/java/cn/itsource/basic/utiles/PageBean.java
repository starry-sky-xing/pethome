package cn.itsource.basic.utiles;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PageBean<T> {
    private Long totals = 0L;
    private List<T> data = new ArrayList<T>();

    public PageBean(Long totals,List<T> data) {
        this.data = data;
        this.totals = totals;
    }
    public PageBean() {

    }
}
