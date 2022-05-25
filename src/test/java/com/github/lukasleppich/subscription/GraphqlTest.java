package com.github.lukasleppich.subscription;

import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.inject.Inject;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.subscription.Cancellable;

@QuarkusTest
public class GraphqlTest {
    private static final Logger logger = LoggerFactory.getLogger(GraphqlTest.class);

    @Inject
    private TestClient client;

    @Test
    @Disabled
    public void testPublishSchema() {
        String schema = RestAssured.given().when().contentType("application/json").get("graphql/schema.graphql").body()
                .asString();
        assertTrue(schema.contains("publish(items: [String]): [String]"), "should contain publish with string arrays");
    }

    @Test
    @Disabled
    public void testQuerySchema() {
        String schema = RestAssured.given().when().contentType("application/json").get("graphql/schema.graphql").body()
                .asString();
        assertTrue(schema.contains("lastItems: [String]"), "should contain lastItems with string arrays");
    }

    @Test
    @Disabled
    public void testSubscriptionSchema_List() {
        String schema = RestAssured.given().when().contentType("application/json").get("graphql/schema.graphql").body()
                .asString();
        assertTrue(schema.contains("listSubscription: [String]"), "should contain listSubscription with string arrays");
    }

    @Test
    @Disabled
    public void testSubscriptionSchema_Array() {
        String schema = RestAssured.given().when().contentType("application/json").get("graphql/schema.graphql").body()
                .asString();
        assertTrue(schema.contains("arraySubscription: [String]"), "should contain arraySubscription with string arrays");
    }

    @Test
    public void testUnsubscribe() throws Exception {
        Multi<String> sub = client.scalarSubscription();
        Cancellable subscription = sub.subscribe().with(item -> logger.info("ITEM EMITTED => " + item));
        // sub.onCancellation().invoke(logger.info(""))
        Thread.sleep(100);
        subscription.cancel();
        Thread.sleep(100);
    }

}
