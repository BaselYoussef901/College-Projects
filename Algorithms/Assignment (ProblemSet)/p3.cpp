#include <bits/stdc++.h>
using namespace std;

typedef long long   ll;
typedef long double ld;
#define print(n,v)      for(int i{}; i<n; i++)  cout<<v[i]<<" \n"[i+1==n];
#define printPair(n,v)  for(int i{}; i<n; i++)  cout<<"("<<v[i].first<<","<<v[i].second<<")"<<" \n"[i+1==n];
#define printVectors(sol)   for(int i{}; i<(int)sol.size(); i++){   cout<<"[ "; for(int j{}; j<(int)sol[i].size(); j++){    cout<<sol[i][j]<<","[j+1==(int)sol[i].size()];   }   cout<<"]"<<"\n "[i+1==(int)sol.size()];  };
#define third(X)        get<2>(X)
#define endl '\n'
#define int long long

void Beso() {
    ios_base::sync_with_stdio(false);
    cin.tie(nullptr);
    cout.tie(nullptr);
    cout << setiosflags(ios::fixed) << setprecision(30);
    freopen("input.txt", "r", stdin), freopen("output.txt", "w", stdout);
}
/**                                 Constrains                                      **/
static int dx8[] = {+0, -0, +1, -1, +1, +1, -1, -1};
static int dy8[] = {+1, -1, +0, -0, +1, -1, +1, -1};
static int dx[] = {+0, +0, -1, +1};
static int dy[] = {-1, +1, +0, +0};
const double PI = 3.14159265358979323846264338327;
const int INF = LONG_LONG_MAX;
const ll XX = 0X3F3F3F3F3F3F3F3F;
const int MOD = 1000000007;        //1e9+7;
const int N = 2e5+5;


/**                                 Declares                                        **/
vector<int>indeg(N);
vector<int>adj[N];
vector<int>v;
int n,m;













/**                              Solution Starts                                    **/
/**                              From Here:"  ^^                                    **/
template<class T>
class SLNode{
private:
    SLNode<T> *next,*down,*back;
    T data;

public:
    SLNode();                           //76
    ~SLNode();                          //81
    void linkNext(SLNode<T> *next);
    void linkDown(SLNode<T> *down);
    void linkBack(SLNode<T> *back);
    void setData(T data);
    SLNode<T> *getNext() const;
    SLNode<T> *getDown() const;
    SLNode<T> *getBack() const;
    T getData()          const;
};
///Class Function Implementation
template<class T>
SLNode<T>::SLNode(){
    this->next = nullptr;
    this->down = nullptr;
}
template<class T>
SLNode<T>::~SLNode(){}
template<class T>
void SLNode<T>::linkNext(SLNode *next) {
    this->next = next;
}
template<class T>
void SLNode<T>::linkDown(SLNode *down) {
    this->down = down;
}
template<class T>
void SLNode<T>::linkBack(SLNode *back) {
    this->back = back;
}
template<class T>
void SLNode<T>::setData(T data) {
    this->data = data;
}
template<class T>
SLNode<T>* SLNode<T>::getNext() const {return next;}
template<class T>
SLNode<T>* SLNode<T>::getDown() const {return down;}
template<class T>
SLNode<T>* SLNode<T>::getBack() const {return back;}
template<class T>
T SLNode<T>::getData() const {return data;}




















template<class T>
class SkipList{
private:
    SLNode<T> *head;
    int level;

