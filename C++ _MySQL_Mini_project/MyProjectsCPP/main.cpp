#include <iostream>
#include <mysql.h>
#include <windows.h>
#include <string>
#include <sstream>

				//university student management systsem

using namespace std;

const char* HOST = "localhost";
const char* USER = "root";
const char* PW = "dipak";
const char* DB = "unidb";

class University {
private:
    int Id;
    string Name;
    string Subject;
    float CGPA;

public:
    University() : Id(0), Name(""), Subject(""), CGPA(0.0) {}

    void setId(int id) {
        Id = id;
    }
    void setName(string name) {
        Name = name;
    }
    void setSubject(string sub) {
        Subject = sub;
    }
    void setGrade(float cgpa) {
        CGPA = cgpa;
    }

    int getId() {
        return Id;
    }
    string getName() {
        return Name;
    }
    string getSubject() {
        return Subject;
    }
    float getGrade() {
        return CGPA;
    }

};
//*******************Insert function**********************
void showRecords(MYSQL* conn);
void insertStudent(MYSQL* conn, University u) {
    int id;
    string name, subject;
    float cgpa;

    cout << "Enter Id:";
    cin >> id;
    u.setId(id);

    cout << "Enter Name:";
    cin.ignore(); // Ignore the newline 
    getline(cin, name); 
    u.setName(name);

    cout << "Enter Subject:";
    getline(cin, subject);
    u.setSubject(subject);

    cout << "Enter CGPA:";
    cin >> cgpa;
    u.setGrade(cgpa);

    int iId = u.getId();
    float fGrade = u.getGrade();

    stringstream ssId, ssGrade;
    ssId << iId;
    ssGrade << fGrade;
    string sId = ssId.str();
    string sGrade = ssGrade.str();

    string ins = "INSERT INTO students (Id, Name, Subject, CGPA) VALUES ('" + sId + "','" + u.getName() + "','" + u.getSubject() + "','" + sGrade + "')";

    if (mysql_query(conn, ins.c_str())) {
        cout << "Error: " << mysql_error(conn) << endl;
    }
    else {
        cout << "Data inserted successfully" << endl;
        showRecords(conn);
    }
    Sleep(3000);
}


//************************** Show Function **************************

void showRecords(MYSQL*conn){
	
	string show="select* from students";
	
	 if (mysql_query(conn, show.c_str())) {
        cout << "Error: " << mysql_error(conn) << endl;
    }
    else {
        cout << endl<<"----------------------Displaying Data----------------------" << endl;
        MYSQL_RES * rs;    //like resultset in jdbc
        
        rs=mysql_store_result(conn);
        
        if(rs){
        	int num=mysql_num_fields(rs);  //return nums of columns
        	MYSQL_ROW row;
        	while(row=mysql_fetch_row(rs)){    //chek existance of data
        	
        		for(int i=0;i<num;i++){
        			cout<<" "<<row[i];
				}
				cout<<endl;
        		
			}
        	
		}
    }
    Sleep(8000);
	
}


//*************************************** Search Function ***********************************************
void searchData(MYSQL* conn) {
    int id;
    cout << "Enter Id to Search data: ";
    cin >> id;

    stringstream ssId;
    ssId << id;
    string sId = ssId.str();

    string show = "SELECT * FROM students WHERE Id='" + sId + "'";

    if (mysql_query(conn, show.c_str())) {
        cout << "Error: " << mysql_error(conn) << endl;
    }
    else {
        

        MYSQL_RES* rs;
        rs = mysql_store_result(conn);

        if (rs) {
            MYSQL_ROW row = mysql_fetch_row(rs);
            if (row) {
                int num_fields = mysql_num_fields(rs);
                MYSQL_FIELD* fields = mysql_fetch_fields(rs);
                cout << "Data Of student Id='" << sId << "' found!!" << endl;
                
                for (int i = 0; i < num_fields; i++) {
                    cout << fields[i].name << ": " << row[i] << endl;
                }
            }
            else {
                cout << "No data found for Id='" << sId << "'" << endl;
            }
            mysql_free_result(rs);
        }
        else {
            cout << "Error fetching result set" << endl;
        }
    }
    Sleep(6000);
}



