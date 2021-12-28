package me.mason.springbatch.task;

import lombok.extern.slf4j.Slf4j;
import me.mason.springbatch.MainBootApplication;
import me.mason.springbatch.common.LogConstants;
import me.mason.springbatch.common.SyncConstants;
import me.mason.springbatch.example.increment.config.IncrementBatchConfig;
import me.mason.springbatch.service.IncrementService;
import me.mason.springbatch.service.UpdateListenService;
import me.mason.springbatch.service.batch.JobLauncherService;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @program: spring-batch-increment
 * @description
 * @author: LZ
 * @create: 2021-12-20 16:01
 **/
@Component
@Slf4j
public class IncrementJobTask {
    @Autowired
    private JobLauncherService jobLauncherService;

    @Autowired
    private IncrementService incrementService;

    @Autowired
    private UpdateListenService updateListenService;

    @Autowired
    @Qualifier("incrementJob")
    private Job incrementJob;

    @Autowired
    @Qualifier("increment2Job")
    private Job increment2Job;
    @Scheduled(cron = "0 */2 * * * ?")
    public void testIncrementJob() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        JobParameters jobParameters = incrementService.initJobParam();
        Map<String, Object> stringObjectMap = jobLauncherService.startJob(incrementJob, jobParameters);
        log.debug(LogConstants.LOG_TAG+stringObjectMap.get(SyncConstants.STR_RETURN_EXITSTATUS));
    }
    @Scheduled(cron = "0 */3 * * * ?")
    public void testIncrementJob2() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        JobParameters jobParameters = incrementService.initJobParam2();
        Map<String, Object> stringObjectMap = jobLauncherService.startJob(increment2Job, jobParameters);
        log.debug(LogConstants.LOG_TAG+stringObjectMap.get(SyncConstants.STR_RETURN_EXITSTATUS));
    }
    @Scheduled(cron = "0 */1 * * * ?")
    public void checkUpdate(){
        log.debug(LogConstants.LOG_TAG+"检查边缘端是否发生更新");
         boolean oringin=updateListenService.isExcuteUpdateOringin();
         boolean edge=updateListenService.isExcuteUpdateEdge();
         if(oringin||edge){
             log.debug(LogConstants.LOG_TAG+"边缘端发生更新");
         }
         else {
             log.debug(LogConstants.LOG_TAG+"边缘端没有发生更新");
         }
    }
}
