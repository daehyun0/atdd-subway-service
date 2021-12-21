package nextstep.subway.path.domain;

import nextstep.subway.common.exception.BadParameterException;

public class AgeFarePolicy {
	public static int calc(int fare, int age) {
		if (age >= 19) {
			return fare;
		}
		if (age >= 13) {
			return (int)((fare - 350) * 0.8);
		}
		if (age >= 6) {
			return (int)((fare - 350) * 0.5);
		}
		throw new BadParameterException("6세 미만은 보호자와 함께 동승해주세요.");
	}
}
