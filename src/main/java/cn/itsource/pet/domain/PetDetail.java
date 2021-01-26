package cn.itsource.pet.domain;

import cn.itsource.basic.domain.BaseDomain;
import lombok.Data;

@Data
public class PetDetail extends BaseDomain {
    //宠物
    private Pet pet;
    //领养须知
    private String adoptNotice;
    //简介
    private String intro;

}
