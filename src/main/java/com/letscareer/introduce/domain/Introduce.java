package com.letscareer.introduce.domain;

import com.letscareer.introduce.dto.request.ReactionReq;
import com.letscareer.recruitment.domain.Recruitment;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Table(name = "introduces")  // introduce 테이블과 매핑
public class Introduce {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="introduce_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruitment_id", nullable = false)
    private Recruitment recruitment;

    @Column(nullable = false)
    private Integer orderIndex;

    @Column(nullable = false)
    private String question;

    @Column(nullable = false)
    private String answer;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private IntroduceStatusType type = null;

    public static Introduce of(Recruitment recruitment, Integer orderIndex, String question, String answer) {
        return Introduce.builder()
                .recruitment(recruitment)
                .orderIndex(orderIndex)
                .question(question)
                .answer(answer)
                .build();
    }

    public void modifyReaction(ReactionReq request) {
        this.type = IntroduceStatusType.of(request.getReaction());
        System.out.println("Updated type: " + this.type);  // 로그로 출력하여 확인
    }

    public void update(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public void minusOrderIndex() {
        this.orderIndex--;
    }
}
