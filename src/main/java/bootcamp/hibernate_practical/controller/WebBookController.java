package bootcamp.hibernate_practical.controller;

import bootcamp.hibernate_practical.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

// Browser (Thymeleaf) UI. Protected by the form-login security chain.
@Controller
public class WebBookController {

    private final BookService bookService;

    public WebBookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/ui/books")
    public String listBooks(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "books";
    }

    @PostMapping("/ui/books/{id}/delete")
    public String deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return "redirect:/ui/books";
    }
}
