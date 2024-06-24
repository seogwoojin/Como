package kote.demo.company.repository

import kote.demo.company.entity.ProblemPrefer
import org.springframework.data.jpa.repository.JpaRepository

interface ProblemPreferRepository : JpaRepository<ProblemPrefer,Long>