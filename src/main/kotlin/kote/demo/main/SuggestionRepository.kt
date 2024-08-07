package kote.demo.main

import kote.demo.main.entity.Suggestion
import org.springframework.data.jpa.repository.JpaRepository

interface SuggestionRepository:JpaRepository<Suggestion,Long> {
}