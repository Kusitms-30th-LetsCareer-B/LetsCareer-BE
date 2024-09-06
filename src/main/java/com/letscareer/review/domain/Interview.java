package com.letscareer.review.domain;

import com.letscareer.introduce.domain.Introduce;
import com.letscareer.introduce.domain.IntroduceStatusType;
import com.letscareer.introduce.dto.request.ReactionReq;
import com.letscareer.recruitment.domain.Recruitment;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Interview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="interview_id", nullable = false)
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
    }

    public void update(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public void minusOrderIndex() {
        this.orderIndex--;
    }
}
