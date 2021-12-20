package me.mason.springbatch;

import lombok.extern.slf4j.Slf4j;
import me.mason.springbatch.common.SyncConstants;
import me.mason.springbatch.example.increment.config.IncrementBatchConfig;
import me.mason.springbatch.service.IncrementService;
import me.mason.springbatch.service.batch.JobLauncherService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

/**
 * increment测试类
 * @author mason
 * @since 2019/6/1
 **/

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {MainBootApplication.class,IncrementBatchConfig.class})
@Slf4j
public class IncrementJobTest {

    @Autowired
    private JobLauncherService jobLauncherService;

    @Autowired
    private IncrementService incrementService;

    @Autowired
    private Job incrementJob;

    @Test
    public void testIncrementJob() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        JobParameters jobParameters = incrementService.initJobParam();
        Map<String, Object> stringObjectMap = jobLauncherService.startJob(incrementJob, jobParameters);
        Assert.assertEquals(ExitStatus.COMPLETED,stringObjectMap.get(SyncConstants.STR_RETURN_EXITSTATUS));
    }
}
