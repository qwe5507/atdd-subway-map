package nextstep.subway.acceptance.station;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.Arrays;
import java.util.List;
import nextstep.subway.acceptance.AcceptanceTest;
import nextstep.subway.common.exception.ColumnName;
import nextstep.subway.common.exception.ErrorMessage;
import nextstep.subway.utils.AcceptanceTestThen;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

@DisplayName("지하철역 관리 기능")
class StationAcceptanceTest extends AcceptanceTest {

    @Autowired
    private StationStep stationStep;

    /**
     * When 지하철역 생성을 요청 하면
     * Then 지하철역 생성이 성공한다.
     */
    @DisplayName("지하철역 생성")
    @Test
    void createStation() {
        // given, when
        ExtractableResponse<Response> response =
            stationStep.지하철역_생성_요청(StationStep.DUMMY_STATION_NAME);

        // then
        AcceptanceTestThen.fromWhen(response)
                          .equalsHttpStatus(HttpStatus.CREATED)
                          .existsLocation();
    }

    /**
     * Given 지하철역 생성을 요청 하고
     * When 같은 이름으로 지하철역 생성을 요청 하면
     * Then 지하철역 생성이 실패한다.
     */
    @DisplayName("지하철역 생성 실패 - 중복 이름")
    @Test
    void createStationThatFailing() {
        // given
        stationStep.지하철역_생성_요청(StationStep.DUMMY_STATION_NAME);

        // when
        ExtractableResponse<Response> response =
            stationStep.지하철역_생성_요청(StationStep.DUMMY_STATION_NAME);

        // then
        AcceptanceTestThen.fromWhen(response)
                          .equalsHttpStatus(HttpStatus.CONFLICT)
                          .equalsMessage(
                              ErrorMessage.DUPLICATE_COLUMN.getMessage(ColumnName.STATION_NAME.getName())
                          )
                          .notExistsLocation();
    }

    /**
     * Given 지하철역 생성을 요청 하고
     * Given 새로운 지하철역 생성을 요청 하고
     * When 지하철역 목록 조회를 요청 하면
     * Then 두 지하철역이 포함된 지하철역 목록을 응답받는다
     */
    @DisplayName("지하철역 목록 조회")
    @Test
    void getStations() {
        /// given
        List<String> names = Arrays.asList("1호선", "2호선");
        names.forEach(stationStep::지하철역_생성_요청);

        // when
        ExtractableResponse<Response> response = stationStep.지하철_역_조회_요청();

        // then
        AcceptanceTestThen.fromWhen(response)
                          .equalsHttpStatus(HttpStatus.OK)
                          .containsAll("name", names);
    }

    /**
     * Given 지하철역 생성을 요청 하고
     * When 생성한 지하철역 삭제를 요청 하면
     * Then 생성한 지하철역 삭제가 성공한다.
     */
    @DisplayName("지하철역 삭제")
    @Test
    void deleteStation() {
        // given
        stationStep.지하철역_생성_요청(StationStep.DUMMY_STATION_NAME);

        // when
        ExtractableResponse<Response> deleteResponse = stationStep.지하철_역_삭제_요청(1L);
        ExtractableResponse<Response> findResponse = stationStep.지하철_역_조회_요청();

        // then
        AcceptanceTestThen.fromWhen(deleteResponse)
                          .equalsHttpStatus(HttpStatus.NO_CONTENT);
        AcceptanceTestThen.fromWhen(findResponse)
                          .checkEmpty("id");
    }
}
