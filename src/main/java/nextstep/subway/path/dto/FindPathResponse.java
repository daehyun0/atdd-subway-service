package nextstep.subway.path.dto;

import java.util.List;
import java.util.Objects;

import nextstep.subway.line.domain.Line;
import nextstep.subway.path.domain.FareCalculator;
import nextstep.subway.path.domain.FindShortestPathResult;
import nextstep.subway.station.dto.StationResponse;

public class FindPathResponse {
	private List<StationResponse> stations;
	private int distance;
	private int totalFare;

	public FindPathResponse(List<StationResponse> stations, int distance, int totalFare) {
		this.stations = stations;
		this.distance = distance;
		this.totalFare = totalFare;
	}

	public static FindPathResponse of(FindShortestPathResult findShortestPathResult, List<Line> lines) {
		List<StationResponse> path = findShortestPathResult.getStations();
		int distance = findShortestPathResult.getDistance();
		int totalFare = FareCalculator.calc(distance, lines);

		return new FindPathResponse(path, distance, totalFare);
	}

	public List<StationResponse> getStations() {
		return stations;
	}

	public int getDistance() {
		return distance;
	}

	public int getTotalFare() {
		return totalFare;
	}

	public void setStations(List<StationResponse> stations) {
		this.stations = stations;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public void setTotalFare(int totalFare) {
		this.totalFare = totalFare;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		FindPathResponse that = (FindPathResponse)o;
		return distance == that.distance && Objects.equals(stations, that.stations);
	}

	@Override
	public int hashCode() {
		return Objects.hash(stations, distance);
	}
}
