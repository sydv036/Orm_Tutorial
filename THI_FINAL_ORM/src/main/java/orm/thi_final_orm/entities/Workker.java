package orm.thi_final_orm.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = {"taskAssignments"})
@Builder
public class Workker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private LocalDate bDate;
    private String gender;
    private Double salary;
    private Integer level;
    private String supperWorkerId;
    @OneToMany(mappedBy = "workker", orphanRemoval = true, cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<TaskAssignment> taskAssignments;

}
