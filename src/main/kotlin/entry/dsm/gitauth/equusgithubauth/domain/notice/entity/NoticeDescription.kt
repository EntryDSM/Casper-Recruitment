package entry.dsm.gitauth.equusgithubauth.domain.notice.entity

import jakarta.persistence.*

@Entity(name = "notice_description")
class NoticeDescription(
    @Id
    @GeneratedValue
    val id: Long = 0,
    @Column(nullable = false)
    var title: String,
    @Column(nullable = false, columnDefinition = "TEXT")
    var content: String,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notice_id")
    var notice: Notice,
)
