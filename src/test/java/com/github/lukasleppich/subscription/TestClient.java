package com.github.lukasleppich.subscription;

import io.smallrye.graphql.api.Subscription;
import io.smallrye.graphql.client.typesafe.api.GraphQLClientApi;
import io.smallrye.mutiny.Multi;

@GraphQLClientApi(configKey = "test-client-config")
public interface TestClient {
    @Subscription
    public Multi<String> scalarSubscription();

}
