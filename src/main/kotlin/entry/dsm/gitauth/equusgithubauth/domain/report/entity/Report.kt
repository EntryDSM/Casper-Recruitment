package entry.dsm.gitauth.equusgithubauth.domain.report.entity

import entry.dsm.gitauth.equusgithubauth.domain.notice.entity.Notice
import entry.dsm.gitauth.equusgithubauth.domain.report.entity.enums.Major
import entry.dsm.gitauth.equusgithubauth.domain.report.entity.enums.ProgrammingExperience
import jakarta.persistence.*
import java.time.LocalDateTime

<<<<<<< HEAD
<<<<<<< HEAD
@Entity(name = "entity")
class Report()
=======
=======
>>>>>>> 39f02fc (feat : (#9) add Eport Entity)
@Entity(name = "report")
class Report(

    @Id
    @GeneratedValue
    @Column(name = "id")
    var reportId: Long = 0,

    @Column(name = "applicant_name", nullable = false)
    var applicantName: String,  // 지원자 이름

    @Column(name = "student_id", nullable = false)
    var studentId: String,  // 학번

    @Column(name = "phone_number", nullable = false)
    var phoneNumber: String,  // 전화번호

    @Enumerated(EnumType.STRING)
    @Column(name = "programming_experience", nullable = false)
    var programmingExperience: ProgrammingExperience,  // 프로그래밍 경력

    @Enumerated(EnumType.STRING)
    @Column(name = "major", nullable = false)
    var major: Major,  // 전공

    @Column(name = "motivation", nullable = false, columnDefinition = "TEXT")
    var motivation: String,  // 지원 동기

    @Column(name = "self_introduction", nullable = false, columnDefinition = "TEXT")
    var selfIntroduction: String,  // 자기소개

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notice_id", nullable = false)
    val notice: Notice

)
<<<<<<< HEAD
>>>>>>> 3f68b54 (feat : (#9) add Report Entity)
=======
>>>>>>> 39f02fc (feat : (#9) add Eport Entity)
