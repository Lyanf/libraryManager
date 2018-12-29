package com.example.demo.controller

import com.example.demo.model.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import java.security.Principal
import javax.servlet.http.HttpSession

//@RestController
@Controller
class MyController {
    @Autowired
    lateinit var userLogRep: UserLogRepository
    @Autowired
    lateinit var userRep: UserRepository
    @Autowired
    lateinit var bookRep: BookRepository
    @Autowired
    lateinit var adminLogRep:AdminLogRepository

    //    定义了注册页面的url
    @RequestMapping("signUpInput")
    fun signUpInputFunc(): String {
        return "signUp"
    }

    //    注册数据收集，并转到主页
    @RequestMapping("/userSignUp", method = [RequestMethod.POST])
    fun userSignUpFunc(ID: String, PW: String, userName: String?,
                       school: String?, major: String?, hobby: String?, born: String?, grade: Int?): String {
        var userDetail = User(ID, userName, school, major, hobby, born, grade)
        var userLog = UserLog(ID, PW)
        userLog.userDetail = userDetail
        userLogRep.save(userLog)
        println(born)
        return "index"
    }

    @RequestMapping("/index")
    fun index():String{
        return "index"
    }
    //    登录接收
    @RequestMapping("/login")
    fun login(session:HttpSession,id: String, pw: String, admin: String?): String {
        if (admin ==null){
            if (userLogRep.findById(id).isPresent== true) {
                if (userLogRep.findById(id).get().pw == pw) {
                    session.setAttribute("id",id)
                    session.setAttribute("admin","no")
                    return "redirect:/userIndex?ID=${id}"
                }
                else return "redirect:/index?error=2"
            }
            else{
                return "redirect:/index?error=1"
            }
        }
        else{
            if (adminLogRep.findById(id).isPresent== true) {
                if (adminLogRep.findById(id).get().pw == pw) {
                    session.setAttribute("id",id)
                    session.setAttribute("admin","yes")
                    return "redirect:/adminIndex?ID=${id}"
                }
                else return "redirect:/index?error=2"
            }
            else{
                return "redirect:/index?error=1"
            }
        }
    }

    //    展示所有用户 （管理员功能）

    @RequestMapping("/adminIndex")
    fun showAllUsers(model: Model): String {
        data class UserShow(var ID: String, var userName: String?)

        var users = mutableListOf<UserShow>()
        for (user in userRep.findAll()) {
            users.add(UserShow(user.id, user.name))
        }
        model.set("users", users)
        return "adminIndex"
    }

    //    进入修改用户的页面(管理员功能)
    @RequestMapping("/changeUserInfoShow")
    fun changeUserInfoShow(model: Model, ID: String): String {
        model.addAttribute("user", userRep.findById(ID).get())
        model.addAttribute("pw", userLogRep.findById(ID).get().pw)
        return "changeUser_admin"
    }

    //    接受修改用户的请求，并且转到管理员主页
    @RequestMapping("/changeUserInfo")
    fun changeUserInfo(ID: String, PW: String, userName: String?,
                       school: String?, major: String?, hobby: String?, born: String?, grade: Int?): String {
        var newUserLog = UserLog(ID, PW)
        newUserLog.userDetail = User(ID, userName, school, major, hobby, born, grade)
        userLogRep.save(newUserLog)
        return "redirect:adminIndex"
    }

    //    进入添加用户的页面
    @RequestMapping("/addUserShow")
    fun addUserShow(): String {
        return "addUser_admin"
    }

    //    增加用户，并转到管理员页面
    @RequestMapping("/addUser")
    fun addUser(ID: String, PW: String, userName: String?,
                school: String?, major: String?, hobby: String?, born: String?, grade: Int?): String {
        var newUserLog = UserLog(ID, PW)
        newUserLog.userDetail = User(ID, userName, school, major, hobby, born, grade)
        userLogRep.save(newUserLog)
        return "redirect:adminIndex"
    }

    @RequestMapping("/delUser")
    fun delUser(ID:String):String{
        userLogRep.deleteById(ID)
        return "redirect:adminIndex"
    }

    //    展示所有图书
    @RequestMapping("/listAllBooks")
    fun listAllBooks(model: Model): String {
        model.addAttribute("books", bookRep.findAll())
        return "findBooks"
    }

    //    增加图书的页面
    @RequestMapping("/addBookShow")
    fun addBookShow(): String {
        return "addBook_admin"
    }

    //    增加图书
    @RequestMapping("/addBook")
    fun addBook(id: String, name: String, price: Float, publish_time: String, publisher: String, type: String): String {
        var newBook = Book(id, name, publisher, publish_time, type, price)
        bookRep.save(newBook)
        return "redirect:/listAllBooks"
    }

    //  修改图书的页面
    @RequestMapping("/changeBookShow")
    fun changeBookShow(model: Model, id: String): String {
        model.addAttribute("book", bookRep.findById(id).get())
        return "changeBook_admin"
    }

    //    修改图书
    @RequestMapping("/changeBook")
    fun changeBook(id: String, name: String, publisher: String?, publish_time: String?, type: String?, price: Float?): String {
        var newBook = Book(id, name, publisher, publish_time, type, price)
        bookRep.save(newBook)
        return "redirect:/listAllBooks"
    }


    @RequestMapping("/userIndex")
    fun userIndex(model: Model, ID: String): String {
        model.addAttribute("user", userRep.findById(ID).get())
        model.addAttribute("borrowedBooks", userLogRep.findById(ID).get().books)
        model.addAttribute("allBooks", bookRep.findAll())
        return "userIndex"
    }

    @RequestMapping("/borrowBook")
    fun borrowBook(userID: String, bookID: String): String {
        val t= userLogRep.findById(userID).get()
        t.books.add(bookRep.findById(bookID).get())
        userLogRep.save(t)
        return "redirect:/userIndex?ID=${userID}"
    }

    @RequestMapping("/returnBook")
    fun returnBook(userID: String, bookID: String): String {
        val t = userLogRep.findById(userID).get()
        t.books.remove(bookRep.findById(bookID).get())
        userLogRep.save(t)
        return "redirect:/userIndex?ID=${userID}"
    }

    @RequestMapping("/changeUserInfoShow_user")
    fun changeUserInfoShow_user(model: Model, ID: String): String {
        model.addAttribute("user", userRep.findById(ID).get())
        model.addAttribute("pw", userLogRep.findById(ID).get().pw)
        return "changeUser"
    }

    @RequestMapping("/changeUserInfo_user")
    fun changeUserInfo_user(ID: String, PW: String, userName: String?,
                            school: String?, major: String?, hobby: String?, born: String?, grade: Int?): String {
        var newUserLog = UserLog(ID, PW)
        newUserLog.userDetail = User(ID, userName, school, major, hobby, born, grade)
        userLogRep.save(newUserLog)
        return "redirect:/userIndex?ID=${ID}"
    }
}