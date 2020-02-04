package com.kopirealm.androidethernetadapter;

import android.annotation.SuppressLint;
import android.content.Context;

import com.kopirealm.reflection.ReflectionFunctions;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;

public class EthernetFunctions extends ReflectionFunctions implements IEthernetFunctions {

    private static final String ClassName_EthernetManager = "android.net.ethernet.EthernetManager";
    private static final String ClassName_EthernetDevInfo = "android.net.ethernet.EthernetDevInfo";

    private boolean isEnabled;
    private Object IEthernetManager;
    private Object IEthernetDevInfo;
    private Method MSetEthEnabled;
    private Method MSetIfName;
    private Method MSetConnectMode;
    private Method MSetIpAddress;
    private Method MSetNetMask;
    private Method MSetRouteAddr;
    private Method MSetDnsAddr;
    private Method MUpdateEthDevInfo;
    private Method MGetSavedEthConfig;
    private Method MGetIfName;
    private Method MGetConnectMode;
    private Method MGetIpAddress;
    private Method MGetNetMask;
    private Method MGetRouteAddr;
    private Method MGetDnsAddr;

    @SuppressLint({"WrongConstant", "PrivateApi"})
    EthernetFunctions(Context context) {
        super(context);
        try {
            final Class<?> classEthernetManager = getClass(ClassName_EthernetManager);
            this.IEthernetManager = getSystemServiceInstance("ethernet");
            try {
                this.MSetEthEnabled = getMethod(classEthernetManager, "setEthEnabled", BOOLEAN);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                this.MUpdateEthDevInfo = getMethod(classEthernetManager, "updateEthDevInfo", Class.forName(ClassName_EthernetDevInfo));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                this.MGetSavedEthConfig = getMethod(classEthernetManager, "getSavedEthConfig");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            final Class<?> classEthernetDevInfo = getClass(ClassName_EthernetDevInfo);
            this.IEthernetDevInfo = getNewInstance(classEthernetDevInfo);
            try {
                this.MSetIfName = getMethod(classEthernetDevInfo, "setIfName", STRING);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                this.MSetConnectMode = getMethod(classEthernetDevInfo, "setConnectMode", STRING);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                this.MSetIpAddress = getMethod(classEthernetDevInfo, "setIpAddress", STRING);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                this.MSetNetMask = getMethod(classEthernetDevInfo, "setNetMask", STRING);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                this.MSetRouteAddr = getMethod(classEthernetDevInfo, "setRouteAddr", STRING);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                this.MSetDnsAddr = getMethod(classEthernetDevInfo, "setDnsAddr", STRING);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                this.MGetIfName = getMethod(classEthernetDevInfo, "getIfName");
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                this.MGetConnectMode = getMethod(classEthernetDevInfo, "getConnectMode");
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                this.MGetIpAddress = getMethod(classEthernetDevInfo, "getIpAddress");
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                this.MGetNetMask = getMethod(classEthernetDevInfo, "getNetMask");
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                this.MGetRouteAddr = getMethod(classEthernetDevInfo, "getRouteAddr");
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                this.MGetDnsAddr = getMethod(classEthernetDevInfo, "getDnsAddr");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<String> getNetworkInterfaces() {
        final List<String> list = new ArrayList<>();
        try {
            final Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
            for (NetworkInterface netint : Collections.list(nets)) {
                list.add(netint.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean isSupported() {
        List<String> list = getNetworkInterfaces();
        return list.contains(ETH_NETWORK_INTERFACE);
    }

    @Override
    public boolean isEthEnabled() {
        final Process p = execRuntimeCommand("netcfg");
        if (p != null) {
            try {
                final BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String line;
                while ((line = input.readLine()) != null) {
                    if (line.contains(ETH_NETWORK_INTERFACE)) {
                        isEnabled = line.contains("UP");
                        break;
                    }
                }
                input.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return isEnabled;
    }

    @Override
    public void setEthEnabled(boolean enabled) {
        if (!isSupported()) return;
        boolean isExecuted = false;
        try {
            invokeMethod(MSetEthEnabled, IEthernetManager, (enabled) ? TRUE : FALSE);
            isExecuted = true;
        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                execRuntimeSUCommand(enabled ? "ifconfig eth0 up" : "ifconfig eth0 down");
                isExecuted = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (isExecuted) isEnabled = enabled;
    }

    @Override
    public EthernetConfig getSavedEthConfig() {
        if (!isSupported()) return null;
        EthernetConfig config = new EthernetConfig();
        try {
            final Object objEthernetDevInfo = invokeMethod(MGetSavedEthConfig, IEthernetManager);
            if (objEthernetDevInfo == null) return null;
            Object objVal;
            try {
                objVal = invokeMethod(MGetIfName, objEthernetDevInfo);
                if (objVal instanceof String) config.dev_name = objVal.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                objVal = invokeMethod(MGetConnectMode, objEthernetDevInfo);
                if (objVal instanceof String) config.mode = objVal.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                objVal = invokeMethod(MGetIpAddress, objEthernetDevInfo);
                if (objVal instanceof String) config.ipaddr = objVal.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                objVal = invokeMethod(MGetNetMask, objEthernetDevInfo);
                if (objVal instanceof String) config.netmask = objVal.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                objVal = invokeMethod(MGetRouteAddr, objEthernetDevInfo);
                if (objVal instanceof String) config.routeAddr = objVal.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                objVal = invokeMethod(MGetDnsAddr, objEthernetDevInfo);
                if (objVal instanceof String) config.dns = objVal.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return config;
    }

    @Override
    public void updateEthDevInfo(boolean dhcp, String ip, String gateway, String subnetmask, String dns) {
        try {
            invokeMethod(MSetIfName, IEthernetDevInfo, ETH_NETWORK_INTERFACE);
            if (dhcp) {
                invokeMethod(MSetConnectMode, IEthernetDevInfo, ETH_CONN_MODE_DHCP);
            } else {
                try {
                    invokeMethod(MSetConnectMode, IEthernetDevInfo, ETH_CONN_MODE_MANUAL);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    invokeMethod(MSetIpAddress, IEthernetDevInfo, ip);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    invokeMethod(MSetNetMask, IEthernetDevInfo, subnetmask);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    invokeMethod(MSetRouteAddr, IEthernetDevInfo, gateway);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    invokeMethod(MSetDnsAddr, IEthernetDevInfo, dns);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    invokeMethod(MUpdateEthDevInfo, IEthernetManager, IEthernetDevInfo);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                if (dhcp) {
                    execRuntimeCommand("ifconfig eth0 dhcp start");
                } else {
                    execRuntimeSUCommand(String.format(Locale.ENGLISH, "ifconfig eth0 %s netmask %s routeAddr %s", ip, subnetmask, gateway));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
