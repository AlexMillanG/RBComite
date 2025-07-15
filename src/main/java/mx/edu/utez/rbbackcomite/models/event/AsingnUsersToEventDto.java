package mx.edu.utez.rbbackcomite.models.event;

import java.util.List;

public class AsingnUsersToEventDto {

    private Long eventId;
    private List<Long> userIds;


    public AsingnUsersToEventDto() {
    }
    public AsingnUsersToEventDto(Long eventId, List<Long> userIds) {
        this.eventId = eventId;
        this.userIds = userIds;
    }

    //getters y setters
    public Long getEventId() {
        return eventId;
    }
    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }
    public List<Long> getUserIds() {
        return userIds;
    }
    public void setUserIds(List<Long> userIds) {
        this.userIds = userIds;
    }


}
