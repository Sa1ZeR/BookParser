package su.sa1zer.bookparser.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "api_tags")
@EqualsAndHashCode(of = "name", callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "books")
public class Tag extends BaseEntity<Long> {

    @Column(nullable = false, columnDefinition = "VARCHAR(32)")
    private String name;

    @ManyToMany(mappedBy = "tags")
    private Set<Book> books;
}