    void Display_LinkedList(const SLNode<int>*current_node) const{
        current_node = current_node->getNext();
        while(current_node != NULL){
            cout<<setfill('-')<<setw(5);
            cout<<">("<< current_node->getData()<<")";
            current_node = current_node->getNext();
        }
    };
    void Optimise_Level(int n_insertions){
        if(n_insertions >= level){
            int counter = this->level;
            for(int i=counter; i<=n_insertions; i++){
                SLNode<T>*new_list_nodes = new SLNode<T>;
                SLNode<T>*current_level = this->head;
                new_list_nodes->setData(INF);
                this->head = new_list_nodes;
                this->head->linkDown(current_level);
                this->level++;
            }
        }
    }
    //***************>                   Insert_To_LinkedList                   <************//
    void LinkedList_Insertion(SLNode<T>*&current_link,T data){
        //Inserted Node
        SLNode<T>*new_node = new SLNode<T>;
        new_node->setData(data);

        //Optimal Position
        bool location = false;
        while(!location && current_link->getNext()){
            if(current_link->getNext()->getData() > data)
                location = true;
            else
                current_link = current_link->getNext();
        }

        if(current_link->getNext() == NULL){
            current_link->linkNext(new_node);
            new_node->linkBack(current_link);
        }
        else{
            new_node->linkNext(current_link->getNext());
            current_link->getNext()->linkBack(new_node);
            current_link->linkNext(new_node);
            new_node->linkBack(current_link);
        }

    }
    //***************>                   Probability_To_Switch_Level                   <************//
    int FlipCoin(){
        return rand()%2;
    };

public:
    SkipList();
    ~SkipList();

    void Insert(T data);
    void Erase(T data);
    bool isEmpty() const;
    int Level() const;
    void Display() const;

    SLNode<T>* Find(T key) const;
};


























//^^^^^^^^^^^^^^^                   Constructor                   ^^^^^^^^^^^^^^^//
template<class T>
SkipList<T>::SkipList() {
    this->level = 1;

    //Initial Nodes
    SLNode<T>*_Node0 = new SLNode<T>;
    SLNode<T>*_Node1 = new SLNode<T>;

    //Set Nodes to INF
    _Node0->setData(INF);
    _Node1->setData(INF);

    //Set Links
    _Node1->linkDown(_Node0);
    this->head = _Node1;

    //random insertion method
    srand(time(NULL));
}

//^^^^^^^^^^^^^^^                    Destructor                   ^^^^^^^^^^^^^^^//
template<class T>
SkipList<T>::~SkipList(){
    if(!this->isEmpty()){       //SL Not Empty which has nodes to be deleted
        ///[1.iterate over each level           2.Iterate over linkedlist       3.Delete Nodes]
        for(int i=level; i>=0; --i){
            SLNode<T>*current = this->head;
            current = current->getNext();
            //Delete LinkedList
            while(current != NULL){
                SLNode<T>*prevNode = current->getNext();
                current->linkNext(NULL);
                current->linkDown(NULL);
                delete current;
                current = prevNode;
            }
            //Next Level
            SLNode<T>*lastLevel = this->head;
            head->linkNext(NULL);
            head = head->getDown();
            lastLevel->linkDown(NULL);
            delete lastLevel;
        }
    }
}































//^^^^^^^^^^^^^^^                    Insert                   ^^^^^^^^^^^^^^^//
template<class T>
void SkipList<T>::Insert(T data){
    //Flip Coin
    int n_insertions = 0;
    while(FlipCoin()){
        n_insertions++;
    }

    Optimise_Level(n_insertions);

    SLNode<T>*new_node = new SLNode<T>;
    new_node->setData(data);

    SLNode<T> *current = this->head;
    int current_level = this->level;
    bool found = false;

    while(!found){
        //on right level?
        if(current_level == n_insertions){
            if(current->getNext() && current->getNext()->getData() <= data)
                current = current->getNext();
            else
                found = true;
        }

            //Move down
        else{
            if(current->getNext() && current->getNext()->getData() <= data)
                current = current->getNext();
            else{
                current = current->getDown();
                current_level--;
            }
        }
    }

    //Insert Node
    if(!current->getNext()){
        current->linkNext(new_node);
        new_node->linkBack(current);
    }else{
        new_node->linkNext(current->getNext());
        if(current->getNext())
            current->getNext()->linkBack(new_node);
        current->linkNext(new_node);
        new_node->linkBack(current);
    }


    SLNode<T> *temp = current->getNext();
    for(int i=0; i<n_insertions; i++){
        current = current->getDown();
        LinkedList_Insertion(current , data);
        temp->linkDown(current->getNext());
        temp = temp->getDown();
    }
}

