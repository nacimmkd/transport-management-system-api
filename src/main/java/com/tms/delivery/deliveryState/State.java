package com.tms.delivery.deliveryState;

import com.tms.delivery.Delivery;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class State implements IState {

    protected Delivery delivery;

    @Override
    public void assignToDriver() {
        throw new InvalidStateTransitionException();
    }

    @Override
    public void transit() {
        throw new InvalidStateTransitionException();
    }

    @Override
    public void arrive() {
        throw new InvalidStateTransitionException();
    }

    @Override
    public void confirm() {
        throw new InvalidStateTransitionException();
    }

    @Override
    public void cancel() {
        throw new InvalidStateTransitionException();
    }

    @Override
    public void fail() {
        throw new InvalidStateTransitionException();
    }

}
