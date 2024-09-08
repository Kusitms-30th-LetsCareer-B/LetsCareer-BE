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

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false, length = 1000)
    private String fileUrl;

    @Column(nullable = false)
    private String fileKey;  // fileKey 필드 추가

    public static Archiving of(Recruitment recruitment, String title, String content, String fileName, String fileUrl, String fileKey) {
        return Archiving.builder()
                .recruitment(recruitment)
                .title(title)
                .content(content)
                .fileName(fileName)
                .fileUrl(fileUrl)
                .fileKey(fileKey)
                .build();
    }

    public void update(String title, String content, String fileName, String fileUrl, String fileKey) {
        this.title = title;
        this.content = content;
        this.fileName = fileName;
        this.fileUrl = fileUrl;
        this.fileKey = fileKey;
    }
}
