package nextstep.subway.path.domain;

import java.util.Comparator;
import java.util.List;

import nextstep.subway.common.exception.InternalServerException;
import nextstep.subway.line.domain.Line;

public class LineFarePolicy {
	public static int calc(List<Line> lines) {
		return lines
			.stream()
			.distinct()
			.max(Comparator.comparingInt(Line::getUsingFare))
			.map(Line::getUsingFare)
			.orElseThrow(() -> new InternalServerException("노선 요금이 유효하지 않습니다."));
	}
}
