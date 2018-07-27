package com.azhen.java.util.concurrent;

import java.util.concurrent.ForkJoinTask;

public class ForkJoinTaskTest extends ForkJoinTask<Long> {
    @Override
    public Long getRawResult() {
        return null;
    }

    @Override
    protected void setRawResult(Long value) {

    }

    @Override
    protected boolean exec() {
        return false;
    }
}
