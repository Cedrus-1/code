package com.cedrus.design.adapter;

import com.cedrus.design.adapter.poweradapter.AC220;
import com.cedrus.design.adapter.poweradapter.DC5;
import com.cedrus.design.adapter.poweradapter.PowerAdapter;

public class PowerAdapterTest {
    public static void main(String[] args) {
        DC5 dc5 = new PowerAdapter(new AC220());
        dc5.outoupDC5V();
    }
}
