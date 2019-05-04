package com.cedrus.design.delegate.simple;

/**
 * @author Cedrus
 * @date 2019/3/17
 */
public class EmployeeB implements IEmployee {
    @Override
    public void doing(String command) {
        System.out.println("我是员工B，我现在开始干" + command + "工作");
    }
}
