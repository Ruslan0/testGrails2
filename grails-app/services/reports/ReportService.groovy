package reports

class ReportService {

    String getMessageFailedData(def reportInstance) {
        StringBuilder str = new StringBuilder()
        reportInstance.errors.allErrors.each {
            str.append(it)
        }
        return str.toString()
    }

    List<Project> getProjects(Integer max, Integer offset) {
        return Project.executeQuery("from Project", [offset: offset, max: max])
    }


    List<Report> getReports(Integer max, Integer offset) {
        return Report.executeQuery("from Report", [offset: offset, max: max])
    }

    Report getReport(int id) {
        Report report = Report.get(id)
        return report
    }

    List<Report> getReportByLogin(String login) {
        return Report.executeQuery("from Report r where assigned.employer.login = :login", [login: login])
    }

    List<Report> getReportByParams(String login, String projectName) {
        final String strquery = "from Report r where assigned.employer.login = :login and assigned.project.name = :projectName"
        return Report.executeQuery(strquery, [login: login, projectName: projectName])
    }

    boolean validateNewReport(Report reportInstance) {
        boolean result = reportInstance.validate();
        if (result) {
            def prevhours = Report.withCriteria {
                projections {
                    sum('hours')
                }
                and {
                    eq("currdate", reportInstance.currdate)
                }
                and {
                    eq("assigned", reportInstance.assigned)
                }

            }
            int allhours = reportInstance.hours
            if (prevhours[0])
                allhours += prevhours[0]
            if (allhours > 24) {
                result = false
                reportInstance.errors.reject('hours', 'The number of hours for this day can not exceed 24')
            }
        }
        return result
    }

    int add(Report reportInstance) {
        int status
        if (validateNewReport(reportInstance)) {
            if (reportInstance.save())
                status = 201
            else
                status = 409
        } else {
            status = 400
        }
        return status
    }
}
