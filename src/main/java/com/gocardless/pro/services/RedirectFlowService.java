package com.gocardless.pro.services;

import java.util.Map;

import com.gocardless.pro.http.*;
import com.gocardless.pro.resources.RedirectFlow;

import com.google.common.collect.ImmutableMap;

/**
 * Service class for working with redirect flow resources.
 *
 * Redirect flows enable you to use GoCardless Pro's secure payment pages to set up mandates with
 * your customers.
 * 
 * The overall flow is:
 * 
 * 1. You
 * [create](https://developer.gocardless.com/pro/#create-a-redirect-flow) a redirect flow for your
 * customer, and redirect them to the returned redirect url, e.g.
 * `https://pay.gocardless.com/flow/RE123`.
 * 
 * 2. Your customer supplies their name, email,
 * address, and bank account details, and submits the form. This securely stores their details, and
 * redirects them back to your `success_redirect_url` with `redirect_flow_id=RE123` in the
 * querystring.
 * 
 * 3. You
 * [complete](https://developer.gocardless.com/pro/#complete-a-redirect-flow) the redirect flow,
 * which creates a [customer](https://developer.gocardless.com/pro/#api-endpoints-customers),
 * [customer bank
 * account](https://developer.gocardless.com/pro/#api-endpoints-customer-bank-accounts), and
 * [mandate](https://developer.gocardless.com/pro/#api-endpoints-mandates), and returns the ID of the
 * mandate. You may wish to create a
 * [subscription](https://developer.gocardless.com/pro/#api-endpoints-subscriptions) or
 * [payment](https://developer.gocardless.com/pro/#api-endpoints-payments) at this point.
 * 
 * It is
 * recommended that you link the redirect flow to your user object as soon as it is created, and
 * attach the created resources to that user in the complete step.
 * 
 * Redirect flows expire 30
 * minutes after they are first created. You cannot
 * [complete](https://developer.gocardless.com/pro/#complete-a-redirect-flow) an expired redirect
 * flow.
 * 
 * [View an example integration](https://pay-sandbox.gocardless.com/AL000000AKFPFF) that
 * uses redirect flows.
 */
public class RedirectFlowService {
    private HttpClient httpClient;

    /**
     * Constructor.  Users of this library should have no need to call this - an instance
     * of this class can be obtained by calling
      {@link com.gocardless.pro.GoCardlessClient#redirectFlows() }.
     */
    public RedirectFlowService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * Creates a redirect flow object which can then be used to redirect your customer to the GoCardless
     * Pro hosted payment pages.
     */
    public RedirectFlowCreateRequest create() {
        return new RedirectFlowCreateRequest(httpClient);
    }

    /**
     * Returns all details about a single redirect flow
     */
    public RedirectFlowGetRequest get(String identity) {
        return new RedirectFlowGetRequest(httpClient, identity);
    }

    /**
     * This creates a [customer](https://developer.gocardless.com/pro/#api-endpoints-customers),
     * [customer bank
     * account](https://developer.gocardless.com/pro/#api-endpoints-customer-bank-account), and
     * [mandate](https://developer.gocardless.com/pro/#api-endpoints-mandates) using the details supplied
     * by your customer and returns the ID of the created mandate.
     * 
     * This will return a
     * `redirect_flow_incomplete` error if your customer has not yet been redirected back to your site,
     * and a `redirect_flow_already_completed` error if your integration has already completed this flow.
     * It will return a `bad_request` error if the `session_token` differs to the one supplied when the
     * redirect flow was created.
     */
    public RedirectFlowCompleteRequest complete(String identity) {
        return new RedirectFlowCompleteRequest(httpClient, identity);
    }

    /**
     * Request class for {@link RedirectFlowService#create }.
     *
     * Creates a redirect flow object which can then be used to redirect your customer to the GoCardless
     * Pro hosted payment pages.
     */
    public static final class RedirectFlowCreateRequest extends PostRequest<RedirectFlow> {
        private String description;
        private Links links;
        private Scheme scheme;
        private String sessionToken;
        private String successRedirectUrl;

        /**
         * A description of the item the customer is paying for
         */
        public RedirectFlowCreateRequest withDescription(String description) {
            this.description = description;
            return this;
        }

