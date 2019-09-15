package com.poggs.opensource.messaging.test;

import com.poggs.opensource.messaging.JsonArraySplitter;
import org.apache.camel.*;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Tests for the JsonArraySplitter
 *
 * @author pwh
 */
public class JsonArraySplitterTest extends CamelTestSupport {

    @EndpointInject(uri = "mock:result")
    protected MockEndpoint resultEndpoint;

    @Produce(uri = "direct:start")
    protected ProducerTemplate producerTemplate;

    private static final ENDPOINT ="direct:start";

    @Test
    public void testSendEmptyMessage() {

        producerTemplate.send(ENDPOINT, new Processor() {

            static final String inputData = "[]";

            public void process(Exchange exchange) {
                Message in = exchange.getIn();
                in.setBody(inputData);
            }

        });

        assertMockEndpointsSatisfied();

        List<Exchange> list = resultEndpoint.getReceivedExchanges();
        assertEquals(0, list.size());

    }

    @Test
    public void sendArrayWithSingleMessage() {

        producerTemplate.send(ENDPOINT, new Processor() {

            static final String inputData = "[\"foo\"]";

            public void process(Exchange exchange) {
                Message in = exchange.getIn();
                in.setBody(inputData);
            }

        });

        assertMockEndpointsSatisfied();

        List<Exchange> list = resultEndpoint.getReceivedExchanges();
        assertEquals(1, list.size());

        ArrayList messages = new ArrayList();

        for (int i = 0; i < 1; i++) {
            Exchange exchange = list.get(i);
            Message in = exchange.getIn();
            messages.add(in.getBody().toString());
        }

        ArrayList expectedMessages = new ArrayList();
        expectedMessages.add("foo");

        assertEquals(expectedMessages, messages);

    }

    @Test
    public void sendArrayWithMultipleMessages() {

        producerTemplate.send(ENDPOINT, new Processor() {

            static final String inputData = "[\"foo\", \"bar\"]";

            public void process(Exchange exchange) {
                Message in = exchange.getIn();
                in.setBody(inputData);
            }

        });

        assertMockEndpointsSatisfied();

        List<Exchange> list = resultEndpoint.getReceivedExchanges();
        assertEquals(2, list.size());

        ArrayList messages = new ArrayList();

        for (int i = 0; i < 2; i++) {
            Exchange exchange = list.get(i);
            Message in = exchange.getIn();
            messages.add(in.getBody().toString());
        }

        ArrayList expectedMessages = new ArrayList();
        expectedMessages.add("foo");
        expectedMessages.add("bar");

        assertEquals(expectedMessages, messages);

    }

    @Override
    protected RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {
            public void configure() {
                from(ENDPOINT).split(method(JsonArraySplitter.class)).to("mock:result");
            }
        };
    }

}