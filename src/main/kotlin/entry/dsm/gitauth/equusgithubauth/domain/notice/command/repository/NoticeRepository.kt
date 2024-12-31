package entry.dsm.gitauth.equusgithubauth.domain.notice.command.repository

import entry.dsm.gitauth.equusgithubauth.domain.notice.entity.Notice
import org.springframework.data.repository.CrudRepository

interface NoticeRepository : CrudRepository<Notice, Long> {


}