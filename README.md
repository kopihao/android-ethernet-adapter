Android Ethernet Adapter
============

![Logo](https://raw.githubusercontent.com/kopihao/android-ethernet-adapter/phase1/photoshop/android-ethernet-adapter-logo.png)

# What's good?
#### Convenient way to access EthernetManager that is a hidden class of Native Android API without private ASOP android.jar.

#### Java reflections behind the scene.

### Minimal methods to get things work

* Provides various EthernetManager.class methods:
    1) boolean isSupported();
    2) boolean isEthEnabled();
    3) void setEthEnabled(boolean enable);
    4) void updateEthDevInfo(boolean dhcp, String ip, String gateway, String subnetmask, String dns);
    5) EthernetConfig getSavedEthConfig(); 

* EthernetConfig value:
    1) `dev_name`
    2) `ipaddr`
    3) `netmask`
    4) `routeAddr`
    5) `dns`
    6) `mode`

* Get your new instance simply by:
```java
EthernetAdapter ethernetAdapter = new EthernetAdapter(getApplicationContext()); 
```

Demostration
--------  
##### __Please checkout [source code][3]__

Add to your Android project
--------
<a href="https://search.maven.org/#search%7Cga%7C1%7Cg%3A%22com.github.kopihao%22%20AND%20a%3A%22peasy-recyclerview%22"><img src="https://img.shields.io/maven-central/v/com.github.kopihao/peasy-recyclerview.svg"></a>

```gradle
dependencies {
  implementation 'com.github.kopihao:android-ethernet-adapter:1.0.1'
} 
```

More Information
-------- 
##### For documentation and additional information please visit [official website][1]. 
##### For enquiries and solutions please visit [stackoverflow][2].

License
-------

Licensed under the GNU General Public License v3.0 (the "License");
Copyright 2013 Kopihao.MY

 [1]: https://github.com/kopihao/android-ethernet-adapter/
 [2]: https://stackoverflow.com/questions/tagged/android-ethernet-adapter?sort=frequent
 [3]: https://github.com/kopihao/android-ethernet-adapter/blob/phase1/demo/src/main/java/com/kopirealm/androidethernetadapter/demo/MainActivity.java
