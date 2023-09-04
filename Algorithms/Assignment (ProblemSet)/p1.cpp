#include <bits/stdc++.h>
using namespace std;
using namespace chrono;

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
vector<int>input;
vector<int> indicies;

int Cross_Case3(int  left,int mid,int right,int &s,int &e){
    s = mid;
    int sum = 0;
    int L = INT_MIN;
    for(int i=mid; i>=left; --i){
        sum += input[i];        //Pick
        if(sum > L){
            L = sum;
            s = i;
        }
    }

    e = mid+1;
    sum = 0;
    int R = INT_MIN;
    for(int i=mid+1; i<=right; ++i){
        sum += input[i];        //Pick
        if(sum > R){
            R = sum;
            e = i;
        }
    }

    return L+R;
}
int Divide_Conquer(int left,int right,int &Start,int &End){
    if(left == right){
        Start = End = left;
        return input[left];
    }

    ///[3 Cases]
    //1: Maximum sub-array sum in left array
    //2: Maximum sub-array sum in right array
    //3: Maximum sub-array sum both left and right crosses the mid-point
    int mid = (left+right)/2;
    int ls,le , rs,re , cs,ce;

    int L_Sum = Divide_Conquer(left,mid,ls,le);
    int R_Sum = Divide_Conquer(mid+1,right,rs,re);
    int C_Sum = Cross_Case3(left,mid,right,cs,ce);


    if(L_Sum >= R_Sum && L_Sum >= C_Sum)
        Start = ls, End = le;
    if(R_Sum >= L_Sum && R_Sum >= C_Sum)
        Start = rs, End = re;
    if(C_Sum >= L_Sum && C_Sum >= R_Sum)
        Start = cs, End = ce;
    return max(C_Sum , max(L_Sum , R_Sum));
}


int BruteForce(){
    int sum = INT_MIN;
    for(int i=0; i<n; i++){
        int current_sum = 0;
        vector<int> current_indicies;
        for(int j=i; j<n; j++){
            current_sum += input[j];
            current_indicies.push_back(j);
            if(current_sum > sum){
                sum = current_sum;
                indicies = current_indicies;
            }
        }
    }
    return sum;
}




void Super_Beso(int tc) {
    ///Problem-1:       Finding the Maximum Subarray Sum
    ///Requirements:    [Max Subarray Sum:: 1.Brute Force   2.Divide and Conquer]
    cin>>n;
    input = vector<int>(n);
    for(int &i:input)
        cin>>i;

    int Start,End;


    auto DC_startTime = chrono::high_resolution_clock::now();
    cout<<">>> Divide & Conquer:\tO(n log(n))\n";
    cout<<Divide_Conquer(0,n-1,Start,End)<<"\t-\t[";
    for(int i=Start; i<=End; i++)
        cout<<input[i]<<",]"[i==End];
    cout<<endl;
    auto DC_endTime = chrono::high_resolution_clock::now();
    auto DC_duration = chrono::duration_cast<chrono::microseconds>(DC_endTime - DC_startTime);
    cout<<"-Time: "<<DC_duration.count()<<" ms\n";

    cout<<"\n\n";

    auto BF_startTime = chrono::high_resolution_clock::now();
    cout<<">>> Brute Force:\tO(n square)\n";
    cout<<BruteForce()<<"\t-\t[";
    for(int i=0; i<indicies.size(); i++)
        cout<<input[indicies[i]]<<",]"[i+1==indicies.size()];
    cout<<endl;
    auto BF_endTime = chrono::high_resolution_clock::now();
    auto BF_duration = chrono::duration_cast<chrono::microseconds>(BF_endTime - BF_startTime);
    cout<<"-Time: "<<BF_duration.count()<<" ms\n";

}



//Tip: [Sub: ((a-b)%MOD+MOD)%MOD ]      [Add: (a+b)%MOD ]     (Mul: (a*b)%MOD ]
signed main(void) {
    Beso();
    int tc=1;
    //cin>>tc;
    for(int i=1; i<=tc; i++)
        Super_Beso(i);
}
