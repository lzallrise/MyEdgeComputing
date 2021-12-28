package me.mason.springbatch.dao.edge;

import me.mason.springbatch.entity.User;
import me.mason.springbatch.entity.User2;
import org.beetl.sql.core.annotatoin.Sql;
import org.beetl.sql.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @program: MyEdgeComputing
 * @description
 * @author: LZ
 * @create: 2021-12-21 12:09
 **/
@Repository
public interface EdgeUserRepository extends BaseMapper<User2> {
    List<User2> getEdgeIncreUser(Map<String,Object> params);
    @Sql(value="select max(sys_update_time) from test_user1")
    Date selectMaxUpdateTime();
}
