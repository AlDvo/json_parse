package org.example.bank_info;

import org.example.Exchanger;

public class NameBank {
    private String name;

    public NameBank(Exchanger exchanger) {
        this.name = exchanger.name + " " + exchanger.name_type;
    }
}
