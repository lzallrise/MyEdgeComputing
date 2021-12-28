package me.mason.springbatch.example.increment.step;

import lombok.extern.slf4j.Slf4j;
import me.mason.springbatch.common.LogConstants;
import me.mason.springbatch.entity.User;
import me.mason.springbatch.entity.User2;
import org.springframework.batch.item.ItemProcessor;

import java.util.Objects;

/**
 * User处理类
 *
 * @author mason
 * @since 2019/5/31
 */
@Slf4j
public class Increment2UserItemProcessor implements ItemProcessor<User2, User2> {
    @Override
    public User2 process(User2 user) throws Exception {
        String title = user.getTitle();
        if(Objects.nonNull(title)){
            user.setTitle(title.toUpperCase());
        }
        log.info(LogConstants.LOG_TAG + "increment item process: " +user.getName());
        return user;
    }
}