//^^^^^^^^^^^^^^^                    Delete                   ^^^^^^^^^^^^^^^//
template<class T>
void SkipList<T>::Erase(T data){
    SLNode<T>*current = this->Find(data);
    if(!current)
        return;

    while(current){
        SLNode<T>*prev = current->getBack();
        SLNode<T>*temp = current;
        prev->linkNext(current->getNext());
        current->linkBack(nullptr);

        if(current->getNext())
            current->getNext()->linkBack(prev);
        else if(prev->getData() == INF && this->level!=1){
            //Deleting el whole level
            SLNode<T>*temp2 = head;
            head = head->getDown();
            temp2->linkDown(nullptr);
            this->level--;
        }

        current->linkNext(nullptr);
        current = current->getDown();
        temp->linkDown(nullptr);
        delete temp;
    }
}
//^^^^^^^^^^^^^^^                    isEmpty                   ^^^^^^^^^^^^^^^//
template<class T>
bool SkipList<T>::isEmpty() const{
    return (this->head->getDown()->getNext() == NULL);
}
//^^^^^^^^^^^^^^^                    Level                   ^^^^^^^^^^^^^^^//
template<class T>
int SkipList<T>::Level()const{
    return this->level;
}
//^^^^^^^^^^^^^^^                    Display                   ^^^^^^^^^^^^^^^//
template<class T>
void SkipList<T>::Display() const {
    SLNode<T>*current = this->head;
    int _Level = this->level;
    for(int i=0; i<=this->level; i++){
        cout<<"Level "<<_Level-i<<":  ";
        Display_LinkedList(current);
        cout<<endl;

        current = current->getDown();
    }
}


//^^^^^^^^^^^^^^^                    Find                   ^^^^^^^^^^^^^^^//
template<class T>
SLNode<T>* SkipList<T>::Find(T key) const{
    if(this->isEmpty())
        return nullptr;

    SLNode<T>*current = this->head;
    bool found = false;
    while(current && !found){
        if(current->getNext() && current->getNext()->getData() > key)
            current = current->getDown();
        else if(current->getNext() && current->getNext()->getData() < key)
            current = current->getNext();
        else if(current->getNext() && current->getNext()->getData() == key)
            found=true;
        else
            current = current->getDown();
    }

    if(found)
        return current->getNext();
    return nullptr;
}


































//^^^^^^^^^^^^^^^                    AVL                   ^^^^^^^^^^^^^^^//
template<class T>
class AVLNode{
public:
    int Height;
    T data;
    AVLNode *left,*right;
    AVLNode():left(nullptr) , right(nullptr){}
    AVLNode(T _data): left(nullptr) , right(nullptr),data(_data){}
};
template<class T>
class AVL{
private:
    AVLNode<T> *root;
    int getBalanced(AVLNode<T>*);
    int getHeight(AVLNode<T>*);
    void inOrder(AVLNode<T>*);

    AVLNode<T>* _insert(AVLNode<T>* , T);
    AVLNode<T>* _erase(AVLNode<T>* , T);
    AVLNode<T>* _search(AVLNode<T>*, T);
    AVLNode<T>* _rightRotate(AVLNode<T>*);
    AVLNode<T>* _leftRotate(AVLNode<T>*);
    AVLNode<T>* _minValue(AVLNode<T>*);

public:
    AVL():root(nullptr){}

