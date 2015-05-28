package org.javatraining.integration.google.calendar;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by olga on 27.05.15.
 */
public interface CalendarManager {
    String addCalendar(@NotNull String title, @NotNull List<String> teachersEmails, @NotNull List<String> studentsEmails);

    void removeCalendar(@NotNull String calendarId);

    void addTeacherToCalendar(@NotNull String calendarId, @NotNull String teacherEmail);

    void removeTeacherFromCalendar(@NotNull String calendarId, @NotNull String teacherEmail);

    void addStudentToCalendar(@NotNull String calendarId, @NotNull String studentEmail);

    void removeStudentFromCalendar(@NotNull String calendarId, @NotNull String studentEmail);

    List<String> getListOfCalendarsIds();

    List<String> getListOfCalendarsSummaries();

    List<String> getListOfCalendarSubscribers(@NotNull String calendarId);

}
