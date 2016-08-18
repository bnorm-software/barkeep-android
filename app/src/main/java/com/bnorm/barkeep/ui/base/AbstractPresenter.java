package com.bnorm.barkeep.ui.base;

import rx.Observable;
import rx.Scheduler;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Action2;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;

public abstract class AbstractPresenter<V> implements Presenter<V> {

    private final Scheduler uiScheduler;
    private final Subject<Action1<? super V>, Action1<? super V>> queue;

    protected V view;
    private Observable<Action1<? super V>> queueOut;
    private Subscription subscribe;

    protected AbstractPresenter(Scheduler uiScheduler) {
        this.uiScheduler = uiScheduler;
        this.queue = PublishSubject.create();
        this.queueOut = queue;
    }

    @Override
    public void attach(V view) {
        this.view = view;
        subscribe = queueOut.observeOn(uiScheduler).subscribe(next -> next.call(view));
    }

    @Override
    public void detach() {
        queueOut = queue.replay().autoConnect(0);
        if (subscribe != null) {
            subscribe.unsubscribe();
            subscribe = null;
        }
        view = null;
    }

    protected <T> Action1<T> enqueue(Action2<? super V, T> action) {
        return t -> enqueue(v -> action.call(v, t));
    }

    protected void enqueue(Action1<? super V> action) {
        queue.onNext(action);
    }
}
