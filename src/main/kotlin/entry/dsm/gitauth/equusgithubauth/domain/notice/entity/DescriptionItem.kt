package entry.dsm.gitauth.equusgithubauth.domain.notice.entity

import jakarta.validation.constraints.NotBlank

data class DescriptionItem(
    @NotBlank(message = "타이틀은 비워둘 수 없습니다")
    val title: String,

    @NotBlank(message = "내용은 비워둘 수 없습니다")
    val content: String
)
