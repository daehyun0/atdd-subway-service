package nextstep.subway.path.domain;

import java.util.List;

import nextstep.subway.line.domain.Line;

public class FareCalculator {
	private static final int DEFAULT_FARE = 1250;

	public static int calc(int distance, List<Line> lines) {
		return DEFAULT_FARE + DistanceFarePolicy.calcPrice(distance)
			+ LineFarePolicy.calc(lines);
	}
}
