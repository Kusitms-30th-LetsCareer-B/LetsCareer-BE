package com.letscareer.archiving.domain;

import com.letscareer.global.domain.BaseTimeEntity;
import com.letscareer.recruitment.domain.Recruitment;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Archiving extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "archiving_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruitment_id", nullable = false)
    private Recruitment recruitment;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(nullable = false, length = 3000)
    private String content;

    @Column(length = 1000)
    private String fileUrl;

    public static Archiving of(Recruitment recruitment, String title, String content, String fileUrl) {
        return Archiving.builder()
                .recruitment(recruitment)
                .title(title)
                .content(content)
                .fileUrl(fileUrl)
                .build();
    }

    public void update(String title, String content, String fileUrl) {
        this.title = title;
        this.content = content;
        this.fileUrl = fileUrl;
    }
}
