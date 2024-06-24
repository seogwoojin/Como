package kote.demo.baekjoon

import org.springframework.data.jpa.repository.JpaRepository


interface CustomerRepository : JpaRepository<Customer, Long?>