        public RedirectFlowCreateRequest withLinks(Links links) {
            this.links = links;
            return this;
        }

        /**
         * The [creditor](https://developer.gocardless.com/pro/#api-endpoints-creditors) for whom the mandate
         * will be created. The `name` of the creditor will be displayed on the payment page.
         */
        public RedirectFlowCreateRequest withLinksCreditor(String creditor) {
            if (links == null) {
                links = new Links();
            }
            links.withCreditor(creditor);
            return this;
        }

        /**
         * The Direct Debit scheme of the mandate. If specified, the payment pages will only allow the set-up
         * of a mandate for the specified scheme.
         */
        public RedirectFlowCreateRequest withScheme(Scheme scheme) {
            this.scheme = scheme;
            return this;
        }

        /**
         * The customer's session ID
         */
        public RedirectFlowCreateRequest withSessionToken(String sessionToken) {
            this.sessionToken = sessionToken;
            return this;
        }

        /**
         * The URI to redirect to upon success mandate setup
         */
        public RedirectFlowCreateRequest withSuccessRedirectUrl(String successRedirectUrl) {
            this.successRedirectUrl = successRedirectUrl;
            return this;
        }

        private RedirectFlowCreateRequest(HttpClient httpClient) {
            super(httpClient);
        }

        @Override
        protected String getPathTemplate() {
            return "/redirect_flows";
        }

        @Override
        protected String getEnvelope() {
            return "redirect_flows";
        }

        @Override
        protected Class<RedirectFlow> getResponseClass() {
            return RedirectFlow.class;
        }

        @Override
        protected boolean hasBody() {
            return true;
        }

        public enum Scheme {
            BACS, SEPA_CORE;
            @Override
            public String toString() {
                return name().toLowerCase();
            }
        }

        public static class Links {
            private String creditor;

            /**
             * The [creditor](https://developer.gocardless.com/pro/#api-endpoints-creditors) for whom the mandate
             * will be created. The `name` of the creditor will be displayed on the payment page.
             */
            public Links withCreditor(String creditor) {
                this.creditor = creditor;
                return this;
            }
        }
    }

    /**
     * Request class for {@link RedirectFlowService#get }.
     *
     * Returns all details about a single redirect flow
     */
    public static final class RedirectFlowGetRequest extends GetRequest<RedirectFlow> {
        @PathParam
        private final String identity;

        private RedirectFlowGetRequest(HttpClient httpClient, String identity) {
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
            return "/redirect_flows/:identity";
        }

        @Override
        protected String getEnvelope() {
            return "redirect_flows";
        }

        @Override
        protected Class<RedirectFlow> getResponseClass() {
            return RedirectFlow.class;
        }
    }

    /**
     * Request class for {@link RedirectFlowService#complete }.
     *
     * This creates a [customer](https://developer.gocardless.com/pro/#api-endpoints-customers),
     * [customer bank
     * account](https://developer.gocardless.com/pro/#api-endpoints-customer-bank-account), and
     * [mandate](https://developer.gocardless.com/pro/#api-endpoints-mandates) using the details supplied
     * by your customer and returns the ID of the created mandate.
     * 
     * This will return a
     * `redirect_flow_incomplete` error if your customer has not yet been redirected back to your site,
     * and a `redirect_flow_already_completed` error if your integration has already completed this flow.
     * It will return a `bad_request` error if the `session_token` differs to the one supplied when the
     * redirect flow was created.
     */
    public static final class RedirectFlowCompleteRequest extends PostRequest<RedirectFlow> {
        @PathParam
        private final String identity;
        private String sessionToken;

        /**
         * The customer's session ID
         */
        public RedirectFlowCompleteRequest withSessionToken(String sessionToken) {
            this.sessionToken = sessionToken;
            return this;
        }

        private RedirectFlowCompleteRequest(HttpClient httpClient, String identity) {
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
            return "/redirect_flows/:identity/actions/complete";
        }

        @Override
        protected String getEnvelope() {
            return "redirect_flows";
        }

        @Override
        protected Class<RedirectFlow> getResponseClass() {
            return RedirectFlow.class;
        }

        @Override
        protected boolean hasBody() {
            return true;
        }
    }
}
