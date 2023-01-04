package su.sa1zer.bookparser.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
@Table(name = "api_books")
@EqualsAndHashCode(of = {"name", "year", "parsedFrom"}, callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"authors"})
public class Book extends BaseEntity<Long> {

    @Column(nullable = false, columnDefinition = "TEXT")
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String desc;

    @Column(nullable = false)
    private Integer year;

    @Column(columnDefinition = "TEXT")
    private String img;

    @Column(nullable = false)
    private Integer pages;

    @Column(columnDefinition = "int8")
    private Long ISBN;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(24)")
    private ParserType parsedFrom;

    private LocalDate created;

    private LocalDate updated;

    @ManyToMany
    @JoinTable(name = "authors_to_books", joinColumns = @JoinColumn(name = "bid"),
            inverseJoinColumns = @JoinColumn(name = "aid"))
    private Set<Author> authors;

    @ManyToMany
    @JoinTable(name = "genres_to_books", joinColumns = @JoinColumn(name = "bid"),
            inverseJoinColumns = @JoinColumn(name = "gid"))
    private Set<Genre> genres;

    @ManyToMany
    @JoinTable(name = "tags_to_books", joinColumns = @JoinColumn(name = "bid"),
            inverseJoinColumns = @JoinColumn(name = "tid"))
    private Set<Tag> tags;

    @PrePersist
    public void onCreated() {
        created = LocalDate.now();
        updated = LocalDate.now();
    }

    @PreUpdate
    public void onUpdated() {
        updated = LocalDate.now();
    }
}
