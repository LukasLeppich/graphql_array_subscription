package com.github.lukasleppich.subscription;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.enterprise.context.ApplicationScoped;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.Subject;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.converters.multi.MultiRx3Converters;

@ApplicationScoped
public class TestService {
    public BehaviorSubject<List<String>> subject = BehaviorSubject.createDefault(List.of());
    public Subject<String> sub = PublishSubject.create();

    public Set<String> subscriptions = new HashSet<>();

    public Multi<Payload> createSubscription() {
        final String subID = UUID.randomUUID().toString();

        Multi<Payload> multi = Multi.createFrom()
                .converter(MultiRx3Converters.fromObservable(), Observable.interval(1000, TimeUnit.MILLISECONDS)//
                        .map(t -> Long.toString(t))//
                        .take(10).doOnNext((t) -> System.out.println("NEXT ITEM " + subID + " => " + t)))
                .map(v -> new Payload(v));
        multi = multi.onTermination().invoke(() -> {
            subscriptions.remove(subID);
            System.out.println("TERMINATION " + subID);
        });
        multi = multi.onSubscription().invoke(() -> {
            subscriptions.add(subID);
            System.out.println("SUBSCRIPTION " + subID);
        });
        multi = multi.onItem().invoke(() -> System.out.println("ITEM " + subID));

        return multi;
    }
}
