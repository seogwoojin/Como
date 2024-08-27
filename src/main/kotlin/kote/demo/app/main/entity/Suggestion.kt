package kote.demo.app.main.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class Suggestion (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null,
    val name:String,
    val inquiry:String,
    val message:String
)