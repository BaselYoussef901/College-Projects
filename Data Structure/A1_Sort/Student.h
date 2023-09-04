#include<bits/stdc++.h>
#include <string>
#ifndef STUDENT_H_INCLUDED
#define STUDENT_H_INCLUDED
using namespace std;
int c=0;
class Student {
private:
    string name , id;
    double gpa;
public:
    Student(){
    }
    Student(string name, string id, double gpa) {
        this->name = name;
        this->id = id;
        this->gpa = gpa;
    }
    bool operator<(const Student& obj) {
        c++;
        if(name<obj.name){
            return true;
        }
        else{
            return false;
        }
    }
    friend ostream& operator << (ostream& output, const Student &writeToFile){
            output<<setw(15)<<writeToFile.name<<setw(5)<<"|"<<setw(15)<<writeToFile.id<<setw(5)<<"|"<<setw(15)<<writeToFile.gpa<<endl;
    }
    void setName(string name)   { this->name = name; }
    string getName()            { return name;       }
    void print() {
        cout << "Name:\t" << name << "\nID:\t" << id << "\nGPA:\t" << gpa << endl;
    }
};
#endif // STUDENT_H_INCLUDED
