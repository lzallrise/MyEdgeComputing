package me.mason.springbatch.example.increment.config;

import cn.hutool.core.collection.CollUtil;
import me.mason.springbatch.common.SyncConstants;
import me.mason.springbatch.entity.User;
import me.mason.springbatch.example.increment.listener.Increment2JobEndListener;
import me.mason.springbatch.example.increment.step.Increment2UserItemProcessor;
import me.mason.springbatch.example.increment.step.Increment2UserItemReader;
import me.mason.springbatch.example.increment.step.Increment2UserItemWriter;
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
public class Increment2BatchConfig {
    @Autowired
    public JobBuilderFactory jobBuilderFactory;
    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job increment2Job(Step increment2Step,JobExecutionListener increment2Listener){
        String funcName = Thread.currentThread().getStackTrace()[1].getMethodName();
        return jobBuilderFactory.get(funcName)
                .listener(increment2Listener)
                .flow(increment2Step)
                .end().build();
    }
    @Bean
    public Step increment2Step(ItemReader increment2ItemReader ,ItemProcessor increment2Processor
            ,ItemWriter increment2Writer){
        String funcName = Thread.currentThread().getStackTrace()[1].getMethodName();
        return stepBuilderFactory.get(funcName)
                .<User,User>chunk(10)
                .reader(increment2ItemReader)
                .processor(increment2Processor)
                .writer(increment2Writer)
                .build();
    }

    /**
     * 根据传入的参数读取数据，直接使用字符串模板，注意需要添加{@link StepScope}注解,
     * @param lastUpdateTime sql参数，上次最后更新时间
     * @return
     */
    @Bean
    @StepScope
    public ItemReader increment2ItemReader(@Value("#{jobParameters['lastUpdateTime']}") String lastUpdateTime) {
        Increment2UserItemReader userItemReader = new Increment2UserItemReader();
        //设置参数，当前示例可不设置参数
        Map<String,Object> params = CollUtil.newHashMap();
        params.put(SyncConstants.STR_LAST_UPDATE_TIME,lastUpdateTime);
        userItemReader.setParams(params);

        return userItemReader;
    }

    @Bean
    public ItemWriter increment2Writer() {
        return new Increment2UserItemWriter();
    }

    @Bean
    public ItemProcessor increment2Processor(){
        return new Increment2UserItemProcessor();
    }

    @Bean
    public JobExecutionListener increment2Listener(){
        return new Increment2JobEndListener();
    }
}
