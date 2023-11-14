package ru.javabegin.backend.todo.controller;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.javabegin.backend.todo.entity.Category;
import ru.javabegin.backend.todo.search.CategorySearchValues;
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

    @PostMapping("/all")
    public List<Category> findAll(@RequestBody String email){
        return categoryService.findAll(email);
    }
    @PostMapping("/add")
    public ResponseEntity<Category> add(@RequestBody Category category){
        if(category.getId()!=null && category.getId()!=0){
            return new ResponseEntity("missed param 1d", HttpStatus.NOT_ACCEPTABLE);
        }
        if(category.getTitle()==null || category.getTitle().trim().length()==0){
            return new ResponseEntity("missed param title", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(categoryService.add(category));
    }
    @PutMapping("/update")
    public ResponseEntity<Category> update(@RequestBody Category category){
        if(category.getId()==null && category.getId()==0){
            return new ResponseEntity("missed param id", HttpStatus.NOT_ACCEPTABLE);
        }
        if(category.getTitle()==null || category.getTitle().trim().length()==0){
            return new ResponseEntity("missed param title", HttpStatus.NOT_ACCEPTABLE);
        }
        categoryService.update(category);
        return new ResponseEntity(HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        try{
            categoryService.deleteById(id);
        }
        catch (EmptyResultDataAccessException e){
            return new ResponseEntity("not found "+ id,HttpStatus.NOT_ACCEPTABLE);
        }

        return new ResponseEntity(HttpStatus.OK);
    }
    @PostMapping("/search")
    public ResponseEntity<List<Category>> search(@RequestBody CategorySearchValues categorySearchValues){
        if(categorySearchValues.getEmail()==null && categorySearchValues.getEmail().trim().length()==0){
            return new ResponseEntity("missed param email", HttpStatus.NOT_ACCEPTABLE);
        }
        List<Category> list=categoryService.findByTitle(categorySearchValues.getTitle(), categorySearchValues.getEmail());
        return ResponseEntity.ok(list);
    }
    @PostMapping("/id")
    public ResponseEntity<Category> findByid(@RequestBody Long id){
        Category category=null;
        try{
            category=categoryService.findById(id);
        }catch (NoSuchElementException e){
            e.printStackTrace();
            return new ResponseEntity("id"+ " "+id+" "+ "не найдено", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(category);
    }

}
