package kote.demo.app.admin.entity

import jakarta.persistence.*

@Entity
@Table(name = "admin")
data class Admin(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long = 0,
    @Column(nullable = false, unique = true) val username: String,
    @Column(nullable = false) val password: String,
    @Column(nullable = false) val role: String
)
