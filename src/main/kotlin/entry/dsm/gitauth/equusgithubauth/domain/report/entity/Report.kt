package entry.dsm.gitauth.equusgithubauth.domain.report.entity

import entry.dsm.gitauth.equusgithubauth.domain.notice.entity.Notice
import entry.dsm.gitauth.equusgithubauth.domain.report.entity.enums.Major
import entry.dsm.gitauth.equusgithubauth.domain.report.entity.enums.ProgrammingExperience
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity(name = "report")
class Report(

    @Id
    @GeneratedValue
    @Column(name = "id")
    val reportId: Long,

    @Column(name = "applicant_name", nullable = false)
    val applicantName: String,  // 지원자 이름

    @Column(name = "student_id", nullable = false)
    val studentId: String,  // 학번

    @Column(name = "phone_number", nullable = false)
    val phoneNumber: String,  // 전화번호

    @Enumerated(EnumType.STRING)
    @Column(name = "programming_experience", nullable = false)
    val programmingExperience: ProgrammingExperience,  // 프로그래밍 경력

    @Enumerated(EnumType.STRING)
    @Column(name = "major", nullable = false)
    val major: Major,  // 전공

    @Column(name = "motivation", nullable = false, columnDefinition = "TEXT")
    val motivation: String,  // 지원 동기

    @Column(name = "self_introduction", nullable = false, columnDefinition = "TEXT")
    val selfIntroduction: String,  // 자기소개

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),  // 제출일 (기본값: 현재 시간)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notice_id", nullable = false)
    val notice: Notice

)
