package me.mason.springbatch.example.incrementReverse.listenner;

import lombok.extern.slf4j.Slf4j;
import me.mason.springbatch.common.SyncConstants;
import me.mason.springbatch.dao.origin.OriginUserRepository;
import me.mason.springbatch.dao.target.TargetUserRepository;
import me.mason.springbatch.service.CdcTempService;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * 结束监听器
 * @author mason
 * @since 2019/6/1
 **/
@Slf4j
public class IncrementReverseJobEndListener extends JobExecutionListenerSupport {

    @Autowired
    private CdcTempService cdcTempService;

    @Autowired
    private OriginUserRepository originUserRepository;

    @Override
    public void afterJob(JobExecution jobExecution) {
        BatchStatus status = jobExecution.getStatus();
        Date latestDate  = originUserRepository.selectMaxUpdateTime();
        cdcTempService.updateCdcTempAfterJob(SyncConstants.CDC_REVERSE_TEMP_ID_USER,status,latestDate);
    }
}
