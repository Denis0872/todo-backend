package ru.javabegin.backend.todo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.javabegin.backend.todo.entity.Category;
import ru.javabegin.backend.todo.service.CategoryService;

import java.util.List;
import java.util.NoSuchElementException;


/*

Используем @RestController вместо обычного @Controller, чтобы все ответы сразу оборачивались в JSON,
иначе пришлось бы добавлять лишние объекты в код, использовать @ResponseBody для ответа, указывать тип отправки JSON

Названия методов могут быть любыми, главное не дублировать их имена внутри класса и URL mapping

*/

@RestController
@RequestMapping("/category") // базовый URI
public class CategoryController {

    // доступ к данным из БД
    private CategoryService categoryService;

    // автоматическое внедрение экземпляра класса через конструктор
    // не используем @Autowired ля переменной класса, т.к. "Field injection is not recommended "
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @GetMapping("/id")
    public Category findById() {
        return categoryService.findById(60130L);
    }


}
