package sba.sms.models;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;


/**
 * Student is a POJO, configured as a persistent class that represents (or maps to) a table
 * name 'student' in the database. A Student object contains fields that represent student
 * login credentials and a join table containing a registered student's email and course(s)
 * data. The Student class can be viewed as the owner of the bi-directional relationship.
 * Implement Lombok annotations to eliminate boilerplate code.
 */

@Entity
@Table(name = "student")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "email") 
@ToString(exclude = "courses") 
public class Student {

    public Student(String string, String string2, String string3) {
		// TODO Auto-generated constructor stub
	}

	@Id
    @Column(name = "email", length = 50)
    private String email;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "password", length = 50, nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinTable(name = "student_courses",
               joinColumns = @JoinColumn(name = "student_email"),
               inverseJoinColumns = @JoinColumn(name = "courses_id"))
    private Set<Course> courses;

}


