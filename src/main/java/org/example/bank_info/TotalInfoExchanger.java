package org.example.bank_info;

import org.example.Exchanger;

public class TotalInfoExchanger {
    private FilialsInfo filials;
    private NameBank names;

    public TotalInfoExchanger(Exchanger exchanger) {
        this.filials = new FilialsInfo(exchanger);
        this.names = new NameBank(exchanger);
    }
}
