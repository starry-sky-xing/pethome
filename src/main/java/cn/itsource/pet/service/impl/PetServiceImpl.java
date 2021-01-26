package cn.itsource.pet.service.impl;

import cn.itsource.basic.constant.PetHomeConstant;
import cn.itsource.basic.service.impl.BaseServiceImpl;
import cn.itsource.pet.domain.Pet;
import cn.itsource.pet.domain.PetDetail;
import cn.itsource.pet.domain.SearchMasterMsg;
import cn.itsource.pet.mapper.PetDetailMapper;
import cn.itsource.pet.mapper.PetMapper;
import cn.itsource.pet.mapper.SearchMasterMsgMapper;
import cn.itsource.pet.service.IPetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class PetServiceImpl extends BaseServiceImpl<Pet> implements IPetService {

    @Autowired
    private PetMapper petMapper;
    @Autowired
    private SearchMasterMsgMapper searchMasterMsgMapper;

    @Autowired
    private PetDetailMapper petDetailMapper;

    @Transactional
    @Override
    public void handlePet(Pet pet) {
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
    }

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
