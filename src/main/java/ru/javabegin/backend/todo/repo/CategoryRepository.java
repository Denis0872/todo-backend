package ru.javabegin.backend.todo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.javabegin.backend.todo.entity.Category;



// Вы можете уже сразу использовать все методы CRUD (Create, Read, Update, Delete)
// принцип ООП: абстракция-реализация - здесь описываем все доступные способы доступа к данным
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {




}
