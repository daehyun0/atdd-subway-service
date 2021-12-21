package nextstep.subway.path;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import nextstep.subway.path.domain.FareCalculator;

public class FareCalculatorTest {

	@ParameterizedTest
	@CsvSource(value = {"1, 1250", "2, 1250", "3, 1250", "4, 1250", "5, 1250", "6, 1250", "7,1250", "8,1250", "9,1250", "10, 1250", "11, 1350", "17, 1450", "23, 1550", "29, 1650", "35, 1750", "40, 1850", "45, 1950", "50, 2050", "51, 2150", "60, 2250", "68, 2350"})
	@DisplayName("기본 요금 + 추가 요금이 반환된다.")
	void calc(int expectedDistance, int expectedPrice) {
		FareCalculator fareCalculator = new FareCalculator();

		int fare = fareCalculator.calc(expectedDistance);

		assertThat(expectedPrice).isEqualTo(fare);
	}
}
