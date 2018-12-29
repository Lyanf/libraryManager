package com.example.demo.model

import java.util.*
import javax.persistence.*

@Entity
@Table(name="basicuser")
class  UserLog (ID: String, PW: String) {
    @Id
    var id: String = ID
    var pw: String = PW
    @OneToOne(cascade = [CascadeType.ALL],orphanRemoval = true)
    @JoinColumn(name = "detailinfo")
    lateinit var  userDetail:User

    @ManyToMany(cascade = [CascadeType.ALL])
    @JoinTable(name="bookanduser",joinColumns =[JoinColumn(name = "userrrr")],inverseJoinColumns = [JoinColumn(name="bookkk")] )
    var books= mutableSetOf<Book>()
    constructor():this("",""){
    }
}

@Entity
@Table(name="detailuser")
class User(ID: String, Name: String?, School: String?, Major: String?, Hobby: String?,
           BornDate: String?, Grade: Int?) {
    @Id
    var id: String = ID
    var name: String? = Name
    var school: String? = School
    var major: String? = Major
    var hobby: String? = Hobby
    var bornDate: String? = BornDate
    var grade: Int? = Grade
    constructor():this("",null,null,null,null,null,null){}
}

@Entity
@Table(name="adminuser")
class AdminLog(ID: String, PW: String) {
    @Id
    var id: String = ID
    var pw: String = PW
    constructor():this("",""){}
}