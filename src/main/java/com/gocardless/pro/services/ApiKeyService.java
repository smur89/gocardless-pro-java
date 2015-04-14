package com.gocardless.pro.services;

import java.util.List;
import java.util.Map;

import com.gocardless.pro.http.*;
import com.gocardless.pro.resources.ApiKey;

import com.google.common.collect.ImmutableMap;
import com.google.gson.reflect.TypeToken;

public class ApiKeyService {
    private HttpClient httpClient;

    public ApiKeyService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public ApiKeyCreateRequest create() {
        return new ApiKeyCreateRequest(httpClient);
    }

    public ApiKeyListRequest list() {
        return new ApiKeyListRequest(httpClient);
    }

    public ApiKeyGetRequest get(String identity) {
        return new ApiKeyGetRequest(httpClient, identity);
    }

    public ApiKeyUpdateRequest update(String identity) {
        return new ApiKeyUpdateRequest(httpClient, identity);
    }

    public ApiKeyDisableRequest disable(String identity) {
        return new ApiKeyDisableRequest(httpClient, identity);
    }

    public static final class ApiKeyCreateRequest extends PostRequest<ApiKey> {
        private Object links;

        public ApiKeyCreateRequest withLinks(Object links) {
            this.links = links;
            return this;
        }

        private String name;

        public ApiKeyCreateRequest withName(String name) {
            this.name = name;
            return this;
        }

        private String webhookUrl;

        public ApiKeyCreateRequest withWebhookUrl(String webhookUrl) {
            this.webhookUrl = webhookUrl;
            return this;
        }

        private ApiKeyCreateRequest(HttpClient httpClient) {
            super(httpClient);
        }

        @Override
        protected String getPathTemplate() {
            return "/api_keys";
        }

        @Override
        protected String getEnvelope() {
            return "api_keys";
        }

        @Override
        protected Class<ApiKey> getResponseClass() {
            return ApiKey.class;
        }

        @Override
        protected boolean hasBody() {
            return true;
        }
    }

    public static final class ApiKeyListRequest extends ListRequest<ApiKey> {
        private String after;

        public ApiKeyListRequest withAfter(String after) {
            this.after = after;
            return this;
        }

        private String before;

        public ApiKeyListRequest withBefore(String before) {
            this.before = before;
            return this;
        }

        public enum Enabled {
            TRUE, FALSE,
        }

        private Enabled enabled;

        public ApiKeyListRequest withEnabled(Enabled enabled) {
            this.enabled = enabled;
            return this;
        }

        private Integer limit;

        public ApiKeyListRequest withLimit(Integer limit) {
            this.limit = limit;
            return this;
        }

        private String role;

        public ApiKeyListRequest withRole(String role) {
            this.role = role;
            return this;
        }

        private ApiKeyListRequest(HttpClient httpClient) {
            super(httpClient);
        }

        @Override
        protected Map<String, Object> getQueryParams() {
            ImmutableMap.Builder<String, Object> params = ImmutableMap.builder();
            if (after != null) {
                params.put("after", after);
            }
            if (before != null) {
                params.put("before", before);
            }
            if (enabled != null) {
                params.put("enabled", enabled);
            }
            if (limit != null) {
                params.put("limit", limit);
            }
            if (role != null) {
                params.put("role", role);
            }
            return params.build();
        }

        @Override
        protected String getPathTemplate() {
            return "/api_keys";
        }

        @Override
        protected String getEnvelope() {
            return "api_keys";
        }

        @Override
        protected TypeToken<List<ApiKey>> getTypeToken() {
            return new TypeToken<List<ApiKey>>() {};
        }
    }

    public static final class ApiKeyGetRequest extends GetRequest<ApiKey> {
        @PathParam
        private final String identity;

        private ApiKeyGetRequest(HttpClient httpClient, String identity) {
            super(httpClient);
            this.identity = identity;
        }

        @Override
        protected Map<String, String> getPathParams() {
            ImmutableMap.Builder<String, String> params = ImmutableMap.builder();
            params.put("identity", identity);
            return params.build();
        }

        @Override
        protected String getPathTemplate() {
            return "/api_keys/:identity";
        }

        @Override
        protected String getEnvelope() {
            return "api_keys";
        }

        @Override
        protected Class<ApiKey> getResponseClass() {
            return ApiKey.class;
        }
    }

    public static final class ApiKeyUpdateRequest extends PutRequest<ApiKey> {
        @PathParam
        private final String identity;
        private String name;

        public ApiKeyUpdateRequest withName(String name) {
            this.name = name;
            return this;
        }

        private String webhookUrl;

        public ApiKeyUpdateRequest withWebhookUrl(String webhookUrl) {
            this.webhookUrl = webhookUrl;
            return this;
        }

        private ApiKeyUpdateRequest(HttpClient httpClient, String identity) {
            super(httpClient);
            this.identity = identity;
        }

        @Override
        protected Map<String, String> getPathParams() {
            ImmutableMap.Builder<String, String> params = ImmutableMap.builder();
            params.put("identity", identity);
            return params.build();
        }

        @Override
        protected String getPathTemplate() {
            return "/api_keys/:identity";
        }

        @Override
        protected String getEnvelope() {
            return "api_keys";
        }

        @Override
        protected Class<ApiKey> getResponseClass() {
            return ApiKey.class;
        }

        @Override
        protected boolean hasBody() {
            return true;
        }
    }

    public static final class ApiKeyDisableRequest extends PostRequest<ApiKey> {
        @PathParam
        private final String identity;

        private ApiKeyDisableRequest(HttpClient httpClient, String identity) {
            super(httpClient);
            this.identity = identity;
        }

        @Override
        protected Map<String, String> getPathParams() {
            ImmutableMap.Builder<String, String> params = ImmutableMap.builder();
            params.put("identity", identity);
            return params.build();
        }

        @Override
        protected String getPathTemplate() {
            return "/api_keys/:identity/actions/disable";
        }

        @Override
        protected String getEnvelope() {
            return "api_keys";
        }

        @Override
        protected Class<ApiKey> getResponseClass() {
            return ApiKey.class;
        }

        @Override
        protected boolean hasBody() {
            return false;
        }
    }
}
