package entry.dsm.gitauth.equusgithubauth.domain.notice.entity

import entry.dsm.gitauth.equusgithubauth.domain.notice.command.dto.request.UpdateNoticeCommand
import entry.dsm.gitauth.equusgithubauth.domain.report.entity.Report
import jakarta.persistence.CascadeType
import jakarta.persistence.CollectionTable
import jakarta.persistence.Column
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToMany

@Entity(name = "notices")
class Notice(
    @Id
    @GeneratedValue
    @Column(name = "notice_id")
    val noticeId: Long,
    @Column(name = "title", nullable = false)
    var title: String,
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "notice_keywords", joinColumns = [JoinColumn(name = "notice_id")])
    @Column(name = "key_word", nullable = false)
    var keyWord: MutableList<String> = mutableListOf(),
    @Column(name = "title_image_url")
    var titleImageUrl: String,
    @OneToMany(mappedBy = "notice", cascade = [CascadeType.ALL], orphanRemoval = true)
    var descriptions: MutableList<NoticeDescription> = mutableListOf(),
    @Column(name = "is_focus_recruit", nullable = false)
    var isFocusRecruit: Boolean,
    @Column(name = "is_important", nullable = false)
    var isImportant: Boolean,
    @OneToMany(mappedBy = "notice", fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE])
    val reports: List<Report> = listOf(),
) {
    fun update(command: UpdateNoticeCommand) {
        title = command.title
        keyWord = command.keyWord.toMutableList()
        titleImageUrl = command.titleImageUrl
        isFocusRecruit = command.isFocusRecruit
        isImportant = command.isImportant

        descriptions.clear()
        command.description.forEach { descDto ->
            descriptions.add(
                NoticeDescription(
                    title = descDto.title,
                    content = descDto.content,
                    notice = this,
                ),
            )
        }
    }
}
