package com.dnd.spaced.domain.report.domain;

import com.dnd.spaced.global.entity.CreateTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "reports")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false, of = "id")
public class Report extends CreateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reported_comment_id")
    private Long commentId;

    @Column(name = "reported_report_id")
    private Long reporterId;

    @Enumerated(EnumType.STRING)
    private Reason reason;

    @Builder
    private Report(Long commentId, Long reporterId, String reasonName) {
        this.commentId = commentId;
        this.reporterId = reporterId;
        this.reason = Reason.find(reasonName);
    }
}
