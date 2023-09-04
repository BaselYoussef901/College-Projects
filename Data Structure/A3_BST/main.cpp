#include <bits/stdc++.h>
using namespace std;
#include "BST.h"
/**
            Basel Youssef - 20200111
            Seif  El-Dein - 20200241
**/
int option , _numberOfPages;
string _title , _author , _category;

void menu(BST &BinaryTree){
    cout<<"What Would you like to do Boss!\n";
    cout<<"  1-Display Books Ascending by Title\n";       //Using in-order
    cout<<"  2-Display Books Descending by Title\n";      //Using out-order
    cout<<"  3-Remove a Book by Title\n";
    cout<<"  4-Insert a new Book\n";
    cout<<"  5-Search for a Book by Title\n";
    cout<<"  6-Display Books by Author's Name\n";
    cout<<"  7-Display Books by Category\n";
    cout<<"  8-I had enough of this GOODBYE! (AKA: Exit)\n";
    cin>>option;

    if(option==1 || option==2 || option==5 || option==6 || option==7){
        if(option==1)   {BinaryTree._DisplayASC();}
        if(option==2)   {BinaryTree._DisplayDSC();}
        if(option==5)   {
            cout<<"What is the Book's Title you're looking for: ";
            cin.ignore();
            getline(cin,_title);
            BinaryTree._SearchOnTitle(_title);
        }
        if(option==6)   {
            cout<<"What is the Author's Name you're looking for: ";
            cin.ignore();
            getline(cin,_author);
            BinaryTree._SearchOnAuthor(_author);
        }
        if(option==7)   {
            cout<<"What is the Category you're looking for: ";
            cin.ignore();
            getline(cin,_category);
            BinaryTree._SearchOnCategory(_category);
        }
    }
    if(option==3){
        cout<<"Please Enter the Removed Book Title: ";
        cin.ignore();
        getline(cin,_title);
        BinaryTree._remove(_title);
    }
    if(option==4){
        cout<<"Enter The Required Data.\n";
        cout<<"Title: ";      cin.ignore();   getline(cin,_title);
        cout<<"Author: ";                     getline(cin,_author);
        cout<<"Category: ";                   getline(cin,_category);
        cout<<"Pages: ";                      cin>>_numberOfPages;
        _title[0] = toupper(_title[0]);
        Book _book(_title,_author,_category,_numberOfPages);
        BinaryTree._insert(_book);
        cout<<"Book: \t["<<_title<<"] - ["<<_author<<"] - ["<<_category<<"] - ["<<_numberOfPages<<"].\n";
        cout<<" Has Been Inserted Successfully\n";
    }
    if(option==8){ return; }
    cout<<"\n\n";
    menu(BinaryTree);
}




void readBooks(ifstream &myFile , BST&BinaryTree){
    int numOfBooks;
    string BookTitle;
    string BookAuthor;
    string BookCategory;
    int BookPages;
    string forFirstString;
    myFile>>numOfBooks;
    for(int i=0; i<numOfBooks; i++){;
        myFile.ignore();
        getline(myFile,BookTitle,'\n');
        getline(myFile,BookAuthor,'\n');
        getline(myFile,BookCategory,'\n');
        myFile>>BookPages;
        Book myBook(BookTitle,BookAuthor,BookCategory,BookPages);
        BinaryTree._insert(myBook);
    }
}




int main() {
    BST BinaryTree;
    cout<<"\t\t\tHello and Welcome!.\n" ,
    cout<<"\t\t\t    Team Member:\n\t\t      (Basel Youssef - 20200111)\n\t\t    (Seif ElDein Yasser - 20200241)\n";
    string filePath = "Books.txt";
    ifstream inFile(filePath);
    readBooks(inFile,BinaryTree);
    menu(BinaryTree);
}



