package cn.itsource.pet.mapper;

import cn.itsource.basic.mapper.BaseMapper;
import cn.itsource.pet.domain.Pet;

import java.util.List;

public interface PetMapper extends BaseMapper<Pet>{
    /**
     * 上架
     * @param pets
     */
    void onsale(List<Pet> pets);
}
