package subway;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 인수 테스트에 공통적으로 사용되는 기능을 정의한다.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AcceptanceTest extends ParentTest{

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }
    @Autowired
    protected EntityManager entityManager;

    /**
     * 생성 요청에 대한 응답을 확인한다.
     * @param response 생성 요청에 대한 응답
     */
    protected void 생성_성공_확인(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    /**
     * query parameter 를 정의한다.
     */
    protected class ParamBuilder {
        private final Map<String, String> params;

        protected ParamBuilder() {
            this.params = new HashMap<>();
        }

        /**
         * query parameter 를 추가한다.
         * @param key query parameter 의 key
         * @param value query parameter 의 value
         * @return query parameter 가 추가된 결과를 뱉는다.
         */
        public ParamBuilder add(String key, String value) {
            params.put(key, value);
            return this;
        }

        public ParamBuilder add(String key, int value) {
            return add(key, String.valueOf(value));
        }

        public ParamBuilder add(String key, long value) {
            return add(key, String.valueOf(value));
        }

        public ParamBuilder add(String key, char value) {
            return add(key, String.valueOf(value));
        }

        public ParamBuilder add(String key, double value) {
            return add(key, String.valueOf(value));
        }

        public ParamBuilder add(String key, float value) {
            return add(key, String.valueOf(value));
        }

        public ParamBuilder add(String key, boolean value) {
            return add(key, String.valueOf(value));
        }

        /**
         * query parameter 를 빌드한다.
         * @return query parameter
         */
        public Map<String, String> build() {
            return params;
        }

    }
}
