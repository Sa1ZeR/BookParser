# ЗАДАЧА

Для обучения и работы приложения необходимо создать БД книг.
Набор данных книги: название, автор, описание, год, изображение обложки, ISBN, количество страниц, жанр, теги.
Ваша задача - подготовить базу данных книг.
Список книг берется с сайта https://igraslov.store/ и ограничен ими
Для этого необходимо написать сервис, который парсит книги с книжных сайтов (любой из списка, но не без аргументации): лабиринт, литрес, читай-город, ozon, google books, goodreads, библиотеки (электронные), а также сохраняет их в БД.
Важно:
- должна быть реализована возможность расширять работу с другими сайтами, на которых есть книги. Иными словами, “хочу парсить новый сайт, на котором есть книги, которых нет нигде больше. Могу легко расширить работу сервиса”.
- апи для работы с сервисом, которое позволит получить список книг

# Документация по API
## API книг
> [GET] **/api/books/{id}** - позволяет получить книгу по id
> 
> [GET] **/api/books/filter/** - позволяет получить список книг по определенным фильтрам (можно использовать от 0 до N параметров)
> * Поддерживаются следующие параметры:
> 
> **title** - Название книги. Тип: **String**
> 
> **ISBN** - ISBN книги. Тип: **Long**
> 
> **year** - год издания книги. Тип: **Integer**
> 
> **yearLower** - Искать до/после. Используется совместно с параметром **year**, так и без него. Тип: **Boolean**
> 
> **pages** - кол-во страниц книги. Тип: **Integer**
>
> **pagesLower** - Искать до/после. Используется совместно с параметром **pages**, так и без него. Тип: **Boolean**
> 
> **authors** - искать по авторам книги (использует id авторов из БД) Тип: **массив Long**
> 
> **genres** - искать по жанрам книги (использует id жанров из БД) Тип: **массив Long**
> 
> **tags** - искать по тегам книги (использует id тегов из БД) Тип: **массив Long**

### Пример запроса:

**/api/books/filter?resultPerPage=7&offset=0&yearLower=false&year=2000&authors=3**

## Ответ:

```json
[
    {
        "id": 3,
        "name": "Материалы для биографии князя А. Д. Кантемира",
        "desc": "С введение и примечаниями профессора В.Н. Александренко.",
        "year": 2013,
        "img": "http://books.google.com/books/content?id=qPb3BQAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api",
        "pages": 355,
        "ISBN": 9785446062331,
        "parsedFrom": "GOOGLE_BOOKS",
        "created": "2023-01-04",
        "updated": "2023-01-04",
        "authors": [
            {
                "id": 3,
                "name": "Майков Л. Н."
            }
        ],
        "genres": [],
        "tags": []
    }
]
```

> [POST] **api/books/parse/<type>** - позволяет спарсить книги с сайта
> 
> **type** - Тип парсера. Доступны: **GOOGLE_BOOKS**

## API авторов

> [GET] /api/authors/all - позволяет получить всех авторов
> 
> [GET] /api/authors/{id} - позволяет получить автора по id

## API жанров

> [GET] /api/genres/all - позволяет получить все жанры
>
> [GET] /api/genres/{id} - позволяет получить жанр по id

## API тегов

> [GET] /api/tags/all - позволяет получить все теги
>
> [GET] /api/tags/{id} - позволяет получить тег по id