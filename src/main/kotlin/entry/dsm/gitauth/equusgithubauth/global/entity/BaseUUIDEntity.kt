package entry.dsm.gitauth.equusgithubauth.global.entity

import jakarta.persistence.Column
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass
import org.hibernate.annotations.UuidGenerator
import org.hibernate.annotations.UuidGenerator.Style
import java.util.UUID

@MappedSuperclass
abstract class BaseUUIDEntity(
    id: UUID? = null,
) {
    @Id
    @UuidGenerator(style = Style.RANDOM)
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)", nullable = false)
    val id: UUID = id ?: UUID.randomUUID()
}
