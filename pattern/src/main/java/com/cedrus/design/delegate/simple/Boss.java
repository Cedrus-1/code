package com.cedrus.design.delegate.simple;


/**
 * @author Cedrus
 * @date 2019/3/17
 */
public class Boss {

    public void command(String command,Leader leader){
        leader.doing(command);
    }

}

