package nextstep.subway.path;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import nextstep.subway.line.domain.Line;
import nextstep.subway.path.domain.LineFarePolicy;
import nextstep.subway.station.domain.Station;

public class LineFarePolicyTest {
	private Line 신분당선;
	private Line 구분당선;
	private Line 이호선;
	private Line 삼호선;

	@BeforeEach
	void setUp() {
		신분당선 = new Line("신분당선", "bg-red-600", new Station("삼성역"), new Station("선릉역"), 10, 200);
		구분당선 = new Line("구분당선", "bg-red-600", new Station("삼성역"), new Station("선릉역"), 10, 500);
		이호선 = new Line("이호선", "bg-red-600", new Station("삼성역"), new Station("선릉역"), 10, 700);
		삼호선 = new Line("삼호선", "bg-red-600", new Station("삼성역"), new Station("선릉역"), 10, 900);
	}

	@Test
	@DisplayName("노선별 추가 요금을 반환한다.")
	void calc() {
		assertThat(LineFarePolicy.calc(Collections.singletonList(신분당선))).isEqualTo(200);
		assertThat(LineFarePolicy.calc(Collections.singletonList(구분당선))).isEqualTo(500);
		assertThat(LineFarePolicy.calc(Collections.singletonList(이호선))).isEqualTo(700);
		assertThat(LineFarePolicy.calc(Collections.singletonList(삼호선))).isEqualTo(900);
	}

	@Test
	@DisplayName("여러 노선이 있으면 가장 높은 금액을 반환한다.")
	void calc_max() {
		assertThat(LineFarePolicy.calc(Arrays.asList(신분당선, 구분당선))).isEqualTo(500);
		assertThat(LineFarePolicy.calc(Arrays.asList(구분당선, 이호선))).isEqualTo(700);
		assertThat(LineFarePolicy.calc(Arrays.asList(이호선, 삼호선))).isEqualTo(900);
		assertThat(LineFarePolicy.calc(Arrays.asList(삼호선, 신분당선))).isEqualTo(900);
	}
}
