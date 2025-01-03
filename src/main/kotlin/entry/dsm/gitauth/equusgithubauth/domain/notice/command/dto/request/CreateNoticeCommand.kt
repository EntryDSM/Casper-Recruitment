package entry.dsm.gitauth.equusgithubauth.domain.notice.command.dto.request

import entry.dsm.gitauth.equusgithubauth.domain.notice.entity.enums.ImportanceOfRecruit
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class CreateNoticeCommand(
    val noticeId: Long,
    @NotBlank(message = "제목은 비워둘 수 없습니다")
    @Size(min = 5, max = 100, message = "제목은 최소 5글자, 최대 100글자까지 가능합니다")
    val title: String,
    @Size(min = 2, max = 50, message = "키워드는 최소 2글자, 최대 50글자까지 가능합니다.")
    val keyWord: String,
    val titleImageUrl: String,
    @NotBlank(message = "설명은 비워둘 수 없습니다")
    val description: String,
<<<<<<< HEAD
    @NotBlank
    val importanceOfRecruit: ImportanceOfRecruit,
)
=======
    val importanceOfRecruit: ImportanceOfRecruit
)
<<<<<<< HEAD
// conflict 이슈로 나중에 검증 추가 예정
>>>>>>> 0f16680 (docs : (#9))
=======
// conflict 이슈로 나중에 검증 추가 예정
>>>>>>> origin/feature/9-develop-application-resubmission-features
