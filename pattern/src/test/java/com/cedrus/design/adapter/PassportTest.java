package com.cedrus.design.adapter;


import com.cedrus.design.adapter.loginadapter.v2.IPassportForThird;
import com.cedrus.design.adapter.loginadapter.v2.PassportForThirdAdapter;

public class PassportTest {

    public static void main(String[] args) {

        IPassportForThird passportForThird = new PassportForThirdAdapter();

        passportForThird.loginForQQ("");

    }

}
