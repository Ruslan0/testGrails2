import reports.Assigned
import reports.Employer
import reports.Project

class BootStrap {

    def init = { servletContext ->
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
