package com.bnorm.barkeep.ui.base;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

public abstract class AbstractPresenter<V> implements Presenter<V> {

    private final Scheduler uiScheduler;
    private final Subject<Consumer<? super V>> queue;

    protected V view;
    private Observable<Consumer<? super V>> queueOut;
    private Disposable subscribe;

    protected AbstractPresenter(Scheduler uiScheduler) {
        this.uiScheduler = uiScheduler;
        this.queue = PublishSubject.create();
        this.queueOut = queue;
    }

    @Override
    public void attach(V view) {
        this.view = view;
        subscribe = queueOut.observeOn(uiScheduler).subscribe(next -> next.accept(view));
    }

    @Override
    public void detach() {
        queueOut = queue.replay().autoConnect(0);
        if (subscribe != null) {
            subscribe.dispose();
            subscribe = null;
        }
        view = null;
    }

    protected <T> Consumer<T> enqueue(BiConsumer<? super V, T> action) {
        return t -> enqueue(v -> action.accept(v, t));
    }

    protected void enqueue(Consumer<? super V> action) {
        queue.onNext(action);
    }
}
