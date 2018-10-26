package com.github.onlynight.chatim.clientdemo.config;

import com.github.onlynight.chatim.server.config.BaseConfigReader;

import java.io.InputStream;

public class ConfigReader extends BaseConfigReader {

    public ConfigReader(InputStream configFile) {
        super(configFile);
    }

}
