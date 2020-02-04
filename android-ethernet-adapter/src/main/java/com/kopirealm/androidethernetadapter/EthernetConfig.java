package com.kopirealm.androidethernetadapter;

public class EthernetConfig {

    String dev_name;
    String ipaddr;
    String netmask;
    String routeAddr;
    String dns;
    String mode;

    EthernetConfig() {
        dev_name = null;
        ipaddr = null;
        dns = null;
        routeAddr = null;
        netmask = null;
        mode = IEthernetFunctions.ETH_CONN_MODE_DHCP;
    }

    public boolean isStaticMode() {
        return mode.equalsIgnoreCase(IEthernetFunctions.ETH_CONN_MODE_MANUAL);
    }

    public boolean isDHCPMode() {
        return mode.equalsIgnoreCase(IEthernetFunctions.ETH_CONN_MODE_DHCP);
    }

    public String getDev_name() {
        return dev_name;
    }

    public String getIpaddr() {
        return ipaddr;
    }

    public String getNetmask() {
        return netmask;
    }

    public String getRouteAddr() {
        return routeAddr;
    }

    public String getDns() {
        return dns;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("EthDevInfo :").append("\n");
        sb.append("dev_name = ").append(this.dev_name).append("\n");
        sb.append("ipaddr = ").append(this.ipaddr).append("\n");
        sb.append("dns = ").append(this.dns).append("\n");
        sb.append("routeAddr = ").append(this.routeAddr).append("\n");
        sb.append("netmask = ").append(this.netmask).append("\n");
        sb.append("mode = ").append(this.mode).append("\n");
        return sb.toString();
    }
}
