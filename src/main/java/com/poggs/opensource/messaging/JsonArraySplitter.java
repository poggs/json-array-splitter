package com.poggs.opensource.messaging;

import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.apache.camel.Message;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;

import java.util.ArrayList;
import java.util.List;

/**
 * Splits a Camel Exchange with a body containing JSON messages in to individual messages
 *
 * @author pwh
 */
public class JsonArraySplitter {

    @Handler
    public List<String> processMessage(Exchange exchange) {

        List<String> messageList = new ArrayList<String>();

        Message message = exchange.getIn();
        String msg = message.getBody(String.class);

        JSONArray jsonArray = (JSONArray) JSONValue.parse(msg);

        for(int i=0; i<jsonArray.size(); i++) {
            String jsonMsg = jsonArray.get(i).toString();
            messageList.add(jsonMsg);
        }

        return messageList;

    }

}
