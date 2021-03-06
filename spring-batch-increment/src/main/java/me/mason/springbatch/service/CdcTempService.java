package me.mason.springbatch.service;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import me.mason.springbatch.common.LogConstants;
import me.mason.springbatch.dao.local.CdcTemp2Repository;
import me.mason.springbatch.dao.local.CdcTempRepository;
import me.mason.springbatch.entity.CdcTemp;
import me.mason.springbatch.entity.CdcTemp2;
import org.springframework.batch.core.BatchStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 读写cdcTemp数据
 *
 * @author mason
 * @date 2019/5/22
 **/
@Service
@Slf4j
public class CdcTempService {
    @Autowired
    private CdcTempRepository cdcTempRepository;
    @Autowired
    private CdcTemp2Repository cdcTemp2Repository;

    /**
     * 根据id获取cdc_temp的记录
     * @param id 记录ID
     * @return {@link CdcTemp}
     */
    public CdcTemp getCurrentCdcTemp(int id){
        return cdcTempRepository.getSQLManager().single(CdcTemp.class, id);
    }
    public CdcTemp2 getCurrentCdcTemp2(int id){
        return cdcTemp2Repository.getSQLManager().single(CdcTemp2.class,id);
    }
    /**
     * 根据参数更新cdcTemp表的数据
     * @param cdcTempId cdcTempId
     * @param status job状态
     * @param lastUpdateTime 最后更新时间
     */
    public void updateCdcTempAfterJob(int cdcTempId,BatchStatus status,Date lastUpdateTime){
        //获取
        CdcTemp cdcTemp = cdcTempRepository.getSQLManager().single(CdcTemp.class, cdcTempId);
        cdcTemp.setCurrentUpdateTime(DateUtil.date());
        //正常完成则更新数据时间
        if( status == BatchStatus.COMPLETED){
            cdcTemp.setLastUpdateTime(lastUpdateTime);
        }else{
            log.info(LogConstants.LOG_TAG+"同步状态异常："+ status.toString());
        }
        //设置同步状态
        cdcTemp.setStatus(status.name());
        cdcTempRepository.updateById(cdcTemp);
    }
    /**
     * 根据参数更新cdcTemp表的数据
     * @param cdcTempId cdcTempId
     * @param status job状态
     * @param lastUpdateTime 最后更新时间
     */
    public void updateCdcTemp2AfterJob(int cdcTempId,BatchStatus status,Date lastUpdateTime){
        //获取
        CdcTemp2 cdcTemp2 = cdcTemp2Repository.getSQLManager().single(CdcTemp2.class, cdcTempId);
        cdcTemp2.setCurrentUpdateTime(DateUtil.date());
        //正常完成则更新数据时间
        log.info("lz"+status.name());
        if( status == BatchStatus.COMPLETED){
            cdcTemp2.setLastUpdateTime(lastUpdateTime);
        }else{
            log.info(LogConstants.LOG_TAG+"同步状态异常："+ status.toString());
        }
        //设置同步状态
        cdcTemp2.setStatus(status.name());
        log.info("lz"+status.name());
        cdcTemp2Repository.updateById(cdcTemp2);
    }
}
