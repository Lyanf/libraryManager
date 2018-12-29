package com.example.demo.model

import org.springframework.data.repository.CrudRepository

interface UserRepository:CrudRepository<User,String>{

}
interface UserLogRepository:CrudRepository<UserLog,String>{

}
interface BookRepository:CrudRepository<Book,String>{

}
interface AdminLogRepository:CrudRepository<AdminLog,String>{

}