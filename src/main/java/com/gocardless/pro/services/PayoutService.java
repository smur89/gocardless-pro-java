package com.gocardless.pro.services;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.gocardless.pro.http.*;
import com.gocardless.pro.resources.Payout;

import com.google.common.collect.ImmutableMap;
import com.google.gson.reflect.TypeToken;

public class PayoutService {
    private HttpClient httpClient;

    public PayoutService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public PayoutListRequest list() throws IOException {
        return new PayoutListRequest(httpClient);
    }

    public PayoutGetRequest get(String identity) throws IOException {
        return new PayoutGetRequest(httpClient, identity);
    }

    public static final class PayoutListRequest extends ListRequest<Payout> {
        private String after;

        public PayoutListRequest withAfter(String after) {
            this.after = after;
            return this;
        }

        private String before;

        public PayoutListRequest withBefore(String before) {
            this.before = before;
            return this;
        }

        private String creditor;

        public PayoutListRequest withCreditor(String creditor) {
            this.creditor = creditor;
            return this;
        }

        private String creditorBankAccount;

        public PayoutListRequest withCreditorBankAccount(String creditorBankAccount) {
            this.creditorBankAccount = creditorBankAccount;
            return this;
        }

        private Integer limit;

        public PayoutListRequest withLimit(Integer limit) {
            this.limit = limit;
            return this;
        }

        public enum Status {
            PENDING, PAID,
        }

        private Status status;

        public PayoutListRequest withStatus(Status status) {
            this.status = status;
            return this;
        }

        private PayoutListRequest(HttpClient httpClient) {
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
            if (creditor != null) {
                params.put("creditor", creditor);
            }
            if (creditorBankAccount != null) {
                params.put("creditor_bank_account", creditorBankAccount);
            }
            if (limit != null) {
                params.put("limit", limit);
            }
            if (status != null) {
                params.put("status", status);
            }
            return params.build();
        }

        @Override
        protected String getPathTemplate() {
            return "/payouts";
        }

        @Override
        protected String getEnvelope() {
            return "payouts";
        }

        @Override
        protected TypeToken<List<Payout>> getTypeToken() {
            return new TypeToken<List<Payout>>() {};
        }
    }

    public static final class PayoutGetRequest extends GetRequest<Payout> {
        @PathParam
        private final String identity;

        private PayoutGetRequest(HttpClient httpClient, String identity) {
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
            return "/payouts/:identity";
        }

        @Override
        protected String getEnvelope() {
            return "payouts";
        }

        @Override
        protected Class<Payout> getResponseClass() {
            return Payout.class;
        }
    }
}
