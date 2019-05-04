package com.cedrus.design.decorator.passport;


import com.cedrus.design.decorator.passport.old.SigninService;
import com.cedrus.design.decorator.passport.upgrade.ISiginForThirdService;
import com.cedrus.design.decorator.passport.upgrade.SiginForThirdService;

public class DecoratorTest {

    public static void main(String[] args) {

        //满足一个is-a
        ISiginForThirdService siginForThirdService = new SiginForThirdService(new SigninService());
        siginForThirdService.loginForQQ("sdfasfdasfsf");

    }


}
