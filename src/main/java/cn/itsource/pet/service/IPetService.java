package cn.itsource.pet.service;

import cn.itsource.basic.service.IBaseService;
import cn.itsource.pet.domain.Pet;

import java.util.List;

public interface IPetService extends IBaseService<Pet>{
    /**
     * 处理寻主的宠物
     * @param pet
     */
    void handlePet(Pet pet);

    /**
     * 宠物批量上架
     * @param pets
     */
    void onsale(List<Pet> pets);
}
