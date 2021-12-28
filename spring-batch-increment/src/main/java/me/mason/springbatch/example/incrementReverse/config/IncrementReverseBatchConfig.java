package me.mason.springbatch.example.incrementReverse.config;

import cn.hutool.core.collection.CollUtil;
import me.mason.springbatch.common.SyncConstants;
import me.mason.springbatch.entity.User;
import me.mason.springbatch.example.increment.listener.IncrementJobEndListener;
import me.mason.springbatch.example.increment.step.IncrementUserItemProcessor;
import me.mason.springbatch.example.increment.step.IncrementUserItemReader;
import me.mason.springbatch.example.increment.step.IncrementUserItemWriter;
import me.mason.springbatch.example.incrementReverse.listenner.IncrementReverseJobEndListener;
import me.mason.springbatch.example.incrementReverse.step.IncrementReverseUserItemProcessor;
import me.mason.springbatch.example.incrementReverse.step.IncrementReverseUserItemReader;
import me.mason.springbatch.example.incrementReverse.step.IncrementReverseUserItemWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * increment Batch配置类
 * @author  mason
 * @since  2019/6/1
 **/
@Configuration
@EnableBatchProcessing
public class IncrementReverseBatchConfig {
    @Autowired
    public JobBuilderFactory jobBuilderFactory;
    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job incrementReverseJob(Step incrementReverseStep,JobExecutionListener incrementReverseListener){
        String funcName = Thread.currentThread().getStackTrace()[1].getMethodName();
        return jobBuilderFactory.get(funcName)
                .listener(incrementReverseListener)
                .flow(incrementReverseStep)
                .end().build();
    }
    @Bean
    public Step incrementReverseStep(ItemReader incrementReverseItemReader ,ItemProcessor incrementReverseProcessor
            ,ItemWriter incrementReverseWriter){
        String funcName = Thread.currentThread().getStackTrace()[1].getMethodName();
        return stepBuilderFactory.get(funcName)
                .<User,User>chunk(10)
                .reader(incrementReverseItemReader)
                .processor(incrementReverseProcessor)
                .writer(incrementReverseWriter)
                .build();
    }

    /**
     * 根据传入的参数读取数据，直接使用字符串模板，注意需要添加{@link StepScope}注解,
     * @param lastUpdateTime sql参数，上次最后更新时间
     * @return
     */
    @Bean
    @StepScope
    public ItemReader incrementReverseItemReader(@Value("#{jobParameters['lastUpdateTime']}") String lastUpdateTime) {
        IncrementReverseUserItemReader userItemReader = new IncrementReverseUserItemReader();
        //设置参数，当前示例可不设置参数
        Map<String,Object> params = CollUtil.newHashMap();
        params.put(SyncConstants.STR_LAST_UPDATE_TIME,lastUpdateTime);
        userItemReader.setParams(params);

        return userItemReader;
    }

    @Bean
    public ItemWriter incrementReverseWriter() {
        return new IncrementReverseUserItemWriter();
    }

    @Bean
    public ItemProcessor incrementReverseProcessor(){
        return new IncrementReverseUserItemProcessor();
    }

    @Bean
    public JobExecutionListener incrementReverseListener(){
        return new IncrementReverseJobEndListener();
    }
}
