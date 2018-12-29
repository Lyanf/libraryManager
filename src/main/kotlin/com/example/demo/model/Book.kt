package com.example.demo.model
import org.springframework.beans.factory.annotation.Autowired
import java.util.*
import javax.persistence.*

@Entity
@Table(name="book")
class Book(ID: String, Name: String, Publisher: String?, PublishTime: String?, Type: String?, Price: Float?) {
    @Id
    var id: String = ID
    var name: String = Name
    var publisher: String? = Publisher
    var publishTime: String? = PublishTime
    var type: String? = Type
    var price: Float? = Price
    @ManyToMany(cascade = [CascadeType.ALL],mappedBy = "books")
    var userLogs= mutableListOf<UserLog>()
    constructor():this("","",null,null,null,null){}
}