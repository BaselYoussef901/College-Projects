#ifndef BST_BST_H
#define BST_BST_H
#include<bits/stdc++.h>
using namespace std;
#include "Book.h"

struct Node{
    Node *left;
    Node *right;
    Book Data;
};
class BST{
    Node *Root = NULL;
    bool found=false;
    int total=0;
    Node *insert(Node *node,Book book){
        if(node==NULL){
            node = new Node;
            node->Data = book;
            node->left = NULL;
            node->right= NULL;
        }
        else if( book < node->Data )
            node->left = insert(node->left , book);
        else if( node->Data < book)
            node->right = insert(node->right , book);
        return node;
    }
    Node *min(Node *node){
        if(node==NULL)
            return NULL;
        else if(node->left == NULL)
            return node;
        else
            min(node->left);
    }
    Node *max(Node *node){
        if(node==NULL)
            return NULL;
        else if(node->right == NULL)
            return node;
        else
            max(node->right);
    }
    Node *SearchByTitile(Node *node,string _title){
        if(node==NULL || node->Data.getTitle() == _title)
            return node;

        if(node->Data.getTitle()<_title)
            return SearchByTitile(node->right , _title);
        return SearchByTitile(node->left , _title);
    }
    Node *SearchByAuthor(Node *node,string _author){
        if(node==NULL)
            return NULL;
        if(node->Data.getAuthor() == _author) {
            cout << node->Data.getTitle();
            cout << endl;
            found = true;
            total++;
        }
        SearchByAuthor(node->left , _author);
        SearchByAuthor(node->right , _author);
    }
    Node *SearchByCategory(Node *node,string _category){
        if(node==NULL)
            return NULL;
        if(node->Data.getCategory() == _category) {
            cout << node->Data.getTitle();
            cout << endl;
            found = true;
            total++;
        }
        SearchByCategory(node->left , _category);
        SearchByCategory(node->right , _category);
    }
    Node *remove(Node *node , string _title){
        if(node == NULL)
            return NULL;

        if(_title < node->Data.getTitle())
            node->left = remove(node->left , _title);
        else if(node->Data.getTitle() < _title)
            node->right = remove(node->right , _title);
        else if(node->left && node->right){
            Node *deleteNode = min(node->right);
            node->Data = deleteNode->Data;
            node->right = remove(node->right , node->Data.getTitle());
        }else{
            Node *deleteNode = node;
            if(node->left == NULL)
                node = node->right;
            else if(node->right == NULL)
                node = node->left;
            delete deleteNode;
        }
        return node;
    }
    /** Not Needed But i wanna make it more accurate **/
    void in_order(Node *node){
        if(node==NULL)
            return;
        total++;
        in_order(node->left);
        node->Data.print();
        cout<<"-\n";
        in_order(node->right);
    }
    void out_order(Node *node){
        if(node==NULL)
            return;
        total++;
        out_order(node->right);
        node->Data.print();
        cout<<"-\n";
        out_order(node->left);
    }

public:
    void _insert(Book);
    void _remove(string);
    void _SearchOnTitle(string);
    void _SearchOnAuthor(string);
    void _SearchOnCategory(string);
    void _DisplayASC();
    void _DisplayDSC();
};
/**              -PRIVATE- Cpp Implementation      **/









/**                -PUBLIC- Cpp Implementation      **/
void BST::_insert(Book book){
    Root = insert(Root , book);
}
void BST::_remove(string _title){
    Node *node = remove(Root , _title);
    if(node == NULL)
        cout<<"~Book Doesn't Exist!.\n";
    else
        cout<<"Book ["<<_title<<"] Has been Deleted\n";
}
void BST::_SearchOnTitle(string _title){
    Node *node = SearchByTitile(Root , _title);
    if(node == NULL)
        cout<<"~Title Doesn't Exist!.\n";
    else
        cout<<"~Title ["<<node->Data.getTitle()<<"] Has been found in our List\n" ,
                cout<<"    ~Main Author ["<<node->Data.getAuthor()<<"]\n";
}
void BST::_SearchOnAuthor(string _author){
    found = false;
    total = 0;
    cout<<"~Author ["<<_author<<"]:\n";
    Node *node = SearchByAuthor(Root , _author);
    if(!found)
        return cout<<"~Not Exist in our DataBase!."<<endl , void();
    cout<<"\t\t\tTotal ["<<_author<<"] Books: "<<total<<".\n";
}
void BST::_SearchOnCategory(string _category){
    found = false;
    total = 0;
    cout<<"~Category ["<<_category<<"]:\n";
    Node *node = SearchByCategory(Root , _category);
    if(!found)
        return cout<<"~Not Exist in our DataBase!."<<endl , void();
    cout<<"\t\t\tTotal ["<<_category<<"] Books: "<<total<<".\n";
}
void BST::_DisplayASC(){
    total = 0;
    cout<<"-\n";
    in_order(Root);
    cout<<endl;
    cout<<"\t\t\tTotal Books in List: ["<<total<<"]\n\t\t\t     Ascending-Sort\n";
}
void BST::_DisplayDSC(){
    total = 0;
    cout<<"-\n";
    out_order(Root);
    cout<<endl;
    cout<<"\t\t\tTotal Books in List: ["<<total<<"]\n\t\t\t     Descending-Sort\n";
}

#endif //BST_BST_H
//1-Display [Sorted by title in ASC] 'Traverse Asc'     void
//2-Display [Sorted by title in DSC] 'Traverse Dsc'     void
//3-Remove  [Remove by title]        'Remove'           Bool (Yes or No)
//4-Insert  []                       'Inser'            void
//5-Search  [Search by title]        'SearchTitle'      Bool (Yes or No)
//6-Display [Search by Author]       'SearchAuth'       void
//7-Display [Search by Category]     'SearchCat'        void