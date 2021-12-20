package nextstep.subway.path;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import nextstep.subway.path.domain.DistanceFarePolicy;

public class DistancePricePolicyTest {
	@ParameterizedTest
	@CsvSource(value = {"1, 0", "2, 0", "3, 0", "4, 0", "5, 0", "6, 0", "7,0", "8,0", "9,0", "10,0"})
	@DisplayName("10km 이내인 경우 기본운임")
	void get_under10(int expectedDistance, int expectedPrice) {
		DistanceFarePolicy distancePricePolicy = new DistanceFarePolicy();

		int price = distancePricePolicy.calcPrice(expectedDistance);

		assertThat(expectedPrice).isEqualTo(price);
	}

	@ParameterizedTest
	@CsvSource(value = {"11, 100", "17, 200", "23, 300", "29, 400", "35, 500", "40, 600", "45, 700", "50, 800"})
	@DisplayName("10km 초과할 경우 5km마다 100원 추가")
	void get_above10_additionalFare(int expectedDistance, int expectedPrice) {
		DistanceFarePolicy distancePricePolicy = new DistanceFarePolicy();

		int price = distancePricePolicy.calcPrice(expectedDistance);

		assertThat(expectedPrice).isEqualTo(price);
	}

	@ParameterizedTest
	@DisplayName("50Km 초과할 경우 8km마다 100원 추가")
	@CsvSource(value = {"51, 900", "60, 1000", "68, 1100"})
	void get_above50_additionalFare(int expectedDistance, int expectedPrice) {
		DistanceFarePolicy distancePricePolicy = new DistanceFarePolicy();

		int price = distancePricePolicy.calcPrice(expectedDistance);

		assertThat(expectedPrice).isEqualTo(price);
	}
}
