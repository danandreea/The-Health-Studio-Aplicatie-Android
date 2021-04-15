package com.example.proiect.asyncTask;

public class HandlerMessage<R> implements Runnable{

    private final R result;
    private final Callback<R> mainThreadOperation;

    public HandlerMessage(Callback<R> mainThreadOperation, R result) {
        this.mainThreadOperation = mainThreadOperation;
        this.result = result;
    }

    @Override
    public void run() {
        mainThreadOperation.runResultOnUiThread(result);
    }
}
