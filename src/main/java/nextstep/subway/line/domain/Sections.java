package nextstep.subway.line.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;

import nextstep.subway.station.domain.Station;

@Embeddable
public class Sections {
	@OneToMany(mappedBy = "line", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
	private List<Section> sections = new ArrayList<>();

	public Sections(List<Section> sections) {
		this.sections = sections;
	}

	protected Sections() {
	}

	public void add(Section other) {
		if (!sections.isEmpty()) {
			validate(other);
			updateConnectedSection(other);
		}
		sections.add(other);
	}

	public boolean isEmpty() {
		return sections.isEmpty();
	}

	private void validate(Section other) {
		List<Station> stations = getOrderedStations();
		boolean isUpStationExisted = stations.stream().anyMatch(station -> station.equals(other.getUpStation()));
		boolean isDownStationExisted = stations.stream()
			.anyMatch(station -> station.equals(other.getDownStation()));

		if (isUpStationExisted && isDownStationExisted) {
			throw new RuntimeException("이미 등록된 구간 입니다.");
		}

		if (!stations.isEmpty() && stations.stream().noneMatch(station -> station.equals(other.getUpStation())) &&
			stations.stream().noneMatch(station -> station.equals(other.getDownStation()))) {
			throw new RuntimeException("등록할 수 없는 구간 입니다.");
		}
	}

	private void updateConnectedSection(Section other) {
		getUpLineSection(other.getUpStation())
			.ifPresent(section -> section.updateUpStation(other.getDownStation(), other.getDistance()));
		getDownLineSection(other.getDownStation())
			.ifPresent(section -> section.updateDownStation(other.getUpStation(), other.getDistance()));
	}

	public List<Station> getOrderedStations() {
		Station downStation = findUpStation();
		List<Station> stations = new ArrayList<>();

		while (downStation != null) {
			stations.add(downStation);
			Optional<Section> nextLineSection = findNextSection(downStation);
			downStation = nextLineSection.map(Section::getDownStation).orElse(null);
		}
		return stations;
	}

	private Station findUpStation() {
		if (sections.size() == 0) {
			return null;
		}

		Station searchStation = sections.get(0).getUpStation();
		Station upStation = searchStation;

		while (searchStation != null) {
			upStation = searchStation;
			Optional<Section> prevLineSection = findPrevSection(searchStation);
			searchStation = prevLineSection.map(Section::getUpStation).orElse(null);
		}
		return upStation;
	}

	private Optional<Section> findNextSection(Station station) {
		return sections.stream()
			.filter(section -> section.getUpStation() == station)
			.findFirst();
	}

	private Optional<Section> findPrevSection(Station station) {
		return sections.stream()
			.filter(section -> section.getDownStation() == station)
			.findFirst();
	}

	public void removeByStation(Station station) {
		validateOnRemove();

		Optional<Section> upLineSection = getUpLineSection(station);
		Optional<Section> downLineSection = getDownLineSection(station);
		if (upLineSection.isPresent() && downLineSection.isPresent()) {
			addSectionOnExistTargetInside(upLineSection.get(), downLineSection.get());
		}
		upLineSection.ifPresent(section -> sections.remove(section));
		downLineSection.ifPresent(section -> sections.remove(section));
	}

	private void addSectionOnExistTargetInside(Section upLineSection, Section downLineSection) {
		Station newUpStation = downLineSection.getUpStation();
		Station newDownStation = upLineSection.getDownStation();
		int newDistance = upLineSection.getDistance() + downLineSection.getDistance();
		sections.add(new Section(upLineSection.getLine(), newUpStation, newDownStation, newDistance));
	}

	private Optional<Section> getDownLineSection(Station station) {
		return sections.stream()
			.filter(section -> section.getDownStation().equals(station))
			.findFirst();
	}

	private Optional<Section> getUpLineSection(Station station) {
		return sections.stream()
			.filter(section -> section.getUpStation().equals(station))
			.findFirst();
	}

	private void validateOnRemove() {
		if (sections.size() <= 1) {
			throw new RuntimeException("최소한 1개의 구간이 등록되어 있어야 합니다.");
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Sections sections1 = (Sections)o;
		return Objects.equals(sections, sections1.sections);
	}

	@Override
	public int hashCode() {
		return Objects.hash(sections);
	}
}
