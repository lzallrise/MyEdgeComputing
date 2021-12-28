package me.mason.springbatch.example.incrementReverse.config;

import cn.hutool.core.collection.CollUtil;
import me.mason.springbatch.common.SyncConstants;
import me.mason.springbatch.entity.User;
import me.mason.springbatch.example.increment.listener.Increment2JobEndListener;
import me.mason.springbatch.example.increment.step.Increment2UserItemProcessor;
import me.mason.springbatch.example.increment.step.Increment2UserItemReader;
import me.mason.springbatch.example.increment.step.Increment2UserItemWriter;
import me.mason.springbatch.example.incrementReverse.listenner.IncrementReverse2JobEndListener;
import me.mason.springbatch.example.incrementReverse.step.IncrementReverse2UserItemProcessor;
import me.mason.springbatch.example.incrementReverse.step.IncrementReverse2UserItemReader;
import me.mason.springbatch.example.incrementReverse.step.IncrementReverse2UserItemWriter;
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
public class IncrementReverse2BatchConfig {
    @Autowired
    public JobBuilderFactory jobBuilderFactory;
    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job incrementReverse2Job(Step incrementReverse2Step,JobExecutionListener incrementReverse2Listener){
        String funcName = Thread.currentThread().getStackTrace()[1].getMethodName();
        return jobBuilderFactory.get(funcName)
                .listener(incrementReverse2Listener)
                .flow(incrementReverse2Step)
                .end().build();
    }
    @Bean
    public Step incrementReverse2Step(ItemReader incrementReverse2ItemReader ,ItemProcessor incrementReverse2Processor
            ,ItemWriter incrementReverse2Writer){
        String funcName = Thread.currentThread().getStackTrace()[1].getMethodName();
        return stepBuilderFactory.get(funcName)
                .<User,User>chunk(10)
                .reader(incrementReverse2ItemReader)
                .processor(incrementReverse2Processor)
                .writer(incrementReverse2Writer)
                .build();
    }

    /**
     * 根据传入的参数读取数据，直接使用字符串模板，注意需要添加{@link StepScope}注解,
     * @param lastUpdateTime sql参数，上次最后更新时间
     * @return
     */
    @Bean
    @StepScope
    public ItemReader incrementReverse2ItemReader(@Value("#{jobParameters['lastUpdateTime']}") String lastUpdateTime) {
        IncrementReverse2UserItemReader userItemReader = new IncrementReverse2UserItemReader();
        //设置参数，当前示例可不设置参数
        Map<String,Object> params = CollUtil.newHashMap();
        params.put(SyncConstants.STR_LAST_UPDATE_TIME,lastUpdateTime);
        userItemReader.setParams(params);

        return userItemReader;
    }

    @Bean
    public ItemWriter incrementReverse2Writer() {
        return new IncrementReverse2UserItemWriter();
    }

    @Bean
    public ItemProcessor incrementReverse2Processor(){
        return new IncrementReverse2UserItemProcessor();
    }

    @Bean
    public JobExecutionListener incrementReverse2Listener(){
        return new IncrementReverse2JobEndListener();
    }
}
