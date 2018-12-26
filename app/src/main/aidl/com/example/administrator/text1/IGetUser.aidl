// IGetUser.aidl
package com.example.administrator.text1;
import com.example.administrator.text1.Person;

interface IGetUser {

    List<Person> getPersonInfo(String value);
}