//*************************************** Update Function ***********************************************

void updateDetails(MYSQL* conn, University u) {
    int id;
    cout << "Enter Id to update data: ";
    cin >> id;

    u.setId(id);

    stringstream ssId;
    ssId << id;
    string sId = ssId.str();

    string sub;
    cout << "Enter New Subject: ";
    cin >> sub;
    u.setSubject(sub);

    string upd = "UPDATE students SET Subject = '" + u.getSubject() + "' WHERE Id = '" + sId + "'";

    if (mysql_query(conn, upd.c_str())) {
        cout << "Error: " << mysql_error(conn) << endl;
    }
    else {
        cout << "Data updated successfully" << endl;

        string query = "SELECT * FROM students WHERE Id = '" + sId + "'";
        if (mysql_query(conn, query.c_str())) {
            cout << "Error: " << mysql_error(conn) << endl;
        }
        else {
            MYSQL_RES* res = mysql_store_result(conn);
            if (res) {
                MYSQL_ROW row;
                cout <<endl<< "------Updated Data:-----" << endl<<endl;
                while ((row = mysql_fetch_row(res))) {
                    cout << "Id: " << row[0] << ", Name: " << row[1] << ", Subject: " << row[2] << ", CGPA: " << row[3] << endl;
                }
                mysql_free_result(res);
            }
        }
    }

    Sleep(9000);
}

//*************************************** Delete Function ***********************************************

void deleteData(MYSQL* conn){
	
	int id;
    cout << "Enter Id to Delete data: ";
    cin >> id;

    stringstream ssId;
    ssId << id;
    string sId = ssId.str();
    
    string del="Delete from students where Id='"+sId+"'";
    
     if (mysql_query(conn, del.c_str())) {
        cout << "Error: " << mysql_error(conn) << endl;
    }
    else {
        cout << "Data of Student Whose Id= '"+sId+"' is Deleted successfully" << endl;
        cout<<endl<<"----------student data-------------"<<endl;
        showRecords(conn);
        
    }
    Sleep(4000);
}

//----------------------------------------------------------------------------------------------------
//*************************************** Main Function ***********************************************

int main() {
    University u;
    MYSQL* conn;
    conn = mysql_init(NULL);

    if (!mysql_real_connect(conn, HOST, USER, PW, DB, 3306, NULL, 0)) {
        cout << "Error: " << mysql_error(conn) << endl;
    }
    else {
        cout << "Logged in!!!" << endl;
    }
    Sleep(2000);

    bool exit = false;
    while (!exit) {
        system("cls");
        int val;
        cout << endl << endl;
        cout << "\t\t   __________________________________________." << endl;
        cout << "\t\t  /                                         /|" << endl;
        cout << "\t\t / Welcome To University Management System /O|" << endl;
        cout << "\t\t/_________________________________________/__!" << endl;
        cout << "\t\t|                    I___I                |  |" << endl;
        cout << "\t\t|                    |   |                |  |" << endl;
        cout << "\t\t|....................I___I................|__|" << endl;
        cout << "\t  *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*" << endl;
		
        cout << "\t !\t\t 1. Insert Student Data." <<"                  !" <<endl;
        cout << "\t *\t\t 2. Show Student Data." <<"                    *" <<endl;
        cout << "\t !\t\t 3. Search Student Data." <<"                  !" <<endl;
        cout << "\t *\t\t 4. Update Student Data." <<"                  *" <<endl;
        cout << "\t !\t\t 5. Delete Student Data." <<"                  !" <<endl;
        cout << "\t *\t\t 0. Exit" <<"                                  *" <<endl;
		cout << "\t !\t\t" <<"                                          !" <<endl;

		cout << "\t *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*" << endl;
		
		cout << endl;
		
        cout << "\t\t Enter choice: ";
        cin >> val;
        cout << endl;

        if (val == 1) {
            insertStudent(conn, u);
        }
        else if(val==2){
        	showRecords(conn);
        	
		}
		else if(val==3){
			
			searchData(conn);
        	
		}
		else if(val==4){
        	updateDetails(conn,u);
		}
		else if(val==5){
        	deleteData(conn);
		}
        else {
            exit = true;
        }
    }

    mysql_close(conn);
    return 0;
}

