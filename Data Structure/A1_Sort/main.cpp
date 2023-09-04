#include<bits/stdc++.h>
#include<fstream>
#include "Student.h"
#define ll  long long
using namespace std;

///Quadratic Sorting (Fastest : Insertion Sort)
template<class I> void insertionSort(I *data, int Size) {
    for (int i = 1; i < Size; i++) {      //Insertion Sort - Implementation
        I temp = data[i];
        int j;
        for (j = i; j > 0 && temp < data[j - 1]; j--)
            data[j] = data[j - 1];
        data[j] = temp;
    }
}

///Sub-Quadratic Sorting (Shell Sort)
template<class S> void shellSort(S *data, int Size) {
    int c = log(Size+1)/log(2);
    int gap,i,j;
    S temp;
    for(gap=Size/2; gap>0; gap/=2){
        for(i=gap; i<Size; i++){
            for(j=i-gap; j>=0&&data[j+gap]<data[j]; j-=gap){
                swap(data[j],data[j+gap]);
            }
        }
    }
}
///Sub-Quadratic Sorting (Merge Sort)
template<class M> void mergeSort(M *data, int left, int mid, int right) {
    int nl = mid - left + 1;        //Size of left Subarray
    int nr = right - mid;         //Size of right Subarray

    M L[nl], R[nr];
    for (auto i = 0; i < nl; i++)
        L[i] = data[left + i];
    for (auto j = 0; j < nr; j++)
        R[j] = data[mid + 1 + j];

    int l = 0, r = 0, idx = left;
    ///Sort Left and Right
    while (l < nl && r < nr) {
        if (L[l] < R[r])
            data[idx++] = L[l++];
        else
            data[idx++] = R[r++];
    }
    ///Copy The Remaining of Left
    while (l < nl)
        data[idx++] = L[l++];
    ///Copy The Remaining of Right
    while (r < nr)
        data[idx++] = R[r++];
}
template<class M> void mergeRecursion(M data[], int l, int r) {
    if (l == r)  return;

    auto mid = l + (r - l) / 2;
    mergeRecursion(data, l, mid);
    mergeRecursion(data, mid + 1, r);
    mergeSort(data, l, mid, r);
}

void Read(){
    ///***                      inFile                     ***///
    ifstream inFile;
    inFile.open("students.txt");
        int numOfStudents;
        string firstName,lastName,id;
        double gpa;
    inFile>>numOfStudents;
        Student *insertion_List = new Student[numOfStudents];
        Student *shell_List = new Student[numOfStudents];
        Student *merge_List = new Student[numOfStudents];
    for(int i=0; i<numOfStudents; i++){
        inFile>>firstName>>lastName>>id>>gpa;
        Student obj(firstName+" "+lastName , id , gpa);
        insertion_List[i] = shell_List[i] = merge_List[i] = obj;
    }


    ///***                     outFile                     ***///
    int numberOfComparisons[3];
    ofstream outFile_insertion , outFile_shell , outFile_merge;
        insertionSort(insertion_List , numOfStudents);
    outFile_insertion.open("insertionSort.txt");
        outFile_insertion<<"Number of comparisons = "<<c<<endl;
        numberOfComparisons[0] = c;
        for(int i=0; i<numOfStudents; i++)
            outFile_insertion<<insertion_List[i];
    c=0;
        shellSort(shell_List , numOfStudents);
    outFile_shell.open("shellSort.txt");
        outFile_shell<<"Number of comparisons = "<<c<<endl;
        numberOfComparisons[1] = c;
        for(int i=0; i<numOfStudents; i++)
            outFile_shell<<shell_List[i];
    c=0;
        mergeRecursion(merge_List , 0 , numOfStudents-1);
    outFile_merge.open("mergeSort.txt");
        outFile_merge<<"Number of comparisons = "<<c<<endl;
        numberOfComparisons[2] = c;
        for(int i=0; i<numOfStudents; i++)
            outFile_merge<<merge_List[i];
    c=0;



    ///***              Binary Search               ***///
    auto searchBy_Name = [](string name,int Size,Student arr[]){
        int l=0 , r=Size-1 , mid;
        bool ok=false;
        while(l<=r){
            mid = (l+r)/2;
            if(arr[mid].getName() == name){
                ok=true;
                break;
            }
            else if(arr[mid].getName() > name)
                r=mid-1;
            else
                l=mid+1;
        }
        if(ok)
            return mid;
        else
            return -1;
    };
    //      User Interface    //
    cout<<"Show number of comparisons and sorted array of:\n";
    cout<<"->  1- insertion sort \t 2- shell sort \t\t 3- merge sort\n";
    cout<<"->  4- Search for a student by its name\n->  5- Exit\n";
    int x;
    while(cin>>x){
        ifstream just_a_file;
        string fName,lName,id,whatever;
        double gpa;
        if(x==5)
            break;
        else if(x==4){
            cout<<"Enter Student's full name: ";
            string f,l;   cin>>f>>l;
            int idx = searchBy_Name(f+" "+l , numOfStudents , insertion_List);
            cout<<"Student Position is: "<<idx<<endl;
            if(idx!=-1)
                insertion_List[idx].print();
            cout<<"\n->  1- insertion sort \t 2- shell sort \t\t 3- merge sort\n";
            cout<<"->  4- Search for a student by its name\n->  5- Exit\n";
            continue;
        }else if(x==3){
            just_a_file.open("insertionSort.txt");
            cout<<"Number of Comparisons is: "<<numberOfComparisons[2]<<endl;
        }else if(x==2){
            just_a_file.open("shellSort.txt");
            cout<<"Number of Comparisons is: "<<numberOfComparisons[1]<<endl;
        }else if(x==1){
            just_a_file.open("mergeSort.txt");
            cout<<"Number of Comparisons is: "<<numberOfComparisons[0]<<endl;
        }else{
            cout<<"ERROR 400, Bad Request.\n Try again!\n";
            cout<<"\n->  1- insertion sort \t 2- shell sort \t\t 3- merge sort\n";
            cout<<"->  4- Search for a student by its name\n->  5- Exit\n";
            continue;
        }
        getline(just_a_file,whatever);
        for(int i=0; i<numOfStudents; i++){
            just_a_file>>fName>>lName>>whatever>>id>>whatever>>gpa;
            cout<<setw(15)<<fName+" "+lName<<setw(12)<<id<<setw(10)<<gpa<<endl;
        }
        cout<<"\n->  1- insertion sort \t 2- shell sort \t\t 3- merge sort\n";
        cout<<"->  4- Search for a student by its name\n->  5- Exit\n";
    }
    delete []insertion_List;
    delete []shell_List;
    delete []merge_List;
}

signed main(void) {
    /**             our real main           **/
    Read();
    return 0;
}
