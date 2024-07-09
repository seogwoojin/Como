package kote.demo.admin.service


import kote.demo.admin.entity.Admin
import kote.demo.admin.repository.AdminRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AdminService(
    private val adminRepository: AdminRepository
) {
    fun findByUsername(username: String): Admin? {
        return adminRepository.findByUsername(username)
    }

}
