package com.cedrus.spring.demo.orm.entity;

import com.cedrus.spring.demo.orm.annotation.CedrusColumn;
import lombok.Data;

/**
 * @author Cedrus
 * @date 2019/4/20
 */
@Data
public class Order {
    private String orderId;
    @CedrusColumn(name="mId")
    private int menberId;
}
