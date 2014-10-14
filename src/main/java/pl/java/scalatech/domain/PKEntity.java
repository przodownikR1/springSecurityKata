package pl.java.scalatech.domain;

import java.time.LocalDate;

import lombok.Data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mysema.query.annotations.QueryEntity;

@Document
@Data
@QueryEntity
public abstract class PKEntity<T> implements PKNature<T> {
    /**
	 * 
	 */
    private static final long serialVersionUID = 7669211182758111346L;

    @Id
    private T id;

    protected boolean disabled;

    protected LocalDate dateModification;

    protected LocalDate dateAdded;

}