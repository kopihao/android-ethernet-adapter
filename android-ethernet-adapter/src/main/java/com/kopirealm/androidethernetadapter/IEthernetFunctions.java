package com.kopirealm.androidethernetadapter;

public interface IEthernetFunctions {

    String ETH_NETWORK_INTERFACE = "eth0";
    String ETH_CONN_MODE_DHCP = "dhcp";
    String ETH_CONN_MODE_MANUAL = "manual";

    boolean isSupported();

    boolean isEthEnabled();

    void setEthEnabled(boolean enable);

    void updateEthDevInfo(boolean dhcp, String ip, String gateway, String subnetmask, String dns);

    EthernetConfig getSavedEthConfig();
}
