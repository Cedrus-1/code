package com.cedrus.spring.demo.orm.entity;

import com.cedrus.spring.demo.orm.annotation.CedrusTable;
import lombok.Data;

/**
 * @author Cedrus
 * @date 2019/4/20
 */

@Data
@CedrusTable(name="t_menber")
public class Member {
    private Long id;
    private String name;
    private String addr;
    private int age;
}
