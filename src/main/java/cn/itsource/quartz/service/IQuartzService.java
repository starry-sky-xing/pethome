package cn.itsource.quartz.service;

import cn.itsource.quartz.domain.QuartzJobInfo;

public interface IQuartzService {
    /**
     * 添加定时任务
     * @param quartzJobInfo
     */
    void addJob(QuartzJobInfo quartzJobInfo);

    void removeJob(String s);
}
