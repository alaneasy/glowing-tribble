package com.example.site_reservation_system.util.result;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

@Data
@AllArgsConstructor
public class Result {
    public int code;
    public String message;
    public Map<String, Object> data;

    public Result put(String key, Object value) {
        if (this.data == null) {
            this.data = new LinkedHashMap<>();
        }
        this.data.put(key, value);
        return this;
    }
}