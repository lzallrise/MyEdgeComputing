package me.mason.springbatch.example.increment.step;

import me.mason.springbatch.dao.target.TargetUserRepository;
import me.mason.springbatch.entity.User;
import me.mason.springbatch.entity.User2;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;

/**
 * User写入类
 *
 * @author mason
 * @since 2019/5/31
 */
public class Increment2UserItemWriter implements ItemWriter<User2> {
    @Autowired
    private TargetUserRepository targetUserRepository;

    @Override
    public void write(List<? extends User2> items) throws Exception {
        if(Objects.nonNull(items))
        {
            targetUserRepository.getSQLManager().updateBatch("user2.insertEdgeIncreUser",items);
        }
    }
}
