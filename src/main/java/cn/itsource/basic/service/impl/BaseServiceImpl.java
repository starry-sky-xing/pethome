package cn.itsource.basic.service.impl;

import cn.itsource.basic.mapper.BaseMapper;
import cn.itsource.basic.query.BaseQuery;
import cn.itsource.basic.service.IBaseService;
import cn.itsource.basic.utiles.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
public class BaseServiceImpl<T> implements IBaseService<T> {

    @Autowired
    private BaseMapper<T> baseMapper;
    @Override
    @Transactional
    public void add(T t) {
        baseMapper.add(t);
    }

    @Override
    @Transactional
    public void update(T t) {
        baseMapper.update(t);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        baseMapper.delete(id);
    }

    @Override
    public void BatchRemove(List<T> t) {
        baseMapper.BatchRemove(t);
    }

    @Override
    public T findById(Long id) {
        return baseMapper.findById(id);
    }

    @Override
    public List findAll() {
        return baseMapper.findAll();
    }

    @Override
    public PageBean<T> findPage(BaseQuery baseQuery) {
        //查询所有条数
        Long count = baseMapper.count(baseQuery);
        //如果条数为空则省略查询数据库的操作
        if(count==null || count<=0){
            return new PageBean<>();

        }
        List<T> list = baseMapper.findPage(baseQuery);
        return new PageBean<T>(count,list);
    }
}
