package me.mason.springbatch.example.incrementReverse.step;

import me.mason.springbatch.dao.edge.EdgeUserRepository;
import me.mason.springbatch.dao.target.TargetUserRepository;
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
public class IncrementReverse2UserItemWriter implements ItemWriter<User2> {
    @Autowired
    private EdgeUserRepository edgeUserRepository;

    @Override
    public void write(List<? extends User2> items) throws Exception {
        if(Objects.nonNull(items))
        {
            edgeUserRepository.getSQLManager().updateBatch("user.insertTargetIncreUser",items);
        }
    }
}
