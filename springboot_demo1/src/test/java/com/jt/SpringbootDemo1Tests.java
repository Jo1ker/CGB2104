package com.jt;

import com.jt.controller.UserController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sound.midi.Soundbank;

@SpringBootTest

class SpringbootDemo1Tests {
    @Autowired
    private UserController userController;

    @Test
    void test01() {
        String msg = userController.getMsg();
        System.out.println(msg);
    }

}
