package cn.itsource.pet.service;

import cn.itsource.basic.service.IBaseService;
import cn.itsource.pet.domain.SearchMasterMsg;

public interface ISearchMasterMsgService extends IBaseService<SearchMasterMsg>{
    void publish(SearchMasterMsg searchMasterMsg);
}
