package cn.itsource.basic.service;

import cn.itsource.basic.query.BaseQuery;
import cn.itsource.basic.utiles.PageBean;

import java.util.List;

public interface IBaseService<T> {
    /**
     * 添加方法
     * @param t
     */
    void add(T t);

    /** 修改方法
     * @param t
     */
    void update(T t);

    /**
     * 删除方法
     * @param id
     */
    void delete(Long id);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    T findById(Long id);

    /**
     * 查询所有
     * @return
     */
    List<T> findAll();

    //分页 高级查询
    PageBean<T> findPage(BaseQuery baseQuery);
}
