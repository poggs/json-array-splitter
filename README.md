JSON Array Splitter
===================

This Camel Splitter will split an Exchange containing an array of JSON messages in to individual messages.

Use this in Spring XML in the following way:

  <route id="testRoute">
    <from uri="queue:input"/>
    <split>
     <method>com.poggs.opensource.messaging.JsonArraySplitter</method>
     <to uri="queue:output">
    </split>
  </route>
