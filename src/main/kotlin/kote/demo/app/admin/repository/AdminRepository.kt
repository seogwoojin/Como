package kote.demo.app.admin.repository

import kote.demo.app.admin.entity.Admin
import org.springframework.data.jpa.repository.JpaRepository

interface AdminRepository : JpaRepository<Admin, Long> {
    fun findByUsername(username: String): Admin?
}