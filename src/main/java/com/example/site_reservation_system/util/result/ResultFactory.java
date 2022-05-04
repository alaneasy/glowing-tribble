package com.example.site_reservation_system.util.result;

public class ResultFactory {
    static public Result buildSuccessResult(String message) {
        return new Result(1, message, null);
    }

    static public Result buildSuccessResult() {
        return new Result(1, null, null);
    }

    static public Result buildErrorResult(String message) {
        return new Result(0, message, null);
    }
}