import grails.converters.JSON
import org.codehaus.groovy.grails.web.converters.marshaller.xml.DomainClassMarshaller
import reports.Assigned
import reports.Employer
import reports.Project
import reports.Report

class BootStrap {

    def init = { servletContext ->
        JSON.registerObjectMarshaller(Report, {
            def output = [:]
            output["currdate"] =  it.currdate.format("dd.MM.yyyy")
            output["hours"] = it.hours
            output["note"] = it.note
            output["projectname"] = it.assigned.project.name
            output["login"] = it.assigned.employer.login
            return output
        })

        Project project1 = new Project(name: 'Test1')
        Project project2 = new Project(name: 'Test2')

        Employer admin = new Employer(login: 'admin', password: 'secret')
        Employer simpleuser = new Employer(login: 'user', password: '123456')

        Project.saveAll([project1, project2])
        Employer.saveAll([admin, simpleuser])
        Assigned.saveAll(new Assigned(project: project1, employer: admin),
                new Assigned(project: project2, employer: admin),
                new Assigned(project: project1, employer: simpleuser))

    }
    def destroy = {
    }
}
