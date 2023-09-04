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

struct Edge{
    char from , to;
    int weight;
    Edge(char t,int w): to(t), weight(w){}                      //Prim's
    Edge(char f,char t,int w): from(f), to(t), weight(w){}      //Kruskal's
};
vector<Edge> G[N];
int verticies , edges;



void Prim_MST(char vertex){
    vector<bool> vis(verticies , false);
    vector<int> minCost(verticies , INF);
    vector<char>parent(verticies , '-');

    int start_vertex_index = vertex - 'A';
    minCost[start_vertex_index] = 0;
    priority_queue<pair<int,int> , vector<pair<int,int>> , greater<pair<int,int>> >pq;
    pq.push({0 , start_vertex_index});
    while(!pq.empty()){
        int u = pq.top().second;
        pq.pop();
        vis[u] = true;

        for(const Edge &e:G[u]){
            int v = e.to - 'A';
            if(!vis[v] && e.weight < minCost[v]){
                parent[v] = u+'A';
                minCost[v] = e.weight;
                pq.push({minCost[v] , v});
            }
        }
    }


    for(int i=0; i<verticies; i++){
        if(parent[i] != '-')
            cout << parent[i] << " - " << char('A' + i) << " (Weight: " << minCost[i] << ")\n";
    }
}














bool compareEdge(const Edge &a , const Edge &b){
    return a.weight < b.weight;
}
int find(vector<int>parent , int x){
    if(parent[x] == -1)
        return x;
    return find(parent , parent[x]);
}
void Connect(vector<int>&parent , int from , int to){
    int P_From = find(parent , from);
    int P_To   = find(parent , to);
    parent[P_From] = P_To;
}
vector<Edge>Path;
void Kruskal_MST(const vector<Edge>Krus){
    vector<Edge>MST;
    vector<int>parent(verticies , -1);
    for(const Edge &e:Krus){
        int ParentFrom = find(parent , e.from-'A');
        int ParentTo   = find(parent , e.to-'A');

        if(ParentFrom != ParentTo){
            MST.push_back(e);
            Connect(parent , ParentFrom , ParentTo);
        }
    }


    Path = MST;
    for(const Edge &e:MST)
        cout<<e.from<<" - "<<e.to<<" (Weight: "<<e.weight<<")\n";

}



















void BruteForce_MST(){
    vector<char> Brute_MST;
    int minWeight = INF;
    for(char current_vertex = 'A';  current_vertex < 'A'+verticies; current_vertex++){
        vector<char> current_MST;
        current_MST.push_back(current_vertex);
        do{
            int currentWeight = 0;
            vector<char> currentEdges = current_MST;
            for(char u:current_MST){
                for(const Edge e:G[u-'A']){
                    char v = e.to;
                    if(find(current_MST.begin() , current_MST.end() , v) == current_MST.end()){
                        currentWeight += e.weight;
                        currentEdges.push_back(v);
                    }
                }
            }
            if(currentWeight < minWeight && currentEdges.size() == verticies){
                minWeight = currentWeight;
                Brute_MST = currentEdges;
            }
        }while(next_permutation(current_MST.begin() , current_MST.end()));
    }


    cout<<"[";
    for(int i=0; i<Brute_MST.size(); i++)
        cout<<Brute_MST[i]<<"-]"[i+1==Brute_MST.size()];
    cout<<'\n';
}




void Super_Beso(int tc) {
    ///Problem-5:       Minimum Spanning Tree (AKA: MST)
    ///Requirements:    [1.Prim's     2.Kruskal's     3.Brute's]
    cin>>verticies>>edges;
    vector<Edge>Krus;
    char startVertex;

    for(int i=0; i<edges; i++){
        char from,to;
        int weight;
        cin>>from>>to>>weight;
        G[from-'A'].push_back({to , weight});
        G[to-'A'].push_back({from , weight});

        Edge edge(from,to,weight);
        Krus.push_back(edge);
        if(!i)
            startVertex = edge.from;
    }

    cout<<">>> Prim's Algorithm MST:\n";
    auto Prim_startTime = chrono::high_resolution_clock::now();
    Prim_MST(startVertex);
    auto Prim_endTime = chrono::high_resolution_clock::now();
    auto Prim_duration = chrono::duration_cast<chrono::microseconds>(Prim_endTime - Prim_startTime);
    cout<<"-Time: "<<Prim_duration.count()<<" ms\n";
    cout<<"\n\n\n";

    cout<<">>> Kruskal's Algorithm MST:\n";
    sort(Krus.begin() , Krus.end() , compareEdge);
    auto Krus_startTime = chrono::high_resolution_clock::now();
    Kruskal_MST(Krus);
    auto Krus_endTime = chrono::high_resolution_clock::now();
    auto Krus_duration = chrono::duration_cast<chrono::microseconds>(Krus_endTime - Krus_startTime);
    cout<<"-Time: "<<Krus_duration.count()<<" ms\n";
    cout<<"\n\n\n";

    cout<<">>> Brute's Algorithm MST:\n";
    auto Brute_startTime = chrono::high_resolution_clock::now();
    BruteForce_MST();
    auto Brute_endTime = chrono::high_resolution_clock::now();
    auto Brute_duration = chrono::duration_cast<chrono::microseconds>(Brute_endTime - Brute_startTime);
    cout<<"-Time: "<<Brute_duration.count()<<" ms\n";
    cout<<"\n\n\n";

}



//Tip: [Sub: ((a-b)%MOD+MOD)%MOD ]      [Add: (a+b)%MOD ]     (Mul: (a*b)%MOD ]
signed main(void) {
    Beso();
    int tc=1;
    //cin>>tc;
    for(int i=1; i<=tc; i++)
        Super_Beso(i);
}
