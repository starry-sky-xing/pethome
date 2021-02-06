package cn.itsource.quartz.service.impl;

import cn.itsource.basic.utiles.QuartzUtils;
import cn.itsource.quartz.domain.QuartzJobInfo;
import cn.itsource.quartz.job.OrderJob;
import cn.itsource.quartz.service.IQuartzService;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

@Service
public class QuartzServiceImpl implements IQuartzService {

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;
    /**
     * 添加定时任务
     * @param quartzJobInfo
     */
    @Override
    public void addJob(QuartzJobInfo quartzJobInfo) {

        Scheduler scheduler = schedulerFactoryBean.getObject();
        QuartzUtils.addJob(scheduler, quartzJobInfo.getJobName(), OrderJob.class,
                quartzJobInfo, quartzJobInfo.getCronj());
    }

    /**
     * 删除定时任务
     * @param jobName
     */
    @Override
    public void removeJob(String jobName) {
        Scheduler scheduler = schedulerFactoryBean.getObject();
        QuartzUtils.removeJob(scheduler, jobName);
    }
}
