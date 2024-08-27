package kote.demo.app.main

import kote.demo.app.main.entity.Suggestion
import org.springframework.data.jpa.repository.JpaRepository

interface SuggestionRepository:JpaRepository<Suggestion,Long> {
}