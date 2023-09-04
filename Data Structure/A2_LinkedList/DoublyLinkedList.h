#ifndef DOUBLYLINKEDLIST_H_INCLUDED
#define DOUBLYLINKEDLIST_H_INCLUDED

//double linked list
template <class T>
class DlinkedList{
private:
    struct Node{
        T data;     //book
        Node *next;
        Node *prev;
    };
    Node *head,*tail;
    int totalDeleted=0;

public:
    DlinkedList();
    int size();
    void clear();
    bool is_empty();
    void insert(T);
    void sort();
    void removeHead();
    void removeTitle(string);
    void removeAuthor(string);
    void printObject();
    void printAuthor(string);
    void printCategory(string);

























    bool search(string value,int way){
        if(way==1){
            Node *newNode = head;
            while(newNode!=NULL){
                if(newNode->data.getTitle()==value)
                    return true;
                newNode = newNode->next;
            }
        }
        else if(way==2){
            Node *newNode = head;
            while(newNode!=NULL){
                if(newNode->data.getCategory()==value)
                    return true;
                newNode = newNode->next;
            }
        }
        else if(!way){
            Node *newNode = head;
            while(newNode!=NULL){
                if(newNode->data.getAuthor()==value)
                    return true;
                newNode = newNode->next;
            }
        }
        return false;
    }
    void reverse(){
        if(is_empty())
            cout << "empty list";
        else{
            Node* before = head;
            Node* current = head->next;
            Node* after = head->next->next;
            while(current != NULL){
                if(before->prev == NULL){
                    before->prev = current;
                    before->next = NULL;
                }

                current->next = before;
                current->prev = after;
                before = current;
                current = after;
                if(after != NULL)
                    after = after->next;
            }

            head = before;
        }
    }
    void remove(T value){
        Node *newNode = head;
        if(newNode!=NULL && newNode->data==value){
            removeHead();
            return;
        }else{
            Node *temp = head;
            while(newNode!=NULL && newNode->data !=value){
                temp = newNode;
                newNode = newNode->next;
            }
            if(newNode==NULL){
                cout<<"Sorry! We can't find the item you're looking for.\n";
                return;
            }
            temp->next = newNode->next;
            delete newNode;
        }
    }
    void insertSort(T value){
        Node *newNode = new Node;
        newNode->data = value;
        newNode->next = NULL;
        if(head==NULL || head->data >= newNode->data){
            newNode->next = head;
            head = newNode;
            return;
        }
        Node *curr=head;
        while(curr->next!=NULL && curr->next->data < newNode->data)
            curr = curr->next;
        newNode->next = curr->next;
        curr->next = newNode;
    }
    /**                ===     ===    ===                **/
    /**                ===     ===    ===                **/
    /**                ===     ===    ===                **/
    /**                ===     ===    ===                **/
    void addToHead(T value){
        Node *newNode = new Node;
        newNode->data = value;
        newNode->prev = NULL;
        newNode->next = head;
        if(head!=NULL)
            head->prev = newNode;
        head = newNode;
    }
    void addToTail(T value){
        Node *newNode = new Node;
        newNode->data = value;
        newNode->next = NULL;
        newNode->prev = tail;
        if(tail!=NULL)
            tail->next = newNode;
        tail = newNode;
    }
    //Remove Head
    void removeTail(){
        if(is_empty()){
            cout<<"List is Empty\n";
            return;
        }
        if(head==tail){
            delete head;
            head = tail = NULL;
        }else{
            Node *newNode = tail;
            tail = tail->prev;
            tail->next = NULL;
            delete newNode;
        }
    }
    void addAt(T value,int index){
        int MaxSize = size();
        Node *newNode = new Node;
        newNode->data = value;
        if(index>=MaxSize){
            cout<<"Sorry You Exceed The Maximum Size of the list\n\tRange: [0:"<<MaxSize-1<<"]\n";
            return;
        }
        if(index==0)
            addToHead(value);
        else{
            Node *temp = head;
            for(int i=1; i<index; i++)
                temp = temp->next;
            newNode->next = temp->next;
            newNode->prev = temp;
            temp->next->prev = newNode;
            temp->next = newNode;
        }
    }
    void removeAt(int index){
        int MaxSize = size();
        if(head==NULL || index>=MaxSize){
            is_empty()?(cout<<"List is Empty\n"):(cout<<"Sorry You Exceed The Maximum Size of the list\n\tRange: [0:"<<MaxSize-1<<"]\n");
            return;
        }
        if(index==0)
            removeHead();
        else if(index==MaxSize-1)
            removeTail();
        else{
            Node *temp = head->next;
            for(int i=1; i<index; i++)
                temp = temp->next;
            temp->prev->next = temp->next;
            temp->next->prev = temp->prev;
            delete temp;
            /**
            You have [3 5 7] and you want to delete the 5
            line 185: Making the 3 = 7 (link prev -> next el next)
            line 186: Making the 7 = 3 (link next -> prev el prev)
            **/
        }
    }
};

