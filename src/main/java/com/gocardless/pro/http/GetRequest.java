package com.gocardless.pro.http;

import java.io.Reader;

public abstract class GetRequest<T> extends HttpRequest<T> {
    public GetRequest(HttpClient httpClient) {
        super(httpClient);
    }

    @Override
    protected T parseResponse(Reader stream, ResponseParser responseParser) {
        return responseParser.parseSingle(stream, getEnvelope(), getResponseClass());
    }

    @Override
    protected final String getMethod() {
        return "GET";
    }

    @Override
    protected final boolean hasBody() {
        return false;
    }

    protected abstract Class<T> getResponseClass();
}
