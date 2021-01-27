package cn.itsource.pet.service.impl;

import cn.itsource.basic.constant.PetHomeConstant;
import cn.itsource.basic.service.impl.BaseServiceImpl;
import cn.itsource.basic.utiles.CodeGenerateUtils;
import cn.itsource.basic.utiles.StrUtils;
import cn.itsource.order.domain.PetAcquisitionOrder;
import cn.itsource.order.mapper.PetAcquisitionOrderMapper;
import cn.itsource.org.domain.Employee;
import cn.itsource.org.domain.Shop;
import cn.itsource.org.mapper.EmployeeMapper;
import cn.itsource.pet.domain.Pet;
import cn.itsource.pet.domain.PetDetail;
import cn.itsource.pet.domain.SearchMasterMsg;
import cn.itsource.pet.mapper.PetDetailMapper;
import cn.itsource.pet.mapper.PetMapper;
import cn.itsource.pet.mapper.SearchMasterMsgMapper;
import cn.itsource.pet.service.IPetService;
import cn.itsource.user.domain.LoginInfo;
import cn.itsource.user.domain.User;
import cn.itsource.user.mapper.LoginInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class PetServiceImpl extends BaseServiceImpl<Pet> implements IPetService {

    @Autowired
    private PetMapper petMapper;
    @Autowired
    private SearchMasterMsgMapper searchMasterMsgMapper;

    @Autowired
    private PetDetailMapper petDetailMapper;

    @Autowired
    private PetAcquisitionOrderMapper petAcquisitionOrderMapper;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Transactional
    @Override
    public void handlePet(Pet pet,LoginInfo loginInfo) {
        //System.out.println(pet);
        //设置宠物来源 寻主信息
        pet.setSourceType(PetHomeConstant.SEARCH_MASTER);
        petMapper.add(pet);
        //获取详细信息
        PetDetail detail = pet.getDetail();
        detail.setPet(pet);
        //保存详细信息
        petDetailMapper.add(detail);

        //把寻主信息改为已处理
        //根据id查找寻主信息
        SearchMasterMsg searchMasterMsg = searchMasterMsgMapper.findById(pet.getSearchMasterMsg().getId());
        searchMasterMsg.setState(PetHomeConstant.HANDLED);//改为已处理
        //更新
        searchMasterMsgMapper.update(searchMasterMsg);

        //创建收购订单
        PetAcquisitionOrder petAcquisitionOrder=creatPetAcquistionOrder(pet,searchMasterMsg);
        //根据登录信息获取用户
        Employee employee=employeeMapper.findByloginInfoId(loginInfo.getId());
        petAcquisitionOrder.setEmployee(employee);
        //保存收购订单
        petAcquisitionOrderMapper.add(petAcquisitionOrder);

    }

    /**
     * 创建收购订单
     * @return 收购订单信息
     */
    private PetAcquisitionOrder creatPetAcquistionOrder(Pet pet,SearchMasterMsg searchMasterMsg) {
        PetAcquisitionOrder order = new PetAcquisitionOrder();
        //摘要
        order.setDigest("[摘要]对"+pet.getName()+"收购订单！");
        //1 待报账(垫付) 2 待打款(银行转账)  3 完成
        //成本价为0时 就是已完成
        order.setState(BigDecimal.ZERO.equals(pet.getCostprice())?PetHomeConstant.PETACQUISITIONORDER_COMPLETE:pet.getPaytype());
        //收购价格
        order.setPrice(pet.getCostprice());
        //收购地址
        order.setAddress(searchMasterMsg.getAddress());
        //订单编号 随机的
        order.setOrderSn(CodeGenerateUtils.generateOrderSn(pet.getUser().getId()));
        //完成时间
        order.setLastcomfirmtime(order.getState()==PetHomeConstant.PETACQUISITIONORDER_COMPLETE?new Date():null);

        //宠物
        order.setPet(pet);
        //信息发布者
        order.setUser(pet.getUser());
        //支付类型  1垫付 2银行转账
        order.setPaytype(pet.getPaytype());
        //收购订单对应的店铺
        order.setShop(pet.getShop());
        return order;
    }

    /**
     * 上架宠物
     * @param pets
     */
    @Override
    public void onsale(List<Pet> pets) {
        petMapper.onsale(pets);
    }

    @Transactional
    @Override
    public void add(Pet pet) {
        super.add(pet);
        //保存详细些
        PetDetail detail = pet.getDetail();
        detail.setPet(pet);
        petDetailMapper.add(detail);
    }

    /**
     *  删除
     * @param id
     */
    @Transactional
    @Override
    public void delete(Long id) {
        //根据id查询pet
        Pet pet = petMapper.findById(id);
        //获取详细
        PetDetail detail = pet.getDetail();
        //删除详细
        petDetailMapper.delete(detail.getId());
        //如果是寻主来源
        if(pet.getSourceType()==1){
            //获取寻主信息
            SearchMasterMsg searchMasterMsg = pet.getSearchMasterMsg();
            if(!StringUtils.isEmpty(searchMasterMsg)){
                //删除寻主信系
                searchMasterMsgMapper.delete(searchMasterMsg.getId());
            }
        }

        //删除宠物
        super.delete(id);

    }
}
