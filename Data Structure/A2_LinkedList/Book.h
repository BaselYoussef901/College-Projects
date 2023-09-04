#ifndef BOOK_H_INCLUDED
#define BOOK_H_INCLUDED
class Book{
    string title;
    string author;
    string category;
    int numOfPages;

public:
    Book();
    Book(string,string,string,int);
    void print();
    void setTitle(string title);
    string getTitle();
    void setAuthor(string);
    string getAuthor();
    void setCategory(string);
    string getCategory();
    void setPages(int);
    int getPages();

};


Book::Book() {}
Book::Book(string title,string author,string category,int numOfPages){
    this->title      = title;
    this->author     = author;
    this->category   = category;
    this->numOfPages = numOfPages;
}
void Book::print(){
    cout<<"Book Title:\t"<<title<<'\n';
    cout<<"Book Author:\t"<<author<<'\n';
    cout<<"Book Category:\t"<<category<<'\n';
    cout<<"Book Pages:\t"<<numOfPages<<'\n';
}
void Book::setTitle(string title){
    this->title=title;
}
string Book::getTitle(){
    return title;
}

void Book::setAuthor(string author){
    this->author=author;
}
string Book::getAuthor(){
    return author;
}

void Book::setCategory(string category){
    this->category=category;
}
string Book::getCategory(){
    return category;
}

void Book::setPages(int numOfPages){
    this->numOfPages=numOfPages;
}
int Book::getPages(){
    return numOfPages;
}
#endif
