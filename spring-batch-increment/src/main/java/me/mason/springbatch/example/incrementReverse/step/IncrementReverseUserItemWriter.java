package me.mason.springbatch.example.incrementReverse.step;

import me.mason.springbatch.dao.origin.OriginUserRepository;
import me.mason.springbatch.dao.target.TargetUserRepository;
import me.mason.springbatch.entity.User;
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
public class IncrementReverseUserItemWriter implements ItemWriter<User> {
    @Autowired
    private OriginUserRepository originUserRepository;

    @Override
    public void write(List<? extends User> items) throws Exception {
        if(Objects.nonNull(items))
        {
            originUserRepository.getSQLManager().updateBatch("user.insertTargetIncreUser",items);
        }
    }
}