    void insert(T data){
        root = _insert(root , data);
    }
    void erase(T data){
        root = _erase(root , data);
    }
    bool search(T data){
        AVLNode<T>* result = _search(root , data);
        return result != nullptr;
    }
    void Display(){
        inOrder(root);
        cout<<endl;
    }
};
template<class T>
AVLNode<T>* AVL<T>::_insert(AVLNode<T>*node , T data){
    if(!node)   return new AVLNode(data);

    if(data < node->data)
        node->left = _insert(node->left , data);
    else if(data > node->data)
        node->right = _insert(node->right , data);
    else
        return node;

    node->Height = 1 + max(getHeight(node->left) , getHeight(node->right));
    int balance = getBalanced(node);

    //check balanced or should rotation be done
    if(balance > 1){
        if(data < node->left->data)
            return _rightRotate(node);
        else{
            node->left = _leftRotate(node->left);
            return _rightRotate(node);
        }
    }
    if(balance < -1){
        if(data > node->right->data)
            return _leftRotate(node);
        else{
            node->right = _rightRotate(node->right);
            return _leftRotate(node);
        }
    }
    return node;
}
template<class T>
AVLNode<T>* AVL<T>::_erase(AVLNode<T>*node , T data){
    if(node == nullptr) return node;

    if(data < node->data)
        node->left = _erase(node->left , data);
    else if(data > node->data)
        node->right = _erase(node->right , data);
    else{
        if (node->left == nullptr || node->right == nullptr) {
            AVLNode<T>* temp = node->left ? node->left : node->right;
            if (temp == nullptr) {
                temp = node;
                node = nullptr;
            } else
                *node = *temp;
            delete temp;
        } else {
            AVLNode<T>* temp = _minValue(node->right);
            root->data = temp->data;
            root->right = _erase(node->right, temp->data);
        }
    }

    if(node == nullptr)
        return node;

    node->Height = 1 + max(getHeight(node->left) , getHeight(node->right));
    int balance = getBalanced(node);

    if(balance > 1){
        if(getBalanced(node->left) >= 0)
            return _rightRotate(node);
        else{
            node->left = _leftRotate(node->left);
            return _rightRotate(node);
        }
    }
    if(balance < -1){
        if(getBalanced(node->right) <= 0)
            return _leftRotate(node);
        else{
            node->right = _rightRotate(node->right);
            return _leftRotate(node);
        }
    }
    return node;
}
template<class T>
AVLNode<T>* AVL<T>::_search(AVLNode<T>*node , T data) {
    if(node == nullptr || node->data == data)
        return node;
    return (data < node->data? _search(node->left , data) : _search(node->right , data));
}
template<class T>
AVLNode<T>* AVL<T>::_leftRotate(AVLNode<T>*x) {
    AVLNode<T>* y = x->right;
    AVLNode<T>* T2 = y->left;

    y->left = x;
    x->right = T2;

    x->Height = std::max(getHeight(x->left), getHeight(x->right)) + 1;
    y->Height = std::max(getHeight(y->left), getHeight(y->right)) + 1;

    return y;
}
template<class T>
AVLNode<T>* AVL<T>::_rightRotate(AVLNode<T>*y) {
    AVLNode<T>* x = y->left;
    AVLNode<T>* T2 = x->right;

    x->right = y;
    y->left = T2;

    y->Height = std::max(getHeight(y->left), getHeight(y->right)) + 1;
    x->Height = std::max(getHeight(x->left), getHeight(x->right)) + 1;

    return x;
}
template<class T>
AVLNode<T>* AVL<T>::_minValue(AVLNode<T>*node) {
    AVLNode<T>* current = node;
    while (current->left != nullptr)
        current = current->left;
    return current;
}
template<class T>
int AVL<T>::getHeight(AVLNode<T>* node) {
    if (node == nullptr)
        return 0;
    return node->Height;
}
template<class T>
int AVL<T>::getBalanced(AVLNode<T>* node) {
    if (node == nullptr)
        return 0;
    return getHeight(node->left) - getHeight(node->right);
}
template<class T>
void AVL<T>::inOrder(AVLNode<T>*node){
    if(node == nullptr)
        return;

    inOrder(node->left);
    cout<<node->data<<' ';
    inOrder(node->right);
}
































vector<int>In_Insert;
vector<int>In_Delete;
vector<int>In_Search;


void Input_Insert(){
    int data;
    while(cin>>data){
        if(!data) break;
        In_Insert.push_back(data);
    }
}
void Input_Search(){
    int data;
    while(cin>>data){
        if(!data)   break;
        In_Search.push_back(data);
    }
}
void Input_Delete(){
    int data;
    while(cin>>data){
        if(!data)   break;
        In_Delete.push_back(data);
    }
}


