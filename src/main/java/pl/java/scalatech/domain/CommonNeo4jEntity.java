package pl.java.scalatech.domain;


import lombok.Getter;
import lombok.Setter;

import org.springframework.data.neo4j.annotation.GraphId;

public abstract class CommonNeo4jEntity  {
    
    @GraphId @Setter @Getter
    Long id;
    
    @Setter @Getter
    protected boolean available;
    
    @Setter @Getter
    protected boolean disabled;
  
    @Getter @Setter
    protected String desc;
}