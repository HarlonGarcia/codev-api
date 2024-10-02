package com.codev.api.config;

import io.quarkus.jsonb.JsonbConfigCustomizer;

import jakarta.inject.Singleton;
import jakarta.json.bind.JsonbConfig;

@Singleton
public class MyJsonbFormatConfig implements JsonbConfigCustomizer {

    public void customize(JsonbConfig config) {
        config.withNullValues(true);
    }
}
