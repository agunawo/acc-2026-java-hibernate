package bootcamp.hibernate_practical.controller;

import bootcamp.hibernate_practical.service.ExternalBookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Exposes the secured outbound call. Under /api/** so it needs an authenticated caller.
@RestController
@RequestMapping("/api/external-books")
public class ExternalBookController {

    private final ExternalBookService externalBookService;

    public ExternalBookController(ExternalBookService externalBookService) {
        this.externalBookService = externalBookService;
    }

    @GetMapping
    public String getExternalBooks() {
        return externalBookService.fetchExternalBooks();
    }
}
