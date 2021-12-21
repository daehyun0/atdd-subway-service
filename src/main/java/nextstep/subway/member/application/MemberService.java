package nextstep.subway.member.application;

import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nextstep.subway.common.exception.Exceptions;
import nextstep.subway.common.exception.UnAuthorizationException;
import nextstep.subway.member.domain.Member;
import nextstep.subway.member.domain.MemberRepository;
import nextstep.subway.member.dto.MemberRequest;
import nextstep.subway.member.dto.MemberResponse;

@Service
@Transactional(readOnly = true)
public class MemberService {
    private MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public MemberResponse saveMember(MemberRequest request) {
        Member member = memberRepository.save(request.toMember());
        return MemberResponse.of(member);
    }

    public MemberResponse findMember(Long id) {
        validate(id);
        Member member = findMemberFromRepository(id);
        return MemberResponse.of(member);
    }

    @Transactional
    public void updateMember(Long id, MemberRequest param) {
        validate(id);
        Member member = findMemberFromRepository(id);
        member.update(param.toMember());
    }

    @Transactional
    public void deleteMember(Long id) {
        validate(id);
        memberRepository.deleteById(id);
    }

    private void validate(Long id) {
        if (Objects.isNull(id)) {
            throw new UnAuthorizationException("권한이 부족하여 실행할 수 없습니다.");
        }
    }

    private Member findMemberFromRepository(Long id) {
        return memberRepository.findById(id).orElseThrow(Exceptions.MEMBER_NOT_FOUND::getException);
    }
}
