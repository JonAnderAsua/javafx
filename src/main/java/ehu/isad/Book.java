package ehu.isad;

import ehu.isad.Details;

public class Book {
    String isbn;
    String title;

    String info_url;
    String bib_key;
    String preview_url;
    String thumbnail_url;
    Details details;

    public Book(String isbn, String title) {
        this.isbn = isbn;
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }

    public String getISBN(){
        return isbn;
    }

    public Details getDetails(){
        return details;
    }

    public String getThumbnail_url(){
        return thumbnail_url;
    }

    public void setIsbn(String isbn) {this.isbn = isbn;}

    public void setTitle(String title) {this.title = title; }

    public void setDetails(Details d){details=d;}
}