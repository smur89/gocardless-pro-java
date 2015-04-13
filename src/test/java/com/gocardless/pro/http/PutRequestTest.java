package com.gocardless.pro.http;

import org.glassfish.grizzly.http.util.HttpStatus;
import org.junit.Test;

import java.io.IOException;

import static com.gocardless.pro.TestUtil.withJsonBody;
import static com.xebialabs.restito.builder.stub.StubHttp.whenHttp;
import static com.xebialabs.restito.semantics.Action.resourceContent;
import static com.xebialabs.restito.semantics.Action.status;
import static com.xebialabs.restito.semantics.Condition.*;
import static org.assertj.core.api.Assertions.assertThat;

public class PutRequestTest extends HttpRequestTest {
    @Test
    public void shouldPerformPutRequest() throws IOException {
        whenHttp(server).match(put("/dummy"), not(withPostBody())).then(status(HttpStatus.OK_200),
                resourceContent("fixtures/single.json"));
        DummyPutRequest request = new DummyPutRequest();
        DummyItem result = request.execute();
        assertThat(result.stringField).isEqualTo("foo");
        assertThat(result.intField).isEqualTo(123);
    }

    @Test
    public void shouldPerformPutRequestWithBody() throws IOException {
        whenHttp(server).match(put("/dummy"), withJsonBody("fixtures/single.json")).then(
                status(HttpStatus.OK_200), resourceContent("fixtures/single.json"));
        DummyPutRequest request = new DummyPutRequestWithBody();
        DummyItem result = request.execute();
        assertThat(result.stringField).isEqualTo("foo");
        assertThat(result.intField).isEqualTo(123);
    }

    private class DummyPutRequest extends PutRequest<DummyItem> {
        public DummyPutRequest() {
            super(client);
        }

        @Override
        protected String getPathTemplate() {
            return "/dummy";
        }

        @Override
        protected String getEnvelope() {
            return "items";
        }

        @Override
        protected Class<DummyItem> getResponseClass() {
            return DummyItem.class;
        }

        @Override
        protected boolean hasBody() {
            return false;
        }
    }

    private class DummyPutRequestWithBody extends DummyPutRequest {
        private int intField = 123;
        private String stringField = "foo";

        @Override
        protected boolean hasBody() {
            return true;
        }
    }
}
