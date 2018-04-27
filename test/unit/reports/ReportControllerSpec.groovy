package reports

import grails.test.mixin.TestFor
import spock.lang.Specification

@TestFor(ReportController)
class ReportControllerSpec extends Specification {

    ReportService reportServiceMock = Mock(ReportService)

    Assigned assigned1
    Assigned assigned2
    Assigned assigned3

    def setup() {
        Project project1 = new Project(name: 'Project1')
        Project project2 = new Project(name: 'Project2')

        Employer user1 = new Employer(login: 'user1', password: 'secret')
        Employer user2 = new Employer(login: 'user2', password: '123456')

        assigned1 = new Assigned(project: project1, employer: user1)
        assigned2 = new Assigned(project: project2, employer: user1)
        assigned3 = new Assigned(project: project1, employer: user2)
        new Report(currdate: new Date(), note: "12211", hours: 8, assigned: assigned1)
        new Report(currdate: new Date(), note: "12211", hours: 8, assigned: assigned1)

        controller.reportService = reportServiceMock
    }


    void 'test add'() {
        when:
            controller.add(new Report(currdate: new Date(), note: "12211", hours: 25, assigned: assigned1))

        then:
            0 * reportServiceMock.save(new Report(currdate: new Date(), note: "12211", hours: 25, assigned: assigned1)) >> {400}
    }

    def cleanup() {
    }
}
