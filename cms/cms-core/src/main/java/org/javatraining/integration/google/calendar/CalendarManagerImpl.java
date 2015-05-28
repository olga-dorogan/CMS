package org.javatraining.integration.google.calendar;

import com.google.api.client.googleapis.batch.BatchRequest;
import com.google.api.client.googleapis.batch.json.JsonBatchCallback;
import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.http.HttpHeaders;
import com.google.api.services.calendar.model.AclRule;
import com.google.api.services.calendar.model.Calendar;
import com.google.api.services.calendar.model.CalendarList;
import com.google.api.services.calendar.model.CalendarListEntry;
import org.javatraining.integration.google.calendar.exception.CalendarException;
import org.javatraining.integration.google.calendar.exception.CalendarRoleAlreadyExistsException;
import org.javatraining.integration.google.calendar.exception.CalendarRoleNotExistsException;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by olga on 27.05.15.
 */
@Stateless
public class CalendarManagerImpl implements CalendarManager {

    private static final String CALENDAR_ROLE_TEACHER = "writer";
    private static final String CALENDAR_ROLE_STUDENT = "reader";

    @Inject
    private com.google.api.services.calendar.Calendar calendarService;

    @Override
    public String addCalendar(@NotNull String title, @NotNull List<String> teachersEmails, @NotNull List<String> studentsEmails) {
        try {
            Calendar calendar = new Calendar();
            calendar.setSummary(title);
            Calendar addedCalendar = calendarService.calendars().insert(calendar).execute();
            if (addedCalendar == null) {
                throw new CalendarException("Calendar " + title + " can't be added.");
            }

            BatchRequest batch = calendarService.batch();
            JsonBatchCallback<AclRule> callback = new JsonBatchCallback<AclRule>() {
                @Override
                public void onSuccess(AclRule aclRule, HttpHeaders httpHeaders) throws IOException {
                }

                @Override
                public void onFailure(GoogleJsonError e, HttpHeaders responseHeaders) {
                    throw new CalendarException(e.getMessage());
                }
            };
            AclRule rule;
            AclRule.Scope scope;

            rule = new AclRule();
            scope = new AclRule.Scope();
            scope.setType("default");
            rule.setScope(scope).setRole("reader");
            calendarService.acl().insert(addedCalendar.getId(), rule).queue(batch, callback);

            for (String teacherEmail : teachersEmails) {
                rule = new AclRule();
                scope = new AclRule.Scope();
                scope.setType("user").setValue(teacherEmail);
                rule.setScope(scope).setRole(CALENDAR_ROLE_TEACHER);
                calendarService.acl().insert(addedCalendar.getId(), rule).queue(batch, callback);
            }

            for (String studentEmail : studentsEmails) {
                rule = new AclRule();
                scope = new AclRule.Scope();
                scope.setType("user").setValue(studentEmail);
                rule.setScope(scope).setRole(CALENDAR_ROLE_STUDENT);
                calendarService.acl().insert(addedCalendar.getId(), rule).queue(batch, callback);
            }

            batch.execute();
            return addedCalendar.getId();

        } catch (IOException e) {
            throw new CalendarException(e);
        }
    }

    @Override
    public void removeCalendar(@NotNull String calendarId) {
        try {
            calendarService.calendars().delete(calendarId).execute();
        } catch (IOException e) {
            throw new CalendarException(e);
        }
    }

    @Override
    public void addTeacherToCalendar(@NotNull String calendarId, @NotNull String teacherEmail) {
        addPersonToCalendar(calendarId, teacherEmail, CALENDAR_ROLE_TEACHER);
    }

    @Override
    public void removeTeacherFromCalendar(@NotNull String calendarId, @NotNull String teacherEmail) {
        removePersonFromCalendar(calendarId, teacherEmail, CALENDAR_ROLE_TEACHER);
    }

    @Override
    public void addStudentToCalendar(@NotNull String calendarId, @NotNull String studentEmail) {
        addPersonToCalendar(calendarId, studentEmail, CALENDAR_ROLE_STUDENT);
    }

    @Override
    public void removeStudentFromCalendar(@NotNull String calendarId, @NotNull String studentEmail) {
        removePersonFromCalendar(calendarId, studentEmail, CALENDAR_ROLE_STUDENT);
    }


    @Override
    public List<String> getListOfCalendarsIds() {
        try {
            CalendarList feed = calendarService.calendarList().list().execute();
            List<String> calendarsIds = new ArrayList<>();
            if (feed.getItems() != null) {
                for (CalendarListEntry entry : feed.getItems()) {
                    calendarsIds.add(entry.getId());
                }
            }
            return calendarsIds;
        } catch (IOException e) {
            throw new CalendarException(e);
        }
    }


    @Override
    public List<String> getListOfCalendarsSummaries() {
        try {
            CalendarList feed = calendarService.calendarList().list().execute();
            List<String> calendarsSummaries = new ArrayList<>();
            if (feed.getItems() != null) {
                for (CalendarListEntry entry : feed.getItems()) {
                    calendarsSummaries.add(entry.getSummary());
                }
            }
            return calendarsSummaries;
        } catch (IOException e) {
            throw new CalendarException(e);
        }
    }

    @Override
    public List<String> getListOfCalendarSubscribers(@NotNull String calendarId) {
        try {
            List<AclRule> aclRules = calendarService.acl().list(calendarId).execute().getItems();
            List<String> emailsList = new ArrayList<>();
            if (aclRules != null) {
                for (AclRule aclRule : aclRules) {
                    emailsList.add(aclRule.getScope().getValue());
                }
            }
            return emailsList;
        } catch (IOException e) {
            throw new CalendarException(e);
        }
    }

    //TODO: реализовать полное удаление подписки на календарь со стороны подписчика
    private void removePersonFromCalendar(String calendarId, String email, String role) {
        try {
            String deletedRuleId = getRuleIdForPerson(calendarId, email, role);
            if (deletedRuleId == null) {
                throw new CalendarRoleNotExistsException(role + " with email " + email + " doesn't exist.");
            }
            calendarService.acl().delete(calendarId, deletedRuleId).execute();
        } catch (IOException e) {
            throw new CalendarException(e);
        }
    }

    private void addPersonToCalendar(String calendarId, String email, String role) {
        try {
            String ruleId = getRuleIdForPerson(calendarId, email, role);
            if (ruleId != null) {
                throw new CalendarRoleAlreadyExistsException(role + " with email " + email + " is already exists.");
            }
            AclRule rule = new AclRule();
            AclRule.Scope scope = new AclRule.Scope();
            scope.setType("user").setValue(email);
            rule.setScope(scope).setRole(role);
            calendarService.acl().insert(calendarId, rule).execute();
        } catch (IOException e) {
            throw new CalendarException(e);
        }
    }

    private String getRuleIdForPerson(String calendarId, String email, String role) throws IOException {
        List<AclRule> aclRules = calendarService.acl().list(calendarId).execute().getItems();
        String ruleId = null;
        if (aclRules != null) {
            for (AclRule aclRule : aclRules) {
                if (aclRule.getScope().getValue().equalsIgnoreCase(email) && aclRule.getRole().equalsIgnoreCase(role)) {
                    ruleId = aclRule.getId();
                }
            }
        }
        return ruleId;
    }

}
