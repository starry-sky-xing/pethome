package cn.itsource.quartz.job;

import cn.itsource.basic.constant.PayConstants;
import cn.itsource.basic.constant.PetHomeConstant;
import cn.itsource.order.domain.ProductOrder;
import cn.itsource.order.mapper.ProductOrderMapper;
import cn.itsource.pay.domain.PayBill;
import cn.itsource.pay.mapper.PayBillMapper;
import cn.itsource.product.domain.Product;
import cn.itsource.quartz.domain.QuartzJobInfo;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchProperties;

import java.util.Date;
import java.util.Map;

public class OrderJob implements Job {

    @Autowired
    private ProductOrderMapper productOrderMapper;
    @Autowired
    private PayBillMapper payBillMapper;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //获取JobDataMap参数
        JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();
        //取值
       QuartzJobInfo quartzJobInfo = ((QuartzJobInfo) jobDataMap.get("params"));
        //获取参数
        Map<String, Object> params = quartzJobInfo.getParams();

        switch (quartzJobInfo.getType()){
            case PayConstants.BUSINESSTYPE_PRODUCT: //产品
                //获取订单编号
                //修改状态和时间
                String orderSn=(String)params.get("orderSn");
                ProductOrder productOrder= productOrderMapper.findByOrderSn(orderSn);
                productOrder.setState(PetHomeConstant.CANCLE);
                productOrder.setLastConfirmTime(new Date());
                productOrderMapper.update(productOrder);

                //根据订单编号查询支付订单
                PayBill payBill = payBillMapper.findByOrderSn(orderSn);
                payBill.setState(PetHomeConstant.CANCLE);
                payBill.setUpdateTime(new Date());
                payBillMapper.update(payBill);
                break;
            case PayConstants.BUSINESSTYPE_ADOPT:
                break;
            case PayConstants.BUSINESSTYPE_GOODS:
                break;
        }
    }
}
