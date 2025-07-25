#include <iostream>
#include <string>
using namespace std;


string XOR(string a, string b) {
    string result = "";
    for (int i = 1; i < b.length(); i++) {
      if(a[i]==b[i])
      {
          result=result+'0';
      }
      else
      result=result+'1';
    }
    return result;
}

string div(string dividend, string divisor) {
    int m = divisor.length();
    string temp = dividend.substr(0, m);

    while (m < dividend.length()) {
        if (temp[0] == '1')
            temp = XOR(divisor, temp) + dividend[m];
        else
            temp = XOR(string(m, '0'), temp) + dividend[m];
        m++;
    }


    if (temp[0] == '1')
        temp = XOR(divisor, temp);
    else
        temp = XOR(string(m, '0'), temp);

    return temp;
}


string encodeData(string data, string key) {
    int l_key = key.length();
    string appended_data = data + string(l_key - 1, '0');
    string remainder = div(appended_data, key);
    return data + remainder;
}

// Receiver side to check if data is error-free
bool receiver(string codeword, string key) {
    string remainder = div(codeword, key);
    for (char bit : remainder)
        if (bit != '0') return false;
    return true;
}

int main() {
    string dataword, divisor;
    cout << "Enter dataword (binary): ";
    cin >> dataword;

    cout << "Enter divisor (binary): ";
    cin >> divisor;

    string codeword = encodeData(dataword, divisor);
    cout << "Transmitted Codeword: " << codeword << endl;
    if (receiver(codeword, divisor)) {
        cout << "Receiver: No error detected. Data is correct.\n";
    } else {
        cout << "Receiver: Error detected in the data.\n";
    }


    char choice;
    cout << "Do you want to introduce an error? (y/n): ";
    cin >> choice;

    if (choice == 'y' || choice == 'Y') {
        int pos;
        cout << "Enter bit position to flip (0-based index): ";
        cin >> pos;

        if (pos >= 0 && pos < codeword.length()) {
            codeword[pos] = (codeword[pos] == '0') ? '1' : '0';
            cout << "Codeword after error: " << codeword << endl;
        } else {
            cout << "Invalid bit position. Skipping error injection.\n";
        }
    }

    // Receiver side check
    if (receiver(codeword, divisor)) {
        cout << "Receiver: No error detected. Data is correct.\n";
    } else {
        cout << "Receiver: Error detected in the data!\n";
    }

    return 0;
}
