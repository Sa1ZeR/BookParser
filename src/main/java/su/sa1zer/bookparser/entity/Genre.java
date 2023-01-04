package su.sa1zer.bookparser.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "api_genre")
@EqualsAndHashCode(of = "name", callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "books")
public class Genre extends BaseEntity<Long> {

    @Column(nullable = false, columnDefinition = "VARCHAR(128)")
    private String name;

    @ManyToMany(mappedBy = "genres")
    private Set<Book> books;
}
