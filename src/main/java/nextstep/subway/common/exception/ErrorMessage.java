package nextstep.subway.common.exception;

public enum ErrorMessage {
    ENTITY_NOT_FOUND("요청하신 데이터를 찾을 수 없습니다."),
    DUPLICATE_COLUMN("이미 존재하는 %s 입니다."),
    SAME_STATION_IN_SECTION("지하철 구간은 같은 지하철 역으로 구성될 수 없습니다.");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getMessage(Object... objs) {
        return String.format(message, objs);
    }
}
