package kote.demo.app.admin.service

import kote.demo.app.admin.entity.Admin
import kote.demo.app.admin.repository.AdminRepository
import org.springframework.stereotype.Service

@Service
class AdminService(private val adminRepository: AdminRepository) {
  fun findByUsername(username: String): Admin? {
    return adminRepository.findByUsername(username)
  }
}
