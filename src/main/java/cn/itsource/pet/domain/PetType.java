package cn.itsource.pet.domain;

import cn.itsource.basic.domain.BaseDomain;
import lombok.Data;

@Data
public class PetType extends BaseDomain {
    //宠物类型
    private String name;
    //描述
    private String description;
    //父种类
    private PetType pid;
}
