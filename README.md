# JSON Array Splitter

This Camel Splitter will split an Exchange containing an array of JSON messages in to individual messages.

## Usage

### Spring XML

    <route id="testRoute">
      <from uri="queue:input"/>
      <split>
        <method>com.poggs.opensource.messaging.JsonArraySplitter</method>
        <to uri="queue:output">
      </split>
    </route>
