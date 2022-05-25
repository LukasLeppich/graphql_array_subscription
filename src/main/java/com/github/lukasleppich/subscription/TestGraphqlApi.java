package com.github.lukasleppich.subscription;

import java.util.List;

import javax.inject.Inject;

import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Query;

import io.smallrye.graphql.api.Subscription;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.converters.multi.MultiRx3Converters;

@GraphQLApi
public class TestGraphqlApi {

    @Inject
    protected TestService service;

    @Mutation("publish")
    public Uni<List<String>> publish(List<String> items) {
        service.subject.onNext(items);
        return Uni.createFrom().item(items);
    }

    @Query("lastItems")
    public Uni<List<String>> lastItems() {
        return Uni.createFrom().item(service.subject.getValue());
    }

    @Subscription("listSubscription")
    public Multi<List<String>> listSubscription() {
        return Multi.createFrom().converter(MultiRx3Converters.fromObservable(), service.subject);
    }

    @Subscription("arraySubscription")
    public Multi<String[]> arraySubscription() {
        return Multi.createFrom().converter(MultiRx3Converters.fromObservable(),
                service.subject.map(list -> list.toArray(String[]::new)));
    }

    @Subscription("scalarSubscription")
    public Multi<Payload> scalarSubscription() {
        return service.createSubscription();
    }
}
