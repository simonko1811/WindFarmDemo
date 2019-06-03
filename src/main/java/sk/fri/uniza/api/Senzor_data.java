package sk.fri.uniza.api;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "Senzors_data")
public class Senzor_data {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Length(max=45)
    @Column
    @NotNull
    private String kto;
    @Column
    private int co;
    @Column
    @NotNull
    private DateTime kedy;

    public Senzor_data() {}

    public Senzor_data(String kto, int co, DateTime kedy) {
        this.kto = kto;
        this.co = co;
        this.kedy = kedy;
    }

    public Senzor_data(Long id, String kto, int co, DateTime kedy) {
        this.id = id;
        this.kto = kto;
        this.co = co;
        this.kedy = kedy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKto() {
        return kto;
    }

    public void setKto(String kto) {
        this.kto = kto;
    }

    public int getCo() {
        return co;
    }

    public void setCo(int co) {
        this.co = co;
    }

    public DateTime getKedy() {
        return kedy;
    }

    public void setKedy(DateTime kedy) {
        this.kedy = kedy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Senzor_data that = (Senzor_data) o;
        return id.equals(that.id) &&
                Objects.equals(kto, that.kto) &&
                Objects.equals(co, that.co) &&
                Objects.equals(kedy, that.kedy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, kto, co, kedy);
    }
}
