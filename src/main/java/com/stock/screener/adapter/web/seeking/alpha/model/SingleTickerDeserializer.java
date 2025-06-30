package com.stock.screener.adapter.web.seeking.alpha.model;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Iterator;

public class SingleTickerDeserializer extends JsonDeserializer<Object> {

    @Override
    public Object deserialize(JsonParser parser, DeserializationContext context) throws IOException {

        if (!(parser.getCodec() instanceof ObjectMapper mapper)) {
            throw new IOException("Expected ObjectMapper as codec for deserialization");
        }
        JsonNode node = mapper.readTree(parser);

        // Get the first (and only) ticker ID
        Iterator<String> fieldNames = node.fieldNames();
        if (!fieldNames.hasNext()) {
            return null;
        }

        String tickerId = fieldNames.next();
        JsonNode tickerData = node.get(tickerId);

        // Determine the target class based on context
        Class<?> targetClass = context.getContextualType().getRawClass();

        return mapper.treeToValue(tickerData, targetClass);
    }
}