package com.github.lukasleppich.subscription;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;

@QuarkusTest
public class GraphqlTest {

    @Test
    public void testPublishSchema() {
        String schema = RestAssured.given().when().contentType("application/json").get("graphql/schema.graphql").body()
                .asString();
        assertTrue(schema.contains("publish(items: [String]): [String]"), "should contain publish with string arrays");
    }

    @Test
    public void testQuerySchema() {
        String schema = RestAssured.given().when().contentType("application/json").get("graphql/schema.graphql").body()
                .asString();
        assertTrue(schema.contains("lastItems: [String]"), "should contain lastItems with string arrays");
    }

    @Test
    public void testSubscriptionSchema_List() {
        String schema = RestAssured.given().when().contentType("application/json").get("graphql/schema.graphql").body()
                .asString();
        assertTrue(schema.contains("listSubscription: [String]"), "should contain listSubscription with string arrays");
    }

    @Test
    public void testSubscriptionSchema_Array() {
        String schema = RestAssured.given().when().contentType("application/json").get("graphql/schema.graphql").body()
                .asString();
        assertTrue(schema.contains("arraySubscription: [String]"), "should contain arraySubscription with string arrays");
    }

}
