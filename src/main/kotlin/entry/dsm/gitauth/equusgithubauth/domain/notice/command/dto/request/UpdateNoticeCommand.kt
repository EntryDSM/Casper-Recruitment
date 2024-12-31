package entry.dsm.gitauth.equusgithubauth.domain.notice.command.dto.request

import entry.dsm.gitauth.equusgithubauth.domain.notice.entity.enums.ImportanceOfRecruit
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class UpdateNoticeCommand(
    @NotBlank(message = "제목은 비워둘 수 없습니다")
    @Size(min = 5, max = 100, message = "제목은 최소 5글자, 최대 100글자까지 가능합니다")
    val title: String,
    @Size(min = 2, max = 50, message = "키워드는 최소 2글자, 최대 50글자까지 가능합니다.")
    val keyWord: String,
    val titleImageUrl: String,
    @NotBlank(message = "설명은 비워둘 수 없습니다")
    val description: String,
    @NotBlank
    val importanceOfRecruit: ImportanceOfRecruit
)