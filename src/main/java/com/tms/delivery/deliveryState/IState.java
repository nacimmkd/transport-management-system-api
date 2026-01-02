package com.tms.delivery.deliveryState;

public interface IState {
    void assignToDriver();
    void transit();
    void arrive();
    void confirm();
    void cancel();
    void fail();
}
