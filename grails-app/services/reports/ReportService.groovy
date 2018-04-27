package reports

import grails.converters.JSON
import grails.validation.Validateable
import org.codehaus.groovy.grails.web.converters.exceptions.ConverterException
import org.codehaus.groovy.grails.web.json.JSONObject
import org.springframework.http.HttpRequest

import javax.servlet.http.HttpServletRequest
import java.text.ParseException
import java.text.SimpleDateFormat

@Validateable
class ReportService {

    String getMessageFailedData(def reportInstance) {
        StringBuilder str = new StringBuilder()
        reportInstance.errors.allErrors.each {
            str.append(it)
        }
        return str.toString()
    }


    List<Report> getReports(Integer max, Integer offset) {
        return Report.executeQuery("from Report", [offset: offset, max: max])
    }

    Report getReport(Long id) {
        return Report.get(id)
    }

    List<Report> getReportByLogin(String login) {
        return Report.executeQuery("from Report r where assigned.employer.login = :login", [login: login])
    }

    List<Report> getReportByParams(String login, String projectName) {
        return Report.executeQuery("from Report r where assigned.employer.login = :login and assigned.project.name = :projectName",
                [login: login, projectName: projectName])
    }

    int save(Report reportInstance) {
        int status
        if (reportInstance.validate()) {
            if (reportInstance.save())
                status = 201
            else
                status = 409
        } else {
            status = 400
        }
        return status
    }

    boolean isValidDate(String inDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy")
        dateFormat.setLenient(false)
        try {
            dateFormat.parse(inDate.trim())
        } catch (ParseException pe) {
            return false
        }
        return true
    }

    boolean valdateJson(String jsonText) {
        JSONObject jsonParams
        try {
            jsonParams = JSON.parse(jsonText)
        }
        catch (ConverterException e) {
            return false
        }
        return jsonParams.has("currdate") & jsonParams.has("hours") & jsonParams.has("note") & jsonParams.has("login") & jsonParams.has("projectname")
    }

    def save(String jsonText) {

        if (!valdateJson(jsonText))
            return 415

        JSONObject jsonParams = JSON.parse(jsonText)

        if (!isValidDate(jsonParams.currdate))
            return 412
        def currdate = new Date().parse('dd.MM.yyyy', jsonParams.currdate)

        List<Assigned> assignedList = Assigned.executeQuery("from Assigned where employer.login = :login and project.name = :projectName",
                [login: jsonParams.login, projectName: jsonParams.projectname])
        if (!assignedList || assignedList.size() != 1) {
            return 412
        } else {
            if (jsonParams.has("id")) {
                Report reportInstance = getReport(jsonParams.id)
                if (reportInstance) {
                    reportInstance.hours = jsonParams.hours
                    reportInstance.note = jsonParams.note
                    reportInstance.currdate = currdate
                    reportInstance.assigned = assignedList.get(0)
                    return save(reportInstance)
                } else
                    return 404
            } else
                return save(new Report(currdate: currdate, hours: jsonParams.hours, note: jsonParams.note, assigned: assignedList.get(0)))
        }
    }

    int delete(Long id) {
        int status
        Report reportInstance = getReport(id)
        if (!reportInstance)
            status = 404
        else {
            reportInstance.delete()
            if (Report.get(id))
                status = 409
            else
                status = 201
        }
        return status
    }

}
