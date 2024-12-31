package entry.dsm.gitauth.equusgithubauth.domain.notice.query.repository

import entry.dsm.gitauth.equusgithubauth.domain.notice.entity.Notice
import org.springframework.data.repository.CrudRepository

interface NoticeQueryRepository: CrudRepository<Notice, Long> {

}