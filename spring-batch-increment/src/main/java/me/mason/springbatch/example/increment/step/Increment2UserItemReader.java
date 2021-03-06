package me.mason.springbatch.example.increment.step;

import lombok.extern.slf4j.Slf4j;
import me.mason.springbatch.dao.edge.EdgeUserRepository;
import me.mason.springbatch.dao.origin.OriginUserRepository;
import me.mason.springbatch.entity.User;
import me.mason.springbatch.entity.User2;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 读取User
 * @author  mason
 * @since  2019/6/1
 **/
@Slf4j
public class Increment2UserItemReader implements ItemReader<User2> {
    protected List<User2> items;

    protected Map<String,Object> params;
    @Autowired
    private OriginUserRepository originUserRepository;
    @Autowired
    private EdgeUserRepository edgeUserRepository;

    @Override
    public User2 read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if(Objects.isNull(items)){
            //使用beetlsql的md执行sql
            items = edgeUserRepository.getEdgeIncreUser(params);
            if(items.size() > 0){
                return items.remove(0);
            }
        }else{
            if (!items.isEmpty()) {
                return items.remove(0);
            }
        }
        return null;
    }
    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }
}
