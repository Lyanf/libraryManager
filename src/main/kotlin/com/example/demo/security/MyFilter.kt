package com.example.demo.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class LoginFilter : HandlerInterceptor {
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val id: String? = request.session.getAttribute("id") as?String
        if (id == null) {
            response.sendRedirect("/index")
            return false
        } else {
            return true
        }
    }
}

@Component
class judgeCorrectID : HandlerInterceptor {
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        var map = request.parameterMap
        val a = map.get("id")?.get(0)
        val b = map.get("ID")?.get(0)
        val c = map.get("userID")?.get(0)
        val idSavedInSession = request.session.getAttribute("id")
        if (idSavedInSession == a || idSavedInSession == b || idSavedInSession == c) {
            return true
        } else {
            response.sendRedirect("/index")
            return false
        }


    }
}

@Component
class judgeAdmin : HandlerInterceptor {
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        if (request.session.getAttribute("admin") == "yes") {
            return true
        } else {
            response.sendRedirect("/index")
            return false
        }
    }
}

@Configuration
class WebMvcConfig : WebMvcConfigurer {

    @Autowired
    lateinit var myInterceptor: LoginFilter;

    @Autowired
    lateinit var judgeCorrectID: judgeCorrectID

    @Autowired
    lateinit var judgeadmin: judgeAdmin

    @Override
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(myInterceptor).excludePathPatterns("/index", "/login", "/logout", "/signUpInput",
                "/userSignUp").order(1)
        registry.addInterceptor(judgeCorrectID).addPathPatterns("/userIndex",
                "/borrowBook", "/returnBook", "/changeUserInfoShow_user", "/changeUserInfo_user").order(2)
        registry.addInterceptor(judgeadmin).addPathPatterns("/adminIndex", "/changeBook",
                "/changeBookShow", "/addBook",
                "/addBookShow", "/listAllBooks",
                "/addUser", "/addUserShow",
                "/changeUserInfo", "/changeUserInfoShow").order(3)
    }

}