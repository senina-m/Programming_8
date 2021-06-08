package ru.senina.itmo.lab8.labwork;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import ru.senina.itmo.lab8.exceptions.InvalidArgumentsException;
import ru.senina.itmo.lab8.Owner;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Class an element of collection
 */
@Entity
public class LabWork  implements Serializable {

    @CreationTimestamp @Setter @Getter
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime creationDate = LocalDateTime.now();
    //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    @Id @Getter
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator_labwork")
    @SequenceGenerator(name = "generator_labwork", sequenceName = "seq_labwork", allocationSize = 1)
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным,

    @Getter
    @Column(name="name", nullable = false)
    private String name; //Поле не может быть null, Строка не может быть пустой

    @Setter @Getter
    @OneToOne(mappedBy = "labWork", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    @JoinColumn(name = "coordinates", nullable = false)
    private Coordinates coordinates; //Поле не может быть null

    @Getter
    @Column(name = "minimalPoint", nullable = false)
    private float minimalPoint; //Значение поля должно быть больше 0

    @Getter
    @Column(name = "description", nullable = false)
    private String description; //Поле не может быть null

    @Getter
    @Column(name = "averagePoint", nullable = false)
    private Integer averagePoint; //Поле не может быть null, Значение поля должно быть больше 0

    @Transient
    @Getter
    @JsonIgnore
    private Difficulty difficulty; //Поле может быть null

    @Basic
    @Getter
    @Column(name = "difficultyIntValue", nullable = false)
    private int difficultyIntValue;

    @ManyToOne()
    @Getter @Setter
    private Discipline discipline; //Поле не может быть null

    @ManyToOne()
    @Getter @Setter
    private Owner owner;

    public LabWork() {
    }

    public LabWork(String name, Coordinates coordinates, float minimalPoint, String description, Integer averagePoint, Difficulty difficulty, Discipline discipline) {
        this.name = name;
        this.coordinates = coordinates;
        this.minimalPoint = minimalPoint;
        this.description = description;
        this.averagePoint = averagePoint;
        this.difficulty = difficulty;
        this.difficultyIntValue = difficulty.getDifficulty();
        this.discipline = discipline;
    }


    @PostLoad
    void fillTransient() {
        if (difficultyIntValue > 0) {
            this.difficulty = Difficulty.of(difficultyIntValue);
        }
    }

    @PrePersist
    void fillPersistent() {
        if (difficulty != null) {
            this.difficultyIntValue = difficulty.getDifficulty();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LabWork labWork = (LabWork) o;
        return Float.compare(labWork.minimalPoint, minimalPoint) == 0 && id.equals(labWork.id) && name.equals(labWork.name) && coordinates.equals(labWork.coordinates) && description.equals(labWork.description) && averagePoint.equals(labWork.averagePoint) && difficulty == labWork.difficulty && discipline.equals(labWork.discipline);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinates, minimalPoint, description, averagePoint, difficulty, discipline);
    }

    public void setMinimalPoint(float minimalPoint) throws InvalidArgumentsException {
        if(minimalPoint > 0){
            this.minimalPoint = minimalPoint;
        }else {
            throw new InvalidArgumentsException("Minimal point can't be less then 0.");
        }
    }

    public void setDescription(String description) throws InvalidArgumentsException {
        if (description != null) {
            this.description = description;
        } else {
            throw new InvalidArgumentsException("Description can't be null.");
        }
    }

    public void setAveragePoint(Integer averagePoint) throws InvalidArgumentsException{
        if (averagePoint != null && averagePoint > 0) {
            this.averagePoint = averagePoint;
        } else {
            throw new InvalidArgumentsException("Average point can't be null or less then 0.");
        }
    }

    public void setDifficulty(String str) throws InvalidArgumentsException{
        boolean rightString = false;
        for(Difficulty difficulty : Difficulty.values()){
            if(str.equals(difficulty.toString())){
                this.difficulty = difficulty;
                this.difficultyIntValue = difficulty.getDifficulty();
                rightString = true;
            }
        }
        if(!rightString){
            throw new InvalidArgumentsException("There is now such value for difficulty.");
        }
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
        this.difficultyIntValue = difficulty.getDifficulty();
    }

    public void copyElement(LabWork labWork){
        this.name = labWork.name;
        this.coordinates = labWork.coordinates;
        this.averagePoint = labWork.averagePoint;
        this.minimalPoint = labWork.minimalPoint;
        this.description = labWork.description;
        this.difficulty = labWork.difficulty;
        this.difficultyIntValue = difficulty.getDifficulty();
        this.discipline = labWork.discipline;
    }

    public long compareById(LabWork labWork) {
        return (this.getId() - labWork.getId());
    }

    public void setName(String name) {
        if(name != null && name.length() > 0) {
            this.name = name;
        }else {
            throw new InvalidArgumentsException("Element name hasn't be null or empty!");
        }
    }

    @JsonIgnore
    public int getX(){
        return coordinates.getX();
    }

    @JsonIgnore
    public long getY(){
        return coordinates.getY();
    }

    @JsonIgnore
    public String getDisciplineName(){
        return discipline.getName();
    }

    @JsonIgnore
    public Long getDisciplineLectureHours(){
        return discipline.getLectureHours();
    }

    @JsonIgnore
    public int getDisciplinePracticeHours(){
        return discipline.getPracticeHours();
    }

    @JsonIgnore
    public int getDisciplineSelfStudyHours(){
        return discipline.getSelfStudyHours();
    }

    @JsonIgnore
    public String getOwnerLogin(){
        return owner.getLogin();
    }

    @JsonIgnore
    public String getStringDifficulty(){
        return Difficulty.of(difficultyIntValue).toString();
    }
}