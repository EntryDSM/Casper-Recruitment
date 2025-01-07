package entry.dsm.gitauth.equusgithubauth.domain.report.entity

import entry.dsm.gitauth.equusgithubauth.domain.notice.entity.Notice
import entry.dsm.gitauth.equusgithubauth.domain.report.entity.enums.Major
import entry.dsm.gitauth.equusgithubauth.domain.report.entity.enums.ProgrammingExperience
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity(name = "report")
class Report(
    @Id
    @GeneratedValue
    @Column(name = "id")
    var reportId: Long = 0,
    @Column(name = "applicant_name", nullable = false)
    var applicantName: String,
    @Column(name = "student_id", nullable = false)
    var studentId: String,
    @Column(name = "phone_number", nullable = false)
    var phoneNumber: String,
    @Enumerated(EnumType.STRING)
    @Column(name = "programming_experience", nullable = false)
    var programmingExperience: ProgrammingExperience,
    @Enumerated(EnumType.STRING)
    @Column(name = "major", nullable = false)
    var major: Major,
    @Column(name = "motivation", nullable = false, columnDefinition = "TEXT")
    var motivation: String,
    @Column(name = "self_introduction", nullable = false, columnDefinition = "TEXT")
    var selfIntroduction: String,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notice_id", nullable = false)
    val notice: Notice,
)
