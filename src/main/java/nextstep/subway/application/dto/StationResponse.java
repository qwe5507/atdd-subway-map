package nextstep.subway.application.dto;

public class StationResponse {
    private Long id;
    private String name;

    public StationResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    private StationResponse() {}

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}