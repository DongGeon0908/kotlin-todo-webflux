package com.goofy.todo.infrastructure

import com.goofy.todo.domain.Todo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TodoRepository : JpaRepository<Todo, Long>
