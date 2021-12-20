package nextstep.subway.path.domain;

public class DistanceFarePolicy {
	public int calcPrice(int distance) {
		int additionalPrice = 0;
		if (distance > 50) {
			additionalPrice += (int) (Math.ceil(((distance - 50) / 8.0)) * 100);
			distance = 50;
		}
		if (distance > 10) {
			additionalPrice += (int) (Math.ceil((distance - 10) / 5.0) * 100);
		}
		return additionalPrice;
	}
}
