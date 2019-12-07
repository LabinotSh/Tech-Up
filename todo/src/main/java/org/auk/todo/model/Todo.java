package org.auk.todo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Data
@Entity
@Table(name = "todo")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String description;
    Date dueDate;
    private int priority;
    private String status;
    @CreationTimestamp
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name="FK_user_id"))
    private User user;

    @ManyToOne
    @JoinColumn(name = "label_id", referencedColumnName = "id", foreignKey=@ForeignKey(name = "FK_label_id"))
    Label label;

}
