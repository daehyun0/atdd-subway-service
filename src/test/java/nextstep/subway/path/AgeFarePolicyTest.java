package nextstep.subway.path;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import nextstep.subway.path.domain.AgeFarePolicy;

public class AgeFarePolicyTest {
	@Test
	@DisplayName("일반인(19~): 할인 없음 (0원)")
	void calc_adult() {
		int price = 1250;

		assertThat(AgeFarePolicy.calc(price, 19)).isEqualTo(1250);
		assertThat(AgeFarePolicy.calc(price, 20)).isEqualTo(1250);
	}

	@DisplayName("청소년(13~18): 운임에서 350원을 공제한 금액의 20%할인")
	void calc_youth() {
		int price = 1250;

		assertThat(AgeFarePolicy.calc(price, 13)).isEqualTo(720);
		assertThat(AgeFarePolicy.calc(price, 14)).isEqualTo(720);
		assertThat(AgeFarePolicy.calc(price, 18)).isEqualTo(720);
	}

	@DisplayName("어린이(6~12): 운임에서 350원을 공제한 금액의 50%할인")
	void name_child() {
		int price = 1250;

		assertThat(AgeFarePolicy.calc(price, 6)).isEqualTo(450);
		assertThat(AgeFarePolicy.calc(price, 7)).isEqualTo(450);
		assertThat(AgeFarePolicy.calc(price, 12)).isNotEqualTo(450);
	}
}
