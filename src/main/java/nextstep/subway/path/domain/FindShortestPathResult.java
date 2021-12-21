package nextstep.subway.path.domain;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import nextstep.subway.common.exception.InternalServerException;
import nextstep.subway.station.domain.Station;
import nextstep.subway.station.dto.StationResponse;

public class FindShortestPathResult {
	public static final int MINIMUM_PATH_FINDING_RESULT_STATION_COUNT = 2;
	public static final int MINIMUM_PATH_FINDING_RESULT_DISTANCE = 1;

	private final List<StationResponse> stationResponses;
	private final int distance;

	public FindShortestPathResult(List<Station> stations, int distance) {
		validate(stations, distance);
		this.stationResponses = stations
			.stream()
			.map(StationResponse::of)
			.collect(Collectors.toList());
		this.distance = distance;
	}

	private void validate(List<Station> stations, int distance) {
		if (stations.size() < MINIMUM_PATH_FINDING_RESULT_STATION_COUNT) {
			throw new InternalServerException("최단 경로의 역들의 개수는 2 이상이어야 합니다.");
		}

		if (distance < MINIMUM_PATH_FINDING_RESULT_DISTANCE)  {
			throw new InternalServerException("최단 경로의 거리는 최소 1 이상이어야 합니다.");
		}
	}

	public List<StationResponse> getStations() {
		return stationResponses;
	}

	public int getDistance() {
		return distance;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		FindShortestPathResult that = (FindShortestPathResult)o;
		return distance == that.distance && Objects.equals(stationResponses, that.stationResponses);
	}

	@Override
	public int hashCode() {
		return Objects.hash(stationResponses, distance);
	}
}
