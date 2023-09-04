//بسم الله الرحمن الرحيم
#include <bits/stdc++.h>
using namespace std;
#define Super_Beso  ios_base::sync_with_stdio(false); cin.tie(nullptr); cout.tie(nullptr);

#include "DoublyLinkedList.h"
#include "Book.h"

/**
            Basel Youssef - 20200111
            Seif  El-Dein - 20200241
**/
DlinkedList<Book> myList;
void menu(){
    cout<<"1.Add a book"<<endl;
    cout<<"2.Sort books"<<endl;
    cout<<"3.Remove a book"<<endl;
    cout<<"4.Display books"<<endl;
    cout<<"0.Exit"<<endl;
    int iWould,pages;
    string titleName,authorName,bookCategory;

    cin>>iWould;
    if(!iWould)
        return;
    if(iWould==1){
        cout<<"Enter in order (Book Title,Author Name,Book Category,Book Pages)\n";
        cin.ignore();
        getline(cin,titleName);
        getline(cin,authorName);
        getline(cin,bookCategory);
        cin>>pages;
        Book myObj(titleName,authorName,bookCategory,pages);
        myList.insert(myObj);
    }
    if(iWould==2){
        myList.sort();
    }
    if(iWould==3){
        cout<<"using\n   1.Title Name\t   2.Author Name:  ";
        cin>>iWould;
        if(iWould==1){
            cout<<"Enter Book Title:  ";
            cin.ignore();
            getline(cin,titleName);
            myList.removeTitle(titleName);
        }else if(iWould==2){
            cout<<"Enter Author's Name:  ";
            cin.ignore();
            getline(cin,authorName);
            myList.removeAuthor(authorName);
        }
    }
    if(iWould==4){
        cout<<"Display\n   1.All Books\t   2.Author's Books\t  3.book Category:  ";
        cin>>iWould;
        if(iWould==1){
            myList.printObject();
        }else if(iWould==2){
            cout<<"Enter Author's Name:  ";
            cin.ignore();
            getline(cin,authorName);
            myList.printAuthor(authorName);
        }else if(iWould==3){
            cout<<"Enter Book Category:  ";
            cin.ignore();
            getline(cin,bookCategory);
            myList.printCategory(bookCategory);
        }
    }
    menu();
}

void TestCaseScenario(){
    cout<<"==================================================="
    <<"======================================================="<<endl;
    /** Display All      */
    myList.printObject();
    cout<<"==================================================="
    <<"======================================================="<<endl;
    /** Add a book       */
    Book _book("Outliers","Malcolm Gladwell","Self Help",309);
    myList.insert(_book);

    /** Sort All         */
    myList.sort();

    /** Display All      */
    myList.printObject();
    cout<<"==================================================="
    <<"======================================================="<<endl;
    /** Display Novels   */
    myList.printCategory("Novels");
    cout<<"==================================================="
    <<"======================================================="<<endl;
    /** Remove Author    */
    myList.removeAuthor("Paulo Koelho");
    cout<<"==================================================="
    <<"======================================================="<<endl;
    /** Remove Author    */
    myList.removeAuthor("Paulo Coelho");
    cout<<"==================================================="
    <<"======================================================="<<endl;
    /** Display Novels   */
    myList.printCategory("Novels");
    cout<<"==================================================="
    <<"======================================================="<<endl;
    /** Remove Title     */
    myList.removeTitle("Pecoming");
    cout<<"==================================================="
    <<"======================================================="<<endl;
    /** Remove Title     */
    myList.removeTitle("Becoming");
    cout<<"==================================================="
    <<"======================================================="<<endl;
    /** Display Author   */
    myList.printAuthor("Michelle Obama");
    cout<<"==================================================="
    <<"======================================================="<<endl;
    /** Display All      */
    myList.printObject();
    cout<<"==================================================="
    <<"======================================================="<<endl;
}
void Read(){
    /**                 inFile              **/
    ifstream inFile;
    inFile.open("Books.txt");
        int numOfBooks;
        string BookTitle;
        string BookAuthor;
        string BookCategory;
        int BookPages;
        string forFirstString;
    inFile>>numOfBooks;
    for(int i=0; i<numOfBooks; i++){;
                inFile.ignore();
        getline(inFile,BookTitle,'\n');
        getline(inFile,BookAuthor,'\n');
        getline(inFile,BookCategory,'\n');
        inFile>>BookPages;

        Book myBook(BookTitle,BookAuthor,BookCategory,BookPages);
        myList.insert(myBook);
    }
    //menu();
    TestCaseScenario();
}


signed main(void){
    Super_Beso
    cout<<"\t\t\t\t\tHello and Welcome!"<<endl;
    Read();

    cout<<endl<<endl;
    cout<<"Code Designed By:\n  -Basel Youssef  \t[20200111]\n  -Seif elDein Yasser \t[20200241]\n";







}