SkipList<int>SL;
AVL<int>AVLTree;
void Super_Beso(int tc) {
    ///Problem-3:       SkipList Structure
    ///Requirements:    [1.SL_Node   2.SL_Implementation    3.Required Functions]
    bool ok = true;
    cout << "[1.Insertion\t2.Searching\t3.Deletion\t4.Display]\t5.Exit\n";
    while(ok) {
        int operation;
        cin >> operation;
        if (operation == 1) {
            Input_Insert();
            auto startTime = chrono::high_resolution_clock::now();
            for (int x: In_Insert)
                SL.Insert(x);
            auto endTime = chrono::high_resolution_clock::now();
            auto duration = chrono::duration_cast<chrono::microseconds>(endTime - startTime);
            cout << "\t-Insertion Time [SkipList]: " << duration.count() << " ms\n";


            startTime = chrono::high_resolution_clock::now();
            for (int x: In_Insert)
                AVLTree.insert(x);
            endTime = chrono::high_resolution_clock::now();
            duration = chrono::duration_cast<chrono::microseconds>(endTime - startTime);
            cout << "\t-Insertion Time [AVL]: " << duration.count() << " ms\n";
        }
        if (operation == 2) {
            Input_Search();
            auto startTime = chrono::high_resolution_clock::now();
            for (int x: In_Search) {
                SLNode<int> *result = SL.Find(x);
                if (result)
                    cout << "Value (" << x << "): Exist [SkipList]\n";
                else
                    cout << "Value (" << x << "): Not Exist [SkipList]\n";
            }
            auto endTime = chrono::high_resolution_clock::now();
            auto duration = chrono::duration_cast<chrono::microseconds>(endTime - startTime);
            cout << "\t-Searching Time [SkipList]: " << duration.count() << " ms\n";


            startTime = chrono::high_resolution_clock::now();
            for (int x: In_Search) {
                if (AVLTree.search(x))
                    cout << "Value (" << x << ") Exist [AVL]\n";
                else
                    cout << "Value (" << x << ") Not Exist [AVL]\n";
            }
            endTime = chrono::high_resolution_clock::now();
            duration = chrono::duration_cast<chrono::microseconds>(endTime - startTime);
            cout << "\t-Searching Time [AVL]: " << duration.count() << " ms\n";
        }
        if (operation == 3) {
            Input_Delete();
            auto startTime = chrono::high_resolution_clock::now();
            for (int x: In_Delete)
                SL.Erase(x);
            auto endTime = chrono::high_resolution_clock::now();
            auto duration = chrono::duration_cast<chrono::microseconds>(endTime - startTime);
            cout << "\t-Deletion Time [SkipList]: " << duration.count() << " ms\n";


            startTime = chrono::high_resolution_clock::now();
            for (int x: In_Delete)
                AVLTree.erase(x);
            endTime = chrono::high_resolution_clock::now();
            duration = chrono::duration_cast<chrono::microseconds>(endTime - startTime);
            cout << "\t-Deletion Time [AVL]: " << duration.count() << " ms\n";
        }
        if (operation == 4) {
            auto startTime = chrono::high_resolution_clock::now();
            SL.Display();
            auto endTime = chrono::high_resolution_clock::now();
            auto duration = chrono::duration_cast<chrono::microseconds>(endTime - startTime);
            cout << "\t-Display Time [SkipList]: " << duration.count() << " ms\n";


            startTime = chrono::high_resolution_clock::now();
            AVLTree.Display();
            endTime = chrono::high_resolution_clock::now();
            duration = chrono::duration_cast<chrono::microseconds>(endTime - startTime);
            cout << "\t-Display Time [AVL]: " << duration.count() << " ms\n\n";
        }
        if(operation == 5)
            break;
        cout<<"\n\n";
    }
}



//Tip: [Sub: ((a-b)%MOD+MOD)%MOD ]      [Add: (a+b)%MOD ]     (Mul: (a*b)%MOD ]
signed main(void) {
    Beso();
    int tc=1;
    //cin>>tc;
    for(int i=1; i<=tc; i++)
        Super_Beso(i);
}
