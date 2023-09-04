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
    freopen("input.txt", "r", stdin);
    freopen("output.txt", "w", stdout);
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

template<typename Key,typename Value>
class ChainingMap{
private:
    struct Node{
        pair<Key,Value> Query;
        Node(const pair<Key,Value>&q) : Query(q){};
    };
    vector<list<Node*>>HashTable;
    size_t table_size;

    int getIndex(const Key &key){
        return hash<Key>{}(key) % table_size;       //hash: works as template
    }

public:
    ChainingMap(int size) : table_size(size){
        HashTable.resize(size);
    }

    void _insert(Key key, Value value){
        pair<Key,Value> q = {key , value};
        int index = getIndex(q.first);
        Node* new_node = new Node(q);
        HashTable[index].push_back(new_node);
    }

    void _retrieve(const Key &key){
        cout<<"Key ["<<key<<"]:\t";
        int index = getIndex(key);
        if(HashTable[index].empty())
            return cout<<"Not Found.\n" , void();


        for(Node* node : HashTable[index])
            if(node->Query.first == key)
                cout<<node->Query.second<<", ";
        cout<<endl;
    }

    void _displayHash() {
        for (int i = 0; i < table_size;){
            for (Node *node: HashTable[i++]) {
                _retrieve(node->Query.first);
                break;
            }
        }
    }
    ~ChainingMap(){
        for(list<Node*>& q:HashTable)
            for(Node *node : q)
                delete node;
    }
};




template<typename Value>
class simpleArray{
private:
    int Size;
    Value *Array = new Value[100];
public:
    simpleArray(int size){
        this->Size = size;
        Array = new Value[this->Size];
    }
    void _insert(int key,Value value){
        Array[key] = value;
    }
    Value _get(int key){
        cout<<"Key ["<<key<<"]:  ";
        if(Array[key]=="")
            cout<<"Not Found.";
        cout<<Array[key]<<'\n';
        return Array[key];
    }
    ~simpleArray(){
        delete []Array;
    }
};













void Super_Beso(int tc) {
    ///Problem-2:       Hash Map Implementation and Collision Handling
    ///Requirements:    [Chaining Methodd:: 1.LinkedList   2.Vector act as a chaining-hashmap  3.required functions]
    int _size , _key;
    string _value;
    cin>>_size;

    ChainingMap<int,string>HashMap(_size);
    simpleArray<string>based_Array(_size);





    vector<pair<int,string>>input_pair;
    while(cin>>_key>>_value){
        if(_key == -1)
            break;
        input_pair.push_back({_key,_value});
    }

    auto startTime = chrono::high_resolution_clock::now();
    for(auto [key,value]:input_pair)
        HashMap._insert(key , value);
    auto endTime = chrono::high_resolution_clock::now();
    auto Duration = chrono::duration_cast<chrono::microseconds>(endTime - startTime);
    cout<<"-Insertion Time [Chaining HashMap]: "<<Duration.count()<<" ms\n";

    startTime = chrono::high_resolution_clock::now();
    for(auto [key,value]:input_pair)
        based_Array._insert(key , value);
    endTime = chrono::high_resolution_clock::now();
    Duration = chrono::duration_cast<chrono::milliseconds>(endTime - startTime);
    cout<<"-Insertion Time [Simple Array]: "<<Duration.count()<<" ms\n";








    vector<int>input_search;
    while(cin>>_key)
        input_search.push_back(_key);

    cout<<"\n>>>Retrieval [Chaining HashMap]:\n";
    startTime = chrono::high_resolution_clock::now();
    for(auto key:input_search)
        HashMap._retrieve(key);
    endTime = chrono::high_resolution_clock::now();
    Duration = chrono::duration_cast<chrono::microseconds>(endTime - startTime);
    cout<<"-Retrieval Time [Chaining HashMap]: "<<Duration.count()<<" ms\n";

    cout<<"\n>>>Retrieval [Simple Array]:\n";
    startTime = chrono::high_resolution_clock::now();
    for(auto key:input_search)
        based_Array._get(key);
    endTime = chrono::high_resolution_clock::now();
    Duration = chrono::duration_cast<chrono::milliseconds>(endTime - startTime);
    cout<<"-Retrieval Time [Based-Array]: "<<Duration.count()<<" ms\n\n";





    ///Doing some operations
    cout<<"\n>>>Displaying Whole Hash:\n";
    HashMap._displayHash();


}



//Tip: [Sub: ((a-b)%MOD+MOD)%MOD ]      [Add: (a+b)%MOD ]     (Mul: (a*b)%MOD ]
signed main(void) {
    Beso();
    int tc=1;
    //cin>>tc;
    for(int i=1; i<=tc; i++)
        Super_Beso(i);
}
