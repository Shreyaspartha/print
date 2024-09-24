package print.models;

import lombok.Data;

import java.lang.reflect.Array;
import java.util.List;

@Data
public class PrintTask {
    private Integer numberOfCopies;

    private String qname;

    private String username;

    private PrintContent[] printContents;
}
