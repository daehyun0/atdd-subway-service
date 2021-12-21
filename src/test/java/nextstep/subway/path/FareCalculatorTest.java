package nextstep.subway.path;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import nextstep.subway.line.domain.Line;
import nextstep.subway.path.domain.FareCalculator;
import nextstep.subway.station.domain.Station;

public class FareCalculatorTest {

	@ParameterizedTest
	@CsvSource(value = {"1, 1250", "2, 1250", "3, 1250", "4, 1250", "5, 1250", "6, 1250", "7,1250", "8,1250", "9,1250",
		"10, 1250", "11, 1350", "17, 1450", "23, 1550", "29, 1650", "35, 1750", "40, 1850", "45, 1950", "50, 2050",
		"51, 2150", "60, 2250", "68, 2350"})
	@DisplayName("기본 요금 + 거리 요금이 반환된다.")
	void calc_with_distance(int expectedDistance, int expectedPrice) {
		Line 신분당선 = new Line("신분당선", "bg-red-600", new Station("삼성역"), new Station("선릉역"), 10, 0);

		int fare = FareCalculator.calc(expectedDistance, Collections.singletonList(신분당선), 20);

		assertThat(expectedPrice).isEqualTo(fare);
	}

	@ParameterizedTest
	@MethodSource("calc_with_line_parameter")
	@DisplayName("기본 요금 + 거리 요금이 반환된다.")
	void calc_with_line(List<Line> lines, int expectedPrice) {
		int fare = FareCalculator.calc(10, lines, 20);

		assertThat(expectedPrice).isEqualTo(fare);
	}

	static Stream<Arguments> calc_with_line_parameter() {
		Line 신분당선 = new Line("신분당선", "bg-red-600", new Station("삼성역"), new Station("선릉역"), 10, 200);
		Line 구분당선 = new Line("구분당선", "bg-red-600", new Station("삼성역"), new Station("선릉역"), 10, 500);
		Line 이호선 = new Line("이호선", "bg-red-600", new Station("삼성역"), new Station("선릉역"), 10, 700);
		Line 삼호선 = new Line("삼호선", "bg-red-600", new Station("삼성역"), new Station("선릉역"), 10, 900);
		return Stream.of(
			Arguments.of(Collections.singletonList(신분당선), 1450),
			Arguments.of(Collections.singletonList(구분당선), 1750),
			Arguments.of(Collections.singletonList(이호선), 1950),
			Arguments.of(Collections.singletonList(삼호선), 2150),
			Arguments.of(Arrays.asList(신분당선, 구분당선, 이호선), 1950),
			Arguments.of(Arrays.asList(이호선, 삼호선), 2150)
		);
	}

	@ParameterizedTest
	@CsvSource(value = {"19, 1250", "13, 720", "6, 450"})
	@DisplayName("나이에 따라 할인된 요금이 반환된다.")
	void calc_with_age(int age, int expectedPrice) {
		Line 신분당선 = new Line("신분당선", "bg-red-600", new Station("삼성역"), new Station("선릉역"), 10, 0);

		int fare = FareCalculator.calc(10, Collections.singletonList(신분당선), age);

		assertThat(expectedPrice).isEqualTo(fare);
	}
}
