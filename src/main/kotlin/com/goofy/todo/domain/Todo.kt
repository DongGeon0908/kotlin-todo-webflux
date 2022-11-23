package com.goofy.todo.domain

import com.goofy.todo.domain.vo.TodoCategory
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "todo")
class Todo(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long = -1,

    var title: String,

    var content: String,

    @Enumerated(value = EnumType.STRING)
    var category: TodoCategory,

    @Column(name = "is_active")
    var active: Boolean = true
) : BaseEntity() {
    fun update(title: String, content: String, category: TodoCategory) {
        this.title = title
        this.content = content
        this.category = category
    }

    fun changedActive(isActive: Boolean) {
        this.active = isActive
    }
}
