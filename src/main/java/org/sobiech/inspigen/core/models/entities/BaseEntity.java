package org.sobiech.inspigen.core.models.entities;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.google.common.base.Objects;

//Klasa stanowiąca bazową encję, zawierającą id rekordu
@MappedSuperclass()
public abstract class BaseEntity {

    @Id						//informacja dla bazy danych, że te pole reprezentuje id rekordu
    @GeneratedValue			//wartość wygenerowana
    @Column(name = "id")
    private Long id; 		//id rekordu

    
    //Gettery i settery
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    //Nadpisana metoda wypisująca ID informacje z nim związane na konsolę
    @Override
    public String toString() {
        return String.format("%s(id=%d)", this.getClass().getSimpleName(), this.getId());
    }

    //Nadpisana metoda do porownywania ID roznych encji
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;

        if (o instanceof BaseEntity) {
            final BaseEntity other = (BaseEntity) o;
            return Objects.equal(getId(), other.getId());
        }
        return false;
    }

    //Nadpisana metoda generujaca haszkod obiektu danej encji
    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}