template <class T>
DlinkedList<T>::DlinkedList(){
    head = tail = NULL;
    totalDeleted = 0;
}
template <class T>
int DlinkedList<T>::size(){
    int length=0;
    Node *newNode = head;
    while(newNode!=NULL){
        newNode = newNode->next;
        length++;
    }
    return length;
}
template <class T>
void DlinkedList<T>::clear(){
    Node *newNode = new Node;
    while(!is_empty()){
        newNode = head;
        head = head->next;
        delete newNode;
    }
    head=tail=NULL;
}
template <class T>
bool DlinkedList<T>::is_empty(){
    return head==NULL;
}
template <class T>
void DlinkedList<T>::insert(T value){
    Node *newNode = new Node;
    newNode->data = value;
    newNode->next = NULL;
    if(tail==NULL){
        head=tail=newNode;
        newNode->next = newNode->prev = NULL;
    }
    else{
        newNode->prev = tail;
        newNode->next = NULL;
        tail->next = newNode;
        tail = newNode;
    }
}
template <class T>
void DlinkedList<T>::sort(){
    if(head==NULL){
        cout<<"List is Empty!\n";
        return;
    }
    Node *i = head;
    T temp; //Book
    while(i!=NULL){
        Node *j=i->next;
        while(j!=NULL){
            if(i->data.getPages() > j->data.getPages())
            {
                temp = i->data;
                i->data = j->data;
                j->data = temp;
            }
            j = j->next;
        }
        i = i->next;
    }
}
template <class T>
void DlinkedList<T>::removeHead(){
    if(is_empty()){
        cout<<"List is Empty\n";
        return;
    }
    Node *newNode = head;
    head = head->next;
    head->prev = NULL;
    delete newNode;
}
template <class T>
void DlinkedList<T>::removeTitle(string value){
    Node *newNode = head;
    if(newNode!=NULL && newNode->data.getTitle()==value){
        removeHead();
        cout<<"The Book is deleted"<<endl;
        return;
    }
    else{
        Node *temp = head;
        while(newNode!=NULL && newNode->data.getTitle() !=value){
            temp = newNode;
            newNode = newNode->next;
        }
        if(newNode==NULL){
            cout<<"This Title doesn't exist\n";
            return;
        }
        temp->next = newNode->next;
        cout<<"The Book ["<<newNode->data.getTitle()<<"] is deleted"<<endl;
        delete newNode;
        return;
    }
}
template <class T>
void DlinkedList<T>::removeAuthor(string value){
    Node *i=head;
    //while(i!=NULL){
    Node *newNode = head;
    if(newNode!=NULL && newNode->data.getAuthor()==value){
        cout<<"   -"<<newNode->data.getTitle()<<endl;
        removeHead();
        totalDeleted++;
    }
    else{
        Node *temp = head;
        while(newNode!=NULL && newNode->data.getAuthor() !=value){
            temp = newNode;
            newNode = newNode->next;
        }

        if(newNode==NULL){
            if(!totalDeleted)
                cout<<"This Author doesn't exist"<<endl;
            else
                cout<<"\t\t\t\t\tTotal ["<<value<<"] Deleted Books: "<<totalDeleted<<endl;
            return;
        }
        temp->next = newNode->next;
        cout<<"   -"<<newNode->data.getTitle()<<endl;
        delete newNode;
        totalDeleted++;
    }
    removeAuthor(value);
    //i=i->next;
    //}


}
template <class T>
void DlinkedList<T>::printObject(){
    if(is_empty())
        cout<<"List is Empty!\n";
    Node *newNode = head;
    while(newNode!=NULL){
        cout<<'-'<<endl;
        newNode->data.print();
        newNode = newNode->next;
    }
    cout<<"\t\t\t\t\tTotal Books in List: "<<size()<<endl;
}
template<class T>
void DlinkedList<T>::printAuthor(string value){
    if(is_empty())
        cout<<"List is Empty!\n";
    int Auth=0;
    Node *newNode = head;
    while(newNode!=NULL){
        if(newNode->data.getAuthor()==value){
            cout<<"   -"<<newNode->data.getAuthor()<<"\t - \t"<<newNode->data.getTitle()<<endl;
            Auth++;
        }
        newNode = newNode->next;
    }
    if(Auth)
        cout<<"\t\t\t\t\tTotal ["<<value<<"] Books: "<<Auth<<endl;
    else
        cout<<"\t\t\t\t\tThis Author Does Not Exist\n";
}
template <class T>
void DlinkedList<T>::printCategory(string value){
    if(is_empty())
        cout<<"List is Empty!\n";
    int Cate=0;
    Node *newNode = head;
    while(newNode!=NULL){
        if(newNode->data.getCategory()==value){
            cout<<"   -"<<newNode->data.getCategory()<<"\t - \t"<<newNode->data.getTitle()<<endl;
            Cate++;
        }
        newNode = newNode->next;
    }
    if(Cate)
        cout<<"\t\t\t\t\tTotal ["<<value<<"] Books: "<<Cate<<endl;
    else
        cout<<"\t\t\t\t\tThis Category Does Not Exist\n";
}


#endif
