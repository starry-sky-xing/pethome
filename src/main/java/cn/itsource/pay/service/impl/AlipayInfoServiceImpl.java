package cn.itsource.pay.service.impl;

import cn.itsource.basic.service.impl.BaseServiceImpl;
import cn.itsource.pay.domain.AlipayInfo;
import cn.itsource.pay.mapper.AlipayInfoMapper;
import cn.itsource.pay.service.IAlipayInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlipayInfoServiceImpl extends BaseServiceImpl<AlipayInfo> implements IAlipayInfoService {
    @Autowired
    private AlipayInfoMapper alipayInfoMapper;

    @Override
    public AlipayInfo loadByAppid(String app_id) {
       return alipayInfoMapper.findByAppid(app_id);
    }
}
