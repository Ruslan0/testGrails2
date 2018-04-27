package reports

import spock.lang.Specification

/**
 *
 */
class ReportServiceIntSpec extends Specification {

    def reportService
    def projectService

    Assigned assigned1
    Assigned assigned2
    Assigned assigned3

    def setup() {
        Project project1 = new Project(name: 'Project1')
        Project project2 = new Project(name: 'Project2')

        Employer user1 = new Employer(login: 'user1', password: 'secret')
        Employer user2 = new Employer(login: 'user2', password: '123456')

        Project.saveAll([project1, project2])
        Employer.saveAll([user1, user2])
        assigned1 = new Assigned(project: project1, employer: user1)
        assigned2 = new Assigned(project: project2, employer: user1)
        assigned3 = new Assigned(project: project1, employer: user2)
        Assigned.saveAll(assigned1, assigned2, assigned3)
    }

    def cleanup() {
    }

    void "The test to add reports with properly params and filter ones"() {

        when: "The add is executed"
        reportService.save(new Report(assigned: assigned1, currdate: "01.01.2012", hours: 2, note: "Research"))
        reportService.save(new Report(assigned: assigned2, currdate: "01.01.2012", hours: 2, note: "Research"))
        reportService.save(new Report(assigned: assigned3, currdate: "01.01.2012", hours: 2, note: "Research"))
        then: "The all reports were added"
        Report.count() == 3
        then: "The first report return properly value"
        reportService.getReport(1).assigned == assigned1

        when: "service is called to search by login"
        ArrayList<Report> reports = reportService.getReportByLogin("user1")
        then: "reports are found with appropriate login"
        reports.size() == 2
        when: "service is called to search by params"
        reports = reportService.getReportByParams("user1", "Project2")
        then: "reports are found with appropriate params"
        reports.size() == 1
    }

    void "Test to add report with not existing assigned"() {
        when: "The add is executed"
        reportService.save(new Report(assigned: 10, currdate: "01.01.2012", hours: 2, note: "Research"))
        then: "The report hasn't been added "
        Report.count() == 0
    }

    void "Test to add reports for validate"() {
        when: "The add is executed"
        reportService.save(new Report(assigned: assigned1, currdate: "01.01.2012", hours: 15, note: "Research"))
        reportService.save(new Report(assigned: assigned1, currdate: "01.01.2012", hours: 8, note: "Research"))
        reportService.save(new Report(assigned: assigned1, currdate: "01.01.2012", hours: 4, note: "Research"))
        reportService.save(new Report(assigned: assigned1, currdate: "01-01-2012", hours: 2, note: "Research"))
        then: "Some reports were't been added: (repoort 3: 23 hours + 4 hours > 24; repoort 4 wrong date format)"
        Report.count() == 2
    }

    void "Test to add report with the appropriate login and projectNAme"() {
        given:
        String jsonObject = '{"currdate": "24.04.2018", "hours": 8, "note": "www", "projectname": "Test1", "login": "admin"}'
        when: "The add is executed"
        int status = reportService.save(jsonObject)
        then: "The report hasn't been added "
        status == 201
    }

    void "Test to add report with the not appropriate login and projectNAme"() {
        given:
        String jsonObject = '{"currdate": "24.04.2018", "hours": 8, "note": "www", "projectname": "Test3", "login": "admin"}'
        when: "The add is executed"
        int status = reportService.save(jsonObject)
        then: "The report hasn't been added "
        status == 412
    }

    void "Test to add report with the wrong format of currdate"() {
        given:
        String jsonObject = '{"currdate": "24-04-2018", "hours": 8, "note": "www", "projectname": "Test3", "login": "admin"}'
        when: "The add is executed"
        int status = reportService.save(jsonObject)
        then: "The report hasn't been added "
        status == 412
    }

    void "Test to add report with the wrong json format "() {
        given:
        String jsonObject = 'poipoi{"currdate": "24-04-2018", "hours": 8, "note": "www", "projectname": "Test3", "login": "admin"}'
        when: "The add is executed"
        int status = reportService.save(jsonObject)
        then: "The report hasn't been added "
        status == 415
    }

    void "Test to add report with the wrong convert json format "() {
        given:
        String jsonObject = '{"currdate: "24-04-2018", "hours": 8, "note": "www", "projectname": "Test3", "login": "admin"}'
        when: "The add is executed"
        int status = reportService.save(jsonObject)
        then: "The report hasn't been added "
        status == 415
    }


     void "Test to add report with the not approriate json format "() {
        given:
        String jsonObject = '{"currdate2": "24-04-2018", "hours": 8, "note": "www", "projectname": "Test3", "login": "admin"}'
        when: "The add is executed"
        int status = reportService.save(jsonObject)
        then: "The report hasn't been added "
        status == 415
    }
}
