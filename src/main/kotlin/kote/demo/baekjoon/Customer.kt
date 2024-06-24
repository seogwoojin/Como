package kote.demo.baekjoon

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id


@Entity // 이 클래스가 JPA 엔티티임을 나타냅니다.
class Customer(
    @Id // 이 필드가 엔티티의 기본 키임을 나타냅니다.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 값이 데이터베이스에 의해 자동으로 생성되도록 설정합니다.
    var id: Long? = null, // id 필드는 Long 타입이며, 초기값은 null입니다. 자동 생성되므로 생성 시에는 값을 할당하지 않습니다.

    var name: String // name 필드는 Person의 이름을 저장합니다. 이 필드는 null을 허용하지 않습니다.
)
