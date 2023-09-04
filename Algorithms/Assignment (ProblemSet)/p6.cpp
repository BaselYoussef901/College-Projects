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
vector<int>adj[N];
vector<int>v;
int n,m;













/**                              Solution Starts                                    **/
/**                              From Here:"  ^^                                    **/
struct Edge{
    char from , to;
    int weight;
    Edge(char t,int w): to(t) , weight(w){}
    Edge(char f,char t,int w): from(f) , to(t) , weight(w){}
};
vector<Edge>G[N];
int verticies , edges;
vector<char>path;
vector<int>cost;
vector<bool>vis(N,false);
void Dijkstra(char vertex){
    vector<int>distances(verticies , INF);
    distances[vertex-'A'] = 0;
    priority_queue<pair<int,int> , vector<pair<int,int>> , greater<pair<int,int>> >pq;
    pq.push({0,vertex-'A'});
    while(!pq.empty()){
        int u = pq.top().second;
        int dist = pq.top().first;
        pq.pop();

        if(dist > distances[u])
            continue;

        for(const Edge &e:G[u]){
            int v = e.to - 'A';
            if(dist+e.weight < distances[v]){
                distances[v] = dist + e.weight;
                pq.push({distances[v] , v});
            }
        }
    }

    int mn = INF;
    int pos = 0;
    for (int i = 0; i < verticies; ++i) {
        if(distances[i] && distances[i]<mn && !vis['A'+i])
            mn = distances[i] , pos = i;
        cout << "from " << vertex << " to " << char('A'+i) << ": " << distances[i] << endl;
    }
    cout<<"-\n";
    if(!vis[pos+'A']) {
        path.push_back(char('A' + pos));
        cost.push_back(distances[pos] + cost.back());
    }
    vis['A'+pos] = true;
}


void NaiveShortestPaths(int start_vertex) {
    vector<int> distances(verticies, INF);
    distances[start_vertex - 'A'] = 0; // Cast 'A' to integer

    for (int i = 0; i < verticies; i++) {
        for (int u = 0; u < verticies; u++) {
            for (const Edge &e : G[u]) {
                int v = e.to - 'A';
                int w = e.weight;
                if (distances[u] != INF && distances[u] + w < distances[v]) {
                    distances[v] = distances[u] + w;
                }
            }
        }
    }

    int mn = INF;
    int pos = 0;
    cout << "-\n";
    for (int i = 0; i < verticies; ++i) {
        if (distances[i] && distances[i] < mn && !vis['A' + i]) {
            mn = distances[i], pos = i;
        }
        cout << "from " << char(start_vertex) << " to " << char('A' + i) << ": " << distances[i] << endl; // Cast 'A' + i to character
    }
    cout << "-\n\n";
    if (!vis[pos + 'A']) {
        path.push_back(char('A' + pos));
        cost.push_back(distances[pos] + cost.back());
    }
    vis['A' + pos] = true;
}







void Super_Beso(int tc) {
    ///Problem-6:       Dijkstra Algorithm
    ///Requirements:    [1.Path of Verticies     2.Cost Each Vertex to its next]
    cin>>verticies>>edges;
    for(int i=0; i<edges; i++){
        char f,t;
        int w;
        cin>>f>>t>>w;
        G[f-'A'].push_back({t,w});
        G[t-'A'].push_back({f,w});
    }

    cout<<">>> Dijkstra Algorithm:\n";
    char start_vertex;
    cin>>start_vertex;
    path.push_back(start_vertex);
    cost.push_back(0);
    vis[(int)start_vertex] = true;

    auto startTime = chrono::high_resolution_clock::now();
    Dijkstra(start_vertex);
    for(int i=0; i<verticies-1; i++)
        Dijkstra(path.back());
    auto endTime = chrono::high_resolution_clock::now();



    cout<<"Path:\t[";
    for(int i=0; i<path.size(); i++)
        cout<<path[i]<<",]"[i+1==path.size()];
    cout<<"\nCost:\t[";
    for(int i=0; i<cost.size(); i++)
        cout<<cost[i]<<",]"[i+1==cost.size()];

    auto duration = chrono::duration_cast<chrono::microseconds>(endTime - startTime);
    cout<<"\n-Time: "<<duration.count()<<" ms\n";






    cout << "\n\n\n\n\n>>> Naive Approach:\n";
    path.push_back(start_vertex);
    cost.push_back(0);
    vis[(int) start_vertex] = true;

    startTime = chrono::high_resolution_clock::now();
    NaiveShortestPaths(start_vertex);
    for(int i=0; i<verticies-1; i++)
        NaiveShortestPaths(path.back());
    endTime = chrono::high_resolution_clock::now();
    duration = chrono::duration_cast<chrono::microseconds>(endTime - startTime);

    cout << "Path:\t[";
    for (int i = 0; i < path.size(); i++)
        cout << path[i] << ",]"[i + 1 == path.size()];
    cout << "\nCost:\t[";
    for (int i = 0; i < cost.size(); i++)
        cout << cost[i] << ",]"[i + 1 == cost.size()];
    cout << "\n-Time: " << duration.count() << " ms\n";


}


//Tip: [Sub: ((a-b)%MOD+MOD)%MOD ]      [Add: (a+b)%MOD ]     (Mul: (a*b)%MOD ]
signed main(void) {
    Beso();
    int tc=1;
    //cin>>tc;
    for(int i=1; i<=tc; i++)
        Super_Beso(i);

}
