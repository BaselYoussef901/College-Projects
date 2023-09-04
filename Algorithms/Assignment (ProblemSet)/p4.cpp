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
int n,m,Target;













/**                              Solution Starts                                    **/
/**                              From Here:"  ^^                                    **/
int DP(vector<int>& coins, int target, vector<int>& usedCoins) {
    vector<int> dp(target + 1, INT_MAX);
    dp[0] = 0;

    for (int i = 1; i <= target; ++i) {
        for (int coin : coins) {
            if (i - coin >= 0 && dp[i - coin] != INT_MAX && dp[i - coin] + 1 < dp[i]) {
                dp[i] = dp[i - coin] + 1;
                usedCoins[i] = coin;
            }
        }
    }
    return dp[target];
}

int Recursive(vector<int>& coins, int target) {
    if (!target)
        return 0;

    int ans = INT_MAX;
    for (int coin : coins) {
        if (target - coin >= 0) {
            int totalCoins = Recursive(coins, target - coin);
            if (totalCoins != INT_MAX)
                ans = min(ansans, totalCoins + 1);
        }
    }
    return ans;
}

signed main() {
    cout<<"Enter number of coins: ";
    cin>>n;
    cout<<"Enter target amount: ";
    cin>>Target;
    cout<<"Enter "<<n<<" Coints: ";
    vector<int> coins;
    for (int i = 0; i <n ; ++i){
        cin>>m;
        coins.push_back(m);
    }

    vector<int> usedCoins(Target + 1, 0);
    auto startTime = chrono::high_resolution_clock::now();
    int DP_ans = DP(coins, Target,usedCoins);
    auto endTime = chrono::high_resolution_clock::now();
    auto DP_Duration = chrono::duration_cast<chrono::microseconds>(endTime - startTime);

    startTime = chrono::high_resolution_clock::now();
    int Rec_ans = Recursive(coins, Target);
    endTime = chrono::high_resolution_clock::now();
    auto Rec_Duration = chrono::duration_cast<chrono::microseconds>(endTime - startTime);

    int currentAmount = Target;
    cout << "\n-Coins used: ";
    while (currentAmount > 0) {
        cout << usedCoins[currentAmount] << " ";
        currentAmount -= usedCoins[currentAmount];
    }
    cout << endl;

    cout << "-DP Result:\t" << DP_ans << " [Time: " << DP_Duration.count() << "] ms" << endl;
    cout << "-Rec Result:\t" << Rec_ans << " [Time: " << Rec_Duration.count() << "] ms" << endl;

    return 0;
}
