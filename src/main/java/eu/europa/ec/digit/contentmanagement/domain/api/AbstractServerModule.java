package eu.europa.ec.digit.contentmanagement.domain.api;

import java.util.concurrent.atomic.AtomicBoolean;

public abstract class AbstractServerModule implements ServerModule_i {

    private AtomicBoolean hasBeenInited = new AtomicBoolean(false);
    private AtomicBoolean hasBeenClosed = new AtomicBoolean(false);

    @Override
    public final void init() throws Exception {
        if (hasBeenClosed.get())
            return;

        if (!hasBeenInited.get()) {
            synchronized (hasBeenInited) {
                if (!hasBeenInited.get()) {
                    initSub();
                    hasBeenInited.set(true);
                }
            }
        }
    }



    protected abstract void initSub() throws Exception;



    @Override
    public void close() throws Exception {
        if (!hasBeenClosed.get()) {
            synchronized (hasBeenClosed) {
                if (!hasBeenClosed.get()) {
                    closeSub();
                    hasBeenClosed.set(true);
                }
            }
        }
    }



    protected abstract void closeSub() throws Exception;

}
