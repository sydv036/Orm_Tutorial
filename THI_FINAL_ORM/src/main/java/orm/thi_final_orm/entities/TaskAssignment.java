package orm.thi_final_orm.entities;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class TaskAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "workker_id")
    private Workker workker;
    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;
    private LocalDate startDate;
    private LocalDate endDate;

}
