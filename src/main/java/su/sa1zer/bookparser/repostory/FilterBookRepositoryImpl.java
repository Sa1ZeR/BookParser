package su.sa1zer.bookparser.repostory;

import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.EntityGraph;
import su.sa1zer.bookparser.entity.Book;
import su.sa1zer.bookparser.entity.QAuthor;
import su.sa1zer.bookparser.payload.request.BookFilterRequest;
import su.sa1zer.bookparser.utils.QPredicates;

import javax.persistence.EntityManager;
import java.util.List;

import static su.sa1zer.bookparser.entity.QBook.book;


@RequiredArgsConstructor
public class FilterBookRepositoryImpl implements FilterBookRepository {

    private final EntityManager entityManager;

    @Override
    @EntityGraph(attributePaths = {"authors", "tags", "genres"})
    public List<Book> findAllByFilter(BookFilterRequest request) {
        Predicate predicate = QPredicates.builder()
                .add(request.getTitle(), book.name::containsIgnoreCase)
                .add(request.getISBN(), book.ISBN::eq)
                .add(request.getYear(), request.getYearLower() != null ?
                        request.getYearLower() ? book.year::loe : book.year::goe : book.year::eq)
                .add(request.getPages(), request.getPagesLower() != null ?
                        request.getYearLower() ? book.pages::loe : book.pages::goe : book.pages::eq)
                .buildAnd();

        JPAQuery<Book> select = new JPAQuery<Book>(entityManager)
                .select(book).from(book);

        if(request.getAuthors() != null)
            select = select.join(book.authors)
                    .where(book.authors.any().id.in(request.getAuthors()));

        if(request.getGenres() != null)
            select = select.join(book.genres)
                    .where(book.genres.any().id.in(request.getGenres()));

        if(request.getTags() != null)
            select = select.join(book.tags)
                    .where(book.tags.any().id.in(request.getTags()));

        select = select.where(predicate);

        if(request.getOffset() != null)
            select = select.offset(request.getOffset());
        if(request.getResultPerPage() != null)
            select = select.limit(request.getResultPerPage());
        else select = select.limit(15);

        return select.fetch();
    }
}
