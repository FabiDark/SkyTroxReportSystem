package reportsystem;

/**
 * Created by Fabian on 22.01.2016.
 */

public enum ReportStatus {

    SENDET("Verschickt", 0),
    INPROGRESS("In Bearbeitung", 1),
    ACCEPTED("Akzeptiert", 2),
    REJECTED("Abgelehnt", 3);

    String name;
    int id;

    private ReportStatus(String name, int id) {
        this.name = name;
        this.id = id;
    }

}
