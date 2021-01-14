package com.just.dstron.entity;

import lombok.Data;

@Data
public class TronAccountBO {
    private Integer code;
    private String address;
    private String key;

    public TronAccountBO() {
    }

    public TronAccountBO(String address, String key) {
        this.address = address;
        this.key = key;
    }
}
