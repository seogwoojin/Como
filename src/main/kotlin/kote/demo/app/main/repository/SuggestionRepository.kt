package kote.demo.app.main.repository

import kote.demo.app.main.entity.Suggestion
import org.springframework.data.jpa.repository.JpaRepository

interface SuggestionRepository : JpaRepository<Suggestion, Long> {}
