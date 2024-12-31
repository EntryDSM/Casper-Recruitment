package entry.dsm.gitauth.equusgithubauth.domain.notice.entity

import entry.dsm.gitauth.equusgithubauth.domain.notice.command.dto.request.UpdateNoticeCommand
import entry.dsm.gitauth.equusgithubauth.domain.notice.entity.enums.ImportanceOfRecruit
import entry.dsm.gitauth.equusgithubauth.domain.report.entity.Report
import jakarta.persistence.*
import jakarta.persistence.OneToMany;

@Entity(name = "notice")
class Notice(

    @Id
    @GeneratedValue
    @Column(name = "notice_id")
    val noticeId: Long,

    @Column(name = "title", nullable = false)
    var title: String,

    @Column(name = "key_word", nullable = false)
    var keyWord: String,

    @Column(name = "title_image_url", nullable = false)
    var titleImageUrl: String,

    @Column(name = "description", nullable = false)
    var description: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "importance_of_recruit", nullable = false)
    var importanceOfRecruit: ImportanceOfRecruit,

    @OneToMany(mappedBy = "notice_id", fetch = FetchType.LAZY)
    val reports: List<Report> = listOf()


) {
    fun update(command: UpdateNoticeCommand) {
        title = command.title
        keyWord = command.keyWord
        titleImageUrl = command.titleImageUrl
        description = command.description
        importanceOfRecruit = command.importanceOfRecruit
    }

}