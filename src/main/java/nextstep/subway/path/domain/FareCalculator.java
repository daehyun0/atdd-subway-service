package nextstep.subway.path.domain;

public class FareCalculator {
	private static final int DEFAULT_FARE = 1250;
	private static final DistanceFarePolicy distanceFarePolicy = new DistanceFarePolicy();

	public int calc(int distance) {
		return DEFAULT_FARE + distanceFarePolicy.calcPrice(distance);
	}
}
