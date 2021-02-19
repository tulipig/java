package com.atguigu.hello.bean;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("atguigu.hello")
public class HelloProperties {
    private String prefix;
    private String suffix;

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getSuffix() {
        return suffix;
    }
}
