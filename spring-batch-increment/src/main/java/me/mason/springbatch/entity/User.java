package me.mason.springbatch.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 *  test_user
 * @author mason
 */
@Entity
@Data
@Table(name="test_user")
public class User{
    @Id
    @GeneratedValue
    /**
     * id
     */
    private Long id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 职称职别
     */
    private String title;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 性别
     */
    private String gender;

    /**
     * 出生时间
     */
    private Date dateOfBirth;

    /**
     * sys_create_time
     */
    private Date sysCreateTime;

    /**
     * sys_create_user
     */
    private String sysCreateUser;

    /**
     * sys_update_time
     */
    private Date sysUpdateTime;

    /**
     * sys_update_user
     */
    private String sysUpdateUser;

    public User() {
    }

}