package su.sa1zer.bookparser.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Builder
@Table(name = "api_author")
@EqualsAndHashCode(of = {"name"}, callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "books")
public class Author extends BaseEntity<Long> {

    @Column(nullable = false)
    private String name; //todo may be not name?

    @ManyToMany(mappedBy = "authors")
    private Set<Book> books;
}
