package entry.dsm.gitauth.equusgithubauth.domain.notice.entity

import entry.dsm.gitauth.equusgithubauth.domain.notice.command.dto.request.UpdateNoticeCommand
import entry.dsm.gitauth.equusgithubauth.domain.notice.entity.enums.ImportanceOfRecruit
import entry.dsm.gitauth.equusgithubauth.domain.report.entity.Report
import jakarta.persistence.CollectionTable
import jakarta.persistence.Column
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToMany

@Entity(name = "notice")
class Notice(
    @Id
    @GeneratedValue
    @Column(name = "notice_id")
    val noticeId: Long,
    @Column(name = "title", nullable = false)
    var title: String,
    @ElementCollection
    @CollectionTable(name = "notice_keywords", joinColumns = [JoinColumn(name = "notice_id")])
    @Column(name = "key_word", nullable = false)
    var keyWord: List<String>,
    @Column(name = "title_image_url", nullable = false)
    var titleImageUrl: String,
    @ElementCollection
    @CollectionTable(name = "notice_descriptions", joinColumns = [JoinColumn(name = "notice_id")])
    @Column(name = "description", nullable = false)
    var description: List<String>,
    @Enumerated(EnumType.STRING)
    @Column(name = "importance_of_recruit", nullable = false)
    var importanceOfRecruit: ImportanceOfRecruit,
    @OneToMany(mappedBy = "notice", fetch = FetchType.LAZY)
    val reports: List<Report> = listOf(),
) {
    fun update(command: UpdateNoticeCommand) {
        title = command.title
        keyWord = command.keyWord
        titleImageUrl = command.titleImageUrl
        description = command.description
        importanceOfRecruit = command.importanceOfRecruit
    }
}
