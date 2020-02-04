package com.kopirealm.androidethernetadapter.demo;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.kopirealm.androidethernetadapter.EthernetAdapter;

public class MainActivity extends AppCompatActivity {

    View vRoot;
    TextView tvDisplay;
    EthernetAdapter ethernetAdapter;

    final String testEthernetIP = "192.168.1.69";
    final String testEthernetGateway = "192.168.1.1";
    final String testEthernetSubnetMask = "255.255.255.0";
    final String testEthernetDNS = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vRoot = findViewById(R.id.vRoot);
        tvDisplay = findViewById(R.id.tvDisplay);
        ethernetAdapter = new EthernetAdapter(getApplicationContext());
        testEthernetAdapter();
    }

    private void testEthernetAdapter() {
        if (ethernetAdapter == null) return;
        if (ethernetAdapter.isSupported()) {
            if (ethernetAdapter.isEthEnabled()) {
                ethernetAdapter.updateEthDevInfo(false, testEthernetIP, testEthernetGateway, testEthernetSubnetMask, testEthernetDNS);
                tvDisplay.setText(ethernetAdapter.getSavedEthConfig().toString());
            } else {
                displayError("Ethernet is disabled.");
                Snackbar.make(vRoot, "Enable Ethernet", Snackbar.LENGTH_SHORT).setAction("Connect", v -> {
                    if (!ethernetAdapter.isEthEnabled()) ethernetAdapter.setEthEnabled(true);
                }).show();
            }
        } else {
            displayError("Device does not support Ethernet");
        }
    }

    private void displayError(String msg) {
        final String styledText = "<font color='red'>" + msg + "</font>.";
        tvDisplay.setText(Html.fromHtml(styledText), TextView.BufferType.SPANNABLE);
    }
}
