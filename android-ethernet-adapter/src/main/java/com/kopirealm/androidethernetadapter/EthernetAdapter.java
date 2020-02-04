package com.kopirealm.androidethernetadapter;

import android.content.Context;

public class EthernetAdapter extends EthernetFunctions {

    public EthernetAdapter(Context context) {
        super(context);
    }

    @Override
    public boolean isSupported() {
        return super.isSupported();
    }

    @Override
    public boolean isEthEnabled() {
        return super.isEthEnabled();
    }

    @Override
    public void setEthEnabled(boolean enable) {
        super.setEthEnabled(enable);
    }

    @Override
    public void updateEthDevInfo(boolean dhcp, String ip, String gateway, String subnetmask, String dns) {
        super.updateEthDevInfo(dhcp, ip, gateway, subnetmask, dns);
    }

    @Override
    public EthernetConfig getSavedEthConfig() {
        return super.getSavedEthConfig();
    }
}
