package su.sa1zer.bookparser.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import su.sa1zer.bookparser.service.GenreService;

@RestController
@RequestMapping("/api/genres/")
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable String id) {
        return ResponseEntity.ok(genreService.findById(Long.parseLong(id)));
    }

    @GetMapping("all")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(genreService.findAll());
    }
